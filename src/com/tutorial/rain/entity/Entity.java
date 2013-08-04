package com.tutorial.rain.entity;

import java.util.Random;

import com.tutorial.rain.graphics.Screen;
import com.tutorial.rain.level.Level;

public class Entity
{

	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();

	public void update()
	{
		
	}
	
	public void render(Screen screen)
	{
		
	}
	
	public void remove()
	{
		// Remove the entity from level
		removed = true;
	}
	
	public boolean isRemoved()
	{
		return removed;
	}

}
