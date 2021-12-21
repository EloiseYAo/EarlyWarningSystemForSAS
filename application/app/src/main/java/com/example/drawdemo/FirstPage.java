package com.example.drawdemo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class FirstPage extends Activity implements View.OnClickListener{
	private TextView mBackBtn;
	public TextView ssxinlv,ssjiankang,sstime,maxxinlv,minxinlv;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstpage);
		
		mBackBtn = (TextView)findViewById(R.id.xinlvtu);
		mBackBtn.setOnClickListener(this);
		
		ssxinlv = (TextView)findViewById(R.id.xintiao);
		ssjiankang = (TextView)findViewById(R.id.zhuangtai);
		sstime = (TextView)findViewById(R.id.time);
		maxxinlv = (TextView)findViewById(R.id.maxxinlvvalue);
		minxinlv = (TextView)findViewById(R.id.minxinlvvalue);
		setdata();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if(view.getId() == R.id.xinlvtu) {
			Intent i = new Intent(this,MainActivity.class);
			startActivity(i);
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			//postData();
//			demoView.setData(dataList);
			switch (msg.what) {
			case 1:
				ssxinlv.setText(SomeValue.getssxinlv());
				ssjiankang.setText(SomeValue.getjiankang());
				sstime.setText(SomeValue.gettime());
				maxxinlv.setText(SomeValue.getmaxxinlv());
				minxinlv.setText(SomeValue.getminxinlv());
				break;

			default:
				break;
			}
		};
	};
	
	private void setdata() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				for (int index = 0; index < 1000; index++) {
					handler.sendEmptyMessage(1);
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

}
