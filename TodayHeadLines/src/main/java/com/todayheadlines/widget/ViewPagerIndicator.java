package com.todayheadlines.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
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

import com.todayheadlines.R;

import java.util.List;

/**
 * Created by Beggar on splash/7/25.
 */
public class ViewPagerIndicator extends LinearLayout {

    //画笔  tab 底下的滑动横条
    private Paint mPaint;

    //画笔 tab 底下的固定的灰色固定条
    private Paint linePaint;


    //初始化时在X轴上的平移距离
    private int mInitTranslationX;

    //平移的距离
    private int mTranlationX;

    //可见tab 的长度
    private int mTabVisibleCount;

    //默认可见 Tab 个数
    private final int DEFAULT_TAB_COUNT = 4;


    // tab 初始化文本的颜色
    private static final String COLOR_TEXT_INIT = "#F7F7F7";
    private static final int TEXT_SIZE_INIT = 16;
    // tab 文本选中颜色
    private static final String COLOR_TEXT_TRUE = "#FFFFFF";
    private static final int TEXT_SIZE_TRUE = 18;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取可见tab的数量
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

        mTabVisibleCount = typedArray.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, DEFAULT_TAB_COUNT);
        if (mTabVisibleCount <= 0) {
            mTabVisibleCount = DEFAULT_TAB_COUNT;
        }

        typedArray.recycle();
    }

    // 动态的添加 Tab
    public void setTabTitle(List<String> titles) {
        if (titles != null && titles.size() > 0)
            this.removeAllViews();
        for (String title : titles) {
            this.addView(getTab(title));
        }
        setTabClickEvent();
    }

    // 动态创建 Tab
    private View getTab(String title) {

        TextView mTextView = new TextView(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = getSceenWidth() / mTabVisibleCount;
        mTextView.setText(title);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        mTextView.setTextColor(Color.parseColor(COLOR_TEXT_INIT));
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
        }else{
            if (positon == 1 || positon == 2){
                this.scrollTo( 0 , 0);
            }
        }
        invalidate();
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
            ((TextView) view).setTextSize(TEXT_SIZE_TRUE);
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
            ((TextView) view).setTextColor(Color.parseColor(COLOR_TEXT_INIT));
            ((TextView) view).setTextSize(TEXT_SIZE_INIT);
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
     * @param pos       默认选中的 tab 下标
     */
    public void setViewPager(ViewPager viewPager, int pos) {

        this.mViewPager = viewPager;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 偏移量
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
