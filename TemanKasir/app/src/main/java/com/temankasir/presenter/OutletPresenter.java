package com.temankasir.presenter;

import androidx.lifecycle.MutableLiveData;

import com.temankasir.api.APIClient;
import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.OutletContract;
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

public class OutletPresenter {

    OutletContract.outletView view;
    BaseApiInterface mApiInterface;
    private MutableLiveData<Boolean> statusBuatOutlet = new MutableLiveData<>();
    private MutableLiveData<Boolean> statusEditOutlet = new MutableLiveData<>();
    private ArrayList<OutletModel> listOutlet = new ArrayList<>();
    private MutableLiveData<ArrayList<OutletModel>> listOutletLive = new MutableLiveData<>();

    public OutletPresenter(OutletContract.outletView view) {
        this.view = view;
    }

    public void buatOutlet(String idOwner, String idBisnis, String namaOutlet, String idProvinsi, String idKabupaten, String idKecamatan) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingOutlet();
        mApiInterface.buatOutlet(idOwner, idBisnis, namaOutlet, idProvinsi, idKabupaten, idKecamatan).enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                view.hideLoadingOutlet();

                                String result = response.body().string();

                                JSONObject objResult = new JSONObject(result);

                                if (objResult.getString("success").equals("1")) {
                                    statusBuatOutlet.setValue(true);
                                } else {
                                    statusBuatOutlet.setValue(false);
                                    view.showToastOutlet(objResult.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                view.hideLoadingOutlet();
                            } catch (IOException e) {
                                e.printStackTrace();
                                view.hideLoadingOutlet();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        view.hideLoadingOutlet();
                    }
                }
        );
    }

    public void getListOutlet(String idOwner, String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingOutlet();
        mApiInterface.getListOutlet(idOwner, idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingOutlet();

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
                            view.showToastOutlet(objResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingOutlet();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingOutlet();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingOutlet();
            }
        });
    }

    public void editOutlet(String idOwner, String idBisnis, String namaOutlet, String idProvinsi, String idKabupaten, String idKecamatan, String idOutlet) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingOutlet();
        mApiInterface.editOutlet(idOwner, idBisnis, namaOutlet, idProvinsi, idKabupaten, idKecamatan, idOutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingOutlet();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            statusEditOutlet.setValue(true);
                        } else {
                            statusEditOutlet.setValue(false);
                            view.showToastOutlet(objResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingOutlet();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingOutlet();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingOutlet();
            }
        });
    }

    public MutableLiveData<Boolean> onBuatOutlet() {
        return statusBuatOutlet;
    }

    public MutableLiveData<Boolean> onEditOutlet() {
        return statusEditOutlet;
    }

    public MutableLiveData<ArrayList<OutletModel>> onListOutlet() {
        return listOutletLive;
    }
}
