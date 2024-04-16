package com.temankasir.utils;

import android.content.Context;

import java.util.HashMap;

public class SharedPreferences {

    private static SharedPreferences mInstance;
    private static Context mCtx;
    android.content.SharedPreferences pref;
    android.content.SharedPreferences.Editor editor;
    int mode = 0;

    private static final String SHARED_PREF_NAME = "bantucatat";
    private static final String IS_LOGIN = "isLogin";
    private static final String IS_FIRST = "isFirst";

    public static final String ID = "keyId";
    public static final String EMAIL = "keyEmail";
    public static final String PASSWORD = "keyPassword";
    public static final String USERNAME = "keyUsername";
    public static final String NO_TELP = "keyNoTelp";
    public static final String ROLE = "keyUser";
    public static final String TOKEN = "keyToken";
    public static final String IDBISNIS = "keyIdBisnis";
    public static final String STATUSAKUN = "keyStatus";

    public static final String NAMABISNISRB = "keyNamaBisnisRB";
    public static final String IDProv = "keyIDProv";
    public static final String IDKab = "keyIDKab";
    public static final String IDKec = "keyIDKec";

    public SharedPreferences(Context context){
        mCtx = context;
        pref = context.getSharedPreferences(SHARED_PREF_NAME, mode);
        editor = pref.edit();
    }

    public static synchronized SharedPreferences getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPreferences(context);
        }
        return mInstance;
    }

//    public void createSessionLogin(OwnerModel km){
//        android.content.SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(IS_LOGIN, true);
//        editor.putString(ID, km.getIdUser());
//        editor.putString(TOKEN, km.getToken());
//        editor.putString(USERNAME, km.getUsername());
//        editor.putString(IDBISNIS, km.getIdBisnis());
//        editor.putString(STATUSAKUN, km.getStatus());
//        editor.putString(ROLE, km.getRole());
//        editor.commit();
//    }

    public void createSessionFirtUse(){
        android.content.SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST, true);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isNotFirst(){
        return pref.getBoolean(IS_FIRST, false);
    }


    public void logout(){
        android.content.SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public HashMap<String, String> getRB(){
        HashMap<String, String> user = new HashMap<>();
        user.put(SHARED_PREF_NAME, pref.getString(SHARED_PREF_NAME, null));
        user.put(NAMABISNISRB, pref.getString(NAMABISNISRB, null));
        user.put(IDProv, pref.getString(IDProv, null));
        user.put(IDKab, pref.getString(IDKab, null));
        user.put(IDKec, pref.getString(IDKec, null));
        return user;
    }

}
