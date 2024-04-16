package com.temankasir.presenter;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.CekEmailContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekEmailPresenter implements CekEmailContract.cekEmailPresenter {

    private BaseApiInterface mApiInterface;
    private CekEmailContract.cekEmailView view;

    public CekEmailPresenter(CekEmailContract.cekEmailView view) {
        this.view = view;
    }

    @Override
    public void sendCekEmail(String email) {
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.cekEmailRegis(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();

                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            view.successCekEmail(objResult.getString("success"));
                        } else {
                            view.showToastCekEmail(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
