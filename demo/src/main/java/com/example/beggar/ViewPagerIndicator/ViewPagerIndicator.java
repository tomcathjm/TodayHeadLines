package com.example.beggar.ViewPagerIndicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.beggar.myapplication.R;
import java.util.List;

/**
 * Created by Beggar on 2016/7/25.
 */
public class ViewPagerIndicator extends LinearLayout {

    //画笔
    private Paint mPaint;

    //构建三角形
    private Path mPath;

    //三角形宽度
    private int mTriangleWidth;

    //三角形高度
    private int mTriangleHeight;

    // 三角形宽度 和 Tab 长度的比例（可以对外暴露，让用户自定义）
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;

    //三角形初始化时在X轴上的平移距离
    private int mInitTranslationX;

    //三角形平移的距离
    private int mTranlationX;

    //可见tab 的长度
    private int mTabVisibleCount;

    // tab 长度
    private  int COUNT_DEFAULT_TAB;
    //三角形的最大宽度
    private final int DEMENSION_TRIANGLE_WIDTH = (int) (getSceenWidth() / 3 * RADIO_TRIANGLE_WIDTH);

    //tab 文本的颜色
    private static final String COLOR_TEXT = "#38D3A9";
    private static final String COLOR_TEXT_TRUE = "#FF0000";


    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取可见tab的数量
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

        mTabVisibleCount = typedArray.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, COUNT_DEFAULT_TAB);
        if (mTabVisibleCount < 0) {
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        }

        typedArray.recycle();

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#38d3a9"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        mPaint.setPathEffect(new CornerPathEffect(3));

    }

    //是ViewGroup里子空间的宽高变化时都会回调这个方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //初始化三角形的宽度
//        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);

        //Math.min(int,int); 两个数值中取最小的
//        mTriangleWidth = Math.min(mTriangleWidth,DEMENSION_TRIANGLE_WIDTH);

        //初始化三角形在X轴的平移量
//        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;

        //初始化平移量
        mInitTranslationX = w / mTabVisibleCount / 4;

        //初始化三角形
//        initTriangle();

    }

    /**
     * 初始化三角形的 Path
     *      如果不需要三角形指示器。可以在这里重新构造需要绘制的图形指示器
     */
    private void initTriangle() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);

        mTriangleHeight = mTriangleWidth / 2;
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();

    }

    /**
     * 绘制指示器
     *      可以在这个方法里面绘制任何指示器的样式
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {

        canvas.save();
        // 绘制三角形
//        canvas.translate(mInitTranslationX + mTranlationX, getHeight() + 2);
//        canvas.drawPath(mPath, mPaint);

        //绘制直线
        canvas.drawLine(mInitTranslationX+mTranlationX,getHeight() - 8,getSceenWidth() / mTabVisibleCount - mInitTranslationX+mTranlationX,getHeight() - 8,mPaint);

        canvas.restore();

        super.dispatchDraw(canvas);
    }

    //指示器跟随手指滚动
    public void scroll(int positon, float offset) {
        int tabWidth = getWidth() / mTabVisibleCount;
        //切换tab的平移量
        mTranlationX = (int) (tabWidth * (positon + offset));

        if (positon >= (mTabVisibleCount - 2) && positon < getChildCount() - 2 && offset > 0 && getChildCount() > mTabVisibleCount) {

            if (mTabVisibleCount != 1) {
                this.scrollTo((positon - (mTabVisibleCount - 2)) * tabWidth + (int) (tabWidth * offset), 0);
            } else {
                this.scrollTo(positon * tabWidth + (int) (tabWidth * offset), 0);
            }
        }
        invalidate();

    }


    //在View中，当xml加载完成之后回调的方法
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int cCount = getChildCount();
        if (cCount == 0) return;

        for (int i = 0; i < cCount; i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getSceenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }
        setTabClickEvent();
    }

    //动态的添加 Tab
    public void setTabTitle(List<String> titles) {
        if (titles != null && titles.size() > 0)
            this.removeAllViews();
        for (String title : titles) {
            this.addView(getTab(title));
        }
        setTabClickEvent();
    }
    //动态创建 TabT


    private View getTab(String title) {

        TextView mTextView = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = getSceenWidth() / mTabVisibleCount;
        mTextView.setText(title);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        mTextView.setTextColor(Color.parseColor(COLOR_TEXT));
        mTextView.setLayoutParams(lp);

        return mTextView;
    }

    /**
     * tab设置点击事件
     */
    private void setTabClickEvent(){
        int mCount = getChildCount();
        for (int i = 0 ; i < mCount ; i ++){
            final int j = i;
            View view = getChildAt(i);
            if (view instanceof TextView){
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(j);
                    }
                });
            }
        }
    }

    /**
     * 选中tab高亮文本
     * @return
     */
    private void highLightText(int position){
        resetTextColor();
        View view = getChildAt(position);
        if (view instanceof TextView){
            ((TextView) view).setTextColor(Color.parseColor(COLOR_TEXT_TRUE));
        }
    }

    /**
     * 重置文本颜色
     * @return
     */
    private void resetTextColor(){
        for (int i = 0 ;i < getChildCount() ; i++){
            View view = getChildAt(i);
            if (view instanceof TextView){
                ((TextView) view).setTextColor(Color.parseColor(COLOR_TEXT));
            }
        }
    }
    //获得屏幕的宽度
    private int getSceenWidth() {

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }


    private ViewPager mViewPager;

    /**
     * 占用了用户对ViewPager的滑动接口，为了方便用户自定义这个接口事件，所以对外暴露出去一个接口
     *
     * @param viewPager 绑定ViewPager
     * @param pos 默认选中的 tab 下标
     * @param size tab的个数 （对外暴露，直接在这个方法传进来，不需要在布局中设置）
     */
    public void setViewPager(ViewPager viewPager,int pos,int size){

        COUNT_DEFAULT_TAB = size;

        this.mViewPager = viewPager;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 三角形的偏移量
                // tabWidth * positionOffset + position * tabWidth
               scroll(position,positionOffset);
                if (listener != null){
                    listener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
                highLightText(position);

            }

            @Override
            public void onPageSelected(int position) {
                if (listener != null){
                    listener.onPageSelected(position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (listener != null){
                    listener.onPageScrollStateChanged(state);
                }

            }
        });
        mViewPager.setCurrentItem(pos);
        highLightText(pos);
    }

    interface OnPagerChangeListener{
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

    private OnPagerChangeListener listener;

    public void setOnPagerChangeListener(OnPagerChangeListener listener){
        this.listener = listener;
    }

}
