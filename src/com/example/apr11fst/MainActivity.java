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
import android.view.MenuItem;
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
		menu.addSubMenu(Menu.NONE, Menu.FIRST+1, 2, "sound off/on");		
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case Menu.FIRST+1 :
			//backgroundview.isSoundon = !backgroundview.isSoundon;
		if(!backgroundview.isSoundon){
			backgroundview.bgmPlayer.start();
			backgroundview.isSoundon = true;
		}
		else{
			backgroundview.bgmPlayer.stop();
			backgroundview.isSoundon = false;
		}
			break;
		}
		return false;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//backgroundview.stop
		super.onDestroy();
	}
}
