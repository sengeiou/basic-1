package com.android.dreams.basic;

import com.android.dreams.basic.bean.SliderItem;

/**
 * Desc: TODO
 * <p>
 * Author: xiezhitao
 * PackageName: com.lzui.screensaver
 * ProjectName: ScreenSave
 * Date: 2019/3/11 15:37
 */
public interface DownLoadInterface {

    void succesed(SliderItem item);

    void failed(SliderItem item);
}
