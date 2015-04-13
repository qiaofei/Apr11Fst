package com.example.apr11fst;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {
	private backgroundView backgroundview;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		backgroundview = new backgroundView(this);
		setContentView(backgroundview);
		//setContentView(R.layout.activity_main);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//backgroundview.stop
		super.onDestroy();
	}
}
