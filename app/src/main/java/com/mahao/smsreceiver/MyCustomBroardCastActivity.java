package com.mahao.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyCustomBroardCastActivity extends AppCompatActivity implements View.OnClickListener {


    private static TextView mTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_broard_cast);

        Button sendMsg = (Button) findViewById(R.id.btn_send_msg);
        sendMsg.setOnClickListener(this);

        mTxtView = (TextView) findViewById(R.id.txt_show_receiver);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id){

            case R.id.btn_send_msg:

                Intent intent = new Intent();
                intent.setAction("com.mahao");
                intent.putExtra("name","mahao");
                sendBroadcast(intent);

                break;

        }
    }

    public static class MyCusTomReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {


            if(intent != null && intent.getAction().equals("com.mahao")){

                String name = intent.getStringExtra("name");
                mTxtView.setText(name);
            }

        }
    }
}
