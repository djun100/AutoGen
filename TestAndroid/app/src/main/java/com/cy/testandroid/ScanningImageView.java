package com.cy.testandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * com.ykb.json.customview
 * 描述 :带扫描线的ImageView
 * 作者 : ykb
 * 时间 : 15/11/4.
 */
public class ScanningImageView extends View {

    private Paint paint;
    private int y = 0;
    private int mHeight = 0;

    public ScanningImageView(Context context) {
        this(context, null);
    }

    public ScanningImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanningImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(20);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0,mHeight,100,mHeight,paint);
        mHeight += 10;

        if (mHeight >= getHeight()) {
            mHeight = 0;
        }
        postInvalidateDelayed(40);

//        canvas.drawLine(0,y,100,y,paint);
//        y=+10;
//        postInvalidateDelayed(100);

        super.onDraw(canvas);
    }
}