package myviews.com.qqqq;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 15-6-8.
 */

public class MyView extends View {

    private String mText;
    private int mTextColor;
    private int mTextSize;

    private Paint mPaint;

    private Rect mTextBound;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.myView);
        int attrCount = a.getIndexCount();
        for (int i = 0; i < attrCount; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.myView_titleColor:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.myView_titleSize:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.myView_titleText:
                    mText = a.getString(attr);
                    break;
            }
        }

        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(mTextSize);
        mTextBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=0;
        int height = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (widthMode){
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                int txtWidth = mTextBound.width();
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                width = paddingLeft+txtWidth+paddingRight;
                break;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                int txtHeight = mTextBound.height();
                height = paddingTop+txtHeight+paddingBottom;
                break;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        canvas.drawRect(0, 0, measuredWidth, measuredHeight, mPaint);
        mPaint.setColor(mTextColor);
        int txtWidth = mTextBound.width();
        int txtHeight = mTextBound.height();
        int x = (getWidth()>>1)-(txtWidth>>1);
        int y = (getHeight()>>1)+(txtHeight>>1);
        canvas.drawText(mText, x,y,mPaint);
    }
}
