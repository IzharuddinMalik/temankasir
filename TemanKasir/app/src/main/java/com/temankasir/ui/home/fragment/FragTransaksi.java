package com.temankasir.ui.home.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.api.APIConnect;
import com.temankasir.contract.ItemContract;
import com.temankasir.presenter.ItemPresenter;
import com.temankasir.ui.home.adapter.AdapterItemTransaksi;
import com.temankasir.ui.home.adapter.AdapterKategoriTransaksi;
import com.temankasir.ui.home.adapter.AdapterPreviewPesanan;
import com.temankasir.ui.home.model.OrderModel;
import com.temankasir.ui.pengaturan.aturitem.model.ItemModel;
import com.temankasir.ui.pengaturan.aturitem.model.KategoriItemModel;
import com.temankasir.ui.pengaturan.aturoutlet.AturOutletActivity;
import com.temankasir.ui.pengaturan.aturoutlet.adapter.AdapterOutlet;
import com.temankasir.ui.transaksi.KeranjangActivity;
import com.temankasir.utils.CustomProgressDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class FragTransaksi extends Fragment implements ItemContract.itemView, AdapterKategoriTransaksi.Callback,
AdapterItemTransaksi.Callback{

    ItemPresenter itemPresenter;
    RecyclerView rvListKategori, rvListItem;
    LinearLayout llKeranjang;
    TextView tvJumlahItem, tvTotalTransaksi;
    String idBisnis, idOutlet;
    AdapterKategoriTransaksi adapterKategoriTransaksi;
    AdapterItemTransaksi adapterItemTransaksi;
    AdapterPreviewPesanan adapterPreviewPesanan;
    CustomProgressDialog customProgressDialog = new CustomProgressDialog();
    Dialog dialogPreview;

    private ArrayList<String> arrIdItem = new ArrayList<>();
    private ArrayList<String> arrNamaItem = new ArrayList<>();
    private ArrayList<String> arrHargaItem = new ArrayList<>();
    private ArrayList<String> arrJumlahItem = new ArrayList<>();
    private ArrayList<OrderModel> arrOrderModel = new ArrayList<>();
    private ArrayList<OrderModel> arrCartModel = new ArrayList<>();

    private MutableLiveData<ArrayList<OrderModel>> arrOrder = new MutableLiveData<>();
    private MutableLiveData<ArrayList<OrderModel>> arrOrderCart = new MutableLiveData<>();

    int jumlahitem = 0;
    int totalItem = 0;
    int subTotal = 0;
    int totalTransaksi = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        idBisnis = sharedPreferences.getString("idBisnis", "");
        idOutlet = sharedPreferences.getString("idOutlet", "");

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        itemPresenter = new ItemPresenter(this);
        rvListItem = view.findViewById(R.id.rvListItemFragTransaksi);
        rvListKategori = view.findViewById(R.id.rvListKategoriFragTransaksi);
        llKeranjang = view.findViewById(R.id.llKeranjangTransaksi);
        tvJumlahItem = view.findViewById(R.id.tvJumlahItemFragTransaksi);
        tvTotalTransaksi = view.findViewById(R.id.tvTotalTransaksiFragTransaksi);

        itemPresenter.listKategoriByOutlet(idBisnis, idOutlet);

        arrOrder.observe(requireActivity(), orderModels -> {
            for (int i = 0; i < orderModels.size(); i++) {
                totalItem += Integer.parseInt(orderModels.get(i).getJumlahItem());
                tvJumlahItem.setText(String.valueOf(totalItem) + "x");

                subTotal = Integer.parseInt(orderModels.get(i).getJumlahItem()) * Integer.parseInt(orderModels.get(i).getHargaItem());
                totalTransaksi = totalTransaksi + subTotal;
                tvTotalTransaksi.setText(formatRupiah.format(totalTransaksi));
            }
        });

        llKeranjang.setOnClickListener(view1 -> {
            arrOrderCart.observe(requireActivity(), orderModels -> {
                dialogPreviewPesanan(orderModels);
            });
        });

        initLiveData();

        return view;
    }

    public void initLiveData() {
        itemPresenter.getListKategoriByOutletLive().observe(requireActivity(), kategoriItemModels -> {
            adapterKategoriTransaksi = new AdapterKategoriTransaksi(requireActivity(), kategoriItemModels, this);
            final RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
            rvListKategori.setLayoutManager(mLayoutManager3);
            rvListKategori.setItemAnimator(new DefaultItemAnimator());
            rvListKategori.setItemViewCacheSize(kategoriItemModels.size());
            rvListKategori.setAdapter(adapterKategoriTransaksi);
            adapterKategoriTransaksi.notifyDataSetChanged();
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

    @Override
    public void setKategori(KategoriItemModel kategori) {
        itemPresenter.listItemByKategori(idBisnis, idOutlet, kategori.getIdKategori());

        getItem();
    }

    public void getItem() {
        itemPresenter.getListItemByKategoriLive().observe(requireActivity(), itemModels -> {
            adapterItemTransaksi = new AdapterItemTransaksi(requireActivity(), itemModels, this);
            final RecyclerView.LayoutManager mLayoutManager3 = new GridLayoutManager(requireActivity(), 2);
            rvListItem.setLayoutManager(mLayoutManager3);
            rvListItem.setItemAnimator(new DefaultItemAnimator());
            rvListItem.setItemViewCacheSize(itemModels.size());
            rvListItem.setAdapter(adapterItemTransaksi);
            adapterItemTransaksi.notifyDataSetChanged();
        });
    }

    @Override
    public void setItem(ItemModel item) {
        dialogPreview = new Dialog(requireActivity());
        dialogPreview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPreview.setContentView(R.layout.dialog_item_trans);
        dialogPreview.setCancelable(true);
        Window window = dialogPreview.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        TextView tvNamaItem, tvHargaItem, tvStokItem;
        EditText edtJumlahItem;
        Button btnMinus, btnPlus, btnTambah, btnBatal;

        tvNamaItem = dialogPreview.findViewById(R.id.tvNamaItemDialogItem);
        tvHargaItem = dialogPreview.findViewById(R.id.tvHargaItemDialogItem);
        tvStokItem = dialogPreview.findViewById(R.id.tvStokItemDialogItem);
        edtJumlahItem = dialogPreview.findViewById(R.id.edtJumlahItemDialogShop);
        btnMinus = dialogPreview.findViewById(R.id.btnMinusItemDialogItem);
        btnPlus = dialogPreview.findViewById(R.id.btnPlusItemDialogItem);
        btnTambah = dialogPreview.findViewById(R.id.btnTambahItemDialogItem);
        btnBatal = dialogPreview.findViewById(R.id.btnBatalDialogItem);

        tvNamaItem.setText(item.getNamaItem());
        tvHargaItem.setText(formatRupiah.format(Integer.parseInt(item.getHargaItem())));
        tvStokItem.setText(item.getStokItem());

        btnPlus.setOnClickListener(view -> {
            jumlahitem = jumlahitem +1;
            edtJumlahItem.setText(String.valueOf(jumlahitem));
        });

        btnMinus.setOnClickListener(view -> {
            jumlahitem = jumlahitem - 1;
            edtJumlahItem.setText(String.valueOf(jumlahitem));
        });

        btnTambah.setOnClickListener(view -> {
            if (jumlahitem == 0) {
                Toast.makeText(requireActivity(), "Harap isi jumlah item", Toast.LENGTH_LONG).show();
            } else {
                arrIdItem.add(item.getIdItem());
                arrNamaItem.add(item.getNamaItem());
                arrHargaItem.add(item.getHargaItem());
                arrJumlahItem.add(String.valueOf(jumlahitem));

                arrOrderModel.add(new OrderModel(item.getIdItem(), item.getNamaItem(), item.getHargaItem(), String.valueOf(jumlahitem)));
                arrOrder.setValue(arrOrderModel);
                arrCartModel.add(new OrderModel(item.getIdItem(), item.getNamaItem(), item.getHargaItem(), String.valueOf(jumlahitem)));
                arrOrderCart.setValue(arrCartModel);
                jumlahitem = 0;
                arrOrderModel.clear();
                dialogPreview.dismiss();
            }
        });

        btnBatal.setOnClickListener(view -> {
            dialogPreview.dismiss();
        });

        dialogPreview.show();
    }

    public void dialogPreviewPesanan(ArrayList<OrderModel> arrOrderModel){
        dialogPreview = new Dialog(requireActivity());
        dialogPreview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPreview.setContentView(R.layout.dialog_keranjang);
        dialogPreview.setCancelable(true);
        Window window = dialogPreview.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        RecyclerView rvListKeranjang;
        Button btnSelanjutnya, btnTambahItem;

        rvListKeranjang = dialogPreview.findViewById(R.id.rvListPreviewPesanan);
        btnSelanjutnya = dialogPreview.findViewById(R.id.btnLanjutPreviewPesanan);
        btnTambahItem = dialogPreview.findViewById(R.id.btnTambahItemPreviewPesanan);

        btnSelanjutnya.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), KeranjangActivity.class);
            intent.putExtra("order", arrOrderModel);
            intent.putExtra("arriditem", arrIdItem);
            intent.putExtra("arrqtyitem", arrJumlahItem);
            intent.putExtra("arrhargaitem",arrHargaItem);
            startActivity(intent);
        });

        adapterPreviewPesanan = new AdapterPreviewPesanan(requireActivity(), arrOrderModel);
        final RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(requireActivity());
        rvListKeranjang.setLayoutManager(mLayoutManager3);
        rvListKeranjang.setItemAnimator(new DefaultItemAnimator());
        rvListKeranjang.setItemViewCacheSize(arrOrderModel.size());
        rvListKeranjang.setAdapter(adapterPreviewPesanan);
        adapterPreviewPesanan.notifyDataSetChanged();

        btnTambahItem.setOnClickListener(view -> {
            dialogPreview.dismiss();
        });

        dialogPreview.show();
    }
}
