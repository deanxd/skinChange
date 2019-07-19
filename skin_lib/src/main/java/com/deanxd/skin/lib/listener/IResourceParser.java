package com.deanxd.skin.lib.listener;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public interface IResourceParser {

    int getColor(int colorId);

    Drawable getDrawableOrMipMap(int drawableId);

    String getString(int stringId);

    ColorStateList getColorStateList(int ids);

    /**
     * @return 返回值特殊情况, 可能是color / drawable / mipmap
     */
    Object getBackgroundOrSrc(int resourceId);

    /**
     * 获得字体
     */
    Typeface getTypeface(int resourceId);
}
