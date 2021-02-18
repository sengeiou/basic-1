package com.android.dreams.basic.bean;

import java.util.List;

/**
 * Desc: TODO
 * <p>
 * Author: xiezhitao
 * PackageName: com.lzui.screensaver
 * ProjectName: ScreenSave
 * Date: 2019/3/5 17:19
 */
public class ScreenSaverBean {

    //最新屏保版本号
    private String vsn;

    //轮播数据类型 （当前仅1） 默认 1：图片
    private int type;

    //间隔时间（单位毫秒 ms）
    private long interval;

    //轮播项
    private List<SliderItem> slider;

    //launcher 对应的渠道号
    private int channelId;

    //名称
    private String name;

    //唤醒时间
    private long wpTime;

    //屏保开关
    private int switchFlag;

    public int getSwitchFlag() {
        return switchFlag;
    }

    public void setSwitchFlag(int switchFlag) {
        this.switchFlag = switchFlag;
    }



    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = vsn;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public List<SliderItem> getSlider() {
        return slider;
    }

    public void setSlider(List<SliderItem> slider) {
        this.slider = slider;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWpTime() {
        return wpTime;
    }

    public void setWpTime(long wpTime) {
        this.wpTime = wpTime;
    }

    public ScreenSaverBean(String vsn, int type, long interval, List<SliderItem> slider, int channelId,
                           String name, long wpTime) {
        this.vsn = vsn;
        this.type = type;
        this.interval = interval;
        this.slider = slider;
        this.channelId = channelId;
        this.name = name;
        this.wpTime = wpTime;
    }

    @Override
    public String toString() {
        return "ScreenSaverBean{" +
                "vsn='" + vsn + '\'' +
                ", type=" + type +
                ", interval=" + interval +
                ", slider=" + slider +
                ", channelId=" + channelId +
                ", name='" + name + '\'' +
                ", wpTime=" + wpTime +
                ", switchFlag=" + switchFlag +
                '}';
    }

}
