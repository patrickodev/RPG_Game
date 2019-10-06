package com.alonerpg.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alonerpg.graficos.Spritesheet;
import com.alonerpg.main.Game;
import com.alonerpg.world.Camera;
import com.alonerpg.world.World;

public class Enemy extends Entity{
	
	private double speed = 0.4;
	
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	
	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 3;
	
	private BufferedImage[] enemyAnimation;
	private BufferedImage enemyDamage;
	
	public boolean heDamaged = false;
	private int damageFrames = 0;
	
	private int life = 5;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		
		enemyAnimation = new BufferedImage[4];
		enemyDamage = Game.spritesheet.getSprite(0, 80, 16, 16);
		
		for(int i=0; i<4; i++) {
			enemyAnimation[i] = Game.spritesheet.getSprite((i*16), 64, 16, 16);
		}
	}
	
	public void tick() {
		if(isCollidingWithPlayer() == false) {
			
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
			
		}else {
			if(Game.rand.nextInt(100) < 10) {
				Game.player.life--;
				Game.player.isDamaged = true;
				System.out.println("life: " + Game.player.life);
			}
			
			if(Game.player.life <= 0) {
				Game.entities = new ArrayList<Entity>();
				Game.enemies = new ArrayList<Enemy>();
				Game.spritesheet = new Spritesheet("/jamal.png");
				Game.player= new Player(16, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
				Game.entities.add(Game.player);
				Game.world = new World("/map.png");
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
		
		if(heDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 30) {
				this.damageFrames = 0;
				heDamaged = false;
			}
		}
		
		coliddingLightning();
		
		if(life <= 0) {
			Game.entities.remove(this);
		}
		
	}
	
	public void coliddingLightning() {
		
		for(int i=0; i<Game.lightnings.size(); i++) {
			Entity e = Game.lightnings.get(i);
			if(e instanceof Lightning) {
				if(Entity.isColliding(this, e)) {
					heDamaged = true;
					life--;
					Game.lightnings.remove(i);
					
					return;
				}
			}
		}	
	}
	
	public void render(Graphics g) {
		if(!heDamaged)
			g.drawImage(enemyAnimation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		else
			g.drawImage(enemyDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}	
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		
		return enemyCurrent.intersects(player);
		
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
