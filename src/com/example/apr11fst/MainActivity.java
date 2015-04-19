package com.example.apr11fst;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	private Intent intent;
	SharedPreferences sharepreferences;
	SharedPreferences.Editor editor;
	private int initscore = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		backgroundview = new backgroundView(this);
		setContentView(backgroundview);
		intent = new Intent();
		intent.setClass(MainActivity.this, ShowHighScores.class);
		// presference
		sharepreferences = getSharedPreferences("HighestScore", MODE_PRIVATE);
		editor = sharepreferences.edit();
		// editor.putInt("reserved_score", initscore);
		// editor.commit();
	}

	private void refresh_highscore() {
		int reserved_score = sharepreferences.getInt("reserved_score", 0);
		if (backgroundview.planeScores > reserved_score) {
			editor.putInt("reserved_score", backgroundview.planeScores);
			editor.commit();
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.addSubMenu(Menu.NONE, Menu.FIRST, 1, "Restart");
		menu.addSubMenu(Menu.NONE, Menu.FIRST + 1, 2, "sound off/on");
		menu.addSubMenu(Menu.NONE, Menu.FIRST + 2, 3, "Pause/Play");
		menu.addSubMenu(Menu.NONE, Menu.FIRST + 3, 4, "High Scores");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case Menu.FIRST:
			refresh_highscore();
			backgroundview.restart_game();
			break;
		case Menu.FIRST + 1:
			if (!backgroundview.isSoundon) {
				backgroundview.bgmPlayer.start();
				backgroundview.isSoundon = true;
			} else {
				backgroundview.bgmPlayer.stop();
				backgroundview.isSoundon = false;
			}
			break;
		case Menu.FIRST + 2:
			if (backgroundview.isPause) {
				backgroundview.isPause = false;
				backgroundview.bgmPlayer.start();
			} else {
				backgroundview.isPause = true;
				backgroundview.bgmPlayer.stop();
			}
			break;
		case Menu.FIRST + 3:
			refresh_highscore();
			intent.putExtra("sorce_high",
			sharepreferences.getInt("reserved_score", 0));
			startActivity(intent);
			break;
		}
		return false;
	}
	protected void onStop() {
		// TODO Auto-generated method stub
		refresh_highscore();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// backgroundview.stop
		refresh_highscore();
		super.onDestroy();

	}
}
