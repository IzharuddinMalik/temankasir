package com.temankasir.presenter;

import androidx.lifecycle.MutableLiveData;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.ProfileBisnisContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileBisnisPresenter {

    BaseApiInterface mApiInterface;
    ProfileBisnisContract.profileBisnisView view;
    public MutableLiveData<Boolean> statusEditBisnis = new MutableLiveData<>();

    public ProfileBisnisPresenter(ProfileBisnisContract.profileBisnisView view) {
        this.view = view;
    }

    public void sendGetProfileBisnis(String idOwner, String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingProfileBisnis();
        mApiInterface.getProfileBisnis(idOwner, idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try{
                        view.hideLoadingProfileBisnis();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONObject objData = objResult.getJSONObject("info");

                            view.getDataBisnis("" + objData.getString("emailbisnis"), "" + objData.getString("namabisnis"), "" + objData.getString("notelponbisnis"), "" + objData.getString("idprovinsi"),
                                    "" + objData.getString("idkabupaten"), "" + objData.getString("idkecamatan"), "" + objData.getString("logobisnis"));
                        } else {
                            view.showToastProfileBisnis(objResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingProfileBisnis();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingProfileBisnis();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingProfileBisnis();
            }
        });
    }

    public void editBisnis(String idOwner, String idBisnis, String namaBisnis, String noTelpBisnis, String idProvinsi, String idKabupaten, String idKecamatan,
                           String logoBisnis){
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingProfileBisnis();
        mApiInterface.editProfileBisnis(idOwner, idBisnis, namaBisnis, noTelpBisnis, idProvinsi, idKabupaten, idKecamatan, logoBisnis).enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try{
                                view.hideLoadingProfileBisnis();

                                String result = response.body().string();
                                JSONObject objResult = new JSONObject(result);

                                if (objResult.getString("success").equals("1")) {
                                    statusEditBisnis.setValue(true);
                                } else {
                                    statusEditBisnis.setValue(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        view.hideLoadingProfileBisnis();
                    }
                }
        );
    }

    public MutableLiveData<Boolean> onStatusEdit() {
        return statusEditBisnis;
    }
}
