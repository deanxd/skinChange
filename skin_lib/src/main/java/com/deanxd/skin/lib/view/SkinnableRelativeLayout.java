package com.deanxd.skin.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.deanxd.skin.lib.R;
import com.deanxd.skin.lib.SkinManager;
import com.deanxd.skin.lib.bean.AttrsBean;
import com.deanxd.skin.lib.core.SkinResource;
import com.deanxd.skin.lib.listener.ISkinnableView;

public class SkinnableRelativeLayout extends RelativeLayout implements ISkinnableView {

    private AttrsBean attrsBean;

    public SkinnableRelativeLayout(Context context) {
        this(context, null);
    }

    public SkinnableRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinnableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        attrsBean = new AttrsBean();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableRelativeLayout, defStyleAttr, 0);
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableRelativeLayout);
        typedArray.recycle();
    }

    @Override
    public void updateSkin() {
        //设置背景
        int key = R.styleable.SkinnableRelativeLayout[R.styleable.SkinnableRelativeLayout_android_background];
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
