package com.temankasir.ui.transaksi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.temankasir.R
import com.temankasir.ui.pengaturan.metodepembayaran.model.MetodePembayaranModel
import kotlinx.android.synthetic.main.item_list_metode_pembayaran.view.*

class AdapterMetodePembayaran(private var listItem : ArrayList<MetodePembayaranModel>, var callback : Callback) : RecyclerView.Adapter<AdapterMetodePembayaran.MetodePembayaranVH>() {

    private lateinit var context: Context
    private var posisi = -1

    class MetodePembayaranVH(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetodePembayaranVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_metode_pembayaran, parent, false)
        val ksv = MetodePembayaranVH(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: MetodePembayaranVH, position: Int) {
        holder.itemView.tvListNamaMetodePembayaran.text = listItem[position].namaTipePembayaran

        holder.itemView.cvListMetodePembayaran.setOnClickListener {
            if (holder.itemView.tvListNamaMetodePembayaran.textColors.defaultColor == context.resources.getColor(R.color.white)) {
                posisi = -1
                notifyDataSetChanged()
            } else {
                posisi = position
                callback.setPayment(listItem[position])
                notifyDataSetChanged()
            }
        }

        if (posisi == position) {
            holder.itemView.cvListMetodePembayaran.setCardBackgroundColor(context.resources.getColor(R.color.tosca))
            holder.itemView.tvListNamaMetodePembayaran.setTextColor(context.resources.getColor(R.color.white))
        } else {
            holder.itemView.cvListMetodePembayaran.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.itemView.tvListNamaMetodePembayaran.setTextColor(context.resources.getColor(R.color.tosca))
        }

        if (listItem[position].statusAktif == "0") {
            holder.itemView.cvListMetodePembayaran.setCardBackgroundColor(context.resources.getColor(R.color.gray8b))
            holder.itemView.tvListNamaMetodePembayaran.setTextColor(context.resources.getColor(R.color.white))
            holder.itemView.cvListMetodePembayaran.isEnabled = false
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    interface Callback {
        fun setPayment(metodePembayaranModel: MetodePembayaranModel)
    }
}