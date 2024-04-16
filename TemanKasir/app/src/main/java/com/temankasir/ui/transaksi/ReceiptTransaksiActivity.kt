package com.temankasir.ui.transaksi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.temankasir.R
import com.temankasir.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_receipt_transaksi.*
import java.text.NumberFormat
import java.util.*

class ReceiptTransaksiActivity : AppCompatActivity() {

    var strNoTransaksi : String? = ""
    var strKembalian : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt_transaksi)

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

        strNoTransaksi = intent.getStringExtra("notransaksi")
        strKembalian = intent.getStringExtra("kembalian")

        tvKodeTransReceipt.text = strNoTransaksi
        tvKembalianReceipt.text = formatRupiah.format(strKembalian!!.toDouble())

        cvBackToTransaksiReceipt.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java)).apply {
                finish()
            }
        }
    }

    @Override
    override fun onBackPressed() {

    }
}