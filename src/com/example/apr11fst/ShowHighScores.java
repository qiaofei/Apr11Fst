package com.example.apr11fst;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ShowHighScores extends Activity {
	private TextView high_score;
	SharedPreferences sharepreferences;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscroes);
		Intent intent = getIntent();
		int scoreText = intent.getIntExtra("sorce_high",0);
		high_score = (TextView)findViewById(R.id.scoreText);
		high_score.setText(" "+scoreText);
	}
}
