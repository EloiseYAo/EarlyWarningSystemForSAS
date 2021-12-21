package com.example.drawdemo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

public class signin extends Activity implements View.OnClickListener{
	private Button loginBtn;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		loginBtn = (Button)findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if(view.getId() == R.id.loginBtn) {
			Intent i = new Intent(this,MainActivity.class);
			startActivity(i);
		}
	}

}
