package com.temankasir.api;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.temankasir.R;
import com.temankasir.utils.ResponseCodeToText;
import com.temankasir.utils.StylingUtils;
import com.temankasir.utils.ToastUtils;

import pl.droidsonroids.gif.GifImageView;

public class APIConnect {
    Context context;
    View viewtoastsucces, viewtoastinfo, viewtoastfailed;
    Dialog dialogloading;
    ResponseCodeToText responseCodeToText;
    StylingUtils stylingUtils;
    ToastUtils toastUtils;
    Boolean isdialogshow1 = false;

    public APIConnect(Context context, View viewtoastsucces, View viewtoastinfo, View viewtoastfailed) {
        this.context = context;
        responseCodeToText = new ResponseCodeToText(context);
        stylingUtils = new StylingUtils();
        toastUtils = new ToastUtils(context);
        this.viewtoastsucces = viewtoastsucces;
        this.viewtoastinfo = viewtoastinfo;
        this.viewtoastfailed = viewtoastfailed;
    }

    public void showdialogloading(){
        dialogloading = new Dialog(context);
        // Include dialog.xml file
        dialogloading.setContentView(R.layout.dialog_loading);
        dialogloading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogloading.setCancelable(false);

        dialogloading.setOnDismissListener(dialog -> isdialogshow1 = false);

        if(!isdialogshow1) {
            isdialogshow1 = true;
            dialogloading.show();

            final GifImageView imgloading = dialogloading.findViewById(R.id.imgloading);

            imgloading.setImageResource(R.drawable.loadingnew);

        } else {
            dialogloading.dismiss();
        }
    }

    public void closeDialogloading(){
        if (isdialogshow1){
            dialogloading.dismiss();
        }
    }

    public void showtoastsucces(String text){
        final TextView tvtoastsuccess = viewtoastsucces.findViewById(R.id.tvtoast);

        tvtoastsuccess.setText(text);
        toastUtils.toastbottomshort(viewtoastsucces);
    }

    public void showtoastinfo(String text){
        final TextView tvtoastinfo = viewtoastinfo.findViewById(R.id.tvtoast);

        tvtoastinfo.setText(text);
        toastUtils.toastbottomshort(viewtoastinfo);
    }

    public void showtoastfailed(String text){
        final TextView tvtoastfailed = viewtoastfailed.findViewById(R.id.tvtoast);

        tvtoastfailed.setText(text);
        toastUtils.toastbottomshort(viewtoastfailed);
    }
}
