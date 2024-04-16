package com.temankasir.presenter;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.GetKabupatenContract;
import com.temankasir.ui.register.model.KabupatenModel;

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

public class GetKabupatenPresenter {

    private BaseApiInterface mApiInterface;
    private GetKabupatenContract.getKabupatenView view;

    public GetKabupatenPresenter(GetKabupatenContract.getKabupatenView view) {
        this.view = view;
    }

    public void sendGetKabupaten(String idUser, String idProvinsi) {
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getKabupaten(idUser, idProvinsi).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try{
                        String result = response.body().string();

                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONArray arrInfo = objResult.getJSONArray("info");

                            String[] idKabupaten = new String[arrInfo.length()+1];
                            String[] namaKabupaten = new String[arrInfo.length()+1];

                            idKabupaten[0] = "0";
                            namaKabupaten[0] = "Pilih Kabupaten";

                            for (int i = 0; i < arrInfo.length();i++){
                                JSONObject objInfo = arrInfo.getJSONObject(i);

                                idKabupaten[i+1] = objInfo.getString("idkabkota");
                                namaKabupaten[i+1] = objInfo.getString("namakabupaten");
                            }

                            view.getDataKabupaten(idKabupaten, namaKabupaten);
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
