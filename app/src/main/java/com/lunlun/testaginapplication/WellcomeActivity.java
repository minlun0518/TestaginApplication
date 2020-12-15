package com.lunlun.testaginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class WellcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    startActivity(new Intent().setClass(WellcomeActivity.this,MainActivity.class));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}