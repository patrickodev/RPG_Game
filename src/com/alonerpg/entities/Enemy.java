package com.alonerpg.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.alonerpg.main.Game;
import com.alonerpg.world.World;

public class Enemy extends Entity{
	
	private double speed = 0.4;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColliding((int)(x+speed), this.getY())) {
			x+=speed;
		}else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
				&& !isColliding((int)(x-speed), this.getY())) {
			x-=speed;
		}
			
		if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
				&& !isColliding(this.getX(), (int)(y+speed))) {
			y+=speed;
		}else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
				&& !isColliding(this.getX(), (int)(y-speed))) {
			y-=speed;
		}
		
	}
	
	public boolean isColliding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext, yNext, World.TILE_SIZE, World.TILE_SIZE);
		
		for(int i=0; i<Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle tangetEnemy = new Rectangle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE);
			if(enemyCurrent.intersects(tangetEnemy)) {
				return true;
			}
			
			
		}
		return false;
	}
}
