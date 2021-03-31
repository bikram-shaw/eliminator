package com.example.eliminator.others;

import android.app.ProgressDialog;
import android.content.Context;

public  class Loader {
    public static void showProgressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }
//    public static void hideProgressDialog(ProgressDialog progressDialog ){
//        progressDialog.dismiss();
//    }
}
