package com.temankasir.ui.pengaturan.aturpelanggan

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.temankasir.R
import com.temankasir.contract.AturPelangganContract
import com.temankasir.presenter.AturPelangganPresenter
import com.temankasir.ui.home.MainActivity
import com.temankasir.ui.pengaturan.aturpelanggan.adapter.AdapterPelanggan
import com.temankasir.ui.pengaturan.aturpelanggan.model.PelangganModel
import com.temankasir.utils.CustomProgressDialog
import com.temankasir.utils.FormateDate
import kotlinx.android.synthetic.main.activity_atur_pelanggan.*

class AturPelangganActivity : AppCompatActivity(), AturPelangganContract.aturPelangganView, AdapterPelanggan.Callback {

    private var aturPelangganPresenter : AturPelangganPresenter? = null
    private var customProgressDialog = CustomProgressDialog()
    private var strIdBisnis : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atur_pelanggan)

        val sharedPreferences = getSharedPreferences("sessionLogin", MODE_PRIVATE)
        strIdBisnis = sharedPreferences.getString("idBisnis", "")
        val idOwner = sharedPreferences.getString("idUser", "")

        aturPelangganPresenter = AturPelangganPresenter(this)
        aturPelangganPresenter?.getListPelanggan(strIdBisnis)

        cvTambahPelanggan.setOnClickListener {
            startActivity(Intent(this, TambahPelangganActivity::class.java))
        }

        initLiveData()
    }

    fun initLiveData() {
        aturPelangganPresenter?.listPelanggan?.observe(this) {
            rvListAturPelanggan.apply {
                rvListAturPelanggan.layoutManager = LinearLayoutManager(this@AturPelangganActivity)
                rvListAturPelanggan.adapter = AdapterPelanggan(this@AturPelangganActivity, it, this@AturPelangganActivity)
            }
        }

        aturPelangganPresenter?.isHapusPelanggan?.observe(this) {
            if (it) {
                aturPelangganPresenter?.getListPelanggan(strIdBisnis)
            }
        }

        ivAturPelangganBackToPengaturan.setOnClickListener {
            this.onBackPressed()
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

    override fun showDetail(pelangganModel: PelangganModel?) {
        val dialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.dialog_detail_pelanggan, null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        val window = dialog!!.window
        window!!.setGravity(Gravity.CENTER)
        window!!.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window!!.setBackgroundDrawable(resources.getDrawable(android.R.drawable.screen_background_light_transparent))

        val ivClose = dialog.findViewById<ImageView>(R.id.ivCloseDialogDetailPelanggan)
        var tvNamaPelanggan = dialog.findViewById<TextView>(R.id.tvNamaDialogDetailPelanggan)
        var tvEmailPelanggan = dialog.findViewById<TextView>(R.id.tvEmailDialogDetailPelanggan)
        var tvNoTeleponPelanggan = dialog.findViewById<TextView>(R.id.tvNoTeleponDialogDetailPelanggan)
        var tvTTLPelanggan = dialog.findViewById<TextView>(R.id.tvBirthdateDialogDetailPelanggan)
        var btnHapusPelanggan = dialog.findViewById<Button>(R.id.btnHapusDialogDetailPelanggan)
        var btnEditPelanggan = dialog.findViewById<Button>(R.id.btnEditDialogDetailPelanggan)

        ivClose?.setOnClickListener {
            dialog.dismiss()
        }

        var formateDate = FormateDate()

        tvNamaPelanggan?.text = pelangganModel?.namaPelanggan
        tvEmailPelanggan?.text = pelangganModel?.emailPelanggan
        tvNoTeleponPelanggan?.text = pelangganModel?.noTeleponPelanggan
        tvTTLPelanggan?.text = formateDate.formatDateID(pelangganModel?.tanggalLahirPelanggan!!)

        btnHapusPelanggan?.setOnClickListener {
            aturPelangganPresenter?.hapusPelanggna(pelangganModel?.idPelanggan)
            dialog.dismiss()
        }

        btnEditPelanggan?.setOnClickListener {
            var intent = Intent(this, TambahPelangganActivity::class.java)
            intent.putExtra("from", "edit")
            intent.putExtra("data", pelangganModel)
            startActivity(intent)
        }

        dialog.show()
    }

    @Override
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fragment", "pengaturan")
        startActivity(intent)
    }
}