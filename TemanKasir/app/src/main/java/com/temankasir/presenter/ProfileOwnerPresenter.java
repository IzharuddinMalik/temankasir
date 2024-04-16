package com.temankasir.presenter;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.ProfileOwnerContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileOwnerPresenter implements ProfileOwnerContract.profileOwnerPresenter {

    private BaseApiInterface mApiInterface;
    private ProfileOwnerContract.profileOwnerView view;

    public ProfileOwnerPresenter(ProfileOwnerContract.profileOwnerView view) {
        this.view = view;
    }

    @Override
    public void sendProfileOwner(String idOwner) {
        view.showLoadingProfileOwner();
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getProfileOwner(idOwner).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingProfileOwner();
                        String result = response.body().string();

                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONObject objData = objResult.getJSONObject("data");

                            view.getDataProfileOwner("" + objData.getString("username"), "" + objData.getString("password"));
                        } else {
                            view.showToastProfileOwner(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingProfileOwner();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingProfileOwner();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingProfileOwner();
            }
        });
    }
}
