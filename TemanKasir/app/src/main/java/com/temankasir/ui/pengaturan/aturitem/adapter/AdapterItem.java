package com.temankasir.ui.pengaturan.aturitem.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.pengaturan.aturitem.TambahItemActivity;
import com.temankasir.ui.pengaturan.aturitem.model.ItemModel;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {

    private Context mCtx;
    private List<ItemModel> listItem;

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public AdapterItem(Context context, List<ItemModel> listItem){
        this.mCtx = context;
        this.listItem = listItem;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_item, parent, false);

        return new ItemViewHolder(view);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvNamaItem, tvNamaKategoriItem, tvHargaItem, tvStokItem, tvOutletItem;
        CardView cvEdit;

        public ItemViewHolder(View view) {
            super(view);

            tvNamaItem = view.findViewById(R.id.tvNamaItemListItem);
            tvNamaKategoriItem = view.findViewById(R.id.tvNamaKategoriListItem);
            tvHargaItem = view.findViewById(R.id.tvHargaItemListItem);
            tvStokItem = view.findViewById(R.id.tvStokItemListItem);
            tvOutletItem = view.findViewById(R.id.tvNamaOutletListItem);
            cvEdit = view.findViewById(R.id.cvEditListItem);
        }
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ItemModel item = listItem.get(position);

        holder.tvNamaItem.setText(item.getNamaItem());
        holder.tvNamaKategoriItem.setText(item.getNamaKategoriItem());
        holder.tvHargaItem.setText(formatRupiah.format(Integer.parseInt(item.getHargaItem())));

        if (Integer.parseInt(item.getStokItem()) < 10 ){
            holder.tvStokItem.setTextColor(mCtx.getResources().getColor(R.color.rednotif));
        } else {
            holder.tvStokItem.setTextColor(mCtx.getResources().getColor(R.color.darkOrange));
        }
        holder.tvStokItem.setText(item.getStokItem());
        holder.tvOutletItem.setText(item.getNamaOutletItem());

        holder.cvEdit.setOnClickListener(view -> {
            Intent intent = new Intent(mCtx, TambahItemActivity.class);
            intent.putExtra("data", item);
            intent.putExtra("from", "edit");
            mCtx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
