package com.temankasir.presenter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.temankasir.api.APIClient;
import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.RegisterAkunContract;
import com.temankasir.ui.register.model.RegisterPostModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAkunPresenter {

    private BaseApiInterface mApiInterface;
    private RegisterAkunContract.registerAkunView view;
    private MutableLiveData<Boolean> statusRegister = new MutableLiveData<>();

    public RegisterAkunPresenter(RegisterAkunContract.registerAkunView view){
        this.view = view;
    }

    public void sendRegister(String namaLengkap, String username, String password, String emailBisnis, String namaBisnis,
                             String noTelponBisnis, String idProvinsi, String idKabupaten, String idKecamatan) {
        view.showLoadingRegister();

        mApiInterface = APIUrl.getAPIService();
        mApiInterface.registerAkun(namaLengkap, username, password, emailBisnis, namaBisnis, noTelponBisnis, idProvinsi, idKabupaten, idKecamatan).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        view.hideLoadingRegister();
                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);
                        if (objResult.getString("success").equals("1")){
                            statusRegister.setValue(true);
                        } else {
                            statusRegister.setValue(false);
                            view.showToastRegister(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingRegister();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingRegister();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<Boolean> onStatusRegister() {
        return statusRegister;
    }
}
