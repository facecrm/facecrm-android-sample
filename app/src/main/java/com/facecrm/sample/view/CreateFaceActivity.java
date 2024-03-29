package com.facecrm.sample.view;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.face.detect.FaceCRMSDK;
import com.face.detect.Listener.CaptureFaceListener;
import com.face.detect.Listener.RegisterFaceListener;
import com.face.detect.Listener.UploadFaceListener;
import com.facecrm.sample.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateFaceActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;

    private Bitmap bitmapFace_1, bitmapFace_2, bitmapFace_3, bitmapFace_4;

    @BindView(R.id.btn_next)
    AppCompatButton btnNext;
    @BindView(R.id.imv_1)
    ImageView imv1;
    @BindView(R.id.imv_2)
    ImageView imv2;
    @BindView(R.id.imv_3)
    ImageView imv3;
    @BindView(R.id.imv_4)
    ImageView imv4;
    @BindView(R.id.imv_close_1)
    ImageView imvDelete1;
    @BindView(R.id.imv_close_2)
    ImageView imvDelete2;
    @BindView(R.id.imv_close_3)
    ImageView imvDelete3;
    @BindView(R.id.imv_close_4)
    ImageView imvDelete4;
    @BindView(R.id.imv_add)
    ImageView imvAdd;
    @BindView(R.id.edt_name)
    AppCompatEditText edtName;
    @BindView(R.id.edt_phone)
    AppCompatEditText edtPhone;
    @BindView(R.id.edt_email)
    AppCompatEditText edtEmail;
    @BindView(R.id.edt_idc)
    AppCompatEditText edtIDC;
    @BindView(R.id.edt_user_name)
    AppCompatEditText edtUsername;
    @BindView(R.id.edt_user_id)
    AppCompatEditText edtUserId;
    @BindView(R.id.edt_address)
    AppCompatEditText edtAddress;
    @BindView(R.id.edt_birthday)
    AppCompatEditText edtBirthday;
    @BindView(R.id.edt_sex)
    AppCompatEditText edtSex;

    private Bitmap mBitmap;
    private int numberFace = 0;
    private List<Bitmap> lstFace = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_create_face);
        ButterKnife.bind(this);

        onClick();
    }

    private void onClick() {
        btnNext.setOnClickListener(this);
        imvDelete1.setOnClickListener(this);
        imvDelete2.setOnClickListener(this);
        imvDelete3.setOnClickListener(this);
        imvDelete4.setOnClickListener(this);
        imvAdd.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FaceCRMSDK.getsInstance().startRegisterByCamera(CreateFaceActivity.this,
                R.id.color_blob_detection_activity_surface_view);

        FaceCRMSDK.getsInstance().captureFace(new CaptureFaceListener() {
            @Override
            public void onCaptureFace(Bitmap face, Bitmap fullImage) {
                mBitmap = face;
            }
        });

        FaceCRMSDK.getsInstance().onUploadFace(new UploadFaceListener() {
            @Override
            public void onUploadFace(Bitmap bitmap, int code, String message) {
                Log.e("onUploadFace", code + "-" + message);
            }
        });

        FaceCRMSDK.getsInstance().onRegisterFace(new RegisterFaceListener() {
            @Override
            public void onRegisterFaceSuccess(int i, String message, String faceId) {
                Toast.makeText(CreateFaceActivity.this, message, Toast.LENGTH_SHORT).show();
                dismissDialog();
            }

            @Override
            public void onRegisterFaceFail(int code, String s) {
                Toast.makeText(CreateFaceActivity.this, s, Toast.LENGTH_SHORT).show();
                dismissDialog();
            }

        });
    }

    private void dismissDialog() {
        FaceCRMSDK.getsInstance().finishRegister();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        FaceCRMSDK.getsInstance().stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_add:
                if (mBitmap != null) {
                    if (imv1.getDrawable() == null) {
                        Glide.with(this).load(mBitmap).into(imv1);
                        bitmapFace_1 = mBitmap;
                        lstFace.add(bitmapFace_1);
                        mBitmap = null;
                        return;
                    }
                    if (imv2.getDrawable() == null) {
                        Glide.with(this).load(mBitmap).into(imv2);
                        bitmapFace_2 = mBitmap;
                        lstFace.add(bitmapFace_2);
                        mBitmap = null;
                        return;
                    }
                    if (imv3.getDrawable() == null) {
                        Glide.with(this).load(mBitmap).into(imv3);
                        bitmapFace_3 = mBitmap;
                        lstFace.add(bitmapFace_3);
                        mBitmap = null;
                        return;
                    }
                    if (imv4.getDrawable() == null) {
                        Glide.with(this).load(mBitmap).into(imv4);
                        bitmapFace_4 = mBitmap;
                        lstFace.add(bitmapFace_4);
                        mBitmap = null;
                        return;
                    }
                }
                break;
            case R.id.btn_next:
                if (bitmapFace_1 != null && bitmapFace_2 != null &&
                        bitmapFace_3 != null && bitmapFace_4 != null) {
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Please waiting...");
                    progressDialog.show();
                    registerFace();
                }
                break;
            case R.id.imv_close_1:
                imv1.setImageDrawable(null);
                bitmapFace_1 = null;
                break;
            case R.id.imv_close_2:
                imv2.setImageDrawable(null);
                bitmapFace_2 = null;
                break;
            case R.id.imv_close_3:
                imv3.setImageDrawable(null);
                bitmapFace_3 = null;
                break;
            case R.id.imv_close_4:
                imv4.setImageDrawable(null);
                bitmapFace_4 = null;
                break;
        }
    }

    private void registerFace() {
        try {
            JSONObject metaData = new JSONObject();
            if (edtName.getText().toString().length() > 0)
                metaData.put("full_name", edtName.getText().toString());
            if (edtPhone.getText().toString().length() > 0)
                metaData.put("phone_number", edtPhone.getText().toString());
            if (edtEmail.getText().toString().length() > 0)
                metaData.put("email", edtEmail.getText().toString());
            if (edtIDC.getText().toString().length() > 0)
                metaData.put("identity_card", edtIDC.getText().toString());
            if (edtUsername.getText().toString().length() > 0)
                metaData.put("user_name", edtUsername.getText().toString());
            if (edtUserId.getText().toString().length() > 0)
                metaData.put("user_id", edtUserId.getText().toString());
            if (edtAddress.getText().toString().length() > 0)
                metaData.put("address", edtAddress.getText().toString());
            if (edtBirthday.getText().toString().length() > 0)
                metaData.put("birthday", edtBirthday.getText().toString());
            if (edtSex.getText().toString().length() > 0)
                metaData.put("sex", edtSex.getText().toString());

            if (metaData.toString().length() > 2)
                FaceCRMSDK.getsInstance().setMetaData(metaData.toString());
            FaceCRMSDK.getsInstance().registerFaces(lstFace);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
