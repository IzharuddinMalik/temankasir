package com.temankasir.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class ToastUtils {
    private Context context;

    public ToastUtils(Context context) {
        this.context = context;
    }

    public void toastbottomshort(View view){
        Toast toast = new Toast(context);
        // Set custom view in toast.
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0,30);
        toast.show();
    }
}