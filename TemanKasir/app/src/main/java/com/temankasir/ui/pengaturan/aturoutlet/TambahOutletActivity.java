package com.temankasir.ui.pengaturan.aturoutlet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.temankasir.R;
import com.temankasir.contract.GetKabupatenContract;
import com.temankasir.contract.GetKecamatanContract;
import com.temankasir.contract.GetProvinsiContract;
import com.temankasir.contract.OutletContract;
import com.temankasir.presenter.GetKabupatenPresenter;
import com.temankasir.presenter.GetKecamatanPresenter;
import com.temankasir.presenter.GetProvinsiPresenter;
import com.temankasir.presenter.OutletPresenter;
import com.temankasir.ui.home.MainActivity;
import com.temankasir.ui.pengaturan.bisnisakun.BisnisAkunActivity;
import com.temankasir.utils.CustomProgressDialog;

public class TambahOutletActivity extends AppCompatActivity implements OutletContract.outletView, GetProvinsiContract.getProvinsiView, GetKabupatenContract.getKabupatenView,
        GetKecamatanContract.getKecamatanView {

    ImageView ivBack;
    TextInputEditText tietNamaOutlet;
    Spinner spinProv, spinKabupaten, spinKecamatan;
    String idOwner = "", idBisnis = "", strNamaOutlet = "", strIdProv = "", strIdKab = "", strIdKecamatan = "", strIntentFrom = "", strIdOutlet = "";
    CardView cvSimpanOutlet;
    CustomProgressDialog customProgressDialog;
    private OutletPresenter presenterOutlet;
    private GetProvinsiPresenter presenterProvinsi;
    private GetKabupatenPresenter presenterKabupaten;
    private GetKecamatanPresenter presenterKecamatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_outlet);

        presenterOutlet = new OutletPresenter(this);
        presenterProvinsi = new GetProvinsiPresenter(this);
        presenterKabupaten = new GetKabupatenPresenter(this);
        presenterKecamatan = new GetKecamatanPresenter(this);
        customProgressDialog = new CustomProgressDialog();

        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        idOwner = sharedPreferences.getString("idUser", "");
        idBisnis = sharedPreferences.getString("idBisnis", "");

        presenterProvinsi.sendGetProvinsi(idOwner);

        ivBack = findViewById(R.id.ivOutletBackToPengaturan);
        tietNamaOutlet = findViewById(R.id.tietNamaOutletTambahOutlet);
        spinProv = findViewById(R.id.spinProvinsiTambahOutlet);
        spinKabupaten = findViewById(R.id.spinKabupatenTambahOutlet);
        spinKecamatan = findViewById(R.id.spinKecamatanTambahOutlet);
        cvSimpanOutlet = findViewById(R.id.cvSimpanTambahOutlet);

        strIntentFrom = getIntent().getStringExtra("from");

        if (strIntentFrom == "edit") {
            strIdOutlet = getIntent().getStringExtra("idOutlet");
            strNamaOutlet = getIntent().getStringExtra("namaOutlet");
            strIdProv = getIntent().getStringExtra("idProvinsi");
            strIdKab = getIntent().getStringExtra("idKabupaten");
            strIdKecamatan = getIntent().getStringExtra("idKecamatan");

            tietNamaOutlet.setText(strNamaOutlet);

            cvSimpanOutlet.setOnClickListener(v -> {
                simpanOutlet("2");
            });
        } else {
            cvSimpanOutlet.setOnClickListener(v -> {
                simpanOutlet("1");
            });

            presenterOutlet.onBuatOutlet().observe(this, aBoolean -> {
                if (aBoolean) {
                    startActivity(new Intent(this, AturOutletActivity.class));
                }
            });
        }
    }

    public void simpanOutlet(String from) {
        if (from == "2") {
            if (!checkNamaOutlet()) {
                Toast.makeText(this, "Harap cek kembali outlet Anda", Toast.LENGTH_LONG).show();
            } else {
                presenterOutlet.editOutlet(idOwner, idBisnis, strNamaOutlet, strIdProv, strIdKab, strIdKecamatan, strIdOutlet);
            }
        } else {
            if (!checkNamaOutlet()) {
                Toast.makeText(this, "Harap cek kembali outlet Anda", Toast.LENGTH_LONG).show();
            } else {
                presenterOutlet.buatOutlet(idOwner, idBisnis, strNamaOutlet, strIdProv, strIdKab, strIdKecamatan);
            }
        }
    }

    @Override
    public void showLoadingOutlet() {
        customProgressDialog.show(this, "Buat outlet sedang diproses...");
    }

    @Override
    public void hideLoadingOutlet() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastOutlet(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public boolean checkNamaOutlet() {
        strNamaOutlet = tietNamaOutlet.getText().toString();

        if (strNamaOutlet.isEmpty()) {
            Toast.makeText(this, "Nama Outlet tidak boleh kosong", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void getDataKabupaten(String[] idKabupaten, String[] namaKabupaten) {
        ArrayAdapter adapter = new ArrayAdapter(TambahOutletActivity.this, R.layout.list_item, namaKabupaten);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKabupaten.setAdapter(adapter);

        strIntentFrom = getIntent().getStringExtra("from");
        if (strIntentFrom == "edit") {
            if (!strIdKab.isEmpty()) {
                for (int i = 0; i < idKabupaten.length;i++) {
                    if (idKabupaten[i].equals(strIdKab)) {
                        spinKabupaten.setSelection(i);
                        strIdKab = idKabupaten[i];
                    }
                }
            }
        }

        spinKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                strIdKab = idKabupaten[position];
                Log.i("IDKAB", " === " + strIdKab);
                presenterKecamatan.sendGetKecamatan("0",strIdKab);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void getDataKecamatan(String[] idKecamatan, String[] namaKecamatan) {
        ArrayAdapter adapter = new ArrayAdapter(TambahOutletActivity.this, R.layout.list_item, namaKecamatan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKecamatan.setAdapter(adapter);

        strIntentFrom = getIntent().getStringExtra("from");
        if (strIntentFrom == "edit") {
            if (!strIdKecamatan.isEmpty()) {
                for(int i = 0; i < idKecamatan.length; i++){
                    if (idKecamatan[i].equals(strIdKecamatan)) {
                        spinKecamatan.setSelection(i);
                        strIdKecamatan = idKecamatan[i];
                    }
                }
            }
        }

        spinKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                strIdKecamatan = idKecamatan[position];
                Log.i("IDKECAMATAN", " === " + strIdKecamatan);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void getDataProvinsi(String[] idProvinsi, String[] namaProvinsi) {
        ArrayAdapter adapter = new ArrayAdapter(TambahOutletActivity.this, R.layout.list_item, namaProvinsi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinProv.setAdapter(adapter);

        strIntentFrom = getIntent().getStringExtra("from");
        if (strIntentFrom == "edit") {
            if (!strIdProv.isEmpty()) {
                for (int i = 0; i < idProvinsi.length;i++) {
                    if (idProvinsi[i].equals(strIdProv)) {
                        spinProv.setSelection(i);
                        strIdProv = idProvinsi[i];
                    }
                }
            }
        }

        spinProv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                strIdProv = idProvinsi[position];
                presenterKabupaten.sendGetKabupaten("0",strIdProv);
                Log.i("IDPROV", " === " + strIdProv);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}