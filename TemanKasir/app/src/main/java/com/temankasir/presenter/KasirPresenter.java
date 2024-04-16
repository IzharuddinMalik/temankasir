package com.temankasir.presenter;

import androidx.lifecycle.MutableLiveData;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.KasirContract;
import com.temankasir.ui.pengaturan.aturkasir.model.KasirModel;
import com.temankasir.ui.pengaturan.aturoutlet.model.OutletModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KasirPresenter {

    BaseApiInterface mApiInterface;
    KasirContract.kasirView view;
    private MutableLiveData<Boolean> statusAddKasir = new MutableLiveData<>();
    private MutableLiveData<Boolean> statusEditKasir = new MutableLiveData<>();
    private MutableLiveData<ArrayList<KasirModel>> listKasirLive = new MutableLiveData<>();
    private ArrayList<KasirModel> listKasir = new ArrayList<>();
    private MutableLiveData<ArrayList<OutletModel>> listOutletLive = new MutableLiveData<>();
    private ArrayList<OutletModel> listOutlet = new ArrayList<>();

    public KasirPresenter(KasirContract.kasirView view) {
        this.view = view;
    }

    public void addKasir(String idOwner, String idBisnis, String idOutlet, String username, String password){
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingKasir();
        mApiInterface.buatKasir(idOwner, idBisnis, idOutlet, username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingKasir();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            statusAddKasir.setValue(true);
                        } else {
                            statusAddKasir.setValue(false);
                            view.showToastKasir(objResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingKasir();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingKasir();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingKasir();
            }
        });
    }

    public void editKasir(String idOwner, String idBisnis, String idOutlet, String username, String password, String idKasir) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingKasir();
        mApiInterface.editKasir(idOwner, idBisnis, idOutlet, username, password, idKasir).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingKasir();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);
                        if (objResult.getString("success").equals("1")) {
                            statusEditKasir.setValue(true);
                        } else {
                            statusEditKasir.setValue(false);
                            view.showToastKasir(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingKasir();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingKasir();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingKasir();
            }
        });
    }

    public void getListKasir(String idOwner, String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingKasir();
        mApiInterface.getListKasir(idOwner, idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingKasir();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONArray arrData = objResult.getJSONArray("data");
                            for (int i=0;i<arrData.length();i++) {
                                JSONObject obData = arrData.getJSONObject(i);

                                listKasir.add(new KasirModel("" + obData.getString("idkasir"), "" + obData.getString("username"), "" + obData.getString("namaoutlet"), "" + obData.getString("password"), "" + obData.getString("idoutlet")));
                            }

                            listKasirLive.setValue(listKasir);
                        } else {
                            view.showToastKasir(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingKasir();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingKasir();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingKasir();
            }
        });
    }

    public void getListOutlet(String idOwner, String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingKasir();
        mApiInterface.getListOutlet(idOwner, idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingKasir();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONArray arrData = objResult.getJSONArray("data");
                            for (int i=0; i < arrData.length();i++) {
                                JSONObject objData = arrData.getJSONObject(i);

                                listOutlet.add(new OutletModel("" + objData.getString("idoutlet"), "" + objData.getString("namaoutlet"), "" + objData.getString("idprovinsi"), "" + objData.getString("namaprovinsi"), "" + objData.getString("idkabupaten"), "" + objData.getString("namakabupaten"), "" + objData.getString("idkecamatan"), "" + objData.getString("namakecamatan")));
                            }

                            listOutletLive.setValue(listOutlet);
                        } else {
                            view.showToastKasir(objResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingKasir();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingKasir();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingKasir();
            }
        });
    }

    public MutableLiveData<Boolean> statusAddKasir() {
        return statusAddKasir;
    }

    public MutableLiveData<Boolean> statusEditKasir() {
        return statusEditKasir;
    }

    public MutableLiveData<ArrayList<KasirModel>> onListKasir() {
        return listKasirLive;
    }

    public MutableLiveData<ArrayList<OutletModel>> listOutlet() {
        return listOutletLive;
    }
}
