package com.temankasir.ui.pengaturan.profileakun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.temankasir.R;
import com.temankasir.contract.EditProfileOwnerContract;
import com.temankasir.contract.ProfileAkunContract;
import com.temankasir.contract.ProfileOwnerContract;
import com.temankasir.presenter.EditProfileOwnerPresenter;
import com.temankasir.presenter.ProfileAkunPresenter;
import com.temankasir.presenter.ProfileOwnerPresenter;
import com.temankasir.ui.home.MainActivity;
import com.temankasir.utils.CustomProgressDialog;

public class ProfileAkunActivity extends AppCompatActivity implements ProfileOwnerContract.profileOwnerView, EditProfileOwnerContract.editProfileOwnerView {

    ProfileOwnerContract.profileOwnerPresenter presenterProfileOwner;
    EditProfileOwnerContract.editProfileOwnerPresenter presenterEditProfileOwner;
    CustomProgressDialog customProgressDialog;
    String idOwner = "", passwordOwner = "", usernameOwner = "";
    TextInputEditText tietUsername, tietPassword, tietVerifikasiPasswrd;
    CardView cvProfileOwner, cvVerifikasiPassword;
    TextView tvUbahPassword;
    Dialog dialogPreview;
    Boolean statusEditPass = false;
    ImageView ivBackProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_akun);

        presenterProfileOwner = new ProfileOwnerPresenter(this);
        customProgressDialog = new CustomProgressDialog();
        presenterEditProfileOwner = new EditProfileOwnerPresenter(this);

        tietUsername = findViewById(R.id.tietUsernameEditProfile);
        tietPassword = findViewById(R.id.tietPasswordEditProfile);
        cvProfileOwner = findViewById(R.id.cvSimpanProfileOwner);
        tvUbahPassword = findViewById(R.id.tvUbahPasswordEditProfile);
        ivBackProfile = findViewById(R.id.ivProfileBackToPengaturan);

        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        idOwner = sharedPreferences.getString("idUser", "");
        Log.i("IDOWNER", " === " + idOwner);

        presenterProfileOwner.sendProfileOwner(idOwner);

        if (!statusEditPass) {
            Log.i("STATUSEDIT", " === " + statusEditPass);
            tietPassword.setEnabled(false);
        }

        cvProfileOwner.setOnClickListener(v -> {
            if (!checkUsername() || !checkPassword()) {
                Toast.makeText(this, "Cek data Anda!", Toast.LENGTH_LONG).show();
            } else {
                presenterEditProfileOwner.sendEditProfileOwner(idOwner, usernameOwner, passwordOwner);
            }
        });

        tvUbahPassword.setOnClickListener(v -> {
            openDialogVerifikasiPassword();
        });

        ivBackProfile.setOnClickListener(v -> {
            this.onBackPressed();
        });

    }

    public void openDialogVerifikasiPassword() {
        dialogPreview = new Dialog(this);
        dialogPreview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPreview.setContentView(R.layout.dialog_ubah_password);
        dialogPreview.setCancelable(true);
        Window window = dialogPreview.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        tietVerifikasiPasswrd = dialogPreview.findViewById(R.id.tietVerifikasiPassword);
        cvVerifikasiPassword = dialogPreview.findViewById(R.id.cvVerifikasiPassword);

        cvVerifikasiPassword.setOnClickListener(v -> {
            if (!tietVerifikasiPasswrd.getText().toString().equals(passwordOwner)) {
                Toast.makeText(this, "Pastikan password lama benar", Toast.LENGTH_SHORT).show();
            } else {
                tietPassword.setEnabled(true);
                dialogPreview.dismiss();
            }
        });

        dialogPreview.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment", "pengaturan");
        startActivity(intent);
    }

    @Override
    public void showLoadingProfileOwner() {
        customProgressDialog.show(this, "Harap menunggu...");
    }

    @Override
    public void hideLoadingProfileOwner() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastProfileOwner(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataProfileOwner(String username, String password) {
        tietUsername.setText(username);
        tietPassword.setText(password);

        passwordOwner = password;
    }

    @Override
    public void showLoadingEditProfileOwner() {
        customProgressDialog.show(this, "Harap menunggu...");
    }

    @Override
    public void hideLoadingEditProfileOwner() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastEditProfileOwner(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successEditProfileOwner() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment", "pengaturan");
        startActivity(intent);
    }

    public boolean checkUsername() {
        usernameOwner = tietUsername.getText().toString();

        if (usernameOwner.isEmpty()) {
            tietUsername.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }

    public boolean checkPassword() {
        passwordOwner = tietPassword.getText().toString();

        if (passwordOwner.isEmpty()) {
            tietPassword.setError("Tidak boleh kosong!");
            return false;
        }

        return true;
    }
}