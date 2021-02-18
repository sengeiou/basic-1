package com.android.dreams.basic.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 作者:meijie
 * 包名:com.lzui.connlife.util
 * 工程名:ConnLife
 * 时间:2018/6/7 15:20
 * 说明:
 */
public class GsonUtil {
    public static final String TAG = "GsonUtil";

    private static Gson mGson = new Gson();

    public static String toJson(Object obj) {
        return mGson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return mGson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return mGson.fromJson(json, typeOfT);
    }

    public static String getJsonString(String normalString) {
        char[] temp = normalString.toCharArray();

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < temp.length; i++) {
            char c = temp[i];
            if (c == '"') {
                stringBuffer.append('\\');
                stringBuffer.append('"');
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

   /* public static ScreenSaverBean getBean(String data){
        JSONObject jsonObject = new JSONObject(data);
        ScreenSaverBean screenSaverBean= null;

        if (!jsonObject.has("Z") || (int) jsonObject.get("Z") != 0) {

            LogUtil.i(TAG, "没有 z 字段 ？");

        }else{

            screenSaverBean = GsonUtil.fromJson(jsonObject.optString("screenSaver"),
                    ScreenSaverBean.class);
        }
        return screenSaverBean;
    }*/
}
