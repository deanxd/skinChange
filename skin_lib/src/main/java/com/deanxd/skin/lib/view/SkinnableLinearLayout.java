package com.deanxd.skin.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.deanxd.skin.lib.R;
import com.deanxd.skin.lib.bean.AttrsBean;
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
        int key = R.styleable.SkinnableLinearLayout[R.styleable.SkinnableLinearLayout_android_background];
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResourceId);
            setBackground(drawable);
        }
    }
}
