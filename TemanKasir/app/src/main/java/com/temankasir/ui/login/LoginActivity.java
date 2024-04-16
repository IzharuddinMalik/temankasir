package com.temankasir.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.temankasir.R;
import com.temankasir.contract.LoginAkunContract;
import com.temankasir.presenter.LoginAkunPresenter;
import com.temankasir.ui.home.MainActivity;
import com.temankasir.ui.register.RegisterAkunActivity;
import com.temankasir.utils.CustomProgressDialog;

public class LoginActivity extends AppCompatActivity implements LoginAkunContract.loginAkunView {

    Button btnLogin;
    TextView tvRegister;
    LoginAkunPresenter presenterLogin;
    CustomProgressDialog customProgressDialog;
    String strUsername, strPassword;
    EditText edtUsername, edtPassword;
    String getToken = "", idOwner = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenterLogin = new LoginAkunPresenter(this);
        customProgressDialog = new CustomProgressDialog();

        btnLogin = findViewById(R.id.btnMasukSignin);
        tvRegister = findViewById(R.id.tvRegisterSignin);
        edtUsername = findViewById(R.id.edtUsernameSignin);
        edtPassword = findViewById(R.id.edtPasswordSignin);

        getToken = FirebaseMessaging.getInstance().getToken().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        idOwner = sharedPreferences.getString("idUser", "");

        if (idOwner != "") {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(v -> {
            if (!checkUsername() || !checkPassword()) {
                Toast.makeText(this, "Cek Data Anda!", Toast.LENGTH_LONG).show();
            } else {
                presenterLogin.loginAkun(strUsername, strPassword, getToken);
            }
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterAkunActivity.class));
        });

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void showLoadingLogin() {
        customProgressDialog.show(this, "Harap menunggu...");
    }

    @Override
    public void hideLoadingLogin() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastLogin(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successLogin(String idUser, String idBisnis, String statusUser, String idOutlet) {
        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idUser", idUser);
        editor.putString("idBisnis", idBisnis);
        editor.putString("statusUser", statusUser);
        editor.putString("idOutlet", idOutlet);
        editor.commit();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public boolean checkUsername(){
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
}