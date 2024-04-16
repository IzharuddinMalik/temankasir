package com.temankasir.ui.transaksi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.temankasir.R
import com.temankasir.contract.AturPembayaranContract
import com.temankasir.contract.CheckoutContract
import com.temankasir.presenter.AturPembayaranPresenter
import com.temankasir.presenter.CheckoutPresenter
import com.temankasir.ui.home.model.OrderModel
import com.temankasir.ui.pengaturan.metodepembayaran.model.MetodePembayaranModel
import com.temankasir.ui.transaksi.adapter.AdapterMetodePembayaran
import com.temankasir.ui.transaksi.adapter.AdapterMetodePembayaranTunai
import com.temankasir.ui.transaksi.model.PaymentTunaiModel
import com.temankasir.utils.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_pembayaran.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class PembayaranActivity : AppCompatActivity(), AturPembayaranContract.aturPembayaranView, AdapterMetodePembayaran.Callback,
AdapterMetodePembayaranTunai.Callback, CheckoutContract.checkoutView{

    private var arrIdItem : ArrayList<String> = ArrayList()
    private var arrQtyItem : ArrayList<String> = ArrayList()
    private var arrHargaItem : ArrayList<String> = ArrayList()
    private var listOrder : ArrayList<OrderModel> = ArrayList()
    private var customProgressDialog = CustomProgressDialog()
    private var aturPembayaranPresenter : AturPembayaranPresenter? = null
    private var checkoutPresenter : CheckoutPresenter? = null

    private var totalItem = 0
    private var totalTransaksi = 0

    private var strIdBisnis : String? = ""
    private var strPayment : String? = ""
    private var strNominal : String? = ""
    private var strIdOutlet : String? = ""
    private var strIdUser : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        val sharedPreferences = getSharedPreferences("sessionLogin", MODE_PRIVATE)
        strIdBisnis = sharedPreferences.getString("idBisnis", "")
        strIdOutlet = sharedPreferences.getString("idOutlet", "")
        strIdUser = sharedPreferences.getString("idUser", "")

        arrIdItem = intent.getStringArrayListExtra("arriditem")!!
        arrQtyItem = intent.getStringArrayListExtra("arrqtyitem")!!
        arrHargaItem = intent.getStringArrayListExtra("arrhargaitem")!!
        listOrder = intent.getParcelableArrayListExtra<OrderModel>("order")!!

        Log.i("ARRIDITEM", " === " + arrIdItem + " qty " + arrQtyItem + " RP " + arrHargaItem)

        aturPembayaranPresenter = AturPembayaranPresenter(this)
        aturPembayaranPresenter?.getMetodePembayaran(strIdBisnis)
        aturPembayaranPresenter?.getPaymentTunai(strIdBisnis)

        for (i in 0 until listOrder.size) {
            var jumlahitem = listOrder[i].jumlahItem
            totalItem = totalItem.plus(jumlahitem.toInt())

            var subtotal = listOrder[i].hargaItem.toInt() * jumlahitem.toInt()
            totalTransaksi = totalTransaksi.plus(subtotal)
        }

        checkoutPresenter = CheckoutPresenter(this)

        llLakukanPembayaranTransaksi.setOnClickListener {
            if (!onCheckPayment()) {

            } else {
                if (strNominal.equals("0") || strNominal == "0") {

                    var jsonObject = JSONObject()
                    var jsonArray = JSONArray()

                    try {
                        jsonObject = JSONObject()
                        for (i in 0 until arrIdItem.size) {
                            try {
                                jsonObject.put("iditem", arrIdItem[i])
                                jsonObject.put("jumlahitem", arrQtyItem[i])
                                jsonObject.put("hargaitem", arrHargaItem[i])
                                jsonObject.put("iddiskonitem", "0")
                                jsonObject.put("nilaidiskon", "0")
                                jsonObject.put("hargaitemdiskon", "0")

                                jsonArray.put(jsonObject)
                            } catch (e : JSONException) {
                                e.printStackTrace()
                            }
                        }
                    }catch (e : JSONException) {
                        e.printStackTrace()
                    }

                    checkoutPresenter?.checkout(strIdOutlet, strIdUser, "0", "0", "0", strPayment,
                        "0", totalTransaksi.toString(), totalTransaksi.toString(), "0", "1", jsonArray.toString())
                } else {
                    var jsonObject = JSONObject()
                    var jsonArray = JSONArray()

                    try {

                        jsonObject = JSONObject()

                        for (i in 0 until arrIdItem.size) {
                            try {
                                jsonObject.put("iditem", arrIdItem[i])
                                jsonObject.put("jumlahitem", arrQtyItem[i])
                                jsonObject.put("hargaitem", arrHargaItem[i])
                                jsonObject.put("iddiskonitem", "0")
                                jsonObject.put("nilaidiskon", "0")
                                jsonObject.put("hargaitemdiskon", "0")

                                jsonArray.put(jsonObject)
                            } catch (e : JSONException) {
                                e.printStackTrace()
                            }
                        }
                    }catch (e : JSONException) {
                        e.printStackTrace()
                    }

                    checkoutPresenter?.checkout(strIdOutlet, strIdUser, "0", "0", "0", strPayment,
                        "1", totalTransaksi.toString(), strNominal, "0", "1", jsonArray.toString())
                }
            }
        }

        initLiveData()
    }

    fun initLiveData() {
        aturPembayaranPresenter?.onListMetodePembayaran()?.observe(this) {
            rvListMetodePembayaranTransaksi.apply {
                rvListMetodePembayaranTransaksi.layoutManager = GridLayoutManager(this@PembayaranActivity, 3)
                rvListMetodePembayaranTransaksi.adapter = AdapterMetodePembayaran(it, this@PembayaranActivity)
            }
        }

        aturPembayaranPresenter?.listPaymentTunaiLive?.observe(this) {
            rvListMetodePembayaranTunaiTransaksi.apply {
                rvListMetodePembayaranTunaiTransaksi.layoutManager = GridLayoutManager(this@PembayaranActivity, 3)
                rvListMetodePembayaranTunaiTransaksi.adapter = AdapterMetodePembayaranTunai(it, this@PembayaranActivity)
            }
        }

        checkoutPresenter?.isCheckout?.observe(this) {
            if (!it) {
                Toast.makeText(this, "Transaksi Gagal!", Toast.LENGTH_LONG).show()
            }
        }

        checkoutPresenter?.responseTransaksi?.observe(this) {

            var kembalian = 0

            if (strNominal == "0" || strNominal.equals("0")) {
                kembalian = 0
            } else {
                kembalian = strNominal!!.toInt() - totalTransaksi
            }

            var intent = Intent(this, ReceiptTransaksiActivity::class.java)
            intent.putExtra("notransaksi", it.noTransaksi)
            intent.putExtra("kembalian", kembalian.toString())
            startActivity(intent)
            finish()
        }
    }

    override fun showLoading() {
        customProgressDialog.show(this, getString(R.string.title_loading))
    }

    override fun hideLoading() {
        customProgressDialog.dialog.dismiss()
    }

    override fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun setPayment(metodePembayaranModel: MetodePembayaranModel) {
        strPayment = metodePembayaranModel.idTipePembayaran
        strNominal = "0"
    }

    override fun setTunai(paymentTunaiModel: PaymentTunaiModel) {
        strPayment = paymentTunaiModel.idTunai
        strNominal = paymentTunaiModel.nominalTunai
    }

    fun onCheckPayment() : Boolean {
        if (strPayment.isNullOrBlank()) {
            Toast.makeText(this, "Pilih metode pembayaran", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
}