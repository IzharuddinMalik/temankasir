package com.temankasir.presenter;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.ProfileAkunContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileAkunPresenter implements ProfileAkunContract.profileAkunPresenter {

    private BaseApiInterface mApiInterface;
    ProfileAkunContract.profileAkunView view;

    public ProfileAkunPresenter(ProfileAkunContract.profileAkunView view) {
        this.view = view;
    }

    @Override
    public void sendProfileAkun(String idOwner) {
        view.showLoadingProfileAkun();
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getProfileAkun(idOwner).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingProfileAkun();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONObject objData = objResult.getJSONObject("data");

                            view.getDataProfile(objData.getString("username"), objData.getString("namabisnis"),
                                    objData.getString("logobisnis"));
                        } else {
                            view.showToastProfileAkun(objResult.getString("message"));
                            view.hideLoadingProfileAkun();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingProfileAkun();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingProfileAkun();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingProfileAkun();
            }
        });
    }
}
