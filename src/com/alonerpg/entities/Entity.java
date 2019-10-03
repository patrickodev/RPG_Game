package com.alonerpg.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alonerpg.main.Game;

public class Entity {
	
	public static BufferedImage LIFEPACK = Game.spritesheet.getSprite(8*16, 0, 16, 16);
	public static BufferedImage WEAPON = Game.spritesheet.getSprite(9*16, 0, 16, 16);
	public static BufferedImage BULLET = Game.spritesheet.getSprite(8*16, 16, 16, 16);
	public static BufferedImage ENEMY = Game.spritesheet.getSprite(9*16, 16, 16, 16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x= x;
		this.y= y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
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
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);
	}



}
