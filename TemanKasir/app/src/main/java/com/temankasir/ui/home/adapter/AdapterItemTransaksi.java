package com.temankasir.ui.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.pengaturan.aturitem.model.ItemModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterItemTransaksi extends RecyclerView.Adapter<AdapterItemTransaksi.ItemTransaksiVH> {

    private Context context;
    private ArrayList<ItemModel> listItem;
    private Callback callback;
    private int posisi = -1;

    public AdapterItemTransaksi(Context context, ArrayList<ItemModel> listItem, Callback callback) {
        this.context = context;
        this.listItem = listItem;
        this.callback = callback;
        posisi = -1;
    }

    @Override
    public ItemTransaksiVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_transaksi, parent, false);

        return new ItemTransaksiVH(view);
    }

    public class ItemTransaksiVH extends RecyclerView.ViewHolder{
        TextView tvNamaItem, tvHargaItem, tvStokItem;
        CardView cvNamaItem;

        public ItemTransaksiVH(View view) {
            super(view);

            tvNamaItem = view.findViewById(R.id.tvListNamaItemTransaksi);
            tvHargaItem = view.findViewById(R.id.tvListHargaItemTransaksi);
            tvStokItem = view.findViewById(R.id.tvListStokItemTransaksi);
            cvNamaItem = view.findViewById(R.id.cvListItemTransaksi);
        }
    }

    @Override
    public void onBindViewHolder(ItemTransaksiVH holder, int position) {
        ItemModel itemModel = listItem.get(position);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        holder.tvNamaItem.setText(itemModel.getNamaItem());
        holder.tvHargaItem.setText(formatRupiah.format(Integer.parseInt(itemModel.getHargaItem())));

        holder.cvNamaItem.setOnClickListener(view -> {
            if (posisi == position) {
                posisi = -1;
                notifyDataSetChanged();
            } else {
                posisi = position;
                notifyDataSetChanged();
            }
        });

        if (posisi == position) {
            holder.cvNamaItem.setCardBackgroundColor(context.getResources().getColor(R.color.tosca));
            holder.tvNamaItem.setTextColor(context.getResources().getColor(R.color.white));
            callback.setItem(itemModel);
        } else {
            holder.cvNamaItem.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tvNamaItem.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }

        if (itemModel.getStokItem() == "0" || itemModel.getStokItem().equals("0")) {
            holder.cvNamaItem.setCardBackgroundColor(context.getResources().getColor(R.color.gray8b));
            holder.tvNamaItem.setTextColor(context.getResources().getColor(R.color.white));
            holder.cvNamaItem.setEnabled(false);
            holder.tvStokItem.setText("Stok Habis");
            holder.tvStokItem.setTextColor(context.getResources().getColor(R.color.rednotif));
        } else {
            if (Integer.parseInt(itemModel.getStokItem()) < 10) {
                holder.cvNamaItem.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                holder.tvNamaItem.setTextColor(context.getResources().getColor(R.color.colorBlack));
                holder.cvNamaItem.setEnabled(true);
                holder.tvStokItem.setTextColor(context.getResources().getColor(R.color.rednotif));
                holder.tvStokItem.setText("Stok menipis : " + itemModel.getStokItem());
            } else {
                holder.cvNamaItem.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                holder.tvNamaItem.setTextColor(context.getResources().getColor(R.color.colorBlack));
                holder.cvNamaItem.setEnabled(true);
                holder.tvStokItem.setText("Stok tersedia : " + itemModel.getStokItem());
                holder.tvStokItem.setTextColor(context.getResources().getColor(R.color.colorBlack));
            }
        }

        if (itemModel.getStatus().equals("Tersedia") || itemModel.getStatus() == "Tersedia") {
            holder.cvNamaItem.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tvNamaItem.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.cvNamaItem.setEnabled(true);
            holder.tvStokItem.setText("Stok tersedia : " + itemModel.getStokItem());
            holder.tvStokItem.setTextColor(context.getResources().getColor(R.color.colorBlack));
        } else {
            holder.cvNamaItem.setCardBackgroundColor(context.getResources().getColor(R.color.gray8b));
            holder.tvNamaItem.setTextColor(context.getResources().getColor(R.color.white));
            holder.cvNamaItem.setEnabled(false);
            holder.tvStokItem.setText("Tidak Tersedia");
            holder.tvStokItem.setPaintFlags(holder.tvStokItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvStokItem.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public interface Callback {
        void setItem(ItemModel item);
    }
}
