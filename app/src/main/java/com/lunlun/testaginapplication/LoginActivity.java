package com.lunlun.testaginapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    String IMEINumber;
    TextView imei;
    private static final int REQUEST_CODE = 101;
    private EditText password;
    private EditText email;
    private String eemail;
    private String pass;
    private CheckBox cb;

    private KeyguardManager mKeyguardManager;
    private FingerprintManager mFingerprintManager;
    private CancellationSignal cancellationSignal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getImei();
        findView();
    }

    private void findView() {
        findViewById(R.id.touchidimageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,"指紋辨識",Toast.LENGTH_LONG).show();
                checkrequirement();
                startFingerprintListening();
            }
        });

        findViewById(R.id.faceidimageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,"目前不支援臉部辨識",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.soonImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setText("wubetty2012@gmail.com");
                password.setText("123456");
            }
        });

        cb = findViewById(R.id.showpswcheckBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //顯示密碼
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //隱藏密碼
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
    }

    private void startFingerprintListening() {
        cancellationSignal = new CancellationSignal();
        if (checkSelfPermission(Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) //In SDK 23, we need to check the permission before we call FingerprintManager API functionality.
        {
            mFingerprintManager.authenticate(
                    null, //crypto objects 的 wrapper class，可以透過它讓 authenticate 過程更為安全，但也可以不使用。
                    cancellationSignal, //用來取消 authenticate 的 object
                    0, //optional flags; should be 0
                    mAuthenticationCallback, //callback 用來接收 authenticate 成功與否，有三個 callback method
                    null); //optional 的參數，如果有使用，FingerprintManager 會透過它來傳遞訊息
        }
    }

    FingerprintManager.AuthenticationCallback mAuthenticationCallback = new FingerprintManager.AuthenticationCallback(){
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            Log.e("", "error 辨識錯誤" + errorCode + " " + errString);
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("指紋辨識結果")
                    .setMessage("辨識錯誤")
                    .setPositiveButton("OK", null)
                    .show();
        }
        @Override
        public void onAuthenticationFailed() {
            Log.e("", "辨識失敗 onAuthenticationFailed");
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("指紋辨識結果")
                    .setMessage("辨識失敗")
                    .setPositiveButton("OK", null)
                    .show();
        }
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            Log.i("", "辨識成功 onAuthenticationSucceeded");
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("指紋辨識結果")
                    .setMessage("辨識成功")
                    .setPositiveButton("OK",null )
                    .show();
            verifiedsuccessfully();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        cancellationSignal.cancel();
        cancellationSignal = null;
    }

    private void checkrequirement() {
        mKeyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);//是否有設定 screen lock
                mFingerprintManager = (FingerprintManager) getSystemService(Activity.FINGERPRINT_SERVICE);//FingerprintManager.class

        if (!mKeyguardManager.isKeyguardSecure()){//是否有設定 fingerprint screen lock
            Toast.makeText(this, "是否有設定 fingerprint screen lock", Toast.LENGTH_LONG).show();
            return;
        }

        if (checkSelfPermission(Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) //In SDK 23, we need to check the permission before we call FingerprintManager API functionality.
        {
            if (!mFingerprintManager.isHardwareDetected())//硬體裝置是否支援 fingerprint reader
            {
                Toast.makeText(this, "硬體裝置是否支援 fingerprint reader", Toast.LENGTH_LONG).show();
                return;
            }

            if (!mFingerprintManager.hasEnrolledFingerprints())//是否有設定至少一枚指紋
            {
                Toast.makeText(this, "是否有設定至少一枚指紋", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    public void signup (View view) {
        Intent signupit = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(signupit);
    }

    public void getImei() {
        imei = findViewById(R.id.ed_imei);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            return;
        }
        IMEINumber = telephonyManager.getDeviceId();
        imei.setText(IMEINumber);
        new AlertDialog.Builder(this)
                .setTitle("手機識別碼讀取結果")
                .setMessage("您的裝置編號為:"+IMEINumber)
                .setPositiveButton("OK", null)
                .show();
    }

    public void login(View view) {
        email = findViewById(R.id.ed_email);
        eemail = email.getText().toString();
        password = findViewById(R.id.ed_password);
        pass = password.getText().toString();

        if (eemail.equals("wubetty2012@gmail.com") && pass.equals("123123")) {
            verifiedsuccessfully();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("登入失敗")
                    .setMessage("員工編號/Email或密碼錯誤!")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    public void verifiedsuccessfully(){
        setResult(RESULT_OK);
        finish();
//            Intent main = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(main);
    }

//    public void authenticate (
//            FingerprintManager.CryptoObject crypto,//為 Android 6.0中 crypto objects 的 wrapper class，可以透過它讓 authenticate 過程更為安全，但也可以不使用；
//            CancellationSignal cancel,//即用來取消 authenticate 的物件；
//            int flags,//為一個旗標，只能設為 0
//            FingerprintManager.AuthenticationCallback callback,//用來接受 authenticate 成功與否，一共有三個 callback method；
//            Handler handler)//為 optional 的參數，如果有使用，則 FingerprintManager 可以透過它來傳遞訊息



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}