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
import com.temankasir.ui.pengaturan.aturitem.TambahItemActivity;
import com.temankasir.ui.pengaturan.aturitem.TambahKategoriItemActivity;
import com.temankasir.ui.pengaturan.aturitem.adapter.AdapterItem;
import com.temankasir.ui.pengaturan.aturitem.adapter.AdapterKategoriItem;
import com.temankasir.ui.pengaturan.aturitem.model.ItemModel;
import com.temankasir.utils.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class FragItem extends Fragment implements ItemContract.itemView {

    CardView cvTambahItem;
    AdapterItem adapterItem;
    RecyclerView rvListItem;
    private ItemPresenter itemPresenter;
    private CustomProgressDialog customProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        customProgressDialog = new CustomProgressDialog();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        String idBisnis = sharedPreferences.getString("idBisnis", "");

        itemPresenter = new ItemPresenter(this);
        itemPresenter.listItem(idBisnis);

        cvTambahItem = view.findViewById(R.id.cvTambahItemFragItem);
        rvListItem = view.findViewById(R.id.rvListItem);

        initLiveData();

        cvTambahItem.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TambahItemActivity.class);
            intent.putExtra("from", "add");
            startActivity(intent);
        });

        return view;
    }

    public void initLiveData() {
        itemPresenter.onGetListItem().observe(requireActivity(), itemModels -> {
            adapterItem = new AdapterItem(requireContext(), itemModels);
            final RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(requireContext());
            rvListItem.setLayoutManager(mLayoutManager3);
            rvListItem.setItemAnimator(new DefaultItemAnimator());
            rvListItem.setAdapter(adapterItem);
            adapterItem.notifyDataSetChanged();
        });
    }

    @Override
    public void showLoadingItem() {
        customProgressDialog.show(requireActivity(), getString(R.string.title_loading));
    }

    @Override
    public void hideLoadingItem() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastItem(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
