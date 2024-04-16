package com.temankasir.ui.pengaturan.metodepembayaran

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.temankasir.R
import com.temankasir.contract.AturPembayaranContract
import com.temankasir.presenter.AturPembayaranPresenter
import com.temankasir.ui.home.MainActivity
import com.temankasir.ui.pengaturan.metodepembayaran.adapter.AdapterAturMetodePembayaran
import com.temankasir.utils.CustomProgressDialog

class AturMetodePembayaranActivity : AppCompatActivity(), AturPembayaranContract.aturPembayaranView, AdapterAturMetodePembayaran.Callback {

    private var aturPembayaranPresenter : AturPembayaranPresenter? = null
    private var rvListPayment : RecyclerView? = null
    private var customProgressDialog = CustomProgressDialog()
    private var ivBack : ImageView? = null
    private var strIdBisnis : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atur_metode_pembayaran)

        rvListPayment = findViewById(R.id.rvListAturMetodePembayaran)
        ivBack = findViewById(R.id.ivAturPaymentBackToPengaturan)

        val sharedPreferences = getSharedPreferences("sessionLogin", MODE_PRIVATE)
        strIdBisnis = sharedPreferences.getString("idBisnis", "")

        aturPembayaranPresenter = AturPembayaranPresenter(this)
        aturPembayaranPresenter?.getMetodePembayaran(strIdBisnis)

        ivBack?.setOnClickListener {
            this.onBackPressed()
        }

        initLiveData()
    }

    fun initLiveData() {
        aturPembayaranPresenter?.onListMetodePembayaran()?.observe(this) {
            rvListPayment?.apply {
                rvListPayment?.layoutManager = LinearLayoutManager(this@AturMetodePembayaranActivity)
                rvListPayment?.adapter = AdapterAturMetodePembayaran(this@AturMetodePembayaranActivity, it, this@AturMetodePembayaranActivity)
            }
        }

        aturPembayaranPresenter?.sendAktiv?.observe(this) {
            if (it) {
                aturPembayaranPresenter?.getMetodePembayaran(strIdBisnis)
            }
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

    override fun setAktif(idPayment: String?, status: String?) {
        aturPembayaranPresenter?.updateStatusPayment(strIdBisnis, idPayment, status)
    }

    @Override
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fragment", "pengaturan")
        startActivity(intent)
    }
}