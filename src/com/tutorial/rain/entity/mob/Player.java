package com.tutorial.rain.entity.mob;

import com.tutorial.rain.graphics.Screen;
import com.tutorial.rain.graphics.Sprite;
import com.tutorial.rain.input.Keyboard;

public class Player extends Mob
{
	private Keyboard input;
	
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
		int xa = 0, ya = 0;
		if(input.up) ya--;
		if(input.down) ya++;
		if(input.left) xa--;
		if(input.right) xa++;
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}
	
	public void render(Screen screen)
	{
		if(dir == 0) { // N
			screen.renderPlayer(x - 16, y - 16, sprite.playerUp);
		} 
		if(dir == 1) { // E
			screen.renderPlayer(x - 16, y - 16, sprite.playerRight);
		}
		if(dir == 2) { // S
			screen.renderPlayer(x - 16, y - 16, sprite.playerDown);
		}
		if(dir == 3) { // W
			screen.renderPlayer(x - 16, y - 16, sprite.playerLeft);
		}
		
	}
		
}
