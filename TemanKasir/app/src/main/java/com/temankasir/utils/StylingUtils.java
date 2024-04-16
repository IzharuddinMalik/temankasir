package com.temankasir.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StylingUtils {

    private Typeface typeface;

    public StylingUtils() {
    }

    public void robotoRegularTextview(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        textView.setTypeface(typeface);
    }

    public void robotoMediumTextview(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        textView.setTypeface(typeface);
    }

    public void robotoBoldTextview(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        textView.setTypeface(typeface);
    }

    public void robotoRegularEdittext(Context context, EditText editText) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        editText.setTypeface(typeface);
    }

    public void robotoMediumButton(Context context, Button btn) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        btn.setTypeface(typeface);
    }

}