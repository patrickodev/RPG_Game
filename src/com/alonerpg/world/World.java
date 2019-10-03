package com.alonerpg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {
	
	private Tile[] tiles;
	
	private static int WIDTH, HEIGHT;

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
					
					if(pixelAtual == 0xff000000) {
						System.out.println("estou no pix preto ");
						tiles[xx + (yy * WIDTH)] = new GroundTile(xx*16, yy*16, Tile.TILE_GROUND);					
					}else if(pixelAtual == 0xffffffff) {
						System.out.println("estou no pix branco ");
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					}else if(pixelAtual == 0xff0036ff) {
						System.out.println("sou o jogador");
						tiles[xx + (yy * WIDTH)] = new GroundTile(xx*16, yy*16, Tile.TILE_GROUND);
					}else {
						//amarelo
						tiles[xx + (yy * WIDTH)] = new GroundTile(xx*16, yy*16, Tile.TILE_GROUND);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		for(int xx=0; xx<WIDTH; xx++) {
			for(int yy=0; yy<HEIGHT; yy++) {
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
