package com.example.apr11fst;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class backgroundView extends View {
	// bitmaps plane and background
	private Bitmap planeBitmap;
	private Bitmap backGroundBitmap;
	// background length
	final int backLength = 3600;
	// size of canvas
	private static int WIDTH = 0;
	private static int HEIGHT = 0;
	// control movement
	// plane`s initial position
	private static float prefx = 0;
	private static float prefy = 0;
	float bulletX = prefx + 30;
	float bulletY = prefy - 10;
	static float moveSpeed = 0;
	static float planeSpeed = 0;
	static float bulletSpeed = 0;
	// draw bullets
	private int bulletCount = 40;
	private int enemyCount = 10;
	private int bulletOrder = 0;
	private int enemiesOrder = 0;
	private int bulletCounter = 0;
	private int oneBulletTime = 5;
	private int enemyCounter = 0;
	private int oneEnemyTime = 10;
	public int planeScores = 0;
	public boolean isPause = false;
	private Bullets bullet[] = new Bullets[bulletCount];
	private Bitmap bulletBitmap[] = new Bitmap[bulletCount];
	// draw enemies
	private EnemyPlane enemyPlanes[] = new EnemyPlane[enemyCount];
	private Bitmap enemyBitmap[] = new Bitmap[enemyCount];
	// the beginning place of background
	static int startY =0;
	// if Game Over?
	private boolean isGameOver = false;
	private int passedEnemy = 0;
	// music
	public boolean isSoundon = false;
	public MediaPlayer bgmPlayer;
	public MediaPlayer gameoverPlayer;
	public MediaPlayer shootingPlayer;
	public MediaPlayer enemyDeadPlayer;
	private Random rand = new Random();

	public backgroundView(Context context) {
		super(context);

		// init sound
		initScreenSize();
		bgmPlayer = MediaPlayer.create(context, R.raw.backgroundmusic);
		if (isSoundon) {
			bgmPlayer.start();
		}
		gameoverPlayer = MediaPlayer.create(context, R.raw.lost_life);
		shootingPlayer = MediaPlayer.create(context, R.raw.shootting_sound);
		enemyDeadPlayer = MediaPlayer.create(context, R.raw.enemydead);
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
								&& enemyPlanes[i].getPositionY() >= HEIGHT) {
							enemyPlanes[i].enemyDead();
							passedEnemy++;
							if (passedEnemy >= 10) {
								isGameOver = true;
								bgmPlayer.stop();
								if (isSoundon) {
									gameoverPlayer.start();
								}
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
									if (isSoundon) {
										shootingPlayer.start();
									}
									planeScores++;
									if (planeScores % 50 == 0
											&& planeScores > 0
											&& oneEnemyTime > 3) {
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
							bgmPlayer.stop();
							if (isSoundon) {
								gameoverPlayer.start();
							}
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
				if (!isPause)
					handler.sendEmptyMessage(0x123);
			}
		}, 0, 50);
	}
	//init screen size
	private void initScreenSize(){
		DisplayMetrics metric = new DisplayMetrics();
		metric = getResources().getDisplayMetrics();
		//screen width
		WIDTH = metric.widthPixels;
		HEIGHT = metric.heightPixels;
		//the initial position of our plane
		prefx = WIDTH/2 -40 ;
		prefy = HEIGHT - 300 ;
		moveSpeed = HEIGHT / 50;
		planeSpeed = WIDTH / 100;
		bulletSpeed = HEIGHT / 100;
		 startY = backLength - HEIGHT ; 
		Log.d("Screen", "Width:" + WIDTH);
		Log.d("Screen" , "Height" + HEIGHT);
	}
	//restart game
	public void restart_game(){
		//speed init
		oneEnemyTime = 10;
		oneBulletTime = 5;
		//plane`s position reset		
		prefx =  WIDTH/2 -40 ;
		prefy = HEIGHT - 300 ;
		//scores reset
		planeScores = 0;
		//bullet reset
		for (int i = 0 ; i < bulletCount ; i ++){
			bullet[i].setExist(false);
		}
		//enemy reset
		for(int i = 0 ; i < enemyCount ; i++){
			enemyPlanes[i].setEnemySpeed(20);
			enemyPlanes[i].setAlive(false);
			enemyPlanes[i].setPositionY(0);
			enemyPlanes[i].setPositionX(rand.nextFloat()*650);
		}
		//passed enemies reset
		passedEnemy = 0;
		//reset ispause
		isPause = false;
		isGameOver = false ;
	}

	// touch to control plane
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (!isPause) {
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
		}
		return true;
	}

	// ondraw
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(WIDTH/25);
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
			canvas.drawText("GAME OVER", WIDTH/2 - WIDTH/80, HEIGHT/2 - HEIGHT/150, paint);
		}
		canvas.drawText("scores:" + planeScores, WIDTH - WIDTH/5, HEIGHT / 40, paint);
		canvas.drawText("passed enemies:" + passedEnemy, WIDTH/500, HEIGHT / 40, paint);
		if(isPause&&!isGameOver){
			canvas.drawText("Pause", WIDTH/2 - WIDTH/150, HEIGHT/2 - HEIGHT/50, paint);
		}
		if (isSoundon) {
			canvas.drawText("sound on", WIDTH/2 - WIDTH/150, HEIGHT/40, paint);
		} else {
			canvas.drawText("sound off", WIDTH/2 - WIDTH/150, HEIGHT/40, paint);
		}
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
			if (!isGameOver) {
				bullet[bulletOrder].setExist(true);
				// shootingPlayer.start();
			}
		}
		// bullet[bulletOrder].setExist(true);
		bullet[bulletOrder].setPositionX(bulletX);
		bullet[bulletOrder].setPositionY(bulletY);
		if (!isGameOver) {
			for (int i = 0; i < bulletCount; i++) {
				if (bullet[i].isExist()) {
					if (!isPause){
					bullet[i].shootAndMove();
					}
					canvas.drawBitmap(bulletBitmap[i],
							bullet[i].getPositionX(), bullet[i].getPositionY(),
							null);
				}
			}
		}
		// draw enemies
		// if it`s time to release an enemy
		if (!isGameOver) {
			if (enemyCounter == oneEnemyTime) {
				enemyCounter = 0;
				if (enemiesOrder >= enemyCount - 1) {
					enemiesOrder = 0;
				}
				enemiesOrder++;
				enemyPlanes[enemiesOrder].setAlive(true);
				enemyPlanes[enemiesOrder].setPositionX(rand.nextFloat()*WIDTH - WIDTH/10);
				enemyPlanes[enemiesOrder].setEnemySpeed(HEIGHT/50);
			}
			enemyCounter++;
			/*
			 * if (enemyCounter == 10) {
			 * enemyPlanes[enemiesOrder].setAlive(true); }
			 */
			for (int i = 0; i < enemyCount; i++) {
				if (enemyPlanes[i].isAlive()) {
					if (!isPause){
					enemyPlanes[i].strikeToPlane();
					}
					canvas.drawBitmap(enemyBitmap[i],
							enemyPlanes[i].getPositionX(),
							enemyPlanes[i].getPositionY(), null);
				}
			}
		}
	}
}
