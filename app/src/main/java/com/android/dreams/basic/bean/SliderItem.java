package com.android.dreams.basic.bean;

/**
 * Desc: TODO
 * <p>
 * Author: xiezhitao
 * PackageName: com.lzui.screensaver
 * ProjectName: ScreenSave
 * Date: 2019/3/5 17:19
 */
public class SliderItem {

    //轮播项 下载地址
    private String img_url;

    //轮播项 完整性校验码
    private long img_code;

    //轮播项 备份下载地址
    private String bakUrl;

    //轮播项 备份完整性校验码
    private long bakCode;

    //点击该轮播项时，要跳转app的包名
    private String app_pk_name;

    //点击该轮播项时，要跳转app的指定类名，或者指定动作
    private String click_action;

    //点击该轮播项，要跳转app时，需要携带的参数
    private String click_param;

    //点击该轮播项，跳转描述
    private String action_desc;

    //版本
    private String lavsn;

    private String pk_name;

    private String unit_action;

    public String getAction_desc() {
        return action_desc;
    }

    public void setAction_desc(String action_desc) {
        this.action_desc = action_desc;
    }

    public String getLavsn() {
        return lavsn;
    }

    public void setLavsn(String lavsn) {
        this.lavsn = lavsn;
    }

    public String getPk_name() {
        return pk_name;
    }

    public void setPk_name(String pk_name) {
        this.pk_name = pk_name;
    }

    public String getUnit_action() {
        return unit_action;
    }

    public void setUnit_action(String unit_action) {
        this.unit_action = unit_action;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public long getImg_code() {
        return img_code;
    }

    public void setImg_code(long img_code) {
        this.img_code = img_code;
    }

    public String getBakUrl() {
        return bakUrl;
    }

    public void setBakUrl(String bakUrl) {
        this.bakUrl = bakUrl;
    }

    public long getBakCode() {
        return bakCode;
    }

    public void setBakCode(long bakCode) {
        this.bakCode = bakCode;
    }

    public String getApp_pk_name() {
        return app_pk_name;
    }

    public void setApp_pk_name(String app_pk_name) {
        this.app_pk_name = app_pk_name;
    }

    public String getClick_action() {
        return click_action;
    }

    public void setClick_action(String click_action) {
        this.click_action = click_action;
    }

    public String getClick_param() {
        return click_param;
    }

    public void setClick_param(String click_param) {
        this.click_param = click_param;
    }

    public SliderItem(String img_url, long img_code, String bakUrl, long bakCode,
                      String app_pk_name, String click_action, String click_param,
                      String action_desc, String lavsn, String pk_name, String unit_action) {
        this.img_url = img_url;
        this.img_code = img_code;
        this.bakUrl = bakUrl;
        this.bakCode = bakCode;
        this.app_pk_name = app_pk_name;
        this.click_action = click_action;
        this.click_param = click_param;
        this.action_desc = action_desc;
        this.lavsn = lavsn;
        this.pk_name = pk_name;
        this.unit_action = unit_action;
    }


    @Override
    public String toString() {
        return "SliderItem{" +
                "img_url='" + img_url + '\'' +
                ", img_code=" + img_code +
                ", bakUrl='" + bakUrl + '\'' +
                ", bakCode=" + bakCode +
                ", app_pk_name='" + app_pk_name + '\'' +
                ", click_action='" + click_action + '\'' +
                ", click_param='" + click_param + '\'' +
                ", action_desc='" + action_desc + '\'' +
                ", lavsn='" + lavsn + '\'' +
                ", pk_name='" + pk_name + '\'' +
                ", unit_action='" + unit_action + '\'' +
                '}';
    }
}
