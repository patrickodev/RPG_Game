package com.alonerpg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.alonerpg.entities.*;
import com.alonerpg.main.Game;

public class World {
	
	public static Tile[] tiles;
	
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
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
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_H);
					}else if(pixelAtual == 0xffb4b3a4) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL_V);
					}else if(pixelAtual == 0xff0036ff) {
						System.out.println("sou o jogador");
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if (pixelAtual == 0xff7700e4) {
						System.out.println("Energia");
						Energy energies = new Energy(xx*16, yy*16,16,16,Entity.ENERGY);
						energies.setMask(5, 5, 5, 5);
						Game.entities.add(energies);
					}else if (pixelAtual == 0xff48e400) {
						System.out.println("vida");
						LifePack pack = new LifePack(xx*16, yy*16,16,16,Entity.LIFEPACK);
						pack.setMask(3, 5, 10, 11);
						Game.entities.add(pack);
					}else if (pixelAtual == 0xffe40000) {
						System.out.println("inimigo");
						Enemy en = new Enemy(xx*16, yy*16,16,16,Entity.ENEMY);
						Game.entities.add(en);
						Game.enemies.add(en);
					}else if (pixelAtual == 0xffe4d800) {
						System.out.println("uniforme");
						Game.entities.add(new Uniform(xx*16, yy*16,16,16,Entity.UNIFORM));
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xNext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (yNext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1+(y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2+(y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3+(y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4+(y4*World.WIDTH)] instanceof WallTile));
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
