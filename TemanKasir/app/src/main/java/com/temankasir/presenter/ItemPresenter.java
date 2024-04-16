package com.temankasir.presenter;

import androidx.lifecycle.MutableLiveData;

import com.temankasir.api.APIClient;
import com.temankasir.api.APIUrl;
import com.temankasir.api.BaseApiInterface;
import com.temankasir.contract.ItemContract;
import com.temankasir.ui.pengaturan.aturitem.model.ItemModel;
import com.temankasir.ui.pengaturan.aturitem.model.KategoriItemModel;
import com.temankasir.ui.pengaturan.aturoutlet.model.OutletModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemPresenter {

    private ItemContract.itemView view;
    private BaseApiInterface mApiInterface;
    private MutableLiveData<Boolean> statusAddKategoriItem = new MutableLiveData<>();
    private MutableLiveData<Boolean> statusEditKategoriItem = new MutableLiveData<>();
    private MutableLiveData<Boolean> statusAddItem = new MutableLiveData<>();
    private MutableLiveData<Boolean> statusEditItem = new MutableLiveData<>();
    private ArrayList<KategoriItemModel> listKategoriItem = new ArrayList<>();
    private ArrayList<ItemModel> listItem = new ArrayList<>();
    private MutableLiveData<ArrayList<KategoriItemModel>> listKategoriItemLive = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ItemModel>> listItemLive = new MutableLiveData<>();
    private MutableLiveData<ArrayList<OutletModel>> listOutletLive = new MutableLiveData<>();
    private ArrayList<OutletModel> listOutlet = new ArrayList<>();
    private MutableLiveData<Boolean> hapusKategoriItem = new MutableLiveData<>();
    private MutableLiveData<Boolean> hapusItem = new MutableLiveData<>();
    private MutableLiveData<ArrayList<KategoriItemModel>> listKategoriByOutletLive = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ItemModel>> listItemByKategoriLive = new MutableLiveData<>();
    private ArrayList<KategoriItemModel> listKategoriByOutlet = new ArrayList<>();
    private ArrayList<ItemModel> listItemByKategori = new ArrayList<>();

    public ItemPresenter(ItemContract.itemView view) {
        this.view = view;
    }

    public void addKategoriItem(String idBisnis, String idOutlet, String namaKategori) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        mApiInterface.addKategoriItem(idBisnis, idOutlet, namaKategori).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingItem();
                        String result = response.body().string();

                        JSONObject objResult = new JSONObject(result);
                        if (objResult.getString("success").equals("1")) {
                            statusAddKategoriItem.setValue(true);
                        } else {
                            statusAddKategoriItem.setValue(false);
                            view.showToastItem(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingItem();
            }
        });
    }

    public void editKategoriItem(String idKategoriItem, String idOutlet, String namaKategoriItem) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        mApiInterface.editKategoriItem(idKategoriItem, idOutlet, namaKategoriItem).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingItem();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            statusEditKategoriItem.setValue(true);
                        } else {
                            statusEditKategoriItem.setValue(false);
                            view.showToastItem(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingItem();
            }
        });
    }

    public void getListKategoriItem(String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        mApiInterface.listKategoriItem(idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingItem();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONArray arrData = objResult.getJSONArray("data");

                            for (int i=0;i<arrData.length();i++) {
                                JSONObject objData = arrData.getJSONObject(i);

                                listKategoriItem.add(new KategoriItemModel("" + objData.getString("idkategoriitem"), "" + objData.getString("idoutlet"), "" + objData.getString("namaoutlet"), "" + objData.getString("namakategori"), "" + objData.getString("status")));
                            }

                            listKategoriItemLive.setValue(listKategoriItem);
                        } else {
                            view.showToastItem(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingItem();
            }
        });
    }

    public void addItem(String idBisnis, String idOutlet, String idKategoriItem, String namaItem, String hargaItem, String stokItem) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        mApiInterface.addItem(idBisnis, idOutlet, idKategoriItem, namaItem, hargaItem, stokItem).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        view.hideLoadingItem();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            statusAddItem.setValue(true);
                        } else {
                            statusAddItem.setValue(false);
                            view.showToastItem(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingItem();
            }
        });
    }

    public void editItem(String idItem, String idOutlet, String idKategoriItem, String namaItem, String hargaItem, String stokItem) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        mApiInterface.editItem(idItem, idOutlet, idKategoriItem, namaItem, hargaItem, stokItem).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingItem();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            statusEditItem.setValue(true);
                        } else {
                            statusEditItem.setValue(false);
                            view.showToastItem(objResult.getString("message"));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingItem();
            }
        });
    }

    public void listItem(String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        mApiInterface.listItem(idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        view.hideLoadingItem();

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONArray arrData = objResult.getJSONArray("data");

                            for (int i=0;i<arrData.length();i++) {
                                JSONObject objData = arrData.getJSONObject(i);

                                listItem.add(new ItemModel("" + objData.getString("iditem"), "" + objData.getString("idoutlet"), "" + objData.getString("namaoutlet"), "" + objData.getString("namakategori"), "" + objData.getString("namaitem"),
                                        "" + objData.getString("hargaitem"), "" + objData.getString("stok"), "" + objData.getString("status")));
                            }

                            listItemLive.setValue(listItem);
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e){
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingItem();
            }
        });
    }

    public void getListOutlet(String idOwner, String idBisnis) {
        mApiInterface = APIUrl.getAPIService();
        mApiInterface.getListOutlet(idOwner, idBisnis).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        String result = response.body().string();
                        JSONObject objResult = new JSONObject(result);

                        if (objResult.getString("success").equals("1")) {
                            JSONArray arrData = objResult.getJSONArray("data");
                            for (int i=0; i < arrData.length();i++) {
                                JSONObject objData = arrData.getJSONObject(i);

                                listOutlet.add(new OutletModel("" + objData.getString("idoutlet"), "" + objData.getString("namaoutlet"), "" + objData.getString("idprovinsi"), "" + objData.getString("namaprovinsi"), "" + objData.getString("idkabupaten"), "" + objData.getString("namakabupaten"), "" + objData.getString("idkecamatan"), "" + objData.getString("namakecamatan")));
                            }

                            listOutletLive.setValue(listOutlet);
                        } else {
                            view.showToastItem(objResult.getString("message"));
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

            }
        });
    }

    public void hapusKategoriItem(String idKategoriItem, String idOutlet) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        mApiInterface.hapusKategoriitem(idKategoriItem, idOutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        view.hideLoadingItem();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("success").equals("1")) {
                            hapusKategoriItem.setValue(true);
                        } else {
                            hapusKategoriItem.setValue(false);
                            view.showToastItem(jsonResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                } else {
                    view.hideLoadingItem();
                    view.showToastItem(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void hapusItem(String idItem, String idOutlet) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        mApiInterface.hapusItem(idItem, idOutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try{
                        view.hideLoadingItem();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("succcess").equals("1")) {
                            hapusItem.setValue(true);
                        } else {
                            hapusItem.setValue(false);
                            view.showToastItem(jsonResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                } else {
                    view.hideLoadingItem();
                    view.showToastItem(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void listKategoriByOutlet(String idBisnis, String idoutlet) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        listKategoriByOutlet.clear();
        mApiInterface.listKategoriItemByOutlet(idBisnis, idoutlet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        view.hideLoadingItem();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("success").equals("1")) {
                            JSONArray arrKategori = jsonResult.getJSONArray("data");

                            for (int i = 0;i < arrKategori.length();i++) {
                                JSONObject data = arrKategori.getJSONObject(i);
                                listKategoriByOutlet.add(new KategoriItemModel(data.getString("idkategoriitem"), data.getString("idoutlet"), data.getString("namaoutlet"), data.getString("namakategori"),
                                        data.getString("status")));
                            }

                            listKategoriByOutletLive.setValue(listKategoriByOutlet);
                        } else {
                            view.showToastItem(jsonResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                } else {
                    view.hideLoadingItem();
                    view.showToastItem(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingItem();
            }
        });
    }

    public void listItemByKategori(String idBisnis, String idOutlet, String idKategoriItem) {
        mApiInterface = APIUrl.getAPIService();
        view.showLoadingItem();
        listItemByKategori.clear();
        mApiInterface.listItemByKategori(idBisnis, idOutlet, idKategoriItem).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try{
                        view.hideLoadingItem();

                        String result = response.body().string();
                        JSONObject jsonResult = new JSONObject(result);

                        if (jsonResult.getString("success").equals("1")) {
                            JSONArray arrItem = jsonResult.getJSONArray("data");

                            for (int i = 0;i < arrItem.length();i++) {
                                JSONObject dataItem = arrItem.getJSONObject(i);
                                listItemByKategori.add(new ItemModel(dataItem.getString("iditem"), dataItem.getString("idoutlet"), dataItem.getString("namaoutlet"), dataItem.getString("namakategori"),
                                        dataItem.getString("namaitem"), dataItem.getString("hargaitem"), dataItem.getString("stok"), dataItem.getString("status")));
                            }

                            listItemByKategoriLive.setValue(listItemByKategori);
                        }else {
                            view.showToastItem(jsonResult.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        view.hideLoadingItem();
                    } catch (IOException e) {
                        e.printStackTrace();
                        view.hideLoadingItem();
                    }
                } else {
                    view.hideLoadingItem();
                    view.showToastItem(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideLoadingItem();
            }
        });
    }

    public MutableLiveData<Boolean> onAddKategoriItem() {
        return statusAddKategoriItem;
    }

    public MutableLiveData<Boolean> onEditKategoriItem() {
        return statusEditKategoriItem;
    }

    public MutableLiveData<Boolean> onAddItem() {
        return statusAddItem;
    }

    public MutableLiveData<Boolean> onEditItem() {
        return statusEditItem;
    }

    public MutableLiveData<ArrayList<KategoriItemModel>> onGetListKategoriItem() {
        return listKategoriItemLive;
    }

    public MutableLiveData<ArrayList<ItemModel>> onGetListItem() {
        return listItemLive;
    }

    public MutableLiveData<ArrayList<OutletModel>> listOutlet() {
        return listOutletLive;
    }

    public MutableLiveData<Boolean> onHapusKategoriItem() { return hapusKategoriItem; }

    public MutableLiveData<Boolean> onHapusItem() { return hapusItem; }

    public MutableLiveData<ArrayList<ItemModel>> getListItemByKategoriLive() {
        return listItemByKategoriLive;
    }

    public MutableLiveData<ArrayList<KategoriItemModel>> getListKategoriByOutletLive() {
        return listKategoriByOutletLive;
    }
}
