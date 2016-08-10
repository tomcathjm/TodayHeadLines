package com.nba.hjm.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hjm on 2016/2/15.
 */
public class MoveViewActivity extends Activity{
    private MyMoveView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MyMoveView(this);
        setContentView(view);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        view.setPos_x((int) event.getX()-50);
        view.setPos_y((int)event.getY()-50);
        view.invalidate();
        return true;
    }
    public class MyMoveView extends View {

        private int pos_x = 50;
        private int pos_y = 50;

        public MyMoveView(Context context) {
            super(context);
        }

        public void setPos_x(int x) {
            pos_x = x;
        }

        public void setPos_y(int y) {
            pos_y = y;

        }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(30);
            canvas.drawText("拖动的文字", pos_x, pos_y, paint);
            super.onDraw(canvas);
        }
    }
}
