package com.facecrm.sample;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import com.face.detect.FaceCRMSDK;
import com.face.detect.Util.OptionFaceCRM;
import com.face.detect.Util.Util;
import com.facecrm.sample.model.MemberResult;
import com.facecrm.sample.network.NetworkClient;
import com.facecrm.sample.network.NetworkInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CusApplication extends Application {


    @SuppressLint("HardwareIds")
    @Override
    public void onCreate() {
        super.onCreate();
        FaceCRMSDK.newInstance(getApplicationContext(), Utils.appId);
        OptionFaceCRM.mInstance().setBrightness(-30);
//        getToken();
//        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        Util.shared().setDeviceId(deviceId);
    }

    private void getToken() {
        NetworkClient.getRetrofit().create(NetworkInterface.class)
                .applicationVerify("ca55719568ae5416e34c260514ac4d3b", Util.shared().getDeviceId())
                .enqueue(new Callback<MemberResult>() {
                    @Override
                    public void onResponse(Call<MemberResult> call, Response<MemberResult> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().status == 200) {
                                Util.shared().setToken(response.body().dataMember.token);
                            }
                        } else {
                            assert response.body() != null;
                            Toast.makeText(getApplicationContext(), "status= " + response.body().status, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error call api verify", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
