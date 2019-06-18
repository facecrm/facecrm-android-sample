package com.facecrm.sample.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.face.detect.FaceCRMSDK;
import com.face.detect.Listener.DetectFaceListener;
import com.face.detect.Listener.FoundFaceListener;
import com.facecrm.sample.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.btn_new)
    AppCompatButton btnNew;
    @BindView(R.id.btn_history)
    AppCompatButton btnHistory;
    @BindView(R.id.tv_id)
    AppCompatTextView tvName;
    @BindView(R.id.tv_name)
    AppCompatTextView tvData;
    @BindView(R.id.tv_db)
    AppCompatTextView tvPhone;
    @BindView(R.id.tv_collection)
    AppCompatTextView tvSex;
    @BindView(R.id.imv_full)
    ImageView imvFull;
    @BindView(R.id.imv_face)
    ImageView imvFace;
    @BindView(R.id.btn_setting)
    AppCompatButton btnSetting;

    @SuppressLint("HardwareIds")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getToken();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initOnClick();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        FaceCRMSDK.getsInstance().startDetectByCamera(this, R.id.color_blob_detection_activity_surface_view);

        FaceCRMSDK.getsInstance().onFoundFace(new FoundFaceListener() {
            @Override
            public void onFoundFace(final Bitmap face, final Bitmap fullImage) {
            }
        });

        FaceCRMSDK.getsInstance().onDetectFace(new DetectFaceListener() {
            @Override
            public void onDetectFaceSuccess(final Bitmap face, final Bitmap fullImage, final String data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(MainActivity.this).load(fullImage).into(imvFull);
                                Glide.with(MainActivity.this).load(face).into(imvFace);
                                try {
                                    JSONObject obData = new JSONObject(data);
                                    tvName.setText(obData.getString("face_id"));
                                    tvData.setText("");
                                    if (obData.has("emotion")) {
                                        String emotion = obData.getString("emotion");
                                        tvData.setText("Emotion: " + emotion);
                                    }
                                    String age = obData.getString("age");
                                    if (Integer.parseInt(age) > 0)
                                        tvData.setText(tvData.getText().toString() + " - Age: " + age);

                                    if (obData.has("gender")) {
                                        String gender = obData.getString("gender");
                                        tvData.setText(tvData.getText().toString() + " - Gender: " + gender);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onDetectFaceFail(final Bitmap face, final Bitmap fullImage, final int errorCode, final String errorMessage) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(MainActivity.this).load(fullImage).into(imvFull);
                                Glide.with(MainActivity.this).load(face).into(imvFace);
                                tvName.setText("errorCode = " + errorCode + ", " + errorMessage);
                                tvData.setText("");
                            }
                        });
                    }
                }).start();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        FaceCRMSDK.getsInstance().stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FaceCRMSDK.getsInstance().stopCamera();
    }

    private void initOnClick() {
        btnNew.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_new) {
            Intent intent = new Intent(MainActivity.this, CreateFaceActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_history) {
            startActivity(new Intent(MainActivity.this, HistoryActivity.class));
        } else if (v.getId() == R.id.btn_setting) {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        }
    }
}
