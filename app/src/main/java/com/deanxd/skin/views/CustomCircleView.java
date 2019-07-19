package com.deanxd.skin.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.deanxd.skin.R;
import com.deanxd.skin.lib.SkinManager;
import com.deanxd.skin.lib.bean.AttrsBean;
import com.deanxd.skin.lib.core.SkinResource;
import com.deanxd.skin.lib.listener.ISkinnableView;

public class CustomCircleView extends View implements ISkinnableView {

    private Paint mTextPain;
    private AttrsBean attrsBean;

    public CustomCircleView(Context context) {
        this(context, null);
    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        attrsBean = new AttrsBean();

        // 根据自定义属性，匹配控件属性的类型集合，如：circleColor
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomCircleView,
                defStyleAttr, 0);

        int corcleColorResId = typedArray.getResourceId(R.styleable.CustomCircleView_circleColor, 0);

        // 存储到临时JavaBean对象
        attrsBean.saveViewResource(typedArray, R.styleable.CustomCircleView);
        // 这一句回收非常重要！obtainStyledAttributes()有语法提示！！
        typedArray.recycle();

        mTextPain = new Paint();
        mTextPain.setColor(getPaintColor(corcleColorResId));

        //开启抗锯齿，平滑文字和圆弧的边缘
        mTextPain.setAntiAlias(true);
        //设置文本位于相对于原点的中间
        mTextPain.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取宽度一半
        int width = getWidth() / 2;
        // 获取高度一半
        int height = getHeight() / 2;
        // 设置半径为宽或者高的最小值（半径）
        int radius = Math.min(width, height);
        // 利用canvas画一个圆
        canvas.drawCircle(width, height, radius, mTextPain);
    }

    @Override
    public void updateSkin() {
        // 根据自定义属性，获取styleable中的circleColor属性
        int key = R.styleable.CustomCircleView[0]; // = R.styleable.CustomCircleView_circleColor
        int resourceId = attrsBean.getViewResource(key);
        if (resourceId > 0) {
            mTextPain.setColor(getPaintColor(resourceId));
        }
        invalidate();
    }

    private int getPaintColor(int resourceId) {
        SkinResource skinResource = SkinManager.getSkinResource();
        return skinResource.getColor(resourceId);
    }
}
