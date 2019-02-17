package com.cy.testandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Practice6DrawLineView extends View {

    public Practice6DrawLineView(Context context) {
        super(context);
        init();
    }

    public Practice6DrawLineView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice6DrawLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(20);
    }
    int y;
    Paint paint;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawLine() 方法画直线



        canvas.drawLine(200,200+y,600,200+y,paint);
        y=+10;
        postInvalidateDelayed(100);
        Log.e("tag","draw哈哈");

    }
}
