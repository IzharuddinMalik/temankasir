package com.temankasir.ui.pengaturan.aturdiskon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.temankasir.R;
import com.temankasir.api.APIConnect;

public class FragDiskonTransaksi extends Fragment {

    APIConnect mApiConnect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diskon_transaksi, container, false);

        View toastsucces = getLayoutInflater().inflate(R.layout.toast_success, null);
        View toastinfo = getLayoutInflater().inflate(R.layout.toast_information, null);
        View toastfail = getLayoutInflater().inflate(R.layout.toast_failed, null);

        mApiConnect = new APIConnect(getActivity(), toastsucces, toastinfo, toastfail);

        return view;
    }

}
