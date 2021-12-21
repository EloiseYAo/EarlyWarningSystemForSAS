package com.example.drawdemo;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DemoView extends View {
	private Paint paint;
	private Paint mpaint;
	private Paint textpaint;
	private int mBorderColor;
	private float mBorderWidth;
	private ArrayList<Float> dataList;
	
	

	public DemoView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		dataList=new ArrayList<Float>();
	}

	public DemoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		final TypedArray attributes = context.getTheme()
				.obtainStyledAttributes(attrs, R.styleable.DemoView,
						0, 0);
		try {
			mBorderColor = attributes.getColor(
					R.styleable.DemoView_border_color, 0);
			mBorderWidth = attributes.getDimension(
					R.styleable.DemoView_border_width, 0);
		} finally {
			attributes.recycle();
		}
		dataList=new ArrayList<Float>();
	}

	public DemoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		dataList=new ArrayList<Float>();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		float heiht = getHeight()/(float)9;
/*        Log.e("height", dataList.size()+"");*/
        float width=getWidth()/(float)10;
        float data1=0,data2=0;
		paint = new Paint(); // ����һ����ˢ��С��3�Ļ�ɫ�Ļ���
		paint.setColor(mBorderColor);
		paint.setStrokeWidth(mBorderWidth);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		
		mpaint = new Paint(); // ����һ����ˢ��С��3�Ļ�ɫ�Ļ���
		mpaint.setColor(Color.LTGRAY);
		mpaint.setStrokeWidth(mBorderWidth);
		mpaint.setAntiAlias(true);
		mpaint.setStyle(Style.STROKE);
		mpaint.setAntiAlias(true);
		mpaint.setAlpha(100);
		
		textpaint = new Paint(); // ����һ����ˢ��С��3�Ļ�ɫ�Ļ���
		textpaint.setColor(Color.BLACK);
		textpaint.setTextSize(25);
		
		int length = getWidth() / 5 + 1;// 横长
		int bound = getHeight() / 9;// 竖长
		int px = 50;
		for (int i = 1; i < bound; i++) {
		//画横线 
		canvas.drawLine(px, i * bound+px, getWidth(), i * bound+px, mpaint);
		canvas.drawText(""+(120-i*10),0, i * bound+px, textpaint);
		}
		for (int j = 0; j < length; j++){
		//画竖线		 
		canvas.drawLine(j * length+px, px, j * length+px, getHeight(), mpaint);	
		canvas.drawText(""+j*2,j * length+px, px, textpaint);
		} 		
		
/*		canvas.drawLine(0,0, 0, 2000*heiht, paint);
		canvas.drawLine(0,0, 10*width, 0, paint);
		canvas.drawLine(10*width, 2000*heiht, 0, 2000*heiht, paint);
		canvas.drawLine(10*width, 2000*heiht, 10*width, 0, paint);*/
		canvas.drawText(""+dataList.get(0),px, getHeight()-(dataList.get(0)-30)/(float)10*heiht, textpaint);
		if (dataList.size()>1) {
			for (int i = 1; i < dataList.size(); i++) {
/*				if (dataList.get(i-1)>2900||dataList.get(i-1)==2900) {
					data1=2900;
				}else if(dataList.get(i-1)<1100||dataList.get(i-1)==1100){
					data1=1100;
				}else if(dataList.get(i-1)>1100&&dataList.get(i-1)<2900){
					data1=dataList.get(i-1);
				}
				
				if (dataList.get(i)>2900||dataList.get(i)==2900) {
					data2=2900;
				}else if(dataList.get(i)<1100||dataList.get(i)==1100){
					data2=1100;
				}else if(dataList.get(i)>1100&&dataList.get(i)<2900){
					data2=dataList.get(i);
				}*/
				canvas.drawLine(i*width+px,getHeight()-(dataList.get(i-1)-30)/(float)10*heiht+px, 
						       (i+1)*width+px, getHeight()-(dataList.get(i)-30)/(float)10*heiht+px, paint);
				canvas.drawText(""+dataList.get(i),(i+1)*width+px, getHeight()-(dataList.get(i)-30)/(float)10*heiht+px, textpaint);
			}
		}


	}

	
	public void setData(ArrayList<Float> data) {
		this.dataList = data;
		invalidate();
	}

}
