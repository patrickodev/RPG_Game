package com.alonerpg.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alonerpg.main.Game;
import com.alonerpg.world.Camera;

public class LifePack extends Entity{
	
	private BufferedImage[] Animation;
	
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;

	public LifePack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		Animation = new BufferedImage[2];
		
		for(int i=0; i<2; i++) {
			Animation[i] = Game.spritesheet.getSprite(128+(i*16), 128, 16, 16);
		}
	}
	
	public void tick() {
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
		g.drawImage(Animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
}
