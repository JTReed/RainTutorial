package com.tutorial.rain.entity.mob;

import com.tutorial.rain.entity.Entity;
import com.tutorial.rain.graphics.Sprite;

public abstract class Mob extends Entity
{
	
	protected Sprite sprite;
	protected int dir = 2;
	protected boolean moving = false;
	
	public void move(int xa, int ya) 
	{
		if(xa > 0) {
			dir = 1; // E
		}
		if (xa < 0) {
			dir = 3; // W
		}
		if (ya > 0) {
			dir = 2; // S
		}
		if (ya < 0) {
			dir = 0; // N
		}
		
		if(!collision()) {
			x += xa;
			y += ya;
		}
	}
	
	public void update()
	{
		
	}
	
	public void render()
	{
		
	}
	
	private boolean collision() 
	{
		// temp return
		return false;
	}
	
}
