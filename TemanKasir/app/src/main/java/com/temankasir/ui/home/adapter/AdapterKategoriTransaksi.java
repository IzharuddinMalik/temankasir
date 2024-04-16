package com.temankasir.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.pengaturan.aturitem.model.KategoriItemModel;
import com.temankasir.ui.pengaturan.aturpelanggan.adapter.AdapterPelanggan;

import java.util.ArrayList;

public class AdapterKategoriTransaksi extends RecyclerView.Adapter<AdapterKategoriTransaksi.KategoriTransVH> {

    private Context context;
    private ArrayList<KategoriItemModel> listKategori;
    private Callback callback;
    private int posisi = 0;

    public AdapterKategoriTransaksi(Context context, ArrayList<KategoriItemModel> listKategori, Callback callback) {
        this.context = context;
        this.listKategori = listKategori;
        this.callback = callback;
    }

    @Override
    public KategoriTransVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori_transaksi, parent, false);

        return new KategoriTransVH(view);
    }

    public class KategoriTransVH extends RecyclerView.ViewHolder{
        TextView tvNamaKategori;
        CardView cvKategori;

        public KategoriTransVH(View view) {
            super(view);

            tvNamaKategori = view.findViewById(R.id.tvListKategoriItemTransaksi);
            cvKategori = view.findViewById(R.id.cvKategoriItemTransaksi);
        }
    }

    @Override
    public void onBindViewHolder(KategoriTransVH holder, int position) {
        KategoriItemModel kategori = listKategori.get(position);

        holder.tvNamaKategori.setText(kategori.getNamaKategori());
        holder.cvKategori.setOnClickListener(v -> {
            if (holder.tvNamaKategori.getTextColors().getDefaultColor() == context.getResources().getColor(R.color.white)) {
                posisi = -1;
                notifyDataSetChanged();
            } else {
                posisi = position;
                notifyDataSetChanged();
            }
        });

        if (posisi == position) {
            holder.cvKategori.setCardBackgroundColor(context.getResources().getColor(R.color.tosca));
            holder.tvNamaKategori.setTextColor(context.getResources().getColor(R.color.white));
            callback.setKategori(kategori);
        } else {
            holder.cvKategori.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tvNamaKategori.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }

    public interface Callback{
        void setKategori(KategoriItemModel kategori);
    }
}
