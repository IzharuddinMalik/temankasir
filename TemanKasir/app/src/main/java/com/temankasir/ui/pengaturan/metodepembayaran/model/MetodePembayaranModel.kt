package com.temankasir.ui.pengaturan.metodepembayaran.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MetodePembayaranModel(
    @SerializedName("idtipepembayaran") var idTipePembayaran : String?,
    @SerializedName("namatipepembayaran") var namaTipePembayaran : String?,
    @SerializedName("statusaktif") var statusAktif : String?
) : Parcelable {
    constructor() : this(null, null, null)
}