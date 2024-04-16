package com.temankasir.ui.pengaturan.aturpelanggan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.pengaturan.aturitem.adapter.AdapterItem;
import com.temankasir.ui.pengaturan.aturitem.model.ItemModel;
import com.temankasir.ui.pengaturan.aturpelanggan.model.PelangganModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterPelanggan extends RecyclerView.Adapter<AdapterPelanggan.PelangganViewHolder> {

    private Context mCtx;
    private ArrayList<PelangganModel> listPelanggan;
    private Callback callback;

    public AdapterPelanggan(Context context, ArrayList<PelangganModel> listPelanggan, Callback callback){
        this.mCtx = context;
        this.listPelanggan = listPelanggan;
        this.callback = callback;
    }

    @Override
    public PelangganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pelanggan, parent, false);

        return new PelangganViewHolder(view);
    }

    public class PelangganViewHolder extends RecyclerView.ViewHolder{

        TextView tvNamaPelanggan, tvEmail, tvNotelepon;

        public PelangganViewHolder(View view) {
            super(view);

            tvNamaPelanggan = view.findViewById(R.id.tvListNamaPelanggan);
            tvEmail = view.findViewById(R.id.tvListEmailPelanggan);
            tvNotelepon = view.findViewById(R.id.tvListNoTeleponPelanggan);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PelangganViewHolder holder, int position) {
        PelangganModel pelangganModel = listPelanggan.get(position);

        holder.tvNamaPelanggan.setText(pelangganModel.getNamaPelanggan());
        holder.tvEmail.setText(pelangganModel.getEmailPelanggan());
        holder.tvNotelepon.setText(pelangganModel.getNoTeleponPelanggan());

        holder.itemView.setOnClickListener(view -> {
            callback.showDetail(pelangganModel);
        });
    }

    @Override
    public int getItemCount() {
        return listPelanggan.size();
    }

    public interface Callback {
        void showDetail(PelangganModel pelangganModel);
    }
}
