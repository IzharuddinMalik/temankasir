package com.temankasir.presenter;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.LoginAkunContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAkunPresenter {

    public BaseApiInterface mApiInterface;
    public LoginAkunContract.loginAkunView view;

    public LoginAkunPresenter(LoginAkunContract.loginAkunView view) {
        this.view = view;
    }

    public void loginAkun(String username, String password, String token) {
        view.showLoadingLogin();
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.loginOwner(username, password, token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        view.hideLoadingLogin();
                        String result = response.body().string();

                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            view.showToastLogin(objResult.getString("message"));
                            JSONObject objData = objResult.getJSONObject("data");

                            view.successLogin(objData.getString("iduser"), objData.getString("idbisnis"), objData.getString("statususer"), objData.getString("idoutlet"));
                        } else {
                            view.showToastLogin(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingLogin();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingLogin();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingLogin();
            }
        });
    }
}
