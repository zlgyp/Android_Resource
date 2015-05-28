package com.vivo.test.systemui.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.vivo.test.systemui.R;

public class DragAnimationView extends ImageView {

	public static final String TAG = "DragAnimationView";
	public static final int NOT_TIP_ARROW = -1;
	
	public static final int STATE_LINE = 0;
	public static final int STATE_ARROW = 1;
	private int mLastViewState = STATE_LINE;
	private int mRotation = Surface.ROTATION_0;

	public static final int ARROW_BLACK_STYLE = Color.parseColor("#66000000");
	public static final int ARROW_WHITE_STYLE = Color.parseColor("#80ffffff");
	private int mColor = ARROW_BLACK_STYLE;
	private Paint mPaint;
	private Path mPath;
	
	private int mStrokeWidth;
	private int mMaxOffset;
	private int mHalfArrowLength;
	private int mViewMinHeight;
	
	private long mDuration = 200;
	private float mAnimProgress = 0;
	private boolean mIsTipArrowState = false;
	private Context mContext;
	
	private int[] mVertexA = new int[2];
	private int[] mVertexB = new int[2];
	private int[] mVertexC = new int[2];
	
	public DragAnimationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		
		mStrokeWidth = context.getResources().getDimensionPixelSize(R.dimen.vivo_drag_arrow_stroke_width);
		mMaxOffset = context.getResources().getDimensionPixelSize(R.dimen.vivo_drag_arrow_max_offset);
		mHalfArrowLength = context.getResources().getDimensionPixelSize(R.dimen.vivo_drag_arrow_half_length);
		mViewMinHeight = context.getResources().getDimensionPixelSize(R.dimen.vivo_drag_view_min_width);
		
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(mStrokeWidth);
		mPath = new Path();
	}
	
	public DragAnimationView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public DragAnimationView(Context context){
		this(context, null);
	}

	public void setColor(int color){
		mPaint.setColor(color);
	}

	public void changeToArrow(int rotation){
		if(STATE_LINE != mLastViewState){
		    Log.w(TAG, "has been Arrow state, return");
			return;
		}
        Log.d(TAG,"changing to Arrow state");
		mLastViewState = STATE_ARROW;
		mRotation = rotation;
		mDrawHander.sendEmptyMessage(MSG_TO_ARROW);
	}
	
	public void changeToLine(int rotation){
		if(STATE_LINE == mLastViewState){
            Log.w(TAG,"has been Line state,, return");
			return;
		}
        Log.d(TAG,"changing Line state");
		mLastViewState = STATE_LINE;
		mRotation = rotation;
		mDrawHander.sendEmptyMessage(MSG_TO_LINE);
	}

	public void keepingLineState(int rotation){
        Log.d(TAG, "keep in the line state, rotation ="+rotation);
		mLastViewState = STATE_LINE;
		mAnimProgress = 0;
		mRotation = rotation;
		mDrawHander.sendEmptyMessage(MSG_KEEP_LINE);
	}

	public void setTipArrowState(boolean state, int arrowResId, int rotation){
        Log.d(TAG, "setTipArrowState state = "+state +", rotation = "+ rotation);
		mIsTipArrowState = state;
		if(state){
			setBackgroundResource(arrowResId);
		}else{
			setBackground(null);
			keepingLineState(rotation);
		}
	}
	
	private void startAnim(int state){
		ValueAnimator anim;
		if(STATE_ARROW == state) {
		    anim = ValueAnimator.ofFloat(0f, 1f);
		} else {
		    anim = ValueAnimator.ofFloat(1f, 0f);
		}
		anim.setDuration(mDuration);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimProgress = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
	}
	
	private void updateVertexCoordinate(){
	    int halfViewWidth = getMeasuredWidth() / 2;
	    int halfViewHeight = getMeasuredHeight()/ 2;
	    switch(mRotation) {
	    case Surface.ROTATION_0:
	        mVertexA[0] = halfViewWidth - mHalfArrowLength; 
	        mVertexB[0] = halfViewWidth; 
	        mVertexC[0] = halfViewWidth + mHalfArrowLength; 
	        mVertexA[1] = mVertexB[1] = mVertexC[1] = halfViewHeight;
	        mVertexA[1] -= mAnimProgress * mMaxOffset;
	        mVertexB[1] += mAnimProgress * mMaxOffset;
	        mVertexC[1] -= mAnimProgress * mMaxOffset;
	        break;
	    case Surface.ROTATION_180:
	        mVertexA[0] = halfViewWidth - mHalfArrowLength; 
	        mVertexB[0] = halfViewWidth; 
	        mVertexC[0] = halfViewWidth + mHalfArrowLength; 
	        mVertexA[1] = mVertexB[1] = mVertexC[1] = halfViewHeight;
	        mVertexA[1] += mAnimProgress * mMaxOffset;
	        mVertexB[1] -= mAnimProgress * mMaxOffset;
	        mVertexC[1] += mAnimProgress * mMaxOffset;
	        break;
	    case Surface.ROTATION_90:
	        mVertexA[1] = halfViewHeight - mHalfArrowLength; 
            mVertexB[1] = halfViewHeight; 
            mVertexC[1] = halfViewHeight + mHalfArrowLength; 
            mVertexA[0] = mVertexB[0] = mVertexC[0] = halfViewWidth;
            mVertexA[0] -= mAnimProgress * mMaxOffset;
            mVertexB[0] += mAnimProgress * mMaxOffset;
            mVertexC[0] -= mAnimProgress * mMaxOffset;
            break;
        case Surface.ROTATION_270:
            mVertexA[1] = halfViewHeight - mHalfArrowLength; 
            mVertexB[1] = halfViewHeight; 
            mVertexC[1] = halfViewHeight + mHalfArrowLength; 
            mVertexA[0] = mVertexB[0] = mVertexC[0] = halfViewWidth;
            mVertexA[0] += mAnimProgress * mMaxOffset;
            mVertexB[0] -= mAnimProgress * mMaxOffset;
            mVertexC[0] += mAnimProgress * mMaxOffset;
            break;
        }
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		ViewGroup.LayoutParams lp = getLayoutParams();
		if(!mIsTipArrowState && lp.width == ViewGroup.LayoutParams.WRAP_CONTENT){
            Log.d(TAG, "normal state: mRotation = "+mRotation);
			int newWidthMeasureSpec, newHeightMeasureSpec;
			int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
			int specModeHeight = MeasureSpec.getSize(heightMeasureSpec);
			int dragViewWidth, dragViewHeight;
			if(mRotation == Surface.ROTATION_0 || mRotation == Surface.ROTATION_180){
				dragViewWidth = mContext.getResources().getDisplayMetrics().widthPixels;
				dragViewHeight = mViewMinHeight ;
			}else{
				dragViewWidth =  mViewMinHeight ;
				dragViewHeight = mContext.getResources().getDisplayMetrics().heightPixels;
			}
			newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(dragViewWidth, specModeWidth);
			newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(dragViewHeight, specModeHeight);
			setMeasuredDimension(newWidthMeasureSpec, newHeightMeasureSpec);
		}else{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(mIsTipArrowState){			
			super.onDraw(canvas);
			return;
		}else{
		    updateVertexCoordinate();
			mPath.moveTo(mVertexA[0], mVertexA[1]);
			mPath.lineTo(mVertexB[0], mVertexB[1]);
			mPath.lineTo(mVertexC[0], mVertexC[1]);
			canvas.drawPath(mPath, mPaint);
			mPath.reset();
		}
	}
	
	public static final int MSG_TO_ARROW = 0;
	public static final int MSG_TO_LINE = 1;
	public static final int MSG_KEEP_LINE = 2;

	private Handler mDrawHander = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case MSG_TO_ARROW:
			    startAnim(STATE_ARROW);
				break;
			case MSG_TO_LINE:
			    startAnim(STATE_LINE);
				break;
			case MSG_KEEP_LINE:
				invalidate();
				break;
			}
		}
		
	};

}
