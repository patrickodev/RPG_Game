package com.alonerpg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alonerpg.main.Game;

public class Tile {
	
	public static BufferedImage TILE_GROUND = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_WALL_H = Game.spritesheet.getSprite(16, 0, 16, 16);
	public static BufferedImage TILE_WALL_V = Game.spritesheet.getSprite(16, 16, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
