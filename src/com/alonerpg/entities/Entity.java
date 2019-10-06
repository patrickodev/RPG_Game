package com.alonerpg.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.alonerpg.main.Game;
import com.alonerpg.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK = Game.spritesheet.getSprite(8*16, 0, 16, 16);
	public static BufferedImage UNIFORM = Game.spritesheet.getSprite(9*16, 0, 16, 16);
	public static BufferedImage ENERGY = Game.spritesheet.getSprite(8*16, 16, 16, 16);
	public static BufferedImage ENEMY = Game.spritesheet.getSprite(0, 4*16, 16, 16);
	
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	private int maskx, masky, maskw, maskh;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x= x;
		this.y= y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.maskw = width;
		this.maskh = height;
		
	}
	
	public void setMask(int maskx, int masky, int maskw, int maskh) {
		this.maskx = maskx;
		this.masky = masky;
		this.maskw = maskw;
		this.maskh = maskh;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}


	public int getWidth() {
		return this.width;
	}


	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY()+e1.masky, e1.maskw, e1.maskh);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY()+e2.masky, e2.maskw, e2.maskh);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		
	}



}
