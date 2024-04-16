package com.temankasir.presenter;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonIOException;
import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.AturPembayaranContract;
import com.temankasir.ui.pengaturan.metodepembayaran.model.MetodePembayaranModel;
import com.temankasir.ui.transaksi.model.PaymentTunaiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AturPembayaranPresenter {

    private AturPembayaranContract.aturPembayaranView view;
    private BaseApiInterface mApiInterface;
    private ArrayList<MetodePembayaranModel> listMetodePembayaran = new ArrayList<>();
    private MutableLiveData<ArrayList<MetodePembayaranModel>> listMetodePembayaranLive = new MutableLiveData<>();
    private MutableLiveData<Boolean> sendAktiv = new MutableLiveData<>();
    private ArrayList<PaymentTunaiModel> listPaymentTunai = new ArrayList<>();
    private MutableLiveData<ArrayList<PaymentTunaiModel>> listPaymentTunaiLive = new MutableLiveData<>();

    public AturPembayaranPresenter(AturPembayaranContract.aturPembayaranView views) {
        this.view = views;
    }

    public void getMetodePembayaran(String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoading();
        listMetodePembayaran.clear();
        mApiInterface.getPaymentMetode(idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try{
                        view.hideLoading();

                        String result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);

                        if (jsonObject.getString("success").equals("1")) {
                            JSONArray arrData = jsonObject.getJSONArray("data");
                            for (int i = 0;i< arrData.length();i++) {
                                JSONObject objData = arrData.getJSONObject(i);

                                listMetodePembayaran.add(new MetodePembayaranModel(objData.getString("idtipepembayaran"), objData.getString("namatipepembayaran"), objData.getString("statusaktif")));
                            }

                            listMetodePembayaranLive.setValue(listMetodePembayaran);
                        } else {
                            view.hideLoading();
                            view.showToast(jsonObject.getString("message"));
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

    public void updateStatusPayment(String idBisnis, String idPayment, String statusaktif){
        mApiInterface = APIUrl.getAPIService();
        view.showLoading();
        mApiInterface.updateStatusPayment(idBisnis, idPayment, statusaktif).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200) {
                    try {
                        view.hideLoading();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("success").equals("1")) {
                            sendAktiv.setValue(true);
                        } else {
                            sendAktiv.setValue(false);
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
                    view.showToast(response.message());
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getPaymentTunai(String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.listPaymentTunai(idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("success").equals("1")) {
                            JSONArray arrTunai = jsonResult.getJSONArray("data");

                            for (int i = 0; i < arrTunai.length(); i++) {
                                JSONObject objData = arrTunai.getJSONObject(i);

                                listPaymentTunai.add(new PaymentTunaiModel(objData.getString("idtunai"), objData.getString("nominaltunai")));
                            }

                            listPaymentTunaiLive.setValue(listPaymentTunai);
                        } else {
                            view.showToast(jsonResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
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

    public MutableLiveData<ArrayList<MetodePembayaranModel>> onListMetodePembayaran() {
        return listMetodePembayaranLive;
    }

    public MutableLiveData<Boolean> getSendAktiv() {
        return sendAktiv;
    }

    public MutableLiveData<ArrayList<PaymentTunaiModel>> getListPaymentTunaiLive() {
        return listPaymentTunaiLive;
    }
}
