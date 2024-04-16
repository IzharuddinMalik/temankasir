package com.temankasir.ui.pengaturan.metodepembayaran.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.ui.pengaturan.metodepembayaran.model.MetodePembayaranModel;

import java.util.ArrayList;

public class AdapterAturMetodePembayaran extends RecyclerView.Adapter<AdapterAturMetodePembayaran.AturMetodePembayaranVH> {

    Context context;
    ArrayList<MetodePembayaranModel> listMetodePembayaran;
    Callback callback;

    public AdapterAturMetodePembayaran(Context context, ArrayList<MetodePembayaranModel> listMetodePembayaran, Callback callback){
        this.context = context;
        this.listMetodePembayaran = listMetodePembayaran;
        this.callback = callback;
    }

    @Override
    public AturMetodePembayaranVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_list_metodepembayaran, parent, false);

        return new AturMetodePembayaranVH(view);
    }

    public class AturMetodePembayaranVH extends RecyclerView.ViewHolder {
        TextView tvNamaPayment;
        Switch swAktifPayment;

        public AturMetodePembayaranVH(View view){
            super(view);

            tvNamaPayment = view.findViewById(R.id.tvListNamaPayment);
            swAktifPayment = view.findViewById(R.id.swListAktifPayment);
        }
    }

    @Override
    public void onBindViewHolder(AturMetodePembayaranVH holder, int position) {

        MetodePembayaranModel payment = listMetodePembayaran.get(position);

        holder.tvNamaPayment.setText(payment.getNamaTipePembayaran());

        if (payment.getStatusAktif().equals("0")) {
            holder.swAktifPayment.setChecked(false);
        } else {
            holder.swAktifPayment.setChecked(true);
        }

        holder.swAktifPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    callback.setAktif(payment.getIdTipePembayaran(), "1");
                } else {
                    callback.setAktif(payment.getIdTipePembayaran(), "0");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMetodePembayaran.size();
    }

    public interface Callback{
        void setAktif(String idPayment, String status);
    }
}
