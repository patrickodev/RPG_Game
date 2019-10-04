package com.alonerpg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alonerpg.entities.*;
import com.alonerpg.main.Game;

public class World {
	
	private Tile[] tiles;
	
	public static int WIDTH;

	public static int HEIGHT;

	public World(String path) {
		
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			int[] pixels = new int[WIDTH*HEIGHT];
			tiles = new Tile[WIDTH*HEIGHT];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx=0; xx<map.getWidth(); xx++) {
				for(int yy=0; yy<map.getWidth();yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					
					tiles[xx + (yy * WIDTH)] = new GroundTile(xx*16, yy*16, Tile.TILE_GROUND);
					
					if(pixelAtual == 0xff000000) {
						//System.out.println("estou no pix chao ");
						tiles[xx + (yy * WIDTH)] = new GroundTile(xx*16, yy*16, Tile.TILE_GROUND);					
					}else if(pixelAtual == 0xffffffff) {
						//System.out.println("estou no pix parede ");
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					}else if(pixelAtual == 0xff0036ff) {
						System.out.println("sou o jogador");
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if (pixelAtual == 0xff7700e4) {
						System.out.println("Muniçao");
						Game.entities.add(new Bullet(xx*16, yy*16,16,16,Entity.BULLET));
					}else if (pixelAtual == 0xff48e400) {
						System.out.println("vida");
						Game.entities.add(new LifePack(xx*16, yy*16,16,16,Entity.LIFEPACK));
					}else if (pixelAtual == 0xffe40000) {
						System.out.println("inimigo");
						Game.entities.add(new Enemy(xx*16, yy*16,16,16,Entity.ENEMY));						
					}else if (pixelAtual == 0xffe4d800) {
						System.out.println("arma");
						Game.entities.add(new Weapon(xx*16, yy*16,16,16,Entity.WEAPON));
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int finalx = xstart + (Game.WIDTH >> 4);
		int finaly = ystart + (Game.HEIGHT >> 4);
		
		for(int xx=xstart; xx<=finalx; xx++) {
			for(int yy=ystart; yy<=finaly; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
