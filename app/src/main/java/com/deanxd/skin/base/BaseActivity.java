package com.deanxd.skin.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.deanxd.skin.lib.SkinManager;

public abstract class BaseActivity extends AppCompatActivity {

    private SkinManager skinManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        skinManager = SkinManager.getInstance();
        skinManager.register(this);
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        skinManager.applySkinConfig(this);
    }

    protected abstract int getLayoutId();


    @Override
    protected void onDestroy() {
        skinManager.unRegister(this);
        super.onDestroy();
    }
}
