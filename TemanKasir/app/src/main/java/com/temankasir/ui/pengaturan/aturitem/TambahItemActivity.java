package com.temankasir.ui.pengaturan.aturitem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.temankasir.R;
import com.temankasir.contract.ItemContract;
import com.temankasir.presenter.ItemPresenter;
import com.temankasir.ui.pengaturan.aturitem.model.ItemModel;
import com.temankasir.utils.CustomProgressDialog;

public class TambahItemActivity extends AppCompatActivity implements ItemContract.itemView {

    CardView cvSimpanItem, cvHapusItem;
    Spinner spinOutlet, spinKategoriItem;
    TextInputEditText tietNamaItem, tietHargaItem, tietStokItem;
    ImageView ivBack;

    private ItemPresenter itemPresenter;
    private String strIntentFrom = "", strIdOutlet = "", strIdKategori = "", strNamaItem = "", strHargaItem = "", strStokItem = "";
    private CustomProgressDialog customProgressDialog;
    private ItemModel itemModel;
    Dialog dialogPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_item);

        customProgressDialog = new CustomProgressDialog();

        cvSimpanItem = findViewById(R.id.cvSimpanItem);
        spinOutlet = findViewById(R.id.spinOutletTambahItem);
        spinKategoriItem = findViewById(R.id.spinKategoriItemTambahItem);
        tietNamaItem = findViewById(R.id.tietNamaItemTambahItem);
        tietHargaItem = findViewById(R.id.tietHargaItemTambahItem);
        tietStokItem = findViewById(R.id.tietJumlahStokItemTambahItem);
        ivBack = findViewById(R.id.ivTambahItemBackToAturItem);
        cvHapusItem = findViewById(R.id.cvHapusItem);

        strIntentFrom = getIntent().getStringExtra("from");

        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        String idOwner = sharedPreferences.getString("idUser", "");
        String idBisnis = sharedPreferences.getString("idBisnis", "");

        itemPresenter = new ItemPresenter(this);
        itemPresenter.getListOutlet(idOwner, idBisnis);
        itemPresenter.getListKategoriItem(idBisnis);

        if (strIntentFrom.equals("edit")) {
            itemModel = getIntent().getParcelableExtra("data");
            strIdOutlet = itemModel.getIdOutletItem();
            cvHapusItem.setVisibility(View.VISIBLE);

            tietNamaItem.setText(itemModel.getNamaItem());
            tietHargaItem.setText(itemModel.getHargaItem());
            tietStokItem.setText(itemModel.getStokItem());

            cvSimpanItem.setOnClickListener(v -> {
                if (!checkNamaItem() || !checkHargaItem() || !checkStokItem()) {

                } else {
                    itemPresenter.editItem(itemModel.getIdItem(), strIdOutlet, strIdKategori, tietNamaItem.getText().toString(), tietHargaItem.getText().toString(), tietStokItem.getText().toString());
                }
            });
        } else {
            cvHapusItem.setVisibility(View.GONE);
            cvSimpanItem.setOnClickListener(v -> {
                if (!checkNamaItem() || !checkHargaItem() || !checkStokItem()) {

                } else {
                    itemPresenter.addItem(idBisnis, strIdOutlet, strIdKategori, strNamaItem, strHargaItem, strStokItem);
                }
            });
        }

        initLiveData();

        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, AturItemActivity.class);
            intent.putExtra("fragment", "item");
            startActivity(intent);
        });

        cvHapusItem.setOnClickListener(view -> {
            dialogHapus();
        });
    }

    public void initLiveData() {
        itemPresenter.onGetListKategoriItem().observe(this, kategoriItemModels -> {
            String[] idKategori = new String[kategoriItemModels.size()+1];
            String[] namaKategori = new String[kategoriItemModels.size()+1];

            idKategori[0] = "0";
            namaKategori[0] = "Silahkan pilih kategori";

            for (int i=0;i<kategoriItemModels.size();i++) {
                idKategori[i+1] = kategoriItemModels.get(i).getIdKategori();
                namaKategori[i+1] = kategoriItemModels.get(i).getNamaKategori();
            }

            ArrayAdapter adapter = new ArrayAdapter(TambahItemActivity.this, R.layout.list_item, namaKategori);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinKategoriItem.setAdapter(adapter);

            strIntentFrom = getIntent().getStringExtra("from");
            if (strIntentFrom.equals("edit")) {
                if (!strIdKategori.isEmpty()) {
                    for (int i = 0; i < idKategori.length;i++) {
                        if (idKategori[i].equals(strIdKategori)) {
                            spinKategoriItem.setSelection(i);
                            strIdKategori = idKategori[i];
                        }
                    }
                }
            }

            spinKategoriItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                    strIdKategori = idKategori[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });

        itemPresenter.listOutlet().observe(this, outletModels -> {
            String[] idOutlet = new String[outletModels.size()+1];
            String[] namaOutlet = new String[outletModels.size()+1];

            idOutlet[0] = "0";
            namaOutlet[0] = "Silahkan pilih outlet";

            for (int i=0;i<outletModels.size();i++) {
                idOutlet[i+1] = outletModels.get(i).getIdOutlet();
                namaOutlet[i+1] = outletModels.get(i).getNamaOutlet();
            }

            ArrayAdapter adapter = new ArrayAdapter(TambahItemActivity.this, R.layout.list_item, namaOutlet);
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

        itemPresenter.onAddItem().observe(this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(this, AturItemActivity.class);
                intent.putExtra("fragment", "item");
                startActivity(intent);
            }
        });

        itemPresenter.onEditItem().observe(this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(this, AturItemActivity.class);
                intent.putExtra("fragment", "item");
                startActivity(intent);
            }
        });

        itemPresenter.onHapusItem().observe(this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(this, AturItemActivity.class);
                intent.putExtra("fragment", "item");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AturItemActivity.class);
        intent.putExtra("fragment", "item");
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

    public void dialogHapus(){
        dialogPreview = new Dialog(this);
        dialogPreview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPreview.setContentView(R.layout.dialog_hapus_item);
        dialogPreview.setCancelable(true);
        Window window = dialogPreview.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        CardView cvHapus, cvBatal;
        cvHapus = dialogPreview.findViewById(R.id.cvYaHapusItem);
        cvBatal = dialogPreview.findViewById(R.id.cvBatalHapusItem);

        cvHapus.setOnClickListener(view -> {
            itemPresenter.hapusItem(itemModel.getIdItem(), itemModel.getIdOutletItem());
            dialogPreview.dismiss();
        });

        cvBatal.setOnClickListener(view -> {
            dialogPreview.dismiss();
        });

        dialogPreview.show();
    }

    public boolean checkNamaItem() {
        strNamaItem = tietNamaItem.getText().toString();

        if (strNamaItem.isEmpty()){
            tietNamaItem.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }

    public boolean checkHargaItem() {
        strHargaItem = tietHargaItem.getText().toString();

        if (strHargaItem.isEmpty()) {
            tietHargaItem.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }

    public boolean checkStokItem() {
        strStokItem = tietStokItem.getText().toString();

        if (strStokItem.isEmpty()) {
            tietStokItem.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }
}