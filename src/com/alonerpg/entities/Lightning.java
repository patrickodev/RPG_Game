package com.alonerpg.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alonerpg.main.Game;
import com.alonerpg.world.Camera;

public class Lightning extends Entity{
	
	private double dx;
	private double dy;
	private double speed = 2;
	
	public static BufferedImage lightning;
	
	private int life = 30, curLife = 0;
	
	public Lightning(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		
		lightning = Game.spritesheet.getSprite(48, 96, 16, 16);
	}
	
	public void tick() {
		x+=dx*speed;
		y+=dy*speed;
		if(curLife == life) {
			Game.lightnings.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		
		g.drawImage(lightning, this.getX()-Camera.x-10, this.getY()-Camera.y-9, null);
	}
	
}
