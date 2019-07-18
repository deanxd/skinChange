package com.deanxd.skin.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.deanxd.skin.lib.R;
import com.deanxd.skin.lib.SkinManager;
import com.deanxd.skin.lib.bean.AttrsBean;
import com.deanxd.skin.lib.core.SkinResource;
import com.deanxd.skin.lib.listener.ISkinnableView;

public class SkinnableLinearLayout extends LinearLayout implements ISkinnableView {

    private AttrsBean attrsBean;

    public SkinnableLinearLayout(Context context) {
        this(context, null);
    }

    public SkinnableLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinnableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        attrsBean = new AttrsBean();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableLinearLayout, defStyleAttr, 0);
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableLinearLayout);
        typedArray.recycle();
    }

    @Override
    public void updateSkin() {
        //设置背景
        int key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_background];
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {

            SkinResource skinResource = SkinManager.getSkinResource();
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
    }
}
