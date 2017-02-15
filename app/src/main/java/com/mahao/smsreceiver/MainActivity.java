package com.mahao.smsreceiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTxtPhone;
    private MySmsReciver mReciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMethodOne = (Button) findViewById(R.id.btn_mehtod_one);
        Button btnMethodTwo = (Button) findViewById(R.id.btn_method_two);
        Button btnIntoView = (Button) findViewById(R.id.btn_next_view);
        Button btnIntoCustom = (Button) findViewById(R.id.btn_next_custom);
        btnIntoCustom.setOnClickListener(this);
        btnIntoView.setOnClickListener(this);
        btnMethodOne.setOnClickListener(this);
        btnMethodTwo.setOnClickListener(this);

        mTxtPhone = (TextView) findViewById(R.id.txt_phone);

        //注册这个广播接受者
        mReciver = new MySmsReciver();
        IntentFilter fliter =  new IntentFilter();
        fliter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mReciver,fliter);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        Intent intent;
        switch (id){

            case R.id.btn_mehtod_one:  //第一种方式获取手机号码

                commonGetPhone();
                break;

            case R.id.btn_method_two:  //第二种方式获取手机号码

                sendMessagePhone();
                break;

            case R.id.btn_next_view:

                 intent = new Intent(MainActivity.this,MyReciverActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_next_custom:

                intent = new Intent(MainActivity.this,MyCustomBroardCastActivity.class);
                startActivity(intent);
                break;
        }
    }


    //通常使用的获取本机号码的方法
    private void commonGetPhone() {

        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String localPhone = manager.getLine1Number();
        if(localPhone != null && localPhone.length() > 0){

            mTxtPhone.setText(localPhone);
        }else {

            mTxtPhone.setText("获取不到本机号码");
        }
    }


    //方法二：
    // 1 ： 发送短信--- 获取发送者的手机号码
    // 2 ： 拦截短信---
    private void sendMessagePhone() {

        //发送短信
        senMessage();

    }

    //向10000号发送短信
    private void senMessage() {

        SmsManager aDefault = SmsManager.getDefault();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,new Intent(),0);
        aDefault.sendTextMessage("15718869767",null,"121",pendingIntent,null);
    }

   public static  class MySmsReciver extends BroadcastReceiver{


        @Override
        public  void onReceive(Context context, Intent intent) {

            Object[] pduses= (Object[])intent.getExtras().get("pdus");
            for(Object pdus: pduses){
                byte[] pdusmessage = (byte[])pdus;
                SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);
                String mobile = sms.getOriginatingAddress();//发送短信的手机号码
                String content = sms.getMessageBody(); //短信内容
                Date date = new Date(sms.getTimestampMillis());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);  //得到发送时间

                Log.i("mahao",mobile + "......." + content + "......" + time);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReciver);
    }
}












