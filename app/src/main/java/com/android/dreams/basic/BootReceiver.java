package com.android.dreams.basic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.dreams.basic.util.CommonUtil;
import com.android.dreams.basic.util.SharedPreferenceUtil;
import com.lunzn.tool.log.LogUtil;

public class BootReceiver extends BroadcastReceiver {

    private Intent mIntent;

    public static boolean hasSetted;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtil.d("=receive bd: " + action + " hasSetted " + hasSetted);
        if (("com.lzui.launcher.adv.completed").equalsIgnoreCase(action) && !hasSetted) {//launcher 开机广播
            hasSetted = true;
            setConfig();
            if (mIntent == null) {
                mIntent = new Intent(context, DownloadService.class);
            }
            context.startService(mIntent);
            SharedPreferenceUtil.set("lastOpenTime", System.currentTimeMillis());

        }
    }

    public static void setConfig() {
        boolean isFirst = SharedPreferenceUtil.getBoolean("isFirst", true);
        LogUtil.d("isFirst " + isFirst);

        if (isFirst) {
            SharedPreferenceUtil.set("isFirst", false);
            CommonUtil.setDream();
            CommonUtil.setEnabled(false);
            SharedPreferenceUtil.set("isOPen", false);
            CommonUtil.setWPTime(CommonUtil.DEFAULT_TIME);

        }

    }
}
