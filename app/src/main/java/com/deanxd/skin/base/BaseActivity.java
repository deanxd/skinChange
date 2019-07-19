package com.deanxd.skin.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.deanxd.skin.lib.SkinManager;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SkinManager.register(this);
        super.onCreate(savedInstanceState);
        SkinManager.updateSkinTheme(this);
    }

    @Override
    protected void onDestroy() {
        SkinManager.unRegister(this);
        super.onDestroy();
    }
}
