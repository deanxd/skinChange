package com.deanxd.skin.lib.core;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.deanxd.skin.lib.SkinManager;
import com.deanxd.skin.lib.view.SkinnableButton;
import com.deanxd.skin.lib.view.SkinnableImageView;
import com.deanxd.skin.lib.view.SkinnableLinearLayout;
import com.deanxd.skin.lib.view.SkinnableRelativeLayout;
import com.deanxd.skin.lib.view.SkinnableTextView;

/**
 * LayoutInflater解析 布局过程监听
 */
public class SkinFactory implements LayoutInflater.Factory2 {

    private Activity mActivity;

    public SkinFactory(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        switch (name) {
            case "LinearLayout":
                view = new SkinnableLinearLayout(context, attrs);
                verifyNotNull(view, name);
                break;
            case "RelativeLayout":
                view = new SkinnableRelativeLayout(context, attrs);
                verifyNotNull(view, name);
                break;
            case "TextView":
                view = new SkinnableTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case "ImageView":
                view = new SkinnableImageView(context, attrs);
                verifyNotNull(view, name);
                break;
            case "Button":
                view = new SkinnableButton(context, attrs);
                verifyNotNull(view, name);
                break;
        }

        if (mActivity instanceof AppCompatActivity) {
            AppCompatDelegate delegate = ((AppCompatActivity) mActivity).getDelegate();
            return delegate.createView(parent, name, context, attrs);
        }
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    private static void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(SkinManager.class.getName() + " asked to inflate view for <" + name + ">, but returned null");
        }
    }
}
