package com.deanxd.skin.lib.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.deanxd.skin.lib.R;
import com.deanxd.skin.lib.bean.AttrsBean;
import com.deanxd.skin.lib.listener.ISkinnableView;


/**
 * 继承TextView兼容包，9.0源码中也是如此
 * 参考：AppCompatViewInflater.java
 * 86行 + 138行 + 206行
 */
public class SkinnableTextView extends AppCompatTextView implements ISkinnableView {

    private AttrsBean attrsBean;

    public SkinnableTextView(Context context) {
        this(context, null);
    }

    public SkinnableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public SkinnableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        attrsBean = new AttrsBean();

        // 根据自定义属性，匹配控件属性的类型集合，如：background + textColor
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SkinnableTextView,
                defStyleAttr, 0);
        // 存储到临时JavaBean对象
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableTextView);
        // 这一句回收非常重要！obtainStyledAttributes()有语法提示！！
        typedArray.recycle();
    }


    @Override
    public void updateSkin() {
        // 根据自定义属性，获取styleable中的background属性
        int key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_background];
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResourceId);
            setBackgroundDrawable(drawable);
        }

        // 根据自定义属性，获取styleable中的textColor属性
        key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_textColor];
        int textColorResourceId = attrsBean.getViewResource(key);
        if (textColorResourceId > 0) {
            ColorStateList color = ContextCompat.getColorStateList(getContext(), textColorResourceId);
            setTextColor(color);
        }
    }
}
