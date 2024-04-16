package com.temankasir.ui.transaksi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.temankasir.R
import com.temankasir.ui.transaksi.model.PaymentTunaiModel
import kotlinx.android.synthetic.main.item_list_metode_pembayaran.view.*
import kotlinx.android.synthetic.main.item_list_payment_tunai.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterMetodePembayaranTunai(private var itemList : ArrayList<PaymentTunaiModel>, var callback : Callback) : RecyclerView.Adapter<AdapterMetodePembayaranTunai.PaymentTunaiVH>() {

    private lateinit var context: Context
    private var posisi = -1

    class PaymentTunaiVH(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentTunaiVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_payment_tunai, parent, false)
        val ksv = PaymentTunaiVH(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: PaymentTunaiVH, position: Int) {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

        holder.itemView.tvListNamaMetodePembayaranTunai.text = formatRupiah.format(itemList[position].nominalTunai.toInt())
        holder.itemView.cvListMetodePembayaranTunai.setOnClickListener {
            if (holder.itemView.tvListNamaMetodePembayaranTunai.textColors.defaultColor == context.resources.getColor(R.color.white)){
                posisi = -1
                notifyDataSetChanged()
            } else {
                posisi = position
                callback.setTunai(itemList[position])
                notifyDataSetChanged()
            }
        }

        if (posisi == position) {
            holder.itemView.cvListMetodePembayaranTunai.setCardBackgroundColor(context.resources.getColor(R.color.tosca))
            holder.itemView.tvListNamaMetodePembayaranTunai.setTextColor(context.resources.getColor(R.color.white))
        } else {
            holder.itemView.cvListMetodePembayaranTunai.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.itemView.tvListNamaMetodePembayaranTunai.setTextColor(context.resources.getColor(R.color.tosca))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface Callback {
        fun setTunai(paymentTunaiModel: PaymentTunaiModel)
    }
}