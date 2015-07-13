package com.example.apr11fst;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomePage extends Activity{
	private Button buttonPlay,buttonHighScore,buttonExit;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_page);
		buttonPlay = (Button)findViewById(R.id.button_play);
		buttonHighScore = (Button)findViewById(R.id.button_highscore);
		buttonExit = (Button)findViewById(R.id.button_exit);
		buttonPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomePage.this,MainActivity.class);
				startActivity(intent);
			}
		});
		buttonHighScore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomePage.this,ShowHighScores.class);
				startActivity(intent);
			}
		});
		buttonExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
/*	private void Dialog(){
		AlertDialog.Builder builder = new Builder(WelcomePage.this);
		builder.setTitle("确定退出吗");
		builder.setMessage("真的确定退出吗");
		builder.setPositiveButton("滚", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();
				System.exit(0);
			}
		});
		builder.setNegativeButton("不滚", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();
			}
		});
		builder.create().show();
	} */
}
