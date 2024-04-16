package com.temankasir.presenter;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.EditProfileOwnerContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileOwnerPresenter implements EditProfileOwnerContract.editProfileOwnerPresenter {

    BaseApiInterface mApiInterface;
    EditProfileOwnerContract.editProfileOwnerView view;

    public EditProfileOwnerPresenter(EditProfileOwnerContract.editProfileOwnerView view) {
        this.view = view;
    }

    @Override
    public void sendEditProfileOwner(String idOwner, String username, String password) {
        view.showLoadingEditProfileOwner();
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.editProfileOwner(idOwner, username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingEditProfileOwner();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            view.successEditProfileOwner();
                            view.showToastEditProfileOwner(objResult.getString("message"));
                        } else {
                            view.showToastEditProfileOwner(objResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingEditProfileOwner();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingEditProfileOwner();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingEditProfileOwner();
            }
        });
    }
}
