package com.temankasir.ui.home.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.temankasir.R;
import com.temankasir.api.APIConnect;
import com.temankasir.contract.ProfileAkunContract;
import com.temankasir.presenter.ProfileAkunPresenter;
import com.temankasir.ui.login.LoginActivity;
import com.temankasir.ui.pengaturan.aturdiskon.AturDiskonActivity;
import com.temankasir.ui.pengaturan.aturitem.AturItemActivity;
import com.temankasir.ui.pengaturan.aturkasir.AturKasirActivity;
import com.temankasir.ui.pengaturan.aturoutlet.AturOutletActivity;
import com.temankasir.ui.pengaturan.aturpelanggan.AturPelangganActivity;
import com.temankasir.ui.pengaturan.bisnisakun.BisnisAkunActivity;
import com.temankasir.ui.pengaturan.metodepembayaran.AturMetodePembayaranActivity;
import com.temankasir.ui.pengaturan.profileakun.ProfileAkunActivity;
import com.temankasir.utils.CustomProgressDialog;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragPengaturan extends Fragment implements ProfileAkunContract.profileAkunView {
    LinearLayout llProfileAkun, llBisnisAkun, llAturKasir, llAturItem, llOutlet, llAturDiskon, llMetodePembayaran, llAturPelanggan;
    String idOwner;
    CustomProgressDialog customProgressDialog;
    ProfileAkunContract.profileAkunPresenter presenterProfileAkun;
    TextView tvUsername, tvNamaBisnis;
    CircleImageView civLogoBisnis;
    Dialog dialogPreview;
    CardView cvLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengaturan, container, false);

        customProgressDialog = new CustomProgressDialog();
        presenterProfileAkun = new ProfileAkunPresenter(this);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
        idOwner = sharedPreferences.getString("idUser", "");
        Log.i("IDOWNER", " === " + idOwner);

        llProfileAkun = view.findViewById(R.id.llProfileAkunFragPengaturan);
        llBisnisAkun = view.findViewById(R.id.llPengaturanBisnisFragPengaturan);
        llAturKasir = view.findViewById(R.id.llPengaturanKasirFragPengaturan);
        llAturItem = view.findViewById(R.id.llPengaturanItemFragPengaturan);
        llOutlet = view.findViewById(R.id.llPengaturanOutletFragPengaturan);
        llAturDiskon = view.findViewById(R.id.llPengaturanPromoDiskonFragPengaturan);
        llMetodePembayaran = view.findViewById(R.id.llMetodePembayaranFragPengaturan);
        llAturPelanggan = view.findViewById(R.id.llPengaturanPelangganFragPengaturan);
        tvUsername = view.findViewById(R.id.tvUsernameAkunFragPengaturan);
        tvNamaBisnis = view.findViewById(R.id.tvNamaBisnisFragPengaturan);
        civLogoBisnis = view.findViewById(R.id.civPhotoProfileFragPengaturan);
        cvLogout = view.findViewById(R.id.cvLogoutFragPengaturan);

        llProfileAkun.setOnClickListener( v -> {
            startActivity(new Intent(requireContext(), ProfileAkunActivity.class));
        });

        llBisnisAkun.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), BisnisAkunActivity.class));
        });

        llAturKasir.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AturKasirActivity.class));
        });

        llAturItem.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AturItemActivity.class));
        });

        llOutlet.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AturOutletActivity.class));
        });

        llAturDiskon.setOnClickListener(v -> {
            dialogMaintenance();
        });

        llMetodePembayaran.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AturMetodePembayaranActivity.class));
        });

        llAturPelanggan.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), AturPelangganActivity.class));
        });

        cvLogout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences1 = requireActivity().getSharedPreferences("sessionLogin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.clear();
            editor.apply();
            editor.commit();

            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        });

        presenterProfileAkun.sendProfileAkun(idOwner);

        return view;
    }

    @Override
    public void showLoadingProfileAkun() {
        customProgressDialog.show(requireContext(), "Harap menunggu...");
    }

    @Override
    public void hideLoadingProfileAkun() {
        customProgressDialog.dialog.dismiss();
    }

    @Override
    public void showToastProfileAkun(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataProfile(String username, String namaBisnis, String logoBisnis) {
        tvUsername.setText(username);
        tvNamaBisnis.setText(namaBisnis);
        Picasso.get().load(logoBisnis).into(civLogoBisnis);
        Log.i("LOGOBISNIS", " === " + logoBisnis);
    }

    public void dialogMaintenance(){
        dialogPreview = new Dialog(requireActivity());
        dialogPreview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPreview.setContentView(R.layout.dialog_maintenance);
        dialogPreview.setCancelable(true);
        Window window = dialogPreview.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparent));

        dialogPreview.show();
    }
}
