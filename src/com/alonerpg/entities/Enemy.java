package com.alonerpg.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.alonerpg.main.Game;
import com.alonerpg.world.Camera;
import com.alonerpg.world.World;

public class Enemy extends Entity{
	
	private double speed = 0.4;
	
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 3;
	
	private BufferedImage[] enemyAnimation;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		
		enemyAnimation = new BufferedImage[4];
		
		for(int i=0; i<4; i++) {
			enemyAnimation[i] = Game.spritesheet.getSprite((i*16), 64, 16, 16);
		}
	}
	
	public void tick() {
		if(isColiddingWithPlayer() == false) {
			if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
					&& !isColidding((int)(x+speed), this.getY())) {
				x+=speed;
			}else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
					&& !isColidding((int)(x-speed), this.getY())) {
				x-=speed;
			}
				
			if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
					&& !isColidding(this.getX(), (int)(y+speed))) {
				y+=speed;
			}else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
					&& !isColidding(this.getX(), (int)(y-speed))) {
				y-=speed;
			}
			
		}else {
			if(Game.rand.nextInt(100) < 10) {
				Game.player.life--;
				System.out.println("life: " + Game.player.life);
			}
			
			if(Game.player.life == 0) {
				System.exit(1);
			}
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index>maxIndex) {
				index=0;
			}
		}
		
	}
	
	public void render(Graphics g) {		
		g.drawImage(enemyAnimation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}	
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		
		return enemyCurrent.intersects(player);
		
	}
	
	public boolean isColidding(int xNext, int yNext) {
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
