package com.temankasir.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BaseApiInterface {

    @FormUrlEncoded
    @POST("register/signupakun")
    Call<ResponseBody> registerAkun(@Field("namalengkap") String namaLengkap, @Field("username") String username,
                                    @Field("password") String password, @Field("emailbisnis") String emailBisnis, @Field("namabisnis") String namaBisnis,
                                    @Field("notelponbisnis") String noTelponBisnis, @Field("idprovinsi") String idProvinsi, @Field("idkabupaten") String idKabupaten,
                                    @Field("idkecamatan") String idKecamatan);

    @FormUrlEncoded
    @POST("register/cekemailbisnis")
    Call<ResponseBody> cekEmailRegis(@Field("email") String emailBisnis);

    @FormUrlEncoded
    @POST("getwilayah/getprovinsi")
    Call<ResponseBody> getProvinsi(@Field("iduser") String idUser);

    @FormUrlEncoded
    @POST("getwilayah/getkabkota")
    Call<ResponseBody> getKabupaten(@Field("iduser") String idUser, @Field("idprovinsi") String idProvinsi);

    @FormUrlEncoded
    @POST("getwilayah/getkecamatan")
    Call<ResponseBody> getKecamatan(@Field("iduser") String idUser, @Field("idkabupaten") String idKabupaten);

    @FormUrlEncoded
    @POST("login/loginowner")
    Call<ResponseBody> loginOwner(@Field("username") String username, @Field("password") String password,
                                  @Field("token") String token);

    @FormUrlEncoded
    @POST("profile/getprofileakun")
    Call<ResponseBody> getProfileAkun(@Field("idowner") String idOwner);

    @FormUrlEncoded
    @POST("profile/getprofileowner")
    Call<ResponseBody> getProfileOwner(@Field("idowner") String idUser);

    @FormUrlEncoded
    @POST("profile/getprofilebisnis")
    Call<ResponseBody> getProfileBisnis(@Field("idowner") String idUser, @Field("idbisnis") String idBisnis);

    @FormUrlEncoded
    @POST("profile/editprofileowner")
    Call<ResponseBody> editProfileOwner(@Field("idowner") String idOwner, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("profile/editprofilbisnis")
    Call<ResponseBody> editProfileBisnis(@Field("idowner") String idOwner, @Field("idbisnis") String idBisnis, @Field("namabisnis") String namaBisnis,
                                         @Field("notelponbisnis") String noTelponBisnis, @Field("idprovinsi") String idProvinsi, @Field("idkabupaten") String idKabupaten,
                                         @Field("idkecamatan") String idKecamatan, @Field("logobisnis") String logoBisnis);

    @FormUrlEncoded
    @POST("outlet/buatoutlet")
    Call<ResponseBody> buatOutlet(@Field("idowner") String idOwner, @Field("idbisnis") String idBisnis, @Field("namaoutlet") String namaOutlet, @Field("idprovinsi") String idProvinsi,
                                  @Field("idkabupaten") String idKabupaten, @Field("idkecamatan") String idKecamatan);

    @FormUrlEncoded
    @POST("outlet/getlistoutlet")
    Call<ResponseBody> getListOutlet(@Field("idowner") String idOwner, @Field("idbisnis") String idBisnis);

    @FormUrlEncoded
    @POST("outlet/editoutlet")
    Call<ResponseBody> editOutlet(@Field("idowner") String idOwner, @Field("idbisnis") String idBisnis, @Field("namaoutlet") String namaOutlet,
                                  @Field("idprovinsi") String idProvinsi, @Field("idkabupaten") String idKabupaten, @Field("idkecamatan") String idKecamatan,
                                  @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("kasir/buatkasir")
    Call<ResponseBody> buatKasir(@Field("idowner") String idOwner, @Field("idbisnis") String idBisnis, @Field("idoutlet") String idOutlet, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("kasir/getlistkasir")
    Call<ResponseBody> getListKasir(@Field("idowner") String idOwner, @Field("idbisnis") String idBisnis);

    @FormUrlEncoded
    @POST("kasir/editkasir")
    Call<ResponseBody> editKasir(@Field("idowner") String idOwner, @Field("idbisnis") String idBisnis, @Field("idoutlet") String idOutlet, @Field("username") String username, @Field("password") String password, @Field("idkasir") String idKasir);

    @FormUrlEncoded
    @POST("item/buatkategoriitem")
    Call<ResponseBody> addKategoriItem(@Field("idbisnis") String idBisnis, @Field("idoutlet") String idOutlet, @Field("namakategori") String namaKategori);

    @FormUrlEncoded
    @POST("item/editkategoriitem")
    Call<ResponseBody> editKategoriItem(@Field("idkategoriitem") String idKategoriItem, @Field("idoutlet") String idOutlet, @Field("namakategori") String namaKategori);

    @FormUrlEncoded
    @POST("item/listkategoriitem")
    Call<ResponseBody> listKategoriItem(@Field("idbisnis") String idBisnis);

    @FormUrlEncoded
    @POST("item/buatitem")
    Call<ResponseBody> addItem(@Field("idbisnis") String idBisnis, @Field("idoutlet") String idOutlet, @Field("idkategoriitem") String idKategoriItem, @Field("namaitem") String namaItem, @Field("hargaitem") String hargaItem, @Field("stok") String stokItem);

    @FormUrlEncoded
    @POST("item/edititem")
    Call<ResponseBody> editItem(@Field("iditem") String idItem, @Field("idoutlet") String idOutlet, @Field("idkategoriitem") String idKategoriItem, @Field("namaitem") String namaItem, @Field("hargaitem") String hargaItem, @Field("stok") String stokItem);

    @FormUrlEncoded
    @POST("item/listitem")
    Call<ResponseBody> listItem(@Field("idbisnis") String idBisnis);

    @FormUrlEncoded
    @POST("item/hapuskategoriitem")
    Call<ResponseBody> hapusKategoriitem(@Field("idkategoriitem") String idKategoriItem, @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("item/hapusitem")
    Call<ResponseBody> hapusItem(@Field("iditem") String idItem, @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("payment/getpaymentmetode")
    Call<ResponseBody> getPaymentMetode(@Field("idbisnis") String idBisnis);

    @FormUrlEncoded
    @POST("payment/updatestatuspayment")
    Call<ResponseBody> updateStatusPayment(@Field("idbisnis") String idBisnis, @Field("idpayment") String idPayment, @Field("statusaktif") String statusaktif);

    @FormUrlEncoded
    @POST("pelanggan/buatpelanggan")
    Call<ResponseBody> buatPelanggan(@Field("idbisnis") String idBisnis, @Field("namapelanggan") String namaPelanggan, @Field("email") String email, @Field("notelepon") String noTelepon, @Field("tanggallahir") String tanggalLahir);

    @FormUrlEncoded
    @POST("pelanggan/getlistpelanggan")
    Call<ResponseBody> getListPelanggan(@Field("idbisnis") String idBisnis);

    @FormUrlEncoded
    @POST("pelanggan/editpelanggan")
    Call<ResponseBody> editPelanggan(@Field("idpelanggan") String idPelanggan,@Field("idbisnis") String idBisnis, @Field("namapelanggan") String namaPelanggan, @Field("email") String email, @Field("notelepon") String noTelepon, @Field("tanggallahir") String tanggalLahir);

    @FormUrlEncoded
    @POST("pelanggan/hapuspelanggan")
    Call<ResponseBody> hapusPelanggan(@Field("idpelanggan") String idPelanggan);

    @FormUrlEncoded
    @POST("item/listkategoriitembyoutlet")
    Call<ResponseBody> listKategoriItemByOutlet(@Field("idbisnis") String idBisnis, @Field("idoutlet") String idOutlet);

    @FormUrlEncoded
    @POST("item/listitembykategori")
    Call<ResponseBody> listItemByKategori(@Field("idbisnis") String idBisnis, @Field("idoutlet") String idOutlet, @Field("idkategoriitem") String idKategoriItem);

    @FormUrlEncoded
    @POST("payment/getpaymenttunai")
    Call<ResponseBody> listPaymentTunai(@Field("idbisnis") String idBisnis);

    @FormUrlEncoded
    @POST("checkout/checkout")
    Call<ResponseBody> checkoutTrans(@Field("idoutlet") String idOutlet, @Field("idkasir") String idKasir, @Field("idpelanggan") String idPelanggan, @Field("iddiskontrans") String idDiskonTrans, @Field("nilaidiskon") String nilaiDiskon, @Field("idtipepembayaran") String idTipePembayaran, @Field("istunai") String isTunai,
                                     @Field("totaltrans") String totalTrans, @Field("jumlahdibayar") String jumlahDibayar, @Field("statusrefund") String statusRefund, @Field("statusdibayar") String statusDibayar, @Field("listitem") String listItem);

    @FormUrlEncoded
    @POST("checkout/historitransaksi")
    Call<ResponseBody> listHistoriTransaksi(@Field("idoutlet") String idOutlet);

}
