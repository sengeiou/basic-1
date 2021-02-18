package com.android.dreams.basic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.lunzn.tool.autofit.GetScreenSize;


/**
 * 图片播放控件
 *
 * @author renweiming
 * @version [版本号]
 * @date 2016年12月14日
 * @project COM.LZ.M02.MEDIA
 * @package com.lzui.media.customview
 * @package ImageViewer.java
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ImageViewer extends View {

    /**
     * 缩放时，每次缩放的比率
     */
    public static final float SCALE_STEP = 0.1f;

    /**
     * 旋转时，每次转动90度
     */
    public static final int ROTATE_STEP = 90;

    /**
     * 移动时，每次移动的距离(in pixels)
     */
    private static final int TRANSLATE_STEP = 5;

    /**
     * 显示的图片
     */
    private Bitmap mBitmap = null;

    /**
     * 当前的缩放比率
     */
    private float mScale = 1;

    /**
     * 当前的旋转角度
     */
    private int mDegree = 0;

    /**
     * 水平移动距离
     */
    private int mTranslateX = 0;

    /**
     * 垂直移动距离
     */
    private int mTranslateY = 0;

    /**
     * 屏幕高度
     */
    private int mScreenHeight = 0;

    /**
     * 屏幕宽度
     */
    private int mScreenWidth = 0;

    /**
     * 内容展示区域的宽度
     */
    private int mContentWidth = 0;

    /**
     * 内容展示区域的高度
     */
    private int mContentHeight = 0;

    /**
     * 如果一些值，在view测量完成之前就设置进来，那在测量之后，需要对这些值重新计算
     */
    private boolean mNeedCalSize = false;

    /**
     * 当前的操作矩阵
     */
    private Matrix mMatrix = new Matrix();

    private Paint paint = new Paint();

    public ImageViewer(Context context) {
        this(context, null, 0);
    }

    public ImageViewer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScreenHeight = GetScreenSize.heightPixels;
        mScreenWidth = GetScreenSize.widthPixels;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mContentWidth = w - getPaddingLeft() - getPaddingRight();
        mContentHeight = h - getPaddingTop() - getPaddingBottom();
        resizeBitmap();
        if (mNeedCalSize) {
            calTranslate();
        }
    }

    /**
     * 设置展示的图片
     *
     * @param bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        realse();
        this.mBitmap = bitmap;
        resizeBitmap();
        // 还原操作值
        setDefaultData();
        calTranslate();
        invalidate();
    }

    private void resizeBitmap() {
        if (mBitmap != null && mContentWidth != 0 && mContentHeight != 0) {
            int bw = mBitmap.getWidth();
            int bh = mBitmap.getHeight();
            float rw = (float) bw / mContentWidth;
            float rh = (float) bh / mContentHeight;
            if (rw > rh && rw > 1) {
                mBitmap = Bitmap.createScaledBitmap(mBitmap, mContentWidth, (int) (mContentHeight / rw), true);
            } else if (rh > rw && rh > 1) {
                mBitmap = Bitmap.createScaledBitmap(mBitmap, (int) (mContentWidth / rh), mContentHeight, true);
            }

        }
    }

    /**
     * 初次显示一张图片时，图片居中显示，根据图片大小，设置移动距离
     */
    private void calTranslate() {
        if (mBitmap != null) {
            int bw = mBitmap.getWidth();
            int bh = mBitmap.getHeight();
            if (mContentHeight == mContentWidth && mContentHeight == 0) {
                mNeedCalSize = true;
            } else {
                mNeedCalSize = false;
                mTranslateX = (mContentWidth - bw) / 2;
                mTranslateY = (mContentHeight - bh) / 2;
            }

        }
    }

    /**
     * 还原操作值
     */
    private void setDefaultData() {
        mScale = 1f;
        mDegree = 0;
        mMatrix.reset();
        mTranslateX = 0;
        mTranslateY = 0;
    }

    /**
     * 放大，缩小
     *
     * @param isGrow true放大，false缩小
     */
    public void scale(boolean isGrow) {
        if (mBitmap != null) {
            if (isGrow) {
                mScale += SCALE_STEP;
            } else {
                mScale -= SCALE_STEP;
            }
            invalidate();
        }
    }

    /**
     * 旋转
     *
     * @param isClockWise true 顺时针，逆时针
     */
    public void rotate(boolean isClockWise) {
        if (mBitmap != null) {
            if (isClockWise) {
                mDegree += ROTATE_STEP;
            } else {
                mDegree -= ROTATE_STEP;
            }
            invalidate();
        }
    }

    /**
     * 处理上下左右移动图片
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                case KeyEvent.KEYCODE_DPAD_UP:
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    translate(event.getKeyCode());
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void translate(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                mTranslateY += TRANSLATE_STEP;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                mTranslateY -= TRANSLATE_STEP;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mTranslateX -= TRANSLATE_STEP;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mTranslateX += TRANSLATE_STEP;
                break;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            // FIXME 中心位置并不是屏幕中心，是图片中心，需要修改
            mMatrix.setTranslate(mTranslateX, mTranslateY);
            mMatrix.postRotate(mDegree, mScreenWidth / 2, mScreenHeight / 2);
            mMatrix.postScale(mScale, mScale, mScreenWidth / 2, mScreenHeight / 2);
            canvas.drawBitmap(mBitmap, mMatrix, null);
        } else {
            canvas.drawText("正在加载中，请稍后", mScreenWidth / 2, mScreenHeight / 2, paint);
        }
    }

    public void realse() {
        if (mBitmap != null) {
            mBitmap.recycle();
            System.gc();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
