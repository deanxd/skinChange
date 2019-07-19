package com.deanxd.skin.lib.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
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
public class SkinnableButton extends AppCompatButton implements ISkinnableView {

    private AttrsBean attrsBean;

    public SkinnableButton(Context context) {
        this(context, null);
    }

    public SkinnableButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public SkinnableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableButton, defStyleAttr, 0);
        if (typedArray != null) {
            attrsBean = new AttrsBean();
            attrsBean.saveViewResource(typedArray, R.styleable.SkinnableButton);
            typedArray.recycle();
        }
    }


    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }



    @Override
    public void updateSkin() {

        SkinResource skinResource = SkinManager.getSkinResource();

        //设置背景
        int key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_background];
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            Object background = skinResource.getBackgroundOrSrc(backgroundResourceId);
            // 兼容包转换
            if (background instanceof Integer) {
                int color = (int) background;
                setBackgroundColor(color);
            } else {
                Drawable drawable = (Drawable) background;
                setBackgroundDrawable(drawable);
            }
        }

        //设置字体颜色
        key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_textColor];
        int textColorResourceId = attrsBean.getViewResource(key);
        if (textColorResourceId > 0) {
            ColorStateList color = skinResource.getColorStateList(textColorResourceId);
            setTextColor(color);
        }

        // 根据自定义属性，获取styleable中的字体 custom_typeface 属性
        key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_custom_typeface];
        int textTypefaceResourceId = attrsBean.getViewResource(key);
        if (textTypefaceResourceId > 0) {
            setTypeface(skinResource.getTypeface(textTypefaceResourceId));
        }
    }
}
