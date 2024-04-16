package com.temankasir.ui.pengaturan.aturdiskon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.temankasir.R;
import com.temankasir.api.APIConnect;

public class FragDiskonItem extends Fragment {

    CardView cvTambahDiskonItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diskon_item, container, false);

        return view;
    }
}
