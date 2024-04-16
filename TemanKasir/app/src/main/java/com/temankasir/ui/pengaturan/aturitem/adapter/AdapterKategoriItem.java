package com.temankasir.ui.pengaturan.aturitem.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.pengaturan.aturitem.TambahKategoriItemActivity;
import com.temankasir.ui.pengaturan.aturitem.model.KategoriItemModel;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.LambdaConversionException;
import java.util.List;

public class AdapterKategoriItem extends RecyclerView.Adapter<AdapterKategoriItem.KategoriViewHolder> {

    private Context mCtx;
    List<KategoriItemModel> listKategori;

    public AdapterKategoriItem(Context context, List<KategoriItemModel> listKategori) {
        this.mCtx = context;
        this.listKategori = listKategori;
    }

    @Override
    public KategoriViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_kategori_item, parent, false);

        return new KategoriViewHolder(view);
    }

    public class KategoriViewHolder extends RecyclerView.ViewHolder{
        TextView tvNamaKategori, tvOutlet;
        CardView cvEdit;

        public KategoriViewHolder(View view) {
            super(view);

            tvNamaKategori = view.findViewById(R.id.tvNamaKategoriListKategori);
            tvOutlet = view.findViewById(R.id.tvNamaOutletListKategori);
            cvEdit = view.findViewById(R.id.cvEditListKategori);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterKategoriItem.KategoriViewHolder holder, int position) {
        KategoriItemModel kategori = listKategori.get(position);

        holder.tvNamaKategori.setText(kategori.getNamaKategori());
        holder.tvOutlet.setText(kategori.getNamaOutletKategori());

        holder.cvEdit.setOnClickListener(v -> {
            Intent intent = new Intent(mCtx, TambahKategoriItemActivity.class);
            intent.putExtra("data", listKategori.get(position));
            intent.putExtra("from", "edit");
            mCtx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }
}
