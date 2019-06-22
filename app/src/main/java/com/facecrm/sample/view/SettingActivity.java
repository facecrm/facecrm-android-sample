package com.facecrm.sample.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;

import com.face.detect.Util.OptionFaceCRM;
import com.facecrm.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.tv_rate)
    AppCompatEditText tvRate;
    @BindView(R.id.tv_bright)
    AppCompatEditText tvBright;
    @BindView(R.id.cb_emotion)
    AppCompatCheckBox cbEmotion;
    @BindView(R.id.cb_age)
    AppCompatCheckBox cbAge;
    @BindView(R.id.cb_gender)
    AppCompatCheckBox cbGender;
    @BindView(R.id.is_show)
    SwitchCompat swShow;
    @BindView(R.id.btn_setting)
    AppCompatButton btnOk;
    @BindView(R.id.btn_reset)
    AppCompatButton btnReset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Setting");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        initData();
        initControl();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        tvRate.setText(OptionFaceCRM.mInstance().getRate() + "");
        tvBright.setText(OptionFaceCRM.mInstance().getBrightness() + "");
        String detectType = OptionFaceCRM.mInstance().getDetectionType();
        if (detectType != null) {
            if (detectType.contains(OptionFaceCRM.DETECT_TYPE_EMOTION))
                cbEmotion.setChecked(true);
            else
                cbEmotion.setChecked(false);

            if (detectType.contains(OptionFaceCRM.DETECT_TYPE_AGE))
                cbAge.setChecked(true);
            else
                cbAge.setChecked(false);

            if (detectType.contains(OptionFaceCRM.DETECT_TYPE_GENDER))
                cbGender.setChecked(true);
            else
                cbGender.setChecked(false);
        }

        if (OptionFaceCRM.mInstance().isEnableShowFaceResult())
            swShow.setChecked(true);
        else
            swShow.setChecked(false);
    }

    public void initControl() {

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvRate.getText().toString().length() == 0)
                    OptionFaceCRM.mInstance().setDetectRate(0);
                else
                    OptionFaceCRM.mInstance().setDetectRate(Integer.parseInt(tvRate.getText().toString().trim()));

                if (tvBright.getText().toString().length() == 0)
                    OptionFaceCRM.mInstance().setBrightness(0);
                else
                    OptionFaceCRM.mInstance().setBrightness(Integer.parseInt(tvBright.getText().toString().trim()));

                OptionFaceCRM.mInstance().setEnableShowFaceResult(swShow.isChecked());
                OptionFaceCRM.mInstance().setDetectionType(null);
                String detectType = null;
                if (cbEmotion.isChecked())
                    detectType = OptionFaceCRM.DETECT_TYPE_EMOTION;

                if (cbAge.isChecked())
                    if (detectType != null)
                        detectType = detectType + "," + OptionFaceCRM.DETECT_TYPE_AGE;
                    else
                        detectType = OptionFaceCRM.DETECT_TYPE_AGE;

                if (cbGender.isChecked())
                    if (detectType != null)
                        detectType = detectType + "," + OptionFaceCRM.DETECT_TYPE_GENDER;
                    else
                        detectType = OptionFaceCRM.DETECT_TYPE_GENDER;

                OptionFaceCRM.mInstance().setDetectionType(detectType);
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionFaceCRM.mInstance().setDetectRate(0);
                OptionFaceCRM.mInstance().setBrightness(0);
                OptionFaceCRM.mInstance().setEnableShowFaceResult(true);
                OptionFaceCRM.mInstance().setDetectionType(OptionFaceCRM.DETECT_TYPE_EMOTION + "," +
                        OptionFaceCRM.DETECT_TYPE_AGE + "," + OptionFaceCRM.DETECT_TYPE_GENDER);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
