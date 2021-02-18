package com.android.dreams.basic.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import com.android.dreams.basic.bean.SliderItem;
import com.lunzn.tool.log.LogUtil;
import com.lunzn.tool.util.CommonUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Desc: TODO
 * <p>
 * Author: xiezhitao
 * PackageName: com.lzui.screensaver.util
 * ProjectName: ScreenSave
 * Date: 2019/3/15 11:08
 */
public class ClickActionUtil {

    public static final String TAG = "ClickActionUtil";

    /**
     * 处理图片点击事件
     */
    public static void openClickDataNoReplace(SliderItem sliderItem, Context context) {

        LogUtil.i(TAG, "处理图片点击事件 " + sliderItem.toString());


        if ("com.ktcp.tvvideo".equals(sliderItem.getPk_name())) {
            // 进入腾讯
            openTecentApp(sliderItem.getClick_action(), sliderItem.getClick_param(), context, sliderItem.getPk_name());

        } else if ("com.gitvvideo.lanzheng".equals(sliderItem.getPk_name())) {
            openAiQiYiApp(context, sliderItem.getPk_name(), sliderItem.getClick_action(), sliderItem.getClick_param());

        } else if ("android.intent.action.VIEW".equalsIgnoreCase(sliderItem.getClick_action())) {
            // 打开浏览器应用

            LogUtil.i("android.intent.action.VIEW: " + sliderItem.getAction_desc());

            String param = sliderItem.getClick_param();
            if (param != null && !"null".equals(param)) {

                if (!param.contains("http")) {
                    param = "http://" + param;
                }
                Uri uri = Uri.parse(param);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }


        } else if ("android.intent.action.play".equalsIgnoreCase(sliderItem.getClick_action())) {

//            Uri uri = Uri.parse(sliderItem.getClick_param());
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            intent.setPackage(sliderItem.getPk_name());
//            context.startActivity(intent);

        } else if ("com.lz.show.image".equalsIgnoreCase(sliderItem.getClick_action())) {


        } else if ("third.abutment.show.largeimg".equalsIgnoreCase(sliderItem.getClick_action())) {
            startPic(context, sliderItem.getPk_name(), sliderItem.getClick_param(), sliderItem.getClick_action());

        } else if (sliderItem.getApp_pk_name() != null && !"".equals(sliderItem.getApp_pk_name())) {
            // 应用
            startApkByPkgname(context, sliderItem.getApp_pk_name(), sliderItem.getClick_action(), sliderItem.getClick_param());

        } else if (sliderItem.getPk_name() != null && !"".equals(sliderItem.getPk_name())) {
            // 应用
            startApkByPkgname(context, sliderItem.getPk_name(), sliderItem.getClick_action(), sliderItem.getClick_param());
        } else {
            // 隐式Intent
            openAppByAction(sliderItem.getClick_action(), sliderItem.getClick_param(), null, context, null);
        }
    }

    private static void startPic(Context context, String app_pk_name, String click_param, String action) {
        if (CommonUtil.isEmpty(click_param) || click_param.length() < 10) {
            LogUtil.i(TAG, "未配置参数或参数不合法 " + click_param);
            return;
        }
        LogUtil.i(TAG, "packageName " + app_pk_name);
        try {
            if (!CommonUtil.isEmpty(action)) {
                Intent intent = new Intent(action);
                intent.setData(Uri.parse(click_param));
                intent.putExtra("img", click_param);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage(app_pk_name);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开爱奇艺app
     *
     * @param pkname 应用包名
     * @param action 动作
     * @param param  参数
     */
    private static void openAiQiYiApp(Context context, String pkname, String action, String param) {
        try {
//          action =   "com.gitvvideo.lanzheng.action.ACTION_DETAIL"
//          包名    =   "com.gitvvideo.lanzheng"
//          ("playType", "history");  //否  从哪里进入的
//          ("history", "0");         //否  播放视频的起始时间(毫秒)。
//          ("videoId", "227801800"); //专辑 ID
//          ("episodeId", "2278018"); //视频 ID
//          ("chnId", "1");           //频道 ID
//          ("customer", "lunzn");    //客户渠道
            if (CommonUtil.isNotEmpty(action)) {
                Intent intent = new Intent(action);
                intent.setClassName(pkname, "com.gala.video.lib.share.ifimpl.openplay.broadcast.activity.LoadingActivity");
                Bundle bundle = new Bundle();
                bundle.putString("playInfo", param);
                LogUtil.i(TAG, "openIQiYiByAction param = " + param);
                intent.putExtras(bundle);
                intent.setPackage(pkname);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            } else {
                startApkByPkgname(context, pkname, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, "openIQiYiByAction failed!");
        }

    }

    /**
     * 打开腾讯app或者下载app
     */
    private static void openTecentApp(String action, String param, Context context, String pkname) {

        if (!openTencentAppByAction(action, param, context)) {
            if (!startApkByPkgname(context, pkname, null, null)) {

                LogUtil.i("打开安装云视听·极光失败 ");
            }
        }
    }

    /**
     * 根据action打开腾讯应用
     */
    private static boolean openTencentAppByAction(String action, String param, Context context) {
        LogUtil.i(TAG, "action:" + action + " param:" + param);
        //标识是否进入腾讯
        boolean flag = false;
        if (action != null && param != null) {
            String[] splitAction = action.split(";");
            String[] splitParam = param.split(";");
            if (splitAction != null && splitParam != null) {
                for (int i = 0; i < splitAction.length; i++) {

                    LogUtil.i(TAG, "splitAction[i] " + splitAction[i]);
                    if ("com.tencent.qqlivetv.open".equalsIgnoreCase(splitAction[i])) {

                        LogUtil.i(TAG, "准备打开 splitParam[i] " + splitParam[i]);

                        Intent intent = new Intent("com.tencent.qqlivetv.open");
                        intent.setData(Uri.parse(splitParam[i]));
//                        intent.setData(Uri.parse("tenvideo2://?menu_name=最新上线&channel_name=电影&action=3&channel_code=movie&stay_flag=0"));

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setPackage("com.ktcp.tvvideo");//设置视频包名，要先确认包名
                        PackageManager packageManager = context.getPackageManager();
                        List<ResolveInfo> activities = packageManager.queryIntentActivities
                                (intent, 0);
                        boolean isIntentSafe = activities.size() > 0;
                        if (isIntentSafe) {
                            flag = true;
                            try {
                                context.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                flag = false;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 通过包名启动apk
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 启动成功返回true，否则返回false
     */
    public static boolean startApkByPkgname(Context context, String packageName, String action, String param) {

        LogUtil.i(TAG, "packageName " + packageName);
        try {
            if (!CommonUtil.isEmpty(action)) {
                Intent intent = new Intent(action);
                if (param != null) {
                    intent.setData(Uri.parse(param));
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage(packageName);
                context.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        boolean startSuccess = false;
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);

            List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

            LogUtil.i(TAG, "apps " + apps.size());
            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
                startSuccess = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        return startSuccess;
    }


    private static void openAppByAction(String action, String param, String extra, Context context, String pkname) {
        LogUtil.i("open app. action:" + action + ",param:" + param);
        try {
            if (action == null) {
                return;
            }
            Intent intent = new Intent(action);
            if (param != null) {
                intent.setData(Uri.parse(param));
            }
            int pullType = 0;
            if (extra != null) {
                Object[] object = setLauncherParams(intent, extra, pkname);
                pullType = (int) object[0];
                intent = (Intent) object[1];
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (pullType == 0) {
                context.startActivity(intent);
            } else if (pullType == 1) {
                context.startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object[] setLauncherParams(Intent intent, String extra, String pkname) {
        Object[] result = new Object[2];
        int pullType = 0;
        try {
            JSONObject extraObject = new JSONObject(extra);
            Map<String, String> extraMap = new HashMap<String, String>();
            Iterator<String> iterator = extraObject.keys();
            while (iterator != null && iterator.hasNext()) {
                String key = iterator.next();
                extraMap.put(key, extraObject.getString(key));
            }
            if (extraMap.size() > 0) {
                if (extraMap.containsKey("clsname")) {
                    String clsNmae = extraMap.remove("clsname");
                    intent = new Intent();
                    intent.setClassName(pkname, clsNmae);
                }

                // 启动方式 0-activity， 1-service
                if (extraMap.containsKey("pulltype")) {
                    pullType = Integer.valueOf(extraMap.remove("pulltype"));
                }
                for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                    intent.putExtra(entry.getKey(), entry.getValue());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        result[0] = pullType;
        result[1] = intent;
        return result;
    }
}
