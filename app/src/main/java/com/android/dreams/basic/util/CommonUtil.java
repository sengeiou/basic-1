package com.android.dreams.basic.util;

import android.content.ComponentName;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.service.dreams.IDreamManager;

import com.android.dreams.basic.DreamApplication;
import com.lunzn.tool.log.LogUtil;

/**
 * Desc: TODO
 * <p>
 * Author: xiezhitao
 * PackageName: com.android.dreams.basic.util
 * ProjectName: basic
 * Date: 2019/6/14 17:53
 */
public class CommonUtil {

    private static final String TAG = CommonUtil.class.getSimpleName();
    public static final String SCREENSAVER_ACTIVATE_ON_SLEEP = "screensaver_activate_on_sleep";
    public static final String SCREENSAVER_ENABLED = "screensaver_enabled";
    public static final String SCREENSAVER_STAY_ON_WHILE_PLUGGED_IN = Settings.Global.STAY_ON_WHILE_PLUGGED_IN;
    public static final long DEFAULT_TIME = 30 * 60 * 1000; //半小时 30 * 60 * 1000
    public static IDreamManager mDreamManager;


    /** 设置屏保启动或关闭 */
    public static void setEnabled(boolean value) {
        setBoolean(SCREENSAVER_ENABLED, value);
        setBoolean(SCREENSAVER_ACTIVATE_ON_SLEEP, value);
        Settings.Global.putInt(DreamApplication.getApplication().getContentResolver(), SCREENSAVER_STAY_ON_WHILE_PLUGGED_IN, value ? 0 : 1);//开屏保 设置为0，

    }

    private static void setBoolean(String key, boolean value) {
        Settings.Secure.putInt(DreamApplication.getApplication().getContentResolver(), key, value ? 1 : 0);
    }

    /** 设置屏保启动时间 */
    public static void setWPTime(long wpTime) {
        try {
            int startTime = Settings.System.getInt(DreamApplication.getApplication().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT);
            LogUtil.d(TAG, "get SCREEN_OFF_TIMEOUT " + startTime);

            if (startTime != wpTime) {

                boolean isOK = Settings.System.putInt(DreamApplication.getApplication().getContentResolver(),
                        android.provider.Settings.System.SCREEN_OFF_TIMEOUT, (int) wpTime);
                LogUtil.d(TAG, "setWPTime " + wpTime + " isOK " + isOK);

            }

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startDreaming() {
        if (mDreamManager == null) {
            mDreamManager = IDreamManager.Stub.asInterface(
                    ServiceManager.getService("dreams"));

        }
        LogUtil.e(TAG, "mDreamManager " + mDreamManager);
        try {
            LogUtil.w(TAG, "getDefaultDream()：" + getDefaultDream());
            mDreamManager.dream();
        } catch (RemoteException e) {
            LogUtil.w(TAG, "Failed to dream" + e.getLocalizedMessage());
        }
    }

    public static ComponentName getDefaultDream() {


        try {

            return mDreamManager.getDefaultDreamComponent();
        } catch (RemoteException e) {
            LogUtil.w(TAG, "Failed to get default dream" + e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setDream() {

        try {
            if (mDreamManager == null) {
                mDreamManager = IDreamManager.Stub.asInterface(
                        ServiceManager.getService("dreams"));

            }
            LogUtil.e(TAG, "mDreamManager " + mDreamManager);
            ComponentName[] componentNames = mDreamManager.getDreamComponents();
            for (ComponentName dreamComponent : componentNames) {
                LogUtil.i(TAG, "dreamComponent: " + dreamComponent);
            }
            componentNames[0] = new ComponentName("com.android.dreams.basic", "com.android.dreams.basic.LzDream");
            mDreamManager.setDreamComponents(componentNames);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
