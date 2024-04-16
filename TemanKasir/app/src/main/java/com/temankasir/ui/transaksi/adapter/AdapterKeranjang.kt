package com.temankasir.ui.transaksi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.temankasir.R
import com.temankasir.ui.home.model.OrderModel
import com.temankasir.ui.pengaturan.metodepembayaran.adapter.AdapterAturMetodePembayaran.AturMetodePembayaranVH
import kotlinx.android.synthetic.main.item_list_preview_pesanan.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterKeranjang(private var listItem : ArrayList<OrderModel>) : RecyclerView.Adapter<AdapterKeranjang.KeranjangVH>() {

    private lateinit var context: Context

    class KeranjangVH(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangVH {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_preview_pesanan, parent, false)
        val ksv = KeranjangVH(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: KeranjangVH, position: Int) {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

        holder.itemView.tvListNamaItemPreviewPesanan.text = listItem[position].namaItem
        holder.itemView.tvListHargaItemPreviewPesanan.text = formatRupiah.format(listItem[position].hargaItem.toInt())
        holder.itemView.tvListJumlahItemPreviewPesanan.text = listItem[position].jumlahItem

        var subtotal = listItem[position].jumlahItem.toInt() * listItem[position].hargaItem.toInt()
        holder.itemView.tvListSubTotalPreviewPesanan.text = formatRupiah.format(subtotal)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}