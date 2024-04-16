package com.temankasir.ui.home.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.api.APIConnect;
import com.temankasir.contract.CheckoutContract;
import com.temankasir.presenter.CheckoutPresenter;
import com.temankasir.ui.home.adapter.AdapterHistoriTransaksi;
import com.temankasir.ui.home.adapter.AdapterItemHistoriTrans;
import com.temankasir.ui.home.adapter.AdapterItemTransaksi;
import com.temankasir.ui.home.adapter.AdapterPreviewPesanan;
import com.temankasir.ui.home.model.HistoriTransaksiModel;
import com.temankasir.utils.CustomProgressDialog;

import java.text.NumberFormat;
import java.util.Locale;

public class FragHistori extends Fragment implements CheckoutContract.checkoutView, AdapterHistoriTransaksi.Callback {
    CheckoutPresenter checkoutPresenter;
    RecyclerView rvListHistori;
    AdapterHistoriTransaksi adapterHistoriTransaksi;
    AdapterItemHistoriTrans adapterItemTransaksi;
    private CustomProgressDialog customProgressDialog = new CustomProgressDialog();
    Dialog dialogPreview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_histori, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        String idOutlet = sharedPreferences.getString("idOutlet", "");

        rvListHistori = view.findViewById(R.id.rvListHistoriTransaksi);

        checkoutPresenter = new CheckoutPresenter(this);
        checkoutPresenter.listHistoriTrans(idOutlet);

        initLiveData();

        return view;
    }

    public void initLiveData() {
        checkoutPresenter.getListHeaderTransLive().observe(requireActivity(), headerTransaksiModels -> {
            adapterHistoriTransaksi = new AdapterHistoriTransaksi(requireActivity(), headerTransaksiModels, this);
            final RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(requireActivity());
            rvListHistori.setLayoutManager(mLayoutManager3);
            rvListHistori.setItemAnimator(new DefaultItemAnimator());
            rvListHistori.setItemViewCacheSize(headerTransaksiModels.size());
            rvListHistori.setAdapter(adapterHistoriTransaksi);
            adapterHistoriTransaksi.notifyDataSetChanged();
        });
    }

    @Override
    public void showLoading() {
        customProgressDialog.show(requireActivity(), getString(R.string.title_loading));
    }

    @Override
    public void hideLoading() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDetail(HistoriTransaksiModel historiTransaksiModel) {
        dialogPreview = new Dialog(requireActivity());
        dialogPreview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPreview.setContentView(R.layout.dialog_detail_histori_trans);
        dialogPreview.setCancelable(true);
        Window window = dialogPreview.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        TextView tvNoTransaksi, tvDateTransaksi, tvOutletTransaksi, tvKasirTransaksi, tvPelangganTransaksi, tvTipePembayaran,
                tvSubtotal, tvTotalTransaksi, tvDibayarkan, tvKembalian, tvStatusTransaksi;
        RecyclerView rvListItem;

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        rvListItem = dialogPreview.findViewById(R.id.rvListItemDetailHistoriTrans);
        tvNoTransaksi = dialogPreview.findViewById(R.id.tvKodeDetailHistoriTrans);
        tvDateTransaksi = dialogPreview.findViewById(R.id.tvDateDetailHistoriTrans);
        tvOutletTransaksi = dialogPreview.findViewById(R.id.tvOutletDetailHistoriTrans);
        tvKasirTransaksi = dialogPreview.findViewById(R.id.tvKasirDetailHistoriTrans);
        tvPelangganTransaksi = dialogPreview.findViewById(R.id.tvNamaPelangganDetailHistoriTrans);
        tvTipePembayaran = dialogPreview.findViewById(R.id.tvTipePembayaranDetailHistoriTrans);
        tvSubtotal = dialogPreview.findViewById(R.id.tvSubTotalDetailHistoriTrans);
        tvTotalTransaksi = dialogPreview.findViewById(R.id.tvTotalTransDetailHistoriTrans);
        tvDibayarkan = dialogPreview.findViewById(R.id.tvDibayarDetailHistoriTrans);
        tvKembalian = dialogPreview.findViewById(R.id.tvKembalianDetailHistoriTrans);
        tvStatusTransaksi = dialogPreview.findViewById(R.id.tvStatusTransDetailHistoriTrans);

        adapterItemTransaksi = new AdapterItemHistoriTrans(requireActivity(), historiTransaksiModel.getItemListTransaksi());
        final RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(requireActivity());
        rvListItem.setLayoutManager(mLayoutManager3);
        rvListItem.setItemAnimator(new DefaultItemAnimator());
        rvListItem.setItemViewCacheSize(historiTransaksiModel.getItemListTransaksi().size());
        rvListItem.setAdapter(adapterItemTransaksi);
        adapterItemTransaksi.notifyDataSetChanged();

        tvNoTransaksi.setText(historiTransaksiModel.getKodeTransaksi());
        tvDateTransaksi.setText(historiTransaksiModel.getDateTransaksi());
        tvOutletTransaksi.setText(historiTransaksiModel.getNamaOutletTransaksi());
        tvKasirTransaksi.setText(historiTransaksiModel.getNamaKasirTransaksi());
        tvPelangganTransaksi.setText(historiTransaksiModel.getNamaPelangganTransaksi());
        tvTipePembayaran.setText(historiTransaksiModel.getTipePembayaranTransaksi());
        tvSubtotal.setText(formatRupiah.format(Integer.parseInt(historiTransaksiModel.getTotalTransaksi())));
        tvTotalTransaksi.setText(formatRupiah.format(Integer.parseInt(historiTransaksiModel.getTotalTransaksi())));
        tvDibayarkan.setText(formatRupiah.format(Integer.parseInt(historiTransaksiModel.getJumlahdibayarTransaksi())));

        int kembalian = Integer.parseInt(historiTransaksiModel.getJumlahdibayarTransaksi()) - Integer.parseInt(historiTransaksiModel.getTotalTransaksi());
        tvKembalian.setText(formatRupiah.format(kembalian));
        tvStatusTransaksi.setText(historiTransaksiModel.getStatusDibayarTransaksi());

        dialogPreview.show();
    }
}
