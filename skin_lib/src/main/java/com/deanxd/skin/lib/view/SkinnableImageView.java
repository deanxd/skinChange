package com.deanxd.skin.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.deanxd.skin.lib.R;
import com.deanxd.skin.lib.SkinManager;
import com.deanxd.skin.lib.bean.AttrsBean;
import com.deanxd.skin.lib.core.SkinResource;
import com.deanxd.skin.lib.listener.ISkinnableView;


/**
 * 继承TextView兼容包，9.0源码中也是如此
 * 参考：AppCompatViewInflater.java
 * 86行 + 138行 + 206行
 */
public class SkinnableImageView extends AppCompatImageView implements ISkinnableView {

    private AttrsBean attrsBean;

    public SkinnableImageView(Context context) {
        this(context, null);
    }

    public SkinnableImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinnableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        attrsBean = new AttrsBean();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableImageView, defStyleAttr, 0);
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableImageView);
        typedArray.recycle();
    }

    @Override
    public void updateSkin() {
        SkinManager skinManager = SkinManager.getInstance();

        //设置背景
        int key = R.styleable.SkinnableImageView[R.styleable.SkinnableImageView_android_background];
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            Object background = skinManager.getBackgroundOrSrc(backgroundResourceId);
            // 兼容包转换
            if (background instanceof Integer) {
                int color = (int) background;
                setBackgroundColor(color);
            } else {
                Drawable drawable = (Drawable) background;
                setBackgroundDrawable(drawable);
            }
        }

        // 根据自定义属性，获取styleable中的src属性
        key = R.styleable.SkinnableImageView[R.styleable.SkinnableImageView_android_src];
        // 根据styleable获取控件某属性的resourceId
        int srcResourceId = attrsBean.getViewResource(key);
        if (srcResourceId > 0) {
            // 获取皮肤包资源
            Object skinResourceId = skinManager.getBackgroundOrSrc(srcResourceId);
            // 兼容包转换
            if (skinResourceId instanceof Integer) {
                int color = (int) skinResourceId;
                setImageResource(color);
            } else {
                Drawable drawable = (Drawable) skinResourceId;
                setImageDrawable(drawable);
            }
        }
    }
}
