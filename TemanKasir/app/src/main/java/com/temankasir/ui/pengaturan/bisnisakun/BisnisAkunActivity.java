package com.temankasir.ui.pengaturan.bisnisakun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.temankasir.R;
import com.temankasir.contract.GetKabupatenContract;
import com.temankasir.contract.GetKecamatanContract;
import com.temankasir.contract.GetProvinsiContract;
import com.temankasir.contract.ProfileBisnisContract;
import com.temankasir.presenter.GetKabupatenPresenter;
import com.temankasir.presenter.GetKecamatanPresenter;
import com.temankasir.presenter.GetProvinsiPresenter;
import com.temankasir.presenter.ProfileBisnisPresenter;
import com.temankasir.ui.home.MainActivity;
import com.temankasir.ui.register.RegisterAkunActivity;
import com.temankasir.utils.CustomProgressDialog;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class BisnisAkunActivity extends AppCompatActivity implements ProfileBisnisContract.profileBisnisView, GetProvinsiContract.getProvinsiView, GetKabupatenContract.getKabupatenView,
        GetKecamatanContract.getKecamatanView {

    ProfileBisnisPresenter presenterProfileBisnis;
    GetProvinsiPresenter presenterProvinsi;
    GetKabupatenPresenter presenterKabupaten;
    GetKecamatanPresenter presenterKecamatan;
    CustomProgressDialog customProgressDialog;
    String namaBisnis = "", noTeleponBisnis = "", strIdProvinsi = "", strIdKabupaten = "", strIdKecamatan = "", idOwner = "", idBisnis = "", foto = "";
    TextInputEditText tietNamaBisnis, tietNoTelpon, tietEmailBisnis;
    Spinner spinProvinsi, spinKabupaten, spinKecamatan;
    CardView cvSimpanProfileBisnis;
    ImageView ivBackBisnis;
    TextView tvUpdateFoto;
    LinearLayout llOpenCamera, llOpenGallery;
    CircleImageView civFotoBisnis;

    private static final int CAMERA_REQUEST = 0;
    private static int LOAD_IMAGE_RESULTS = 1;
    Bitmap img;
    Dialog dialogPreview;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bisnis_akun);

        presenterProfileBisnis = new ProfileBisnisPresenter(this);
        presenterProvinsi = new GetProvinsiPresenter(this);
        presenterKabupaten = new GetKabupatenPresenter(this);
        presenterKecamatan = new GetKecamatanPresenter(this);
        customProgressDialog = new CustomProgressDialog();

        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        idOwner = sharedPreferences.getString("idUser", "");
        idBisnis = sharedPreferences.getString("idBisnis", "");
        Log.i("IDOWNER", " === " + idOwner);

        tietEmailBisnis = findViewById(R.id.tietEmailEditBisnis);
        tietNamaBisnis = findViewById(R.id.tietNamaBisnisEditBisnis);
        tietNoTelpon = findViewById(R.id.tietNoTeleponEditBisnis);
        spinProvinsi = findViewById(R.id.spinProvinsiEditBisnis);
        spinKabupaten = findViewById(R.id.spinKabupatenEditBisnis);
        spinKecamatan = findViewById(R.id.spinKecamatanEditBisnis);
        cvSimpanProfileBisnis = findViewById(R.id.cvSimpanTambahBisnis);
        ivBackBisnis = findViewById(R.id.ivBisnisBackToPengaturan);
        tvUpdateFoto = findViewById(R.id.tvUpdateFotoEditBisnis);
        civFotoBisnis = findViewById(R.id.civPhotoBisnisEditBisnis);

        presenterProfileBisnis.sendGetProfileBisnis(idOwner, idBisnis);
        presenterProvinsi.sendGetProvinsi(idOwner);

        ivBackBisnis.setOnClickListener(v -> {
            this.onBackPressed();
        });

        cvSimpanProfileBisnis.setOnClickListener(v -> {
            if (!checkNamaBisnis() || !checkNoTelpon()) {
                Toast.makeText(this, "Cek kembali data Anda", Toast.LENGTH_LONG).show();
            } else {
                foto = convertImageToStringForServer(img);
                presenterProfileBisnis.editBisnis(idOwner, idBisnis, namaBisnis, noTeleponBisnis, strIdProvinsi, strIdKabupaten, idBisnis, foto);
            }
        });

        tvUpdateFoto.setOnClickListener(v -> {
            openDialogFoto();
        });

        presenterProfileBisnis.onStatusEdit().observe(this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("fragment", "pengaturan");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment", "pengaturan");
        startActivity(intent);
    }

    @Override
    public void showLoadingProfileBisnis() {
        customProgressDialog.show(this, "Harap menunggu...");
    }

    @Override
    public void hideLoadingProfileBisnis() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastProfileBisnis(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataBisnis(String emailBisnis, String namaBisnis, String noTeleponBisnis, String idProvinsi, String idKabupaten, String idKecamatan, String logoBisnis) {
        tietEmailBisnis.setText(emailBisnis);
        tietNamaBisnis.setText(namaBisnis);
        tietNoTelpon.setText(noTeleponBisnis);

        Picasso.get().load(logoBisnis).into(civFotoBisnis);

        strIdProvinsi = idProvinsi;
        strIdKabupaten = idKabupaten;
        strIdKecamatan = idKecamatan;
    }

    @Override
    public void getDataKabupaten(String[] idKabupaten, String[] namaKabupaten) {
        ArrayAdapter adapter = new ArrayAdapter(BisnisAkunActivity.this, R.layout.list_item, namaKabupaten);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKabupaten.setAdapter(adapter);

        if (!strIdKabupaten.isEmpty()) {
            for (int i = 0; i < idKabupaten.length;i++) {
                if (idKabupaten[i].equals(strIdKabupaten)) {
                    spinKabupaten.setSelection(i);
                    strIdKabupaten = idKabupaten[i];
                }
            }
        }

        spinKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                strIdKabupaten = idKabupaten[position];
                Log.i("IDKAB", " === " + strIdKabupaten);
                presenterKecamatan.sendGetKecamatan("0",strIdKabupaten);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void getDataKecamatan(String[] idKecamatan, String[] namaKecamatan) {
        ArrayAdapter adapter = new ArrayAdapter(BisnisAkunActivity.this, R.layout.list_item, namaKecamatan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKecamatan.setAdapter(adapter);

        if (!strIdKecamatan.isEmpty()) {
            for(int i = 0; i < idKecamatan.length; i++){
                if (idKecamatan[i].equals(strIdKecamatan)) {
                    spinKecamatan.setSelection(i);
                    strIdKecamatan = idKecamatan[i];
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
        ArrayAdapter adapter = new ArrayAdapter(BisnisAkunActivity.this, R.layout.list_item, namaProvinsi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinProvinsi.setAdapter(adapter);

        if (!strIdProvinsi.isEmpty()) {
            for (int i = 0; i < idProvinsi.length;i++) {
                if (idProvinsi[i].equals(strIdProvinsi)) {
                    spinProvinsi.setSelection(i);
                    strIdProvinsi = idProvinsi[i];
                }
            }
        }

        spinProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                strIdProvinsi = idProvinsi[position];
                presenterKabupaten.sendGetKabupaten("0",strIdProvinsi);
                Log.i("IDPROV", " === " + strIdProvinsi);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void openDialogFoto(){
        dialogPreview = new Dialog(BisnisAkunActivity.this);
        dialogPreview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPreview.setContentView(R.layout.dialog_open_foto);
        dialogPreview.setCancelable(true);
        Window window = dialogPreview.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        llOpenCamera = dialogPreview.findViewById(R.id.llTakePhotoTambahFoto);
        llOpenGallery = dialogPreview.findViewById(R.id.llGalleryTambahFoto);

        llOpenCamera.setOnClickListener(v -> {
            uploadFoto();
        });

        llOpenGallery.setOnClickListener(v -> {
            openGallery();
        });

        dialogPreview.show();

        verifyStoragePermissions(this);
    }

    public void uploadFoto(){
        if(hasCameraPermission()) {
            pickImage();
        }
        else
        {
            requestCameraPermission();
        }
    }

    public void pickImage(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA},
                CAMERA_REQUEST );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST :
                uploadFoto();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void openGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            civFotoBisnis.setImageBitmap(mphoto);
            img = mphoto;
        } else if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            img = BitmapFactory.decodeFile(imagePath);
            civFotoBisnis.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            cursor.close();
        }
    }

    public static String convertImageToStringForServer(Bitmap imageBitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(imageBitmap != null) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }else{
            return null;
        }
    }

    public boolean checkNamaBisnis() {
        namaBisnis = tietNamaBisnis.getText().toString();

        if (namaBisnis.isEmpty()) {
            Toast.makeText(this, "Nama bisnis tidak boleh kosong", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public boolean checkNoTelpon() {
        noTeleponBisnis = tietNoTelpon.getText().toString();

        if (noTeleponBisnis.isEmpty()) {
            Toast.makeText(this, "No Telepon tidak boleh kosong", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}