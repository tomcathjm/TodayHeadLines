package com.example.beggar.ViewPagerIndicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

import java.util.List;

/**
 * Created by Beggar on 2016/7/25.
 */
public class ViewPagerIndicator extends LinearLayout {

    //画笔  tab 底下的滑动横条
    private Paint mPaint;

    //画笔 tab 底下的固定的灰色固定条
    private Paint linePaint;

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

    // tab 默认个数
    private int COUNT_DEFAULT_TAB = 3;
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

//        //获取可见tab的数量
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
//
//        mTabVisibleCount = typedArray.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, COUNT_DEFAULT_TAB);
//        if (mTabVisibleCount <= 0) {
//            mTabVisibleCount = COUNT_DEFAULT_TAB;
//        }
//
//        typedArray.recycle();

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#38D3A9"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(4);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor("#E6E6E6"));
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(4);

    }

    //是ViewGroup里子空间的宽高变化时都会回调这个方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化平移量
        mInitTranslationX = w / mTabVisibleCount / 5;
    }

    /**
     * 绘制指示器
     * 可以在这个方法里面绘制任何指示器的样式
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {

        canvas.save();

        // 绘制 指示器灰色背景线条
        canvas.drawLine(0, getHeight() - 5, getSceenWidth() + mTranlationX, getHeight() - 5, linePaint);

        // 绘制 绿色滑动指示条
        canvas.drawLine(mInitTranslationX + mTranlationX, getHeight() - 5, getSceenWidth() / mTabVisibleCount - mInitTranslationX + mTranlationX, getHeight() - 5, mPaint);

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
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        mTextView.setTextColor(Color.parseColor(COLOR_TEXT));
        mTextView.setLayoutParams(lp);

        return mTextView;
    }

    /**
     * tab设置点击事件
     */
    private void setTabClickEvent() {
        int mCount = getChildCount();
        for (int i = 0; i < mCount; i++) {
            final int j = i;
            View view = getChildAt(i);
            if (view instanceof TextView) {
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
     *
     * @return
     */
    private void highLightText(int position) {
        resetTextColor();
        View view = getChildAt(position);
       this.position = position;
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(Color.parseColor(COLOR_TEXT_TRUE));
        }
    }

    /**
     * 重置文本颜色
     *
     * @return
     */
    private int position;
    private void resetTextColor() {
        View view = getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(Color.parseColor(COLOR_TEXT));
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
     * @param viewPager 绑定ViewPager
     * @param pos       默认选中的 tab 下标
     * @param size      tab的个数 （对外暴露，直接在这个方法传进来，不需要在布局中设置）
     */
    public void setViewPager(ViewPager viewPager, int pos, int size) {

        mTabVisibleCount = size;

        this.mViewPager = viewPager;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 三角形的偏移量
                scroll(position, positionOffset);
                if (listener != null) {
                    listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                resetTextColor();
                highLightText(position);
                if (listener != null) {
                    listener.onPageSelected(position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (listener != null) {
                    listener.onPageScrollStateChanged(state);
                }

            }
        });
        mViewPager.setCurrentItem(pos);
        highLightText(pos);
    }

    interface OnPagerChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    private OnPagerChangeListener listener;

    public void setOnPagerChangeListener(OnPagerChangeListener listener) {
        this.listener = listener;
    }

}
