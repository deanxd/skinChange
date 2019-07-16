package com.deanxd.skin.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.deanxd.skin.lib.R;
import com.deanxd.skin.lib.bean.AttrsBean;
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
        int key = R.styleable.SkinnableRelativeLayout[R.styleable.SkinnableRelativeLayout_android_background];
        int backgroundResourceId = attrsBean.getViewResource(key);
        if (backgroundResourceId > 0) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), backgroundResourceId);
            setBackground(drawable);
        }
    }
}
