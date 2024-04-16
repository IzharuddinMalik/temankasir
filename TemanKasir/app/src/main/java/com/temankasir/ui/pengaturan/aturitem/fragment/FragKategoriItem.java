package com.temankasir.ui.pengaturan.aturitem.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.api.APIConnect;
import com.temankasir.contract.ItemContract;
import com.temankasir.presenter.ItemPresenter;
import com.temankasir.ui.pengaturan.aturitem.TambahKategoriItemActivity;
import com.temankasir.ui.pengaturan.aturitem.adapter.AdapterKategoriItem;
import com.temankasir.ui.pengaturan.aturitem.model.KategoriItemModel;
import com.temankasir.ui.pengaturan.aturkasir.AturKasirActivity;
import com.temankasir.ui.pengaturan.aturkasir.adapter.AdapterListKasir;
import com.temankasir.utils.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class FragKategoriItem extends Fragment implements ItemContract.itemView {

    CardView cvTambahKategori;
    AdapterKategoriItem adapterKategoriItem;
    RecyclerView rvListKategori;
    private ItemPresenter itemPresenter;
    private CustomProgressDialog customProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kategori_item, container, false);

        customProgressDialog = new CustomProgressDialog();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        String idBisnis = sharedPreferences.getString("idBisnis", "");

        itemPresenter = new ItemPresenter(this);
        itemPresenter.getListKategoriItem(idBisnis);

        initLiveData();

        cvTambahKategori = view.findViewById(R.id.cvTambahKategoriItemFragKategoriItem);
        rvListKategori = view.findViewById(R.id.rvListKategoriItem);

        cvTambahKategori.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TambahKategoriItemActivity.class);
            intent.putExtra("from", "add");
            startActivity(intent);
        });

        return view;
    }

    public void initLiveData() {
        itemPresenter.onGetListKategoriItem().observe(requireActivity(), kategoriItemModels -> {
            adapterKategoriItem = new AdapterKategoriItem(requireContext(), kategoriItemModels);
            final RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(requireContext());
            rvListKategori.setLayoutManager(mLayoutManager3);
            rvListKategori.setItemAnimator(new DefaultItemAnimator());
            rvListKategori.setItemViewCacheSize(kategoriItemModels.size());
            rvListKategori.setAdapter(adapterKategoriItem);
            adapterKategoriItem.notifyDataSetChanged();
        });
    }

    @Override
    public void showLoadingItem() {
        customProgressDialog.show(requireContext(), getString(R.string.title_loading));
    }

    @Override
    public void hideLoadingItem() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastItem(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
