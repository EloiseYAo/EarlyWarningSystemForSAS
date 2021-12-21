package com.example.drawdemo;

import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

//ssss

import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.drawdemo.R.id;
import com.example.drawdemo.FirstPage;
import com.example.drawdemo.R;

import android.app.Activity;
import android.app.DownloadManager.Query;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{
	DemoView demoView;
	public TextView xinlvtu,shouye,gwval;
	ArrayList<Float> dataList,tempList,resultList;
	int intt = 0;
	String command = "normal";


	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				postData();
				demoView.setData(dataList);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dataList = new ArrayList<Float>();
		dataList.add((float)50.0);
		dataList.add((float) 60.0);
		dataList.add((float) 60.0);
		dataList.add((float) 60.0);
		dataList.add((float) 70.0);
		tempList=new ArrayList<Float>();
		resultList=new ArrayList<Float>();
		demoView = (DemoView) findViewById(R.id.demoview);
		gwval = (TextView)findViewById(R.id.gwval);
		shouye = (TextView)findViewById(R.id.shouye);
		shouye.setOnClickListener(this);
		//demoView.setData(dataList);
		//getAuth();
/*		postData();*/
		getdata();
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view.getId() == R.id.shouye) {
			Intent i = new Intent(this,FirstPage.class);
			startActivity(i);
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	private void postData() {
		// TODO Auto-generated method stub

		// final String address = "https://portal-datahub-def1589187330992-ews001.hz.wise-paas.com.cn/api/v1/RealData/raw";

		final String address = "http://3.145.205.174:5000/android";
		final String DataJsonString = "[\n" +
				"  {\n" + 
				"    \"nodeId\": \"b92bb287-0447-440e-a4e8-80c9e0f0bc6f\",\n" + 
				"    \"deviceId\": \"Device1\",\n" + 
				"    \"tagName\": \"eee\"\n" + 
				"  }\n" + 
				"]";
		final String DataJsonString1 = "[\n" + 
				"  {\n" + 
				"    \"nodeId\": \"b92bb287-0447-440e-a4e8-80c9e0f0bc6f\",\n" + 
				"    \"deviceId\": \"Device1\",\n" + 
				"    \"tagName\": \"ttt\"\n" + 
				"  }\n" + 
				"]";
		Log.v("address",address);
		Log.v("jsonString",DataJsonString);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
				HttpClient httpclient = new DefaultHttpClient();
			    HttpPost post = new HttpPost(address);
                post.setHeader("Content-Type", "application/json");
				post.setHeader("Accept", "*/*");
				post.setHeader("Accept-Encoding", "gzip, deflate, br");
				post.setHeader("Connection", "keep-alive");
				post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				//post.setHeader("Cookie", "EIToken=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJjb3VudHJ5IjoiQ04iLCJjcmVhdGlvblRpbWUiOjE1ODczNTQzMjYsImV4cCI6MTU5MTAzMzA2MywiZmlyc3ROYW1lIjoiV2VuIExpYW4gWWFvIiwiaWF0IjoxNTkxMDI5NDYzLCJpZCI6IjVmMDQyODgwLTgyYjktMTFlYS1hOGNkLWQ2OTQwNTM5ZGRlMiIsImlzcyI6Indpc2UtcGFhcyIsImxhc3RNb2RpZmllZFRpbWUiOjE1OTEwMjk0MTksImxhc3ROYW1lIjoiV2VuIExpYW4gWWFvIiwicmVmcmVzaFRva2VuIjoiMzc1YmNjYTItYTQyNi0xMWVhLTgzOGMtMjZlYjQ0ZGM2ZWU2Iiwic3RhdHVzIjoiQWN0aXZlIiwidXNlcm5hbWUiOiIyMzg5MjkyNzkxQHFxLmNvbSJ9.aBSqomfKOuWJ7msEunhrn9xZy5FJUR0whtaKx4jLflaRw22AlBKugcQ-hMZCQ_1JTFELc_hphrOJYWKhvnpqSA; EIName=Wen%20Lian%20Yao");
	            post.setEntity(new ByteArrayEntity(DataJsonString.getBytes("UTF-8")));

	    		HttpResponse postresponse = null;
	            postresponse = httpclient.execute(post);
				
	            if(postresponse != null)
	            {
	            	String result=EntityUtils.toString(postresponse.getEntity());
					Log.v("result",result);
					System.out.println("aaa"+result);

	    	        JSONArray jsonArray = new JSONArray(result);
	    	        for (int i=0; i < jsonArray.length(); i++)    {
	    	            JSONObject jsonObject = jsonArray.getJSONObject(i);
	    	            String value = jsonObject.getString("value");
	    	            Float fvalue = Float.parseFloat(value);
/*	    	            ssxinlv.setText(value);
	    	            ssjiankang.setText("危险");*/
	    				if (dataList.size()==0) {
	    					dataList.add(fvalue);
	    				}else {
	    					dataList.add(0, fvalue);
	    				}
	    	            //dataList.add(fvalue);
	    	            SomeValue.setssxinlv(value);
	            }
	            post.setEntity(new ByteArrayEntity(DataJsonString1.getBytes("UTF-8")));
	            postresponse = null;
	            postresponse = httpclient.execute(post);
	            if(postresponse != null)
	            {
	            	String result1=EntityUtils.toString(postresponse.getEntity());
					Log.v("result",result1);
	    	        JSONArray jsonArray1 = new JSONArray(result1);
	    	        for (int i=0; i < jsonArray1.length(); i++)    {
	    	            JSONObject jsonObject = jsonArray1.getJSONObject(i);
	    	            String value = jsonObject.getString("value");
	    	            Float fvalue = Float.parseFloat(value);
	    	            String ts = jsonObject.getString("ts");
	    	            String com = jsonObject.getString("com");
	    	            if (com == "0"){
	    	            	command ="normal";
						}else{
	    	            	command="dangerous";
						}
	    	            
                        //ts = ts.replace("Z", " UTC");
	            		//SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSS Z" );
	            		//SimpleDateFormat ff = new SimpleDateFormat( "HH:mm:ss");
	            		//ff.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	            		String prizeDate = null;
	            		//java.util.Date d = sdf.parse(ts);  //这里用sdf的解析标准先把原始的字串还原. 这里的d就是标准时间了.
	            		//prizeDate = ff.format(d); //这里再用ff输出你想要的形式就可以了.
						prizeDate = ts;
						Log.v("prizeDate",prizeDate);
	    	            
	    	            SomeValue.settime(prizeDate);
	    	            SomeValue.setjiankang(value);
	    	            /*
                        if(fvalue>4.5) {
                            zwval.setText("0");
							gwval.setText("0");
                        }else if(fvalue<2.6||fvalue==2.6) {
                        	SomeValue.setjiankang("高危");
                            zwval.setText("0");
							gwval.setText("1");
						}else {
							SomeValue.setjiankang("中危");
							zwval.setText("1");
                            gwval.setText("0");
						}*/
	    	            System.out.println("value" + value + ";ts" + ts);
	    	        }
	            }
	            }
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}).start();
		gwval.setText(command);
	}
	
	public void shouyeupdate(String time,String jiankang, String ssxinlvvalue) {
        System.out.println("time" + time + ";jiankang" + jiankang);
        SomeValue.settime(time);
        SomeValue.setjiankang(jiankang);
        SomeValue.setssxinlv(ssxinlvvalue);
/*		sstime.setText(time);*/
	}

	private void getdata() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				for (int index = 0; index < 100; index++) {
					
					intt = index + 1;
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
