package com.temankasir.ui.pengaturan.aturitem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.temankasir.R;
import com.temankasir.contract.ItemContract;
import com.temankasir.contract.OutletContract;
import com.temankasir.presenter.ItemPresenter;
import com.temankasir.presenter.OutletPresenter;
import com.temankasir.ui.pengaturan.aturitem.model.KategoriItemModel;
import com.temankasir.ui.pengaturan.aturkasir.TambahKasirActivity;
import com.temankasir.utils.CustomProgressDialog;

public class TambahKategoriItemActivity extends AppCompatActivity implements ItemContract.itemView, OutletContract.outletView {

    CardView cvSimpanKategoriItem, cvHapusKategori;
    private ItemPresenter itemPresenter;
    private CustomProgressDialog customProgressDialog;
    private String strNamaKategoriItem = "", strIdOutlet = "", idOwner = "", idBisnis = "", strIntentFrom = "";
    private TextInputEditText tietNamaKategori;
    private Spinner spinOutlet;
    private KategoriItemModel kategoriItemModel;
    Dialog dialogPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kategori_item);

        customProgressDialog = new CustomProgressDialog();
        itemPresenter = new ItemPresenter(this);

        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        idOwner = sharedPreferences.getString("idUser", "");
        idBisnis = sharedPreferences.getString("idBisnis", "");
        itemPresenter.getListOutlet(idOwner, idBisnis);

        cvSimpanKategoriItem = findViewById(R.id.cvSimpanKategoriItem);
        tietNamaKategori = findViewById(R.id.tietNamaKategoriItemTambahKategoriItem);
        spinOutlet = findViewById(R.id.spinOutletTambahKategoriItem);
        cvHapusKategori = findViewById(R.id.cvHapusKategoriItem);

        strIntentFrom = getIntent().getStringExtra("from");

        if (strIntentFrom.equals("add")) {
            cvSimpanKategoriItem.setOnClickListener(v -> {
                if (!checkNamaKategoriItem()){

                } else {
                    itemPresenter.addKategoriItem(idBisnis, strIdOutlet, strNamaKategoriItem);
                }
            });
        } else {
            kategoriItemModel = getIntent().getParcelableExtra("data");
            strIdOutlet = kategoriItemModel.getIdOutletKategori();
            strNamaKategoriItem = kategoriItemModel.getNamaKategori();

            tietNamaKategori.setText(kategoriItemModel.getNamaKategori());

            cvSimpanKategoriItem.setOnClickListener(v -> {
                if (!checkNamaKategoriItem()) {

                } else {
                    itemPresenter.editKategoriItem(kategoriItemModel.getIdKategori(), strIdOutlet, strNamaKategoriItem);
                }
            });
        }

        cvHapusKategori.setOnClickListener(view -> {
            dialogHapusKategori();
        });

        initLiveData();
    }

    public void initLiveData() {
        itemPresenter.listOutlet().observe(this, outletModels -> {
            String[] idOutlet = new String[outletModels.size()+1];
            String[] namaOutlet = new String[outletModels.size()+1];

            idOutlet[0] = "0";
            namaOutlet[0] = "Silahkan pilih outlet";

            for (int i=0;i<outletModels.size();i++) {
                idOutlet[i+1] = outletModels.get(i).getIdOutlet();
                namaOutlet[i+1] = outletModels.get(i).getNamaOutlet();
            }

            ArrayAdapter adapter = new ArrayAdapter(TambahKategoriItemActivity.this, R.layout.list_item, namaOutlet);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinOutlet.setAdapter(adapter);

            strIntentFrom = getIntent().getStringExtra("from");
            if (strIntentFrom.equals("edit")) {
                if (!strIdOutlet.isEmpty()) {
                    for (int i = 0; i < idOutlet.length;i++) {
                        if (idOutlet[i].equals(strIdOutlet)) {
                            spinOutlet.setSelection(i);
                            strIdOutlet = idOutlet[i];
                        }
                    }
                }
            }

            spinOutlet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                    strIdOutlet = idOutlet[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });

        itemPresenter.onAddKategoriItem().observe(this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(this, AturItemActivity.class);
                intent.putExtra("fragment", "kategoriitem");
                startActivity(intent);
            }
        });

        itemPresenter.onEditKategoriItem().observe(this, aBoolean -> {
            if (aBoolean){
                Intent intent = new Intent(this, AturItemActivity.class);
                intent.putExtra("fragment", "kategoriitem");
                startActivity(intent);
            }
        });

        itemPresenter.onHapusKategoriItem().observe(this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(this, AturItemActivity.class);
                intent.putExtra("fragment", "kategoriitem");
                startActivity(intent);
            }
        });
    }

    public boolean checkNamaKategoriItem() {
        strNamaKategoriItem = tietNamaKategori.getText().toString();

        if (strNamaKategoriItem.isEmpty()) {
            Toast.makeText(this, "Tidak boleh kosong!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AturItemActivity.class);
        intent.putExtra("fragment", "kategoriitem");
        startActivity(intent);
    }

    @Override
    public void showLoadingItem() {
        customProgressDialog.show(this, getString(R.string.title_loading));
    }

    @Override
    public void hideLoadingItem() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastItem(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingOutlet() {
        customProgressDialog.show(this, getString(R.string.title_loading));
    }

    @Override
    public void hideLoadingOutlet() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastOutlet(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void dialogHapusKategori() {
        dialogPreview = new Dialog(this);
        dialogPreview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPreview.setContentView(R.layout.dialog_hapus_kategori_item);
        dialogPreview.setCancelable(true);
        Window window = dialogPreview.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        CardView cvHapus, cvBatal;
        cvHapus = dialogPreview.findViewById(R.id.cvYaHapusKategoriItem);
        cvBatal = dialogPreview.findViewById(R.id.cvBatalHapusKategoriItem);

        cvHapus.setOnClickListener(view -> {
            itemPresenter.hapusKategoriItem(kategoriItemModel.getIdKategori(), kategoriItemModel.getIdOutletKategori());
            dialogPreview.dismiss();
        });

        cvBatal.setOnClickListener(view -> {
            dialogPreview.dismiss();
        });

        dialogPreview.show();
    }
}