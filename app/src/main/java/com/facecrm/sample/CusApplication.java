package com.facecrm.sample;

import android.annotation.SuppressLint;
import android.app.Application;

import com.face.detect.FaceCRMSDK;

public class CusApplication extends Application {


    @SuppressLint("HardwareIds")
    @Override
    public void onCreate() {
        super.onCreate();
        FaceCRMSDK.newInstance(getApplicationContext(), Utils.appId);
    }
}
