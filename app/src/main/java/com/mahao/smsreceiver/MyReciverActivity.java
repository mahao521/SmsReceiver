package com.mahao.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static android.os.Build.VERSION_CODES.M;

public class MyReciverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reciver);
    }

    //获取手机的打开状态
    public static  class  MyOpenPhoneReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {


            if(intent != null && intent.getAction().equals(Intent.ACTION_USER_PRESENT)){

                Toast.makeText(context,"屏幕已经解锁",Toast.LENGTH_SHORT).show();
                Log.i("mahao","1234");
            }
        }
    }
}
