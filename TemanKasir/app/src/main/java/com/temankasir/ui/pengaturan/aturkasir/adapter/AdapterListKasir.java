package com.temankasir.ui.pengaturan.aturkasir.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.pengaturan.aturkasir.TambahKasirActivity;
import com.temankasir.ui.pengaturan.aturkasir.model.KasirModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterListKasir extends RecyclerView.Adapter<AdapterListKasir.KasirViewHolder> {

    Context mCtx;
    List<KasirModel> listKasir;

    public AdapterListKasir(Context context, List<KasirModel> listKasir){
        this.mCtx = context;
        this.listKasir = listKasir;
    }

    @Override
    public KasirViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_kasir, parent, false);

        return new KasirViewHolder(view);
    }

    public class KasirViewHolder extends RecyclerView.ViewHolder{
        TextView tvNamaKasir, tvNamaOutletKasir;

        public KasirViewHolder(View view){
            super(view);

            tvNamaKasir = view.findViewById(R.id.tvListNamaKasir);
            tvNamaOutletKasir = view.findViewById(R.id.tvListNamaOutletKasir);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterListKasir.KasirViewHolder holder, int position) {

        KasirModel kasir = listKasir.get(position);

        holder.tvNamaKasir.setText(kasir.getNamaKasir());
        holder.tvNamaOutletKasir.setText(kasir.getNamaOutletKasir());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mCtx, TambahKasirActivity.class);
            intent.putExtra("idKasir", listKasir.get(position).getIdKasir());
            intent.putExtra("namaKasir", listKasir.get(position).getNamaKasir());
            intent.putExtra("idOutlet", listKasir.get(position).getIdOutletKasir());
            intent.putExtra("passwordKasir", listKasir.get(position).getPasswordKasir());
            intent.putExtra("from", "edit");
            mCtx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listKasir.size();
    }
}
