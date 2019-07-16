package com.deanxd.skin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.deanxd.skin.base.BaseActivity;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void openActivity(View v) {
        startActivity(new Intent(this, ThirdActivity.class));

    }
}
