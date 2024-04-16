package com.temankasir.ui.transaksi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.temankasir.R
import com.temankasir.ui.home.model.OrderModel
import com.temankasir.ui.transaksi.adapter.AdapterKeranjang
import kotlinx.android.synthetic.main.activity_keranjang.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class KeranjangActivity : AppCompatActivity() {

    private var listOrder : ArrayList<OrderModel> = ArrayList()
    private var arrIdItem : ArrayList<String> = ArrayList()
    private var arrQtyItem : ArrayList<String> = ArrayList()
    private var arrHargaItem : ArrayList<String> = ArrayList()
    private var totalItem = 0
    private var totalTransaksi = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        listOrder = intent.getParcelableArrayListExtra<OrderModel>("order")!!
        arrIdItem = intent.getStringArrayListExtra("arriditem")!!
        arrQtyItem = intent.getStringArrayListExtra("arrqtyitem")!!
        arrHargaItem = intent.getStringArrayListExtra("arrhargaitem")!!

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

        rvListKeranjangPesanan.apply {
            rvListKeranjangPesanan.layoutManager = LinearLayoutManager(this@KeranjangActivity)
            rvListKeranjangPesanan.adapter = AdapterKeranjang(listOrder)
        }

        for (i in 0 until listOrder.size) {
            var jumlahitem = listOrder[i].jumlahItem
            totalItem = totalItem.plus(jumlahitem.toInt())

            tvJumlahItemKeranjangPesanan.text = totalItem.toString()

            var subtotal = listOrder[i].hargaItem.toInt() * jumlahitem.toInt()
            totalTransaksi = totalTransaksi.plus(subtotal)
            tvTotalTransaksiKeranjangPesanan.text = formatRupiah.format(totalTransaksi)
        }

        llLanjutKeranjangPesanan.setOnClickListener {
            var intent = Intent(this, PembayaranActivity::class.java)
            intent.putExtra("arriditem", arrIdItem)
            intent.putExtra("arrqtyitem", arrQtyItem)
            intent.putExtra("arrhargaitem", arrHargaItem)
            intent.putExtra("order", listOrder)
            startActivity(intent)
        }

        ivKeranjangBackToHome.setOnClickListener {
            this.onBackPressed()
        }
    }
}