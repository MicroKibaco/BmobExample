package com.asiainfo.bmob.receiver;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.asiainfo.bmob.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月08日00点03分 描述:自定义Notifaction推送状态栏
 */

public class MyPushMessageReceiver extends BroadcastReceiver {

    String message = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {

            String msg = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            //解析json字符串
            parseJsonStr(context, msg);
        }
    }

    private void parseJsonStr(Context context, String msg) {


        JSONTokener token = new JSONTokener(msg);
        try {
            JSONObject jsonObj = (JSONObject) token.nextValue();
            message = jsonObj.getString("alert");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NotificationManager manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

            manager.notify(R.mipmap.ic_launcher,
                    new Notification.Builder(context)
                            .setAutoCancel(true)
                            .setContentTitle("title")
                            .setContentText("TestBomb")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setWhen(System.currentTimeMillis())
                            .build());

        }
    }
}
