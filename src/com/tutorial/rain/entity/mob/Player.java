package com.tutorial.rain.entity.mob;

import com.tutorial.rain.graphics.Screen;
import com.tutorial.rain.graphics.Sprite;
import com.tutorial.rain.input.Keyboard;

public class Player extends Mob
{
	private Keyboard input;
	private int anim = 0;
	private boolean walking = false;
	
	public Player(Keyboard input)
	{
		this.input = input;
		sprite = Sprite.playerDown;
	}
	
	public Player(int x, int y, Keyboard input)
	{
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.playerDown;
	}
	
	public void update()
	{
		if(anim > 7500) {
			anim = 0;
		}
		anim++;		
		
		int xa = 0, ya = 0;
		if(input.up) ya--;
		if(input.down) ya++;
		if(input.left) xa--;
		if(input.right) xa++;
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}
	
	public void render(Screen screen)
	{
		if(dir == 0) { // N
			sprite = sprite.playerUp;
			if(walking) {
				if(anim % 20 > 10) {
					sprite = sprite.playerUp1;
				} else {
					sprite = sprite.playerUp2;
				}
			}
		} 
		if(dir == 1) { // E
			sprite = sprite.playerRight;
			if(walking) {
				if(anim % 20 > 10) {
					sprite = sprite.playerRight1;
				} else {
					sprite = sprite.playerRight2;
				}
			}
		}
		if(dir == 2) { // S
			sprite = sprite.playerDown;
			if(walking) {
				if(anim % 20 > 10) {
					sprite = sprite.playerDown1;
				} else {
					sprite = sprite.playerDown2;
				}
			}
		}
		if(dir == 3) { // W
			sprite = sprite.playerLeft;
			if(walking) {
				if(anim % 20 > 10) {
					sprite = sprite.playerLeft1;
				} else {
					sprite = sprite.playerLeft2;
				}
			}
		}
		
		screen.renderPlayer(x - 16, y - 16, sprite);
	}
		
}
