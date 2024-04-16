package com.temankasir.presenter;

import androidx.lifecycle.MutableLiveData;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.CheckoutContract;
import com.temankasir.ui.home.model.HistoriTransaksiModel;
import com.temankasir.ui.home.model.ItemTransaksiModel;
import com.temankasir.ui.transaksi.model.ResponseTransaksiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutPresenter {

    private BaseApiInterface mApiInterface;
    private CheckoutContract.checkoutView view;
    private MutableLiveData<Boolean> isCheckout = new MutableLiveData<>();
    private ArrayList<HistoriTransaksiModel> listHeaderTrans = new ArrayList<>();
    private MutableLiveData<ArrayList<HistoriTransaksiModel>> listHeaderTransLive = new MutableLiveData<>();
    private MutableLiveData<ResponseTransaksiModel> responseTransaksi = new MutableLiveData<>();
    private ArrayList<ItemTransaksiModel> listItem = new ArrayList<>();

    public CheckoutPresenter(CheckoutContract.checkoutView view) {
        this.view = view;
    }

    public void checkout(String idOutlet, String idKasir, String idPelanggan, String iddiskonTrans, String nilaiDiskon, String idtipePembayaran,
                         String isTunai, String totalTrans, String jumlahDibayar, String statusRefund, String statusDibayar, String listItem) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoading();
        mApiInterface.checkoutTrans(idOutlet, idKasir, idPelanggan, iddiskonTrans, nilaiDiskon, idtipePembayaran, isTunai, totalTrans, jumlahDibayar, statusRefund, statusDibayar, listItem).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try{
                        view.hideLoading();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        JSONObject dataResult = jsonResult.getJSONObject("data");
                        if (jsonResult.getString("success").equals("1")) {
                            isCheckout.setValue(true);
                            responseTransaksi.setValue(new ResponseTransaksiModel(dataResult.getString("notransaksi")));
                        } else {
                            isCheckout.setValue(false);
                            view.showToast(jsonResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoading();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoading();
                    }
                } else {
                    view.hideLoading();
                    view.showToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void listHistoriTrans(String idOutlet) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoading();
        listHeaderTrans.clear();
        mApiInterface.listHistoriTransaksi(idOutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try{
                        view.hideLoading();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("success").equals("1")) {
                            JSONArray arrData = jsonResult.getJSONArray("data");

                            for (int i = 0; i < arrData.length(); i++) {
                                JSONObject objData = arrData.getJSONObject(i);

                                JSONArray arrItemList = objData.getJSONArray("itemlist");

                                for (int j = 0; j < arrItemList.length();j++) {

                                    JSONObject objItem = arrItemList.getJSONObject(j);

                                    listItem.add(new ItemTransaksiModel(objItem.getString("namaitem"), objItem.getString("hargaitem"), objItem.getString("jumlahitem")));
                                }

                                listHeaderTrans.add(new HistoriTransaksiModel(objData.getString("idtransheader"), objData.getString("kodetransaksi"), objData.getString("namaoutlet"), objData.getString("namakasir"), objData.getString("namapelanggan"),
                                        objData.getString("datetrans"), objData.getString("totaltrans"), objData.getString("jumlahdibayar"), objData.getString("statusrefund"), objData.getString("statusdibayar"),
                                        objData.getString("tipepembayaran"), listItem));
                            }

                            listHeaderTransLive.setValue(listHeaderTrans);
                        } else {
                            view.showToast(jsonResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoading();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoading();
                    }
                } else {
                    view.hideLoading();
                    view.showToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<Boolean> getIsCheckout() {
        return isCheckout;
    }

    public MutableLiveData<ArrayList<HistoriTransaksiModel>> getListHeaderTransLive() {
        return listHeaderTransLive;
    }

    public MutableLiveData<ResponseTransaksiModel> getResponseTransaksi() {
        return responseTransaksi;
    }
}
