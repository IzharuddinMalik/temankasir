package com.temankasir.presenter;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.GetProvinsiContract;
import com.temankasir.ui.register.model.ProvinsiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProvinsiPresenter {

    private BaseApiInterface mApiInterface;
    private GetProvinsiContract.getProvinsiView view;

    public GetProvinsiPresenter(GetProvinsiContract.getProvinsiView view) {
        this.view = view;
    }

    public void sendGetProvinsi(String idUser) {
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getProvinsi(idUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONArray arrInfo = objResult.getJSONArray("info");

                            String[] idProvinsi = new String[arrInfo.length()+1];
                            String[] namaProvinsi = new String[arrInfo.length()+1];

                            idProvinsi[0] = "0";
                            namaProvinsi[0] = "Pilih Provinsi";

                            for (int i = 0; i < arrInfo.length();i++){
                                JSONObject objInfo = arrInfo.getJSONObject(i);

                                idProvinsi[i+1] = objInfo.getString("idprovinsi");
                                namaProvinsi[i+1] = objInfo.getString("namaprovinsi");
                            }

                            view.getDataProvinsi(idProvinsi,namaProvinsi);
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
