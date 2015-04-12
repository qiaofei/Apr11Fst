package com.example.apr11fst;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.Time;

public class Bullets {
	private float positionX;
	private float positionY;
	private float bulletSpeed;
	private boolean isExist;

	public Bullets(float x, float y) {
		// TODO Auto-generated constructor stub
		this.positionX = x;
		this.positionY = y;
		this.isExist = false;
		bulletSpeed = 40;
		// bulletBitmap = BitmapFactory.decodeResource(context.getResources(),
		// R.drawable.bullet);
	}

	public void shootAndMove() {
		positionY -= bulletSpeed;
	}

	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
}
