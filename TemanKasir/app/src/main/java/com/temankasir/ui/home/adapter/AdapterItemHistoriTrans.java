package com.temankasir.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.home.model.ItemTransaksiModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterItemHistoriTrans extends RecyclerView.Adapter<AdapterItemHistoriTrans.ItemHistoriTrans> {

    private Context context;
    private ArrayList<ItemTransaksiModel> listItemTrans;

    public AdapterItemHistoriTrans(Context context, ArrayList<ItemTransaksiModel> listItemTrans) {
        this.context = context;
        this.listItemTrans = listItemTrans;
    }

    @Override
    public ItemHistoriTrans onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_preview_pesanan, parent, false);

        return new ItemHistoriTrans(view);
    }

    public class ItemHistoriTrans extends RecyclerView.ViewHolder{

        TextView tvNamaItem, tvHargaItem, tvQtyItem, tvSubTotal;

        public ItemHistoriTrans(View view) {
            super(view);

            tvNamaItem = view.findViewById(R.id.tvListNamaItemPreviewPesanan);
            tvHargaItem = view.findViewById(R.id.tvListHargaItemPreviewPesanan);
            tvQtyItem = view.findViewById(R.id.tvListJumlahItemPreviewPesanan);
            tvSubTotal = view.findViewById(R.id.tvListSubTotalPreviewPesanan);
        }
    }

    @Override
    public void onBindViewHolder(ItemHistoriTrans holder, int position) {
        ItemTransaksiModel itemTransaksiModel = listItemTrans.get(position);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        holder.tvNamaItem.setText(itemTransaksiModel.getNamaItem());
        holder.tvHargaItem.setText(formatRupiah.format(Integer.parseInt(itemTransaksiModel.getHargaItem())));
        holder.tvQtyItem.setText(itemTransaksiModel.getJumlahItem());

        int subtotal = Integer.parseInt(itemTransaksiModel.getJumlahItem()) * Integer.parseInt(itemTransaksiModel.getHargaItem());
        holder.tvSubTotal.setText(formatRupiah.format(subtotal));
    }

    @Override
    public int getItemCount() {
        return listItemTrans.size();
    }
}
