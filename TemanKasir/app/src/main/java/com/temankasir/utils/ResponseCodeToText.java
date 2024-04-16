package com.temankasir.utils;

import android.content.Context;

import com.temankasir.R;

public class ResponseCodeToText {
    private Context context;
    String result;

    public ResponseCodeToText() {
    }

    public ResponseCodeToText(Context context) {
        this.context = context;
    }

    public String responsecode(int code) {
        if(code==400){
            result = context.getString(R.string.response400);
        }else if(code==401){
            result = context.getString(R.string.response401);
        }else if(code==402){
            result = context.getString(R.string.response402);
        }else if(code==403){
            result = context.getString(R.string.response403);
        }else if(code==404){
            result = context.getString(R.string.response404);
        }else if(code==405){
            result = context.getString(R.string.response405);
        }else if(code==406){
            result = context.getString(R.string.response406);
        }else if(code==407){
            result = context.getString(R.string.response407);
        }else if(code==408){
            result = context.getString(R.string.response408);
        }else if(code==409){
            result = context.getString(R.string.response409);
        }else if(code==410){
            result = context.getString(R.string.response410);
        }else if(code==411){
            result = context.getString(R.string.response411);
        }else if(code==412){
            result = context.getString(R.string.response412);
        }else if(code==413){
            result = context.getString(R.string.response413);
        }else if(code==414){
            result = context.getString(R.string.response414);
        }else if(code==415){
            result = context.getString(R.string.response415);
        }else if(code==416){
            result = context.getString(R.string.response416);
        }else if(code==417){
            result = context.getString(R.string.response417);
        }else if(code==418){
            result = context.getString(R.string.response418);
        }else if(code==421){
            result = context.getString(R.string.response421);
        }else if(code==422){
            result = context.getString(R.string.response422);
        }else if(code==423){
            result = context.getString(R.string.response423);
        }else if(code==424){
            result = context.getString(R.string.response424);
        }else if(code==426){
            result = context.getString(R.string.response426);
        }else if(code==428){
            result = context.getString(R.string.response428);
        }else if(code==429){
            result = context.getString(R.string.response429);
        }else if(code==431){
            result = context.getString(R.string.response431);
        }else if(code==451){
            result = context.getString(R.string.response451);
        }else if(code==500){
            result = context.getString(R.string.response500);
        }else if(code==501){
            result = context.getString(R.string.response501);
        }else if(code==502){
            result = context.getString(R.string.response502);
        }else if(code==503){
            result = context.getString(R.string.response503);
        }else if(code==504){
            result = context.getString(R.string.response504);
        }else if(code==505){
            result = context.getString(R.string.response505);
        }else if(code==506){
            result = context.getString(R.string.response506);
        }else if(code==507){
            result = context.getString(R.string.response507);
        }else if(code==508){
            result = context.getString(R.string.response508);
        }else if(code==510){
            result = context.getString(R.string.response510);
        }else if(code==511){
            result = context.getString(R.string.response511);
        }else {
            result = String.valueOf(code);
        }

        return result;

    }
}
