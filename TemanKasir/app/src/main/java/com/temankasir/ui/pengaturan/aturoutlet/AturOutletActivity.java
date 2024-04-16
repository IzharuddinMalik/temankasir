package com.temankasir.ui.pengaturan.aturoutlet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.temankasir.R;
import com.temankasir.contract.OutletContract;
import com.temankasir.presenter.OutletPresenter;
import com.temankasir.ui.home.MainActivity;
import com.temankasir.ui.pengaturan.aturkasir.AturKasirActivity;
import com.temankasir.ui.pengaturan.aturkasir.adapter.AdapterListKasir;
import com.temankasir.ui.pengaturan.aturoutlet.adapter.AdapterOutlet;
import com.temankasir.utils.CustomProgressDialog;

public class AturOutletActivity extends AppCompatActivity implements OutletContract.outletView {

    CardView cvTambahOutlet;
    CustomProgressDialog customProgressDialog;
    OutletPresenter presenterOutlet;
    RecyclerView rvListOutlet;
    AdapterOutlet adapterOutlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_outlet);

        customProgressDialog = new CustomProgressDialog();
        presenterOutlet = new OutletPresenter(this);

        SharedPreferences sharedPreferences = getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        String idOwner = sharedPreferences.getString("idUser", "");
        String idBisnis = sharedPreferences.getString("idBisnis", "");

        presenterOutlet.getListOutlet(idOwner,idBisnis);

        cvTambahOutlet = findViewById(R.id.cvTambahOutletAturOutlet);
        rvListOutlet = findViewById(R.id.rvListOutlet);

        cvTambahOutlet.setOnClickListener(v -> {
            Intent intent = new Intent(this, TambahOutletActivity.class);
            intent.putExtra("from", "add");
            startActivity(intent);
        });

        initLiveData();
    }

    public void initLiveData() {
        presenterOutlet.onListOutlet().observe(this, outletModels -> {
            adapterOutlet = new AdapterOutlet(this, outletModels);
            final RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(AturOutletActivity.this);
            rvListOutlet.setLayoutManager(mLayoutManager3);
            rvListOutlet.setItemAnimator(new DefaultItemAnimator());
            rvListOutlet.setItemViewCacheSize(outletModels.size());
            rvListOutlet.setAdapter(adapterOutlet);
            adapterOutlet.notifyDataSetChanged();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment", "pengaturan");
        startActivity(intent);
    }

    @Override
    public void showLoadingOutlet() {
        customProgressDialog.show(this, "Mohon menunggu...");
    }

    @Override
    public void hideLoadingOutlet() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastOutlet(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}