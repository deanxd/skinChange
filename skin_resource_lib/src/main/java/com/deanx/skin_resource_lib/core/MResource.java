package com.deanx.skin_resource_lib.core;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class MResource extends Resources {

    private SkinResource mSkinResource;

    public MResource(SkinResource skinResource) {
        super(skinResource.getDefaultResource().getAssets(),
                skinResource.getDefaultResource().getDisplayMetrics(),
                skinResource.getDefaultResource().getConfiguration());
        mSkinResource = skinResource;
    }

    @Override
    @NonNull
    public CharSequence getText(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getText(realUsedResId);
    }

    @Override
    @RequiresApi(26)
    @NonNull
    public Typeface getFont(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getFont(realUsedResId);
    }

    @Override
    @NonNull
    public CharSequence getQuantityText(int id, int quantity) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getQuantityText(realUsedResId, quantity);
    }

    @Override
    @NonNull
    public String getString(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getString(realUsedResId);
    }

    @Override
    @NonNull
    public String getString(int id, Object... formatArgs) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getString(realUsedResId, formatArgs);
    }

    @Override
    @NonNull
    public String getQuantityString(int id, int quantity, Object... formatArgs) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getQuantityString(realUsedResId, quantity, formatArgs);
    }

    @Override
    @NonNull
    public String getQuantityString(int id, int quantity) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getQuantityString(realUsedResId, quantity);
    }

    @Override
    @NonNull
    public CharSequence getText(int id, CharSequence def) {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getText(realUsedResId, def);
    }

    @Override
    @NonNull
    public CharSequence[] getTextArray(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getTextArray(realUsedResId);
    }

    @Override
    @NonNull
    public String[] getStringArray(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getStringArray(realUsedResId);
    }

    @Override
    @NonNull
    public int[] getIntArray(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getIntArray(realUsedResId);
    }

    @Override
    @NonNull
    public TypedArray obtainTypedArray(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.obtainTypedArray(realUsedResId);
    }

    @Override
    public float getDimension(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getDimension(realUsedResId);
    }

    @Override
    public int getDimensionPixelOffset(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getDimensionPixelOffset(realUsedResId);
    }

    @Override
    public int getDimensionPixelSize(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getDimensionPixelSize(realUsedResId);
    }

    @Override
    public float getFraction(int id, int base, int pbase) {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getFraction(realUsedResId, base, pbase);
    }

    @Override
    public Drawable getDrawable(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getDrawable(realUsedResId);
    }

    @Override
    @RequiresApi(21)
    public Drawable getDrawable(int id, Resources.Theme theme) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getDrawable(realUsedResId, theme);
    }

    @Override
    public Drawable getDrawableForDensity(int id, int density) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getDrawableForDensity(realUsedResId, density);
    }

    @Override
    @RequiresApi(21)
    public Drawable getDrawableForDensity(int id, int density, Resources.Theme theme) {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getDrawableForDensity(realUsedResId, density, theme);
    }

    @Override
    public Movie getMovie(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getMovie(realUsedResId);
    }

    @Override
    public int getColor(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getColor(realUsedResId);
    }

    @Override
    @RequiresApi(23)
    public int getColor(int id, Resources.Theme theme) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getColor(realUsedResId, theme);
    }

    @Override
    @NonNull
    public ColorStateList getColorStateList(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getColorStateList(realUsedResId);
    }

    @Override
    @RequiresApi(23)
    @NonNull
    public ColorStateList getColorStateList(int id, Resources.Theme theme) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getColorStateList(realUsedResId, theme);
    }

    @Override
    public boolean getBoolean(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getBoolean(realUsedResId);
    }

    @Override
    public int getInteger(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);

        return resource.getInteger(realUsedResId);
    }

    @Override
    @NonNull
    public XmlResourceParser getLayout(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);
        return resource.getLayout(realUsedResId);
    }

    @Override
    @NonNull
    public XmlResourceParser getAnimation(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);
        return resource.getAnimation(realUsedResId);
    }

    @Override
    @NonNull
    public XmlResourceParser getXml(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);
        return resource.getXml(realUsedResId);
    }

    @Override
    @NonNull
    public InputStream openRawResource(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);
        return resource.openRawResource(realUsedResId);
    }

    @Override
    @NonNull
    public InputStream openRawResource(int id, TypedValue value) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);
        return resource.openRawResource(realUsedResId, value);
    }

    @Override
    public AssetFileDescriptor openRawResourceFd(int id) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);
        return resource.openRawResourceFd(realUsedResId);
    }

    @Override
    public void getValue(int id, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);
        resource.getValue(realUsedResId, outValue, resolveRefs);
    }

    @Override
    public void getValueForDensity(int id, int density, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(id);
        int realUsedResId = mSkinResource.getRealUsedResId(id);
        resource.getValueForDensity(realUsedResId, density, outValue, resolveRefs);
    }

    @Override
    public void getValue(String name, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
        mSkinResource.getDefaultResource().getValue(name, outValue, resolveRefs);
    }

    @Override
    public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
        return mSkinResource.getDefaultResource().obtainAttributes(set, attrs);
    }

    @Override
    public void updateConfiguration(Configuration config, DisplayMetrics metrics) {
        mSkinResource.getDefaultResource().updateConfiguration(config, metrics);
    }

    @Override
    public DisplayMetrics getDisplayMetrics() {
        return mSkinResource.getDefaultResource().getDisplayMetrics();
    }

    @Override
    public Configuration getConfiguration() {
        return mSkinResource.getDefaultResource().getConfiguration();
    }

    @Override
    public int getIdentifier(String name, String defType, String defPackage) {
        return mSkinResource.getDefaultResource().getIdentifier(name, defType, defPackage);
    }

    @Override
    public String getResourceName(int resid) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(resid);
        int realUsedResId = mSkinResource.getRealUsedResId(resid);
        return resource.getResourceName(realUsedResId);
    }

    @Override
    public String getResourcePackageName(int resid) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(resid);
        int realUsedResId = mSkinResource.getRealUsedResId(resid);
        return resource.getResourcePackageName(realUsedResId);
    }

    @Override
    public String getResourceTypeName(int resid) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(resid);
        int realUsedResId = mSkinResource.getRealUsedResId(resid);
        return resource.getResourceTypeName(realUsedResId);
    }

    @Override
    public String getResourceEntryName(int resid) throws NotFoundException {
        Resources resource = mSkinResource.getRealResource(resid);
        int realUsedResId = mSkinResource.getRealUsedResId(resid);
        return resource.getResourceEntryName(realUsedResId);
    }

    @Override
    public void parseBundleExtras(XmlResourceParser parser, Bundle outBundle) throws IOException, XmlPullParserException {
        mSkinResource.getDefaultResource().parseBundleExtras(parser, outBundle);
    }

    @Override
    public void parseBundleExtra(String tagName, AttributeSet attrs, Bundle outBundle) throws XmlPullParserException {
        mSkinResource.getDefaultResource().parseBundleExtra(tagName, attrs, outBundle);
    }
}
