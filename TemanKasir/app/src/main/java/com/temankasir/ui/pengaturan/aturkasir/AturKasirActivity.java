package com.temankasir.ui.pengaturan.aturkasir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.temankasir.R;
import com.temankasir.contract.KasirContract;
import com.temankasir.presenter.KasirPresenter;
import com.temankasir.ui.home.MainActivity;
import com.temankasir.ui.pengaturan.aturkasir.adapter.AdapterListKasir;
import com.temankasir.ui.pengaturan.aturkasir.model.KasirModel;
import com.temankasir.utils.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class AturKasirActivity extends AppCompatActivity implements KasirContract.kasirView {

    AdapterListKasir adapterListKasir;
    RecyclerView rvListKasir;
    CardView cvTambahKasir;
    CustomProgressDialog customProgressDialog;
    KasirPresenter kasirPresenter;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_kasir);

        kasirPresenter = new KasirPresenter(this);
        customProgressDialog = new CustomProgressDialog();

        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        String idOwner = sharedPreferences.getString("idUser", "");
        String idBisnis = sharedPreferences.getString("idBisnis", "");
        kasirPresenter.getListKasir(idOwner, idBisnis);

        rvListKasir = findViewById(R.id.rvListKasir);
        cvTambahKasir = findViewById(R.id.cvTambahKasirAturKasir);
        ivBack = findViewById(R.id.ivAturKasirBackToPengaturan);

        cvTambahKasir.setOnClickListener(v -> {
            Intent intent = new Intent(this, TambahKasirActivity.class);
            intent.putExtra("from", "add");
            startActivity(intent);
        });

        ivBack.setOnClickListener(v -> {
            this.onBackPressed();
        });

        initLiveData();
    }

    public void initLiveData() {
        kasirPresenter.onListKasir().observe(this, kasirModels -> {
            adapterListKasir = new AdapterListKasir(this, kasirModels);
            final RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(AturKasirActivity.this);
            rvListKasir.setLayoutManager(mLayoutManager3);
            rvListKasir.setItemAnimator(new DefaultItemAnimator());
            rvListKasir.setItemViewCacheSize(kasirModels.size());
            rvListKasir.setAdapter(adapterListKasir);
            adapterListKasir.notifyDataSetChanged();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment", "pengaturan");
        startActivity(intent);
    }

    @Override
    public void showLoadingKasir() {

    }

    @Override
    public void hideLoadingKasir() {

    }

    @Override
    public void showToastKasir(String message) {

    }
}