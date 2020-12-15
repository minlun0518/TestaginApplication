package com.lunlun.testaginapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findview();
    }

    private void findview() {
//        EditText edmail = findViewById(R.id.ed_email2);
//        EditText edmemberId = findViewById(R.id.edmemberId);
//        EditText edpassword = findViewById(R.id.ed_password2);
//        EditText edagpassword = findViewById(R.id.agained_password);

//        String umail=edmail.getText().toString();
        String umail="hello10050067@gmail.com";
//        String uid=edmemberId.getText().toString();
//        String upass=edpassword.getText().toString();
        String upass="000000";
//        String uspass=edagpassword.getText().toString();

//        findViewById(R.id.singinButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("AUTH",umail+"/"+upass);
//                auth.signInWithEmailAndPassword(umail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(!task.isSuccessful()){
//                            Log.d("ONComplete","登入失敗");
//                            register(umail,upass);
//                        }
//                    }
//                });
//
//            }
//        });

    }

//    private void register(String umail, String upass) {
//        new AlertDialog.Builder(SignupActivity.this)
//                .setTitle("登入問題")
//                .setMessage("無此帳號，需要註冊")
//                .setPositiveButton("好",
//                        new DialogInterface.OnClickListener(){
//                            @Override
//                            public void onClick (DialogInterface dialog, int which){
//                                creatUser(umail,upass);
//                            }
//                        })
//                .setNeutralButton("取消",null)
//                .show();
//    }

//    private void creatUser(String umail, String upass) {
//        auth.createUserWithEmailAndPassword(umail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                String message = task.isComplete() ? "註冊成功" : "註冊失敗" ;
//                new AlertDialog.Builder(SignupActivity.this)
//                        .setMessage(message)
//                        .setNeutralButton("OK",null)
//                        .show();
//            }
//        });
//
//    }
}