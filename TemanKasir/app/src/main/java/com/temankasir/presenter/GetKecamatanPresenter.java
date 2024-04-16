package com.temankasir.presenter;

import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.GetKecamatanContract;
import com.temankasir.ui.register.model.KecamatanModel;

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

public class GetKecamatanPresenter {

    private GetKecamatanContract.getKecamatanView view;
    private BaseApiInterface mApiInterface;

    public GetKecamatanPresenter(GetKecamatanContract.getKecamatanView view) {
        this.view = view;
    }

    public void sendGetKecamatan(String idUser, String idKabupaten) {
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getKecamatan(idUser, idKabupaten).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();

                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONArray arrInfo = objResult.getJSONArray("info");

                            String[] idKecamatan = new String[arrInfo.length()+1];
                            String[] namaKecamatan = new String[arrInfo.length()+1];

                            idKecamatan[0] = "0";
                            namaKecamatan[0] = "Pilih Kecamatan";

                            for (int i = 0; i < arrInfo.length();i++){
                                JSONObject objInfo = arrInfo.getJSONObject(i);

                                idKecamatan[i+1] = objInfo.getString("idkecamatan");
                                namaKecamatan[i+1] = objInfo.getString("namakecamatan");
                            }

                            view.getDataKecamatan(idKecamatan, namaKecamatan);
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
