package com.android.dreams.basic.util;

import android.os.SystemProperties;

import com.lunzn.tool.log.LogUtil;
import com.smart.localfile.LocalFileCRUDUtils;
import com.smart.net.SendCmd;
import com.smart.xml.EditorInter;
import com.smart.xml.XmlEditor;

import java.io.File;

public class SystemUtil {

    /** 存储在xml里面的sn标记 */
    private final static String TAG_XML_SN = "sn";

    /** 存储在xml里面的gdid标记 */
    private final static String TAG_XML_GDID = "gdid";

    /** 存储在xml里面的mac标记 */
    private final static String TAG_XML_MAC = "mac";

    /** 存储在xml里面的authresult标记 */
    private final static String TAG_XML_AUTHRESULT = "authresult";

    /** 存储在xml里面的reserved标记 */
    private final static String TAG_XML_RESERVED = "reserved";

    /** 存储数据的xml地址 */
    private final static String XML_FILE_PATH = "/data/etc/Equipmentdata.xml";

    /** gdid在共享区域存放路径 */
    private final static String GDID_FILE_PATH = "/mnt/oem/gdid";

    /** sn在共享区域存放路径 */
    private final static String SN_FILE_PATH = "/mnt/oem/sn";

    /** mac在共享区域存放路径 */
    private final static String MAC_FILE_PATH = "/mnt/oem/mac";

    /** app认证结果在共享区域存放路径 */
    private final static String AUTH_RESULT_FILE_PATH = "/mnt/oem/authresult";

    /** 预留数据在共享区域内存放路径 */
    private final static String RESERVED_FILE_PATH = "/mnt/oem/reserved";

    /** rom版本号 */
    private static String softVersion = null;

    /** xml编辑器 */
    private static XmlEditor mXmlEditor = null;

    /**
     * 改变目录权限
     */
    public static void changeFilePermission() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //修改文件权限
                SendCmd.get().oem_rwx();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取型号
     *
     * @return 机型
     */
    public static String getModel() {
        getSoftwareVersion();
        String model = null;
        if (softVersion != null && softVersion.contains("-")) {
            String[] ary = softVersion.split("-");
            if (ary.length == 4) {
                model = ary[2];
            }
        }
        LogUtil.i("deviceinfo", "getModel：  " + model);
        return model;
    }

    /**
     * 获取Rom固件版本号 ,南传规则：客户-版型-机型-软件版本
     *
     * @return 固件版本号  LZ-R1-M01-V01001，岚正规则： 品牌（对应南传客户）-芯片（版型）-型号（机型）-版本（软件版本）
     */
    public static String getSoftwareVersion() {
        if (softVersion == null) {
            try {
                softVersion = SystemProperties.get("ro.build.display.id", "unKnow");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LogUtil.i("deviceinfo", "getSoftwareVersion:  " + softVersion);
        return softVersion;
    }

    /**
     * 获取有线MAC地址
     *
     * @return mac地址
     */
    public static String getEthernetMac() {
        return getMacAddress();
    }

    /**
     * 获取序列号
     *
     * @return SN
     */
    public static String getSN() {
        String sn = null;
        try {
            // 读取共享区域的sn地址
            sn = getData(TAG_XML_SN, SN_FILE_PATH);
            if (sn == null || "".equals(sn)) {
                // 如果共享区域没有，则读取默认地址下的sn，读完之后将其写入共享区域内
                String bootSn = SystemProperties.get("ro.boot.serialno");
                // 岚正魔方的sn长度是8，讯飞超脑盒子（中星微）的sn是12
                int snLength = isOemDirExist() ? 8 : 12;
                if (bootSn != null && bootSn.length() == snLength) {
                    sn = bootSn;
                    saveData(TAG_XML_SN, sn, SN_FILE_PATH);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.i("deviceinfo", "getSN:  " + sn);
        return sn;
    }

    /**
     * 获取MAC地址
     *
     * @return String mac地址
     */
    private static String getMacAddress() {
        // 读取共享区域的mac地址
        String address = getData(TAG_XML_MAC, MAC_FILE_PATH);
        if (address == null || "".equals(address)) {
            // 如果共享区域没有，则读取默认地址下的mac地址，读完之后将其写入共享区域内
            String mac = LocalFileCRUDUtils.readFileNoNull("/sys/class/net/eth0/address");
            String sn = SystemProperties.get("ro.boot.serialno");
            // 岚正魔方的sn长度是8，讯飞超脑盒子（中星微）的sn是12位
            int snLength = isOemDirExist() ? 8 : 12;
            if (sn != null && sn.length() == snLength) {
                address = mac;
                saveData(TAG_XML_MAC, address, MAC_FILE_PATH);
            }
        }
        LogUtil.i("deviceinfo", "getMacAddress:  " + address);
        return address;
    }

    /**
     * 判断oem目录是否存在
     *
     * @return oem是否存在
     */
    private static boolean isOemDirExist() {
        File file = new File("/mnt/oem/");
        return file.exists() && file.isDirectory();
    }

    /**
     * 获取GDID
     *
     * @return GDID
     */
    public static String getGDID() {
        String gdid = getData(TAG_XML_GDID, GDID_FILE_PATH);
        LogUtil.i("deviceinfo", "getGDID:  " + gdid);
        return gdid;
    }

    /**
     * 获取SW(客户-版型-机型)
     *
     * @return SW
     */
    public static String getSW() {
        getSoftwareVersion();
        String sw = null;
        if (softVersion != null && softVersion.contains("-")) {
            String[] ary = softVersion.split("-");
            if (ary.length == 4) {
                sw = ary[0] + ary[1] + ary[2];
            }
        }
        LogUtil.i("deviceinfo", "getSW:  " + sw);
        return sw;
    }

    /**
     * 获取IC(芯片平台)
     *
     * @return IC
     */
    public static String getIC() {
        getSoftwareVersion();
        String ic = null;
        if (softVersion != null && softVersion.contains("-")) {
            String[] ary = softVersion.split("-");
            if (ary.length == 4) {
                ic = ary[1];
            }
        }
        LogUtil.i("deviceinfo", "getIC:  " + ic);
        return ic;
    }

    /**
     * 获取OsVersion(Android版本)
     *
     * @return OsVersion
     */
    public static String getOsVersion() {
        String osVersion = android.os.Build.VERSION.RELEASE;
        LogUtil.i("deviceinfo", "getOsVersion:  " + osVersion);
        return osVersion;
    }

    /**
     * 获取厂商
     *
     * @return 厂商
     */
    public static String getBoardName() {
        getSoftwareVersion();
        String bname = null;
        if (softVersion != null && softVersion.contains("-")) {
            String[] ary = softVersion.split("-");
            if (ary.length == 4) {
                bname = ary[0];
            }
        }
        LogUtil.i("deviceinfo", "getBoardName:  " + bname);
        return bname;
    }

    /**
     * 将GDID保存至本地
     *
     * @param value gdid值
     */
    public static void setGDID(String value) {
        saveData(TAG_XML_GDID, value, GDID_FILE_PATH);
    }

    /**
     * 保存app认证结果到共享区域内
     *
     * @param result app认证结果
     */
    public static void setApprresult(String result) {
        saveData(TAG_XML_AUTHRESULT, result, AUTH_RESULT_FILE_PATH);
    }

    /**
     * 保存预留数据值共享区域内
     *
     * @param reserved 预留数据
     */
    public static void setReserved(String reserved) {
        saveData(TAG_XML_RESERVED, reserved, RESERVED_FILE_PATH);
    }

    /**
     * 获取保存在共享区域内的app认证结果
     *
     * @return app认证结果
     */
    public static String getApprresult() {
        String apprresult = getData(TAG_XML_AUTHRESULT, AUTH_RESULT_FILE_PATH);
        LogUtil.i("deviceinfo", "getApprresult:  " + apprresult);
        return apprresult;
    }

    /**
     * 获取共享区域内预留数据
     *
     * @return 预留数据
     */
    public static String getReserved() {
        String reserved = getData(TAG_XML_RESERVED, RESERVED_FILE_PATH);
        LogUtil.i("deviceinfo", "getReserved:  " + reserved);
        return reserved;
    }

    /**
     * 保存key对应的value
     *
     * @param key      键值key
     * @param value    键值value
     * @param filePath 保存在/oem/目录下的文件路径
     */
    private static void saveData(String key, String value, String filePath) {
        initXmlEditor();
        if (isOemDirExist()) {
            LocalFileCRUDUtils.saveInfoToLocal(value, filePath);
        } else {
            EditorInter mEditor = mXmlEditor.edit();
            mEditor.put(key, value);
            mEditor.commit();
        }
    }

    /**
     * 获取指定key对应的value
     *
     * @param key      键值key
     * @param filePath 保存在/oem/目录下的文件路径
     * @return key对应的value
     */
    private static String getData(String key, String filePath) {
        String result = null;
        initXmlEditor();
        if (isOemDirExist()) {
            result = LocalFileCRUDUtils.readFileNoNull(filePath);
        } else {
            result = mXmlEditor.getString(key);
        }
        return result;
    }

    /**
     * 初始化xml编辑器
     */
    private static void initXmlEditor() {
        if (mXmlEditor == null) {
            mXmlEditor = XmlEditor.getXmlEditor(XML_FILE_PATH);
        }
    }
}
