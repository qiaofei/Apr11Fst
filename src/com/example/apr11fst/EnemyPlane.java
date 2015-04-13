package com.example.apr11fst;

import java.util.Random;

public class EnemyPlane {
	private float positionX;
	private float positionY;
	private boolean isAlive;
	private float enemySpeed = 20;
	Random rand = new Random();
	public EnemyPlane() {
		// TODO Auto-generated constructor stub		
		this.positionX = rand.nextFloat()*650;
		this.positionY = 0;
		isAlive = false;
	}
	public void enemyDead(){
		this.isAlive = false;
		this.positionX = rand.nextFloat()*650;
		this.positionY = 0;
	}
	public void strikeToPlane() {
		positionY += enemySpeed;
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
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
}
