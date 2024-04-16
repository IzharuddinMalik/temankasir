package com.temankasir.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.temankasir.R;
import com.temankasir.api.APIConnect;
import com.temankasir.contract.CekEmailContract;
import com.temankasir.contract.GetKabupatenContract;
import com.temankasir.contract.GetKecamatanContract;
import com.temankasir.contract.GetProvinsiContract;
import com.temankasir.contract.RegisterAkunContract;
import com.temankasir.presenter.CekEmailPresenter;
import com.temankasir.presenter.GetKabupatenPresenter;
import com.temankasir.presenter.GetKecamatanPresenter;
import com.temankasir.presenter.GetProvinsiPresenter;
import com.temankasir.presenter.RegisterAkunPresenter;
import com.temankasir.ui.home.MainActivity;
import com.temankasir.ui.login.LoginActivity;
import com.temankasir.ui.register.model.KabupatenModel;
import com.temankasir.ui.register.model.KecamatanModel;
import com.temankasir.ui.register.model.ProvinsiModel;
import com.temankasir.ui.register.model.RegisterBusinessPostModel;
import com.temankasir.ui.register.model.RegisterPostModel;
import com.temankasir.ui.register.model.RegisterUserPostModel;
import com.temankasir.utils.CustomProgressDialog;
import com.temankasir.utils.ToastUtils;

import java.util.List;

import okhttp3.RequestBody;

public class RegisterAkunActivity extends AppCompatActivity implements RegisterAkunContract.registerAkunView, GetProvinsiContract.getProvinsiView,
        GetKabupatenContract.getKabupatenView, GetKecamatanContract.getKecamatanView, CekEmailContract.cekEmailView {

    CustomProgressDialog customProgressDialog;
    RegisterAkunPresenter presenter;
    GetProvinsiPresenter presenterProv;
    GetKabupatenPresenter presenterKab;
    GetKecamatanPresenter presenterKec;
    CekEmailPresenter presenterCekEmail;
    String strIdProvinsi, strIdKabupaten, strIdKecamatan;
    Spinner spinProvinsi, spinKabupaten, spinKecamatan;
    EditText edtNamaLengkap, edtUsername, edtPassword, edtEmailBisnis, edtNoTeleponBisnis, edtNamaBisnis;
    Button btnRegister, btnLanjutInformasiBisnis;
    LinearLayout llInformasiPribadi, llInformasiBisnis;
    TextView tvSudahPunyaAkun;

    String strNamaLengkap, strUsername, strPassword, strEmailBisnis, strNoTelpBisnis, strNamaBisnis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_akun);

        presenter = new RegisterAkunPresenter(this);
        presenterProv = new GetProvinsiPresenter(this);
        presenterKab = new GetKabupatenPresenter(this);
        presenterKec = new GetKecamatanPresenter(this);
        presenterCekEmail = new CekEmailPresenter(this);
        customProgressDialog = new CustomProgressDialog();

        spinProvinsi = findViewById(R.id.spinProvinsiSignup);
        spinKabupaten = findViewById(R.id.spinKabupatenSignup);
        spinKecamatan = findViewById(R.id.spinKecamatanSignup);

        edtNamaLengkap = findViewById(R.id.edtNamaLengkapSignup);
        edtUsername = findViewById(R.id.edtUsernameSignup);
        edtPassword = findViewById(R.id.edtPasswordSignup);
        edtEmailBisnis = findViewById(R.id.edtEmailBisnisSignup);
        edtNoTeleponBisnis = findViewById(R.id.edtNoTelpBisnisSignup);
        edtNamaBisnis = findViewById(R.id.edtNamabisnisSignup);
        btnRegister = findViewById(R.id.btnSimpanSignup);
        btnLanjutInformasiBisnis = findViewById(R.id.btnLanjutInformasiBisnisSignup);
        llInformasiPribadi = findViewById(R.id.llInformasiPribadiSignup);
        llInformasiBisnis = findViewById(R.id.llInformasiBisnisSignup);
        tvSudahPunyaAkun = findViewById(R.id.tvSudahPunyaAkunSignup);

        tvSudahPunyaAkun.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        presenterProv.sendGetProvinsi("0");

        edtEmailBisnis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenterCekEmail.sendCekEmail(s.toString());
            }
        });

        btnLanjutInformasiBisnis.setOnClickListener(v -> {
            if (!checkNamaLengkap() || !checkUsername() || !checkPassword()) {
                llInformasiBisnis.setVisibility(View.GONE);
                Toast.makeText(this, "Harap pastikan data terisi dengan benar", Toast.LENGTH_LONG).show();
            } else {
                llInformasiBisnis.setVisibility(View.VISIBLE);
                llInformasiPribadi.setVisibility(View.GONE);
            }
        });

        btnRegister.setOnClickListener(v -> {
            if (!checkEmailBisnis() || !checkNoTeleponBisnis() || !checkNamaBisnis() || !checkUsername() || !checkPassword() || !checkNamaLengkap()) {
                Toast.makeText(this, "Cek Data Anda!", Toast.LENGTH_LONG).show();
            } else {
                presenter.sendRegister(strNamaLengkap, strUsername, strPassword, strEmailBisnis, strNamaBisnis, strNoTelpBisnis, strIdProvinsi, strIdKabupaten, strIdKecamatan);
            }
        });

        presenter.onStatusRegister().observe(this, aBoolean -> {
            if (aBoolean) {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
    }

    @Override
    public void showLoadingRegister() {
        customProgressDialog.show(this, "Harap menunggu...");
    }

    @Override
    public void hideLoadingRegister() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastRegister(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataKabupaten(String[] idKabupaten, String[] namaKabupaten) {
        ArrayAdapter adapter = new ArrayAdapter(RegisterAkunActivity.this, R.layout.list_item, namaKabupaten);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKabupaten.setAdapter(adapter);

        spinKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                strIdKabupaten = idKabupaten[position];
                Log.i("IDKAB", " === " + strIdKabupaten);
                presenterKec.sendGetKecamatan("0", strIdKabupaten);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void getDataKecamatan(String[] idKecamatan, String[] namaKecamatan) {
        ArrayAdapter adapter = new ArrayAdapter(RegisterAkunActivity.this, R.layout.list_item, namaKecamatan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKecamatan.setAdapter(adapter);

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
        ArrayAdapter adapter = new ArrayAdapter(RegisterAkunActivity.this, R.layout.list_item, namaProvinsi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinProvinsi.setAdapter(adapter);

        spinProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                strIdProvinsi = idProvinsi[position];
                presenterKab.sendGetKabupaten("0", strIdProvinsi);
                Log.i("IDPROV", " === " + strIdProvinsi);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public boolean checkNamaLengkap() {

        strNamaLengkap = edtNamaLengkap.getText().toString();

        if (strNamaLengkap.isEmpty()) {
            edtNamaLengkap.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }

    public boolean checkUsername() {

        strUsername = edtUsername.getText().toString();

        if (strUsername.isEmpty()) {
            edtUsername.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }

    public boolean checkPassword(){

        strPassword = edtPassword.getText().toString();

        if (strPassword.isEmpty()) {
            edtPassword.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }

    public boolean checkEmailBisnis(){

        strEmailBisnis = edtEmailBisnis.getText().toString();

        if (strEmailBisnis.isEmpty()) {
            edtEmailBisnis.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }

    public boolean checkNoTeleponBisnis(){

        strNoTelpBisnis = edtNoTeleponBisnis.getText().toString();

        if (strNoTelpBisnis.isEmpty()) {
            edtNoTeleponBisnis.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }

    public boolean checkNamaBisnis(){

        strNamaBisnis = edtNamaBisnis.getText().toString();

        if (strNamaBisnis.isEmpty()) {
            edtNamaBisnis.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }

    @Override
    public void showToastCekEmail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successCekEmail(String success) {
        if (success.equals("0")) {
            btnRegister.setEnabled(false);
            edtEmailBisnis.setError("Email sudah digunakan");
        } else {
            btnRegister.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}