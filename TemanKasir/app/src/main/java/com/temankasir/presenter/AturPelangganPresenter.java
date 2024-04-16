package com.temankasir.presenter;

import androidx.lifecycle.MutableLiveData;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.AturPelangganContract;
import com.temankasir.contract.AturPembayaranContract;
import com.temankasir.ui.pengaturan.aturpelanggan.model.PelangganModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AturPelangganPresenter {

    private AturPelangganContract.aturPelangganView view;
    private MutableLiveData<Boolean> isTambahPelanggan = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEditPelanggan = new MutableLiveData<>();
    private MutableLiveData<Boolean> isHapusPelanggan = new MutableLiveData<>();
    private MutableLiveData<ArrayList<PelangganModel>> listPelanggan = new MutableLiveData<>();
    private ArrayList<PelangganModel> listPelanggans = new ArrayList<>();
    private BaseApiInterface mApiInterface;

    public AturPelangganPresenter(AturPelangganContract.aturPelangganView views) {
        this.view = views;
    }

    public void buatPelanggan(String idBisnis, String namaPelanggan, String email,
                       String noTelepon, String tanggalLahir) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoading();
        mApiInterface.buatPelanggan(idBisnis, namaPelanggan, email, noTelepon, tanggalLahir).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try{
                        view.hideLoading();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);
                        if (jsonResult.getString("success").equals("1")) {
                            isTambahPelanggan.setValue(true);
                        } else {
                            isTambahPelanggan.setValue(false);
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

    public void editPelanggan(String idPelanggan, String idBisnis, String namaPelanggan, String email,
                       String noTelepon, String tanggalLahir){
        mApiInterface = APIUrl.getAPIService();
        view.showLoading();
        mApiInterface.editPelanggan(idPelanggan, idBisnis, namaPelanggan, email, noTelepon, tanggalLahir).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try{
                        view.hideLoading();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("success").equals("1")) {
                            isEditPelanggan.setValue(true);
                        } else {
                            isEditPelanggan.setValue(false);
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

    public void getListPelanggan(String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoading();
        listPelanggans.clear();
        mApiInterface.getListPelanggan(idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try{
                        view.hideLoading();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("success").equals("1")) {
                            JSONArray arrPelanggan = jsonResult.getJSONArray("data");

                            for (int i=0;i<arrPelanggan.length();i++) {
                                JSONObject data = arrPelanggan.getJSONObject(i);
                                listPelanggans.add(new PelangganModel(data.getString("idpelanggan"), data.getString("namapelanggan"), data.getString("email"),
                                        data.getString("notelepon"), data.getString("tanggallahir")));
                            }

                            listPelanggan.setValue(listPelanggans);
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

   public void hapusPelanggna(String idPelanggan) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoading();
        mApiInterface.hapusPelanggan(idPelanggan).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        view.hideLoading();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("success").equals("1")) {
                            isHapusPelanggan.setValue(true);
                        } else {
                            isHapusPelanggan.setValue(false);
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

    public MutableLiveData<Boolean> getIsTambahPelanggan() {
        return isTambahPelanggan;
    }

    public MutableLiveData<Boolean> getIsHapusPelanggan() {
        return isHapusPelanggan;
    }

    public MutableLiveData<Boolean> getIsEditPelanggan() {
        return isEditPelanggan;
    }

    public MutableLiveData<ArrayList<PelangganModel>> getListPelanggan() {
        return listPelanggan;
    }
}
