package com.temankasir.ui.pengaturan.aturkasir;

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
import com.temankasir.contract.KasirContract;
import com.temankasir.presenter.KasirPresenter;
import com.temankasir.ui.pengaturan.aturoutlet.TambahOutletActivity;
import com.temankasir.utils.CustomProgressDialog;

import org.json.JSONObject;

public class TambahKasirActivity extends AppCompatActivity implements KasirContract.kasirView {

    KasirPresenter kasirPresenter;
    CustomProgressDialog customProgressDialog;
    CardView cvTambahKasir;
    TextInputEditText tietUsername, tietPassword;
    Spinner spinOutlet;
    ImageView ivBack;
    String idOwner = "", idBisnis = "", idKasir = "", namaKasir = "", passwordKasir = "", strIdOutlet = "", strIntentFrom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kasir);

        strIntentFrom = getIntent().getStringExtra("from");

        kasirPresenter = new KasirPresenter(this);
        customProgressDialog = new CustomProgressDialog();

        cvTambahKasir = findViewById(R.id.cvSimpanTambahKasir);
        spinOutlet = findViewById(R.id.spinOutletTambahKasir);
        tietUsername = findViewById(R.id.tietUsernameTambahKasir);
        tietPassword = findViewById(R.id.tietPasswordTambahKasir);
        ivBack = findViewById(R.id.ivTambahKasirBackToPengaturan);

        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        idOwner = sharedPreferences.getString("idUser", "");
        idBisnis = sharedPreferences.getString("idBisnis", "");

        Log.i("FROM", " === " + strIntentFrom);

        if (strIntentFrom.equals("edit")) {
            strIdOutlet = getIntent().getStringExtra("idOutlet");
            idKasir = getIntent().getStringExtra("idKasir");
            namaKasir = getIntent().getStringExtra("namaKasir");
            passwordKasir = getIntent().getStringExtra("passwordKasir");

            Log.i("NAMAKASIR", " === " + namaKasir);

            tietUsername.setText(namaKasir);
            tietPassword.setText(passwordKasir);

            cvTambahKasir.setOnClickListener(v -> {
                if (!checkNamaKasir() || !checkPassword()) {
                    kasirPresenter.editKasir(idOwner, idBisnis, strIdOutlet, tietUsername.getText().toString(), tietPassword.getText().toString(), idKasir);
                } else {
                    kasirPresenter.editKasir(idOwner, idBisnis, strIdOutlet, namaKasir, passwordKasir, idKasir);
                }
            });
        } else {
            cvTambahKasir.setOnClickListener(v -> {
                if (!checkNamaKasir() || !checkPassword()) {
                    Toast.makeText(this, "Cek data Anda!", Toast.LENGTH_LONG).show();
                } else {
                    kasirPresenter.addKasir(idOwner, idBisnis, strIdOutlet, namaKasir, passwordKasir);
                }
            });
        }

        kasirPresenter.getListOutlet(idOwner, idBisnis);

        kasirPresenter.statusEditKasir().observe(this, aBoolean -> {
            startActivity(new Intent(this, AturKasirActivity.class));
        });

        kasirPresenter.statusAddKasir().observe(this, aBoolean -> {
            startActivity(new Intent(this, AturKasirActivity.class));
        });

        ivBack.setOnClickListener(v -> {
            startActivity(new Intent(this, AturKasirActivity.class));
        });

        initLive();
    }

    public void initLive() {
        kasirPresenter.listOutlet().observe(this, outletModels -> {
            String[] idOutlet = new String[outletModels.size()+1];
            String[] namaOutlet = new String[outletModels.size()+1];

            idOutlet[0] = "0";
            namaOutlet[0] = "Silahkan pilih outlet";

            for (int i=0;i<outletModels.size();i++) {
                idOutlet[i+1] = outletModels.get(i).getIdOutlet();
                namaOutlet[i+1] = outletModels.get(i).getNamaOutlet();
            }

            ArrayAdapter adapter = new ArrayAdapter(TambahKasirActivity.this, R.layout.list_item, namaOutlet);
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
                    Log.i("IDOUTLET", " === " + strIdOutlet);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });
    }

    @Override
    public void showLoadingKasir() {
        customProgressDialog.show(this, "Harap menunggu...");
    }

    @Override
    public void hideLoadingKasir() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastKasir(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public boolean checkNamaKasir() {
        namaKasir = tietUsername.getText().toString();

        if (namaKasir.isEmpty()) {
            Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public boolean checkPassword() {
        passwordKasir = tietPassword.getText().toString();

        if (passwordKasir.isEmpty()) {
            Toast.makeText(this, "Password tidak boleh kosong!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}