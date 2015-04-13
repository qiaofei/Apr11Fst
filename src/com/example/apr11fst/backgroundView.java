package com.example.apr11fst;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

public class backgroundView extends View {
	// bitmaps plane and background
	private Bitmap planeBitmap;
	private Bitmap backGroundBitmap;
	// background length
	final int backLength = 3600;
	// size of canvas
	final int WIDTH = 800;
	final int HEIGHT = 1200;
	// control movement
	// plane`s initial position
	float prefx = 330;
	float prefy = 800;
	float bulletX = prefx + 30;
	float bulletY = prefy - 10;
	final float moveSpeed = 40;
	final float planeSpeed = 3;
	final float bulletSpeed = 20;
	// draw bullets
	private int bulletCount = 40;
	private int enemyCount = 10;
	private int bulletOrder = 0;
	private int enemiesOrder = 0;
	private int bulletCounter = 0;
	private int oneBulletTime = 5;
	private int enemyCounter = 0;
	private int oneEnemyTime = 10;
	private int planeScores = 0;
	private Bullets bullet[] = new Bullets[bulletCount];
	private Bitmap bulletBitmap[] = new Bitmap[bulletCount];
	// draw enemies
	private EnemyPlane enemyPlanes[] = new EnemyPlane[enemyCount];
	private Bitmap enemyBitmap[] = new Bitmap[enemyCount];
	// the beginning place of background
	int startY = backLength - HEIGHT;
	// if Game Over?
	private boolean isGameOver = false;
	private int passedEnemy = 0;

	public backgroundView(Context context) {
		super(context);
		// plane bitmap
		planeBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.plane);
		// background bitmaps
		backGroundBitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.background);
		// bullets bitmap
		for (int i = 0; i < bulletCount; i++) {
			bulletBitmap[i] = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.bullet);
			bullet[i] = new Bullets(bulletX, bulletY);
		}
		// enemies bitmap
		for (int i = 0; i < enemyCount; i++) {
			enemyBitmap[i] = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.enemy);
			enemyPlanes[i] = new EnemyPlane();
		}
		// handler
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					if (startY <= 0) {
						startY = backLength - HEIGHT;
					} else {
						startY -= planeSpeed;
					}
					// if bullets is out of screen
					for (int i = 0; i < bulletCount; i++) {
						if (bullet[i].isExist()
								&& bullet[i].getPositionY() <= 0) {
							bullet[i].setExist(false);
						}
					}
					// is enemy is dead
					for (int i = 0; i < enemyCount; i++) {
						if (enemyPlanes[i].isAlive()
								&& enemyPlanes[i].getPositionY() >= 1200) {
							enemyPlanes[i].enemyDead();
							passedEnemy++;
							if (passedEnemy >= 10) {
								isGameOver = true;
							}
						}
					}
					if (!isGameOver) {
						for (int i = 0; i < bulletCount; i++) {
							for (int j = 0; j < enemyCount; j++) {
								if (bullet[i].getPositionX()
										- enemyPlanes[j].getPositionX() <= 85
										&& bullet[i].getPositionX()
												- enemyPlanes[j].getPositionX() >= -5
										&& bullet[i].getPositionY()
												- enemyPlanes[j].getPositionY() <= 85
										&& bullet[i].getPositionY()
												- enemyPlanes[j].getPositionY() >= -5) {
									enemyPlanes[j].enemyDead();
									planeScores++;
									if (planeScores % 50 == 0
											&& planeScores > 0
											&& oneEnemyTime > 1) {
										oneEnemyTime--;
										for (int k = 0; k < enemyCount; k++)

										{
											enemyPlanes[k]
													.setEnemySpeed(enemyPlanes[k]
															.getEnemySpeed() + 3);
										}
									}
								}
							}
						}
					}
					// check game over
					for (int i = 0; i < enemyCount; i++) {
						if (enemyPlanes[i].isAlive()
								&& enemyPlanes[i].getPositionX() - prefx >= -80
								&& enemyPlanes[i].getPositionX() - prefx <= 80
								&& enemyPlanes[i].getPositionY() - prefy >= -80
								&& enemyPlanes[i].getPositionY() - prefy <= 80) {
							isGameOver = true;
						}
					}
				}
				// if(!isGameOver){
				invalidate();
				// }
			}
		};
		// timer
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(0x123);
			}
		}, 0, 50);
	}

	// touch to control plane
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			/*
			 * if(x <= prefx){ prefx -= 10; } else { prefx += 10; } if(y <=
			 * prefy){ prefy -= 10; } else { prefy += 10; } invalidate();
			 */
			break;
		case MotionEvent.ACTION_MOVE:
			// moveSlope = (y-prefy)/(x-prefx);
			if (y <= prefy) {
				// prefx -= speedY*moveSlope;
				prefy -= moveSpeed;
			} else {
				// prefx += speedY*moveSlope;
				prefy += moveSpeed;
			}
			if (x <= prefx) {
				// prefx -= speedY*moveSlope;
				prefx -= moveSpeed;
			} else {
				// prefx += speedY*moveSlope;
				prefx += moveSpeed;
			}
			// if(!isGameOver){
			invalidate();
			// }
			break;
		}
		return true;
	}

	// ondraw
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(40);
		bulletX = prefx + 30;
		bulletY = prefy - 10;
		// string scores = (string)
		super.onDraw(canvas);
		Bitmap bitmapCurrent = Bitmap.createBitmap(backGroundBitmap, 0, startY,
				WIDTH, HEIGHT);
		// draw background & plane
		canvas.drawBitmap(bitmapCurrent, 0, 0, null);
		if (!isGameOver) {
			canvas.drawBitmap(planeBitmap, prefx, prefy, null);
		} else {
			canvas.drawText("GAME OVER", 230, 550, paint);
		}
		canvas.drawText("scores:" + planeScores, 500, 80, paint);
		canvas.drawText("passed enemies:" + passedEnemy, 10, 80, paint);
		// draw bullets
		if (bulletOrder >= bulletCount - 1) {
			bulletOrder = 0;
		}
		bulletOrder++;
		if (bulletCounter == oneBulletTime) {
			bulletCounter = 0;
		}
		bulletCounter++;
		if (bulletCounter == oneBulletTime) {
			bullet[bulletOrder].setExist(true);
		}
		// bullet[bulletOrder].setExist(true);
		bullet[bulletOrder].setPositionX(bulletX);
		bullet[bulletOrder].setPositionY(bulletY);
		if (!isGameOver) {
			for (int i = 0; i < bulletCount; i++) {
				if (bullet[i].isExist()) {
					bullet[i].shootAndMove();
					canvas.drawBitmap(bulletBitmap[i],
							bullet[i].getPositionX(), bullet[i].getPositionY(),
							null);
				}
			}
		}
		// draw enemies
		// if it`s time to release an enemy
		if (enemyCounter == oneEnemyTime) {
			enemyCounter = 0;
			if (enemiesOrder >= enemyCount - 1) {
				enemiesOrder = 0;
			}
			enemiesOrder++;
			enemyPlanes[enemiesOrder].setAlive(true);
		}
		enemyCounter++;
		/*
		 * if (enemyCounter == 10) { enemyPlanes[enemiesOrder].setAlive(true); }
		 */
		for (int i = 0; i < enemyCount; i++) {
			if (enemyPlanes[i].isAlive()) {
				enemyPlanes[i].strikeToPlane();
				canvas.drawBitmap(enemyBitmap[i],
						enemyPlanes[i].getPositionX(),
						enemyPlanes[i].getPositionY(), null);
			}
		}
	}
}
