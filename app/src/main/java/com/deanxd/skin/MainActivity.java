package com.deanxd.skin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

import com.deanxd.skin.base.BaseActivity;
import com.deanxd.skin.dynamic.DynamicMainActivity;
import com.deanxd.skin.lib.SkinManager;
import com.deanxd.skin.lib.utils.PreferencesUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isNight = PreferencesUtils.getBoolean(this, "isNight");
        SkinManager.setDayNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void openActivity(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    /**
     * 打开皮肤测试界面
     */
    public void openSkinDynamicTestActivity(View view) {
        startActivity(new Intent(this, DynamicMainActivity.class));
    }
}
