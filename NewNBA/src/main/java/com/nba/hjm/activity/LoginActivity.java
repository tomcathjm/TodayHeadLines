package com.nba.hjm.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nba.hjm.myapplication.R;

/**
 * Created by hjm on 2016/1/25.
 */
public class LoginActivity extends Activity {

    private TextView login_text;
    private RelativeLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        findviewIDOnCLick();
    }

    private void findviewIDOnCLick() {
        frame = (RelativeLayout) findViewById(R.id.frame);
        login_text = (TextView) findViewById(R.id.login_text);
        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame.addView(new MyView(LoginActivity.this));
            }
        });
    }
    public class MyView extends View{
        public MyView(Context context){
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint paint = new Paint();//采用默认设置创建一个画笔
            paint.setAntiAlias(true);//使用抗锯齿功能
            paint.setColor(0xFFA4C739);//设置画笔的颜色为绿色
            //绘制机器人的头
            RectF android_head = new RectF(10,10,100,100);
            android_head.offset(100, 20);
            canvas.drawArc(android_head, -10, -160, false, paint);//绘制弧
            //绘制眼睛
            paint.setColor(Color.WHITE);
            canvas.drawCircle(135, 53, 4, paint);
            canvas.drawCircle(175, 53, 4, paint);
            paint.setColor(0xFFA4C739);
            //绘制天线
            paint.setStrokeMiter(6);
            canvas.drawLine(120, 15, 135, 35, paint);//绘制线
            canvas.drawLine(190, 15, 175, 35, paint);
            //绘制身体
            canvas.drawRect(110, 75, 200, 150, paint);//绘制矩形
            RectF android_body = new RectF(110,140,200,160);
            canvas.drawRoundRect(android_body,10,10,paint);//绘制圆角矩形

            //绘制胳膊
            RectF rectf_arm=new RectF(85,75,105,140);
            canvas.drawRoundRect(rectf_arm, 10, 10, paint);	//绘制左侧的胳膊
            rectf_arm.offset(120, 0);	 //设置在X轴上偏移120像素
            canvas.drawRoundRect(rectf_arm, 10, 10, paint);	//绘制右侧的胳膊
            //绘制腿
            RectF rectf_leg=new RectF(125,150,145,200);
            canvas.drawRoundRect(rectf_leg, 10, 10, paint);	//绘制左侧的腿
            rectf_leg.offset(40, 0);	 //设置在X轴上偏移40像素
            canvas.drawRoundRect(rectf_leg, 10, 10, paint);	//绘制右侧的腿



            super.onDraw(canvas);
        }
    }
}
