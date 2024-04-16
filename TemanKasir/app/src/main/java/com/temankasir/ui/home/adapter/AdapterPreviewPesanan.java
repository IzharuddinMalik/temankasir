package com.temankasir.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.home.model.OrderModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterPreviewPesanan extends RecyclerView.Adapter<AdapterPreviewPesanan.PreviewPesananVH> {

    private Context context;
    private ArrayList<OrderModel> listOrder;

    public AdapterPreviewPesanan(Context context, ArrayList<OrderModel> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public PreviewPesananVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_preview_pesanan, parent, false);

        return new PreviewPesananVH(view);
    }

    public class PreviewPesananVH extends RecyclerView.ViewHolder{

        TextView tvNamaItem, tvHargaItem, tvQtyItem, tvSubTotal;

        public PreviewPesananVH(View view) {
            super(view);

            tvNamaItem = view.findViewById(R.id.tvListNamaItemPreviewPesanan);
            tvHargaItem = view.findViewById(R.id.tvListHargaItemPreviewPesanan);
            tvQtyItem = view.findViewById(R.id.tvListJumlahItemPreviewPesanan);
            tvSubTotal = view.findViewById(R.id.tvListSubTotalPreviewPesanan);
        }
    }

    @Override
    public void onBindViewHolder(PreviewPesananVH holder, int position) {

        OrderModel orderModel = listOrder.get(position);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        holder.tvNamaItem.setText(orderModel.getNamaItem());
        holder.tvHargaItem.setText(formatRupiah.format(Integer.parseInt(orderModel.getHargaItem())));
        holder.tvQtyItem.setText(orderModel.getJumlahItem());

        int subtotal = Integer.parseInt(orderModel.getJumlahItem()) * Integer.parseInt(orderModel.getHargaItem());
        holder.tvSubTotal.setText(formatRupiah.format(subtotal));
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }
}
