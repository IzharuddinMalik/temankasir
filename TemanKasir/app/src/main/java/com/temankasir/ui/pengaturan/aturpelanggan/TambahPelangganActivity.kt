package com.temankasir.ui.pengaturan.aturpelanggan

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.temankasir.R
import com.temankasir.contract.AturPelangganContract
import com.temankasir.presenter.AturPelangganPresenter
import com.temankasir.ui.pengaturan.aturpelanggan.adapter.AdapterPelanggan
import com.temankasir.ui.pengaturan.aturpelanggan.model.PelangganModel
import com.temankasir.utils.CustomProgressDialog
import com.temankasir.utils.FormateDate
import kotlinx.android.synthetic.main.activity_tambah_pelanggan.*
import java.util.*

class TambahPelangganActivity : AppCompatActivity(), AturPelangganContract.aturPelangganView,
    DatePickerDialog.OnDateSetListener{

    private var aturPelangganPresenter : AturPelangganPresenter? = null
    private var strIdBisnis : String? = ""
    private var strIdOwner : String? = ""
    private var strFrom : String? = ""
    private var strNamaPelanggan : String? = ""
    private var strEmailPelanggan : String? = ""
    private var strNoTeleponPelanggan : String? = ""

    var days = 0
    var months: Int = 0
    var years: Int = 0

    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0

    var bulanWaktu : String? = ""

    val calendar: Calendar = Calendar.getInstance()

    private var customProgressDialog = CustomProgressDialog()
    private var pelangganModel : PelangganModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pelanggan)

        val sharedPreferences = getSharedPreferences("sessionLogin", MODE_PRIVATE)
        strIdBisnis = sharedPreferences.getString("idBisnis", "")
        strIdOwner = sharedPreferences.getString("idUser", "")

        strFrom = intent.getStringExtra("from")

        if (strFrom.equals("edit")) {
            pelangganModel = intent.getParcelableExtra("data")

            var formateDate = FormateDate()

            tietNamaTambahPelanggan.setText(pelangganModel?.namaPelanggan)
            tietEmailTambahPelanggan.setText(pelangganModel?.emailPelanggan)
            tietNoTeleponTambahPelanggan.setText(pelangganModel?.noTeleponPelanggan)
            tvTTLTambahPelanggan.text = formateDate.formatDateID(pelangganModel?.tanggalLahirPelanggan!!)

            cvSimpanTambahPelanggan.setOnClickListener {
                if (!checkNamaPelanggan()) {
                    aturPelangganPresenter?.editPelanggan(pelangganModel?.idPelanggan, strIdBisnis, pelangganModel?.namaPelanggan, pelangganModel?.emailPelanggan,pelangganModel?.noTeleponPelanggan, pelangganModel?.tanggalLahirPelanggan)
                } else {
                    strEmailPelanggan = tietEmailTambahPelanggan.text.toString()
                    strNoTeleponPelanggan = tietNoTeleponTambahPelanggan.text.toString()
                    aturPelangganPresenter?.editPelanggan(pelangganModel?.idPelanggan, strIdBisnis, strNamaPelanggan, strEmailPelanggan, strNoTeleponPelanggan, bulanWaktu)
                }
            }
        } else {
            cvSimpanTambahPelanggan.setOnClickListener {
                if (!checkNamaPelanggan()) {

                } else {
                    strEmailPelanggan = tietEmailTambahPelanggan.text.toString()
                    strNoTeleponPelanggan = tietNoTeleponTambahPelanggan.text.toString()

                    aturPelangganPresenter?.buatPelanggan(strIdBisnis, strNamaPelanggan, strEmailPelanggan, strNoTeleponPelanggan, bulanWaktu)
                }
            }
        }

        tvTTLTambahPelanggan.setOnClickListener {
            days = calendar.get(Calendar.DAY_OF_MONTH)
            months = calendar.get(Calendar.MONTH)
            years = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, years, months,days)
            datePickerDialog.show()
        }

        aturPelangganPresenter = AturPelangganPresenter(this)

        ivTambahPelangganBackToPengaturan.setOnClickListener {
            this.onBackPressed()
        }

        initLiveData()
    }

    fun initLiveData() {
        aturPelangganPresenter?.isTambahPelanggan?.observe(this) {
            if (it) {
                startActivity(Intent(this, AturPelangganActivity::class.java)).apply {
                    finish()
                }
            }
        }

        aturPelangganPresenter?.isEditPelanggan?.observe(this) {
            if (it) {
                startActivity(Intent(this, AturPelangganActivity::class.java)).apply {
                    finish()
                }
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

    fun checkNamaPelanggan() : Boolean {
        strNamaPelanggan = tietNamaTambahPelanggan.text.toString()

        if (strNamaPelanggan.isNullOrBlank()) {
            tietNamaTambahPelanggan.setError(getString(R.string.title_no_empty))
            return false
        }

        return true
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month+1

        if (myMonth < 10){
            tvTTLTambahPelanggan.setText("" + myDay + "-" + "0" + myMonth + "-" + myYear)
        } else{
            tvTTLTambahPelanggan.setText("" + myDay + "-" + myMonth + "-" + myYear)
        }

        if (myMonth<10){
            bulanWaktu = "" + myYear + "-" + "0" + myMonth + "-" + myDay

            calendar.apply {
                set(Calendar.YEAR, myYear)
                set(Calendar.MONTH,0+myMonth)
                set(Calendar.DAY_OF_MONTH, myDay)
            }

        }else{
            bulanWaktu = "" + myYear + "-" + myMonth + "-" + myDay

            calendar.apply {
                set(Calendar.YEAR, myYear)
                set(Calendar.MONTH, myMonth)
                set(Calendar.DAY_OF_MONTH, myDay)
            }
        }
    }

    @Override
    override fun onBackPressed() {
        startActivity(Intent(this, AturPelangganActivity::class.java)).apply { finish() }
    }
}