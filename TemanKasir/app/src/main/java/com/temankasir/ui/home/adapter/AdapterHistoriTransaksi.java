package com.temankasir.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.home.model.HistoriTransaksiModel;

import java.util.ArrayList;

public class AdapterHistoriTransaksi extends RecyclerView.Adapter<AdapterHistoriTransaksi.HistoriTransVH> {

    private Context context;
    private ArrayList<HistoriTransaksiModel> listHistoriTransaksi;
    private Callback callback;

    public AdapterHistoriTransaksi(Context context, ArrayList<HistoriTransaksiModel> listHistoriTransaksi, Callback callback) {
        this.context = context;
        this.listHistoriTransaksi = listHistoriTransaksi;
        this.callback = callback;
    }

    @Override
    public HistoriTransVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_histori_transaksi, parent, false);

        return new HistoriTransVH(view);
    }

    public class HistoriTransVH extends RecyclerView.ViewHolder{
        TextView tvKodeTrans, tvDateTrans, tvNamaPelanggan, tvNamaKasir, tvNamaOutlet;

        public HistoriTransVH(View view) {
            super(view);

            tvKodeTrans = view.findViewById(R.id.tvListKodeHistoriTransaksi);
            tvDateTrans = view.findViewById(R.id.tvListDateHistoriTransaksi);
            tvNamaPelanggan = view.findViewById(R.id.tvListNamaPelangganHistoriTransaksi);
            tvNamaKasir = view.findViewById(R.id.tvListNamaKasirHistoriTransaksi);
            tvNamaOutlet = view.findViewById(R.id.tvListNamaOutletHistoriTransaksi);
        }
    }

    @Override
    public void onBindViewHolder(HistoriTransVH holder, int position) {
        HistoriTransaksiModel historiTransaksiModel = listHistoriTransaksi.get(position);

        holder.tvKodeTrans.setText(historiTransaksiModel.getKodeTransaksi());
        holder.tvDateTrans.setText(historiTransaksiModel.getDateTransaksi());
        holder.tvNamaPelanggan.setText(historiTransaksiModel.getNamaPelangganTransaksi());
        holder.tvNamaKasir.setText(historiTransaksiModel.getNamaKasirTransaksi());
        holder.tvNamaOutlet.setText(historiTransaksiModel.getNamaOutletTransaksi());

        holder.itemView.setOnClickListener(view -> {
            callback.setDetail(historiTransaksiModel);
        });
    }

    @Override
    public int getItemCount() {
        return listHistoriTransaksi.size();
    }

    public interface Callback {
        void setDetail(HistoriTransaksiModel historiTransaksiModel);
    }
}
