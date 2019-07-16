package com.deanxd.skin.lib.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.deanxd.skin.lib.R;
import com.deanxd.skin.lib.bean.AttrsBean;
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
    public void updateSkin() {
        //设置背景
        int key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_background];
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResourceId);
            // 控件自带api，这里不用setBackgroundColor()因为在9.0测试不通过
            // setBackgroundDrawable本来过时了，但是兼容包重写了方法
            setBackgroundDrawable(drawable);
        }

        //设置字体颜色
        key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_textColor];
        int textColorResourceId = attrsBean.getViewResource(key);
        if (textColorResourceId > 0) {
            ColorStateList color = ContextCompat.getColorStateList(getContext(), textColorResourceId);
            setTextColor(color);
        }
    }
}
