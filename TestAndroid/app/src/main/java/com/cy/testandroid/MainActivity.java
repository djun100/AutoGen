package com.cy.testandroid;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import static android.view.View.GONE;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.mtv);
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
    }

    private void doAnimation(final View view){
        ValueAnimator animator = ValueAnimator.ofInt(0,400);
        animator.setDuration(1000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
//                view.layout(curValue,curValue,curValue+view.getWidth(),curValue+view.getHeight());
                view.setPivotX(curValue);
                view.setPivotY(curValue);
            }
        });
        animator.start();
    }


    private PathMeasure mPathMeasure;
    private float[] mPoint = new float[2];
    private float[] mTan = new float[2];
    private Rect frame;

    private void anim(final View imageView){

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int) ((metrics.widthPixels) * 0.7);
        int height = width;

        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point screenResolution = new Point(display.getWidth(), display.getHeight() - getStatusBarHeight() - 110);

        int leftOffset = (screenResolution.x - width) / 2;
        int topOffset = (screenResolution.y - height) / 3 + getStatusBarHeight();
        frame = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);

        Path path = new Path();
        path.moveTo(frame.centerX(),frame.centerY());
//        Log.w("tag", "终点 x " + getScreenMetrics(mContext).x / 2 + " y " + (getScreenMetrics(mContext).y - dp(mContext, 40)));
        path.quadTo(frame.right,frame.centerY(),getScreenMetrics(this).x/2,getScreenMetrics(this).y-dp(this,40));
        mPathMeasure = new PathMeasure(path, false);

        final ValueAnimator animator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator()); //插值器
//        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float distance = (float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(distance, mPoint, mTan);

                        imageView.setPivotX(mPoint[0]);
                        imageView.setPivotY(mPoint[1]);

                Log.w("tag","imageView中心位置 "+mPoint[0]+" "+mPoint[1]);
//                drawBitmap(mContext, canvas, paint, R.drawable.supermart_anim_bar, new PointF(mPoint[0], mPoint[1]));
//                invalidate();

            };


        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.start();
    }

    public int getStatusBarHeight()
    {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try
        {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusBarHeight;
    }


    public static int dp(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     *
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);

    }
}
