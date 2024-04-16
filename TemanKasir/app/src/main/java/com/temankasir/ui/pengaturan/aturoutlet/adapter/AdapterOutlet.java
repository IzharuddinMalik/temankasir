package com.temankasir.ui.pengaturan.aturoutlet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.pengaturan.aturkasir.adapter.AdapterListKasir;
import com.temankasir.ui.pengaturan.aturoutlet.TambahOutletActivity;
import com.temankasir.ui.pengaturan.aturoutlet.model.OutletModel;

import java.util.ArrayList;

public class AdapterOutlet extends RecyclerView.Adapter<AdapterOutlet.OutletViewHolder> {

    Context context;
    ArrayList<OutletModel> listOutlet;

    public AdapterOutlet(Context context, ArrayList<OutletModel> listOutlet){
        this.context = context;
        this.listOutlet = listOutlet;
    }

    @Override
    public OutletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_outlet, parent, false);

        return new OutletViewHolder(view);
    }

    public class OutletViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaOutlet, tvNamaProv, tvNamaKab, tvNamaKec;

        public OutletViewHolder(View view){
            super(view);

            tvNamaOutlet = view.findViewById(R.id.tvNamaOutletListOutlet);
            tvNamaProv = view.findViewById(R.id.tvProvinsiListOutlet);
            tvNamaKab = view.findViewById(R.id.tvKabupatenListOutlet);
            tvNamaKec = view.findViewById(R.id.tvKecamatanListOutlet);
        }
    }

    @Override
    public void onBindViewHolder(OutletViewHolder holder, int position) {

        OutletModel outlet = listOutlet.get(position);

        holder.tvNamaOutlet.setText(outlet.getNamaOutlet());
        holder.tvNamaProv.setText(outlet.getNamaProvinsiOutlet());
        holder.tvNamaKab.setText(outlet.getNamaKabupatenOutlet());
        holder.tvNamaKec.setText(outlet.getNamaKecamatanOutlet());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TambahOutletActivity.class);
            intent.putExtra("idOutlet", outlet.getIdOutlet());
            intent.putExtra("namaOutlet", outlet.getNamaOutlet());
            intent.putExtra("idProvinsi", outlet.getIdProvinsiOutlet());
            intent.putExtra("idKabupaten", outlet.getIdKabupatenOutlet());
            intent.putExtra("idKecamatan", outlet.getIdKecamatanOutlet());
            intent.putExtra("from", "edit");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listOutlet.size();
    }
}
