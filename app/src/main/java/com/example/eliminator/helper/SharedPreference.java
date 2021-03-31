package com.example.eliminator.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eliminator.modal.UserDetails;

public class SharedPreference {
    private static SharedPreference mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "eliminator_user_data";

    private static final String KEY_USER_MOBILE = "userMobile";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_TOKEN = "userToken";
    private static final String KEY_USER_REFER_CODE = "refer_code";
    private static final String KEY_USER_BRANCH_NAME = "userBranchName";
    private static final String KEY_USER_BRANCH_ID = "userBranchId";

    private SharedPreference(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreference(context);
        }
        return mInstance;
    }

    public boolean insertUserData(UserDetails userDetails) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_EMAIL, userDetails.getEmail());
        editor.putString(KEY_USER_TOKEN, userDetails.getToken());
        editor.putString(KEY_USER_REFER_CODE, userDetails.getRefer_code());
        editor.putString(KEY_USER_MOBILE, userDetails.getMobile());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_TOKEN, null) != null)
            return true;
        return false;
    }

    public UserDetails getUserData() {


        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserDetails(

                sharedPreferences.getString(KEY_USER_TOKEN,null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_MOBILE,null),
                sharedPreferences.getString(KEY_USER_REFER_CODE,null)

        );
    }


    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
}