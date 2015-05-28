package com.vivo.test.systemui.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.vivo.test.systemui.R;

import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * TODO: document your custom view class.
 */
public class MyCustomView extends View {

    private  Paint mArrowPaint;
    private Path mPath;

    public MyCustomView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        initAnim();
        mArrowPaint = new Paint();
        mArrowPaint.setAntiAlias(true);
        mArrowPaint.setColor(Color.BLACK);
        mArrowPaint.setStrokeWidth(50);

        mPath = new Path();
        // Update TextPaint and text measurements from attributes

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;


        updateV(contentWidth,contentHeight);

        mPath.moveTo(VA[0],VA[1]);
        mPath.lineTo(VB[0],VB[1]);
        mPath.lineTo(VC[0],VC[1]);

        canvas.drawPath(mPath,mArrowPaint);
        mPath.reset();
        Log.d("zhangliang" , "VB[1] " + VB[1]);
    }

    int [] VA = new int[2];
    int [] VB = new int[2];
    int [] VC = new int[2];
    int space = 20;
    private void updateV(int width, int height){
         VA[0] = space;
         VA[1] = 0;
         VB[0] = (width - 2 *space )/2;
         VB[1] = (int)(height * process);
         VC[0] = width -space;
         VC[1] = 0;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
            startAnim();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    ValueAnimator animator;
    float process = 0;

    public void initAnim() {
        animator = ValueAnimator.ofFloat(0,1);
        animator.setDuration(10000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                process = (float)animation.getAnimatedValue();
                invalidate();
            }
        });
    }



    public void startAnim(){
        if (animator != null ) {
            animator.end();
        }
        animator.start();
    }

    public void stopAnim() {
        if (animator != null) {
            animator.cancel();
        }
    }

}
