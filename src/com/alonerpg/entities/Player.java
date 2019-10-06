package com.alonerpg.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.alonerpg.main.Game;
import com.alonerpg.world.Camera;
import com.alonerpg.world.World;

public class Player extends Entity {
	
	public double life = 100, maxLife=100;
	
	public int mx, my; //posiçoes do mouse
	
	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 0.9;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	public boolean isDamaged = false;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage[] rightHero;
	private BufferedImage[] leftHero;
	private BufferedImage[] upHero;
	private BufferedImage[] downHero;
	
	private BufferedImage playerDamage;
	private BufferedImage heroDamage;
	
	private int damageFrames = 0;
	
	public int energies = 0;
	private boolean hasUniform = false;
	
	public boolean shoot = false, mouseShoot = false;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		rightHero = new BufferedImage[4];
		leftHero = new BufferedImage[4];
		upHero = new BufferedImage[4];
		downHero = new BufferedImage[4];
		
		playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);
		heroDamage = Game.spritesheet.getSprite(0, 32, 16, 16);
		
		
		
		for(int i=0; i<4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(64 +(i*16), 0, 16, 16);
		}
		for(int i=0; i<4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(64 +(i*16), 16, 16, 16);
		}
		for(int i=0; i<4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(48, (i*16), 16, 16);
		}
		for(int i=0; i<4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32, (i*16), 16, 16);
		}
		for(int i=0; i<4; i++) {
			rightHero[i] = Game.spritesheet.getSprite(64 +(i*16), 80, 16, 16);
		}
		for(int i=0; i<4; i++) {
			leftHero[i] = Game.spritesheet.getSprite(64 +(i*16), 96, 16, 16);
		}
		for(int i=0; i<4; i++) {
			upHero[i] = Game.spritesheet.getSprite(144, 48+(i*16), 16, 16);
		}
		for(int i=0; i<4; i++) {
			downHero[i] = Game.spritesheet.getSprite(128, 48+(i*16), 16, 16);
		}
	}
	
	public void tick() {
		moved = false;
		
		if(right && World.isFree((int)(x+speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		}	
		else if(left && World.isFree((int)(x-speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		
		if(up && World.isFree(this.getX(), (int)(y-speed))) {
			moved = true;
			dir = up_dir;
			y -= speed;
		}	
		else if(down && World.isFree(this.getX(), (int)(y+speed))){
			moved = true;
			dir = down_dir;
			y += speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index>maxIndex) {
					index=0;
				}
			}
		}
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(shoot) {
			/*Criando o raio para atirar com o teclado*/
			shoot = false;
			if(hasUniform && energies > 0) {
				energies--;
								
				int dx = 0;
				int dy = 0;
				int px = 0;
				int py = 10;
				
				if(dir == right_dir) {
					px = 10;
					dx = 1;
				}else if(dir == left_dir) {
					px = 2;
					dx = -1;
				}
				if(dir == up_dir) {
					dy = -1;
				}else if(dir == down_dir) {
					dy = 1;
				}
				
				Lightning lightning = new Lightning(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				Game.lightnings.add(lightning);
			}
		}
		
		if(mouseShoot) {
			/*Criando o raio para atirar com o mouse*/
			double angle = Math.atan2(my - (this.getY()+8 - Camera.y), mx - (this.getX()+8 - Camera.x));
			mouseShoot = false;
			if(hasUniform && energies > 0) {
				energies--;
						
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				int px = 8;
				int py = 8;
								
				Lightning lightning = new Lightning(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				Game.lightnings.add(lightning);
			}
		}
		
		checkLifepack();
		checkEnergies();
		checkUniform();
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkUniform() {
		for(int i=0; i<Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Uniform) { // quer dizer que esta vendo um lifepack
				if(Entity.isColliding(this, e)) {
					hasUniform = true;
					System.out.println("pegou o uniforme ");
					Game.entities.remove(e);
					return;
				}
			}
		}
	}
	
	public void checkEnergies() {
		for(int i=0; i<Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Energy) { // quer dizer que esta vendo um lifepack
				if(Entity.isColliding(this, e)) {
					energies+=100;
					System.out.println("Muniçao: "+ energies);
					Game.entities.remove(e);
					return;
				}
			}
		}
	}
	
	public void checkLifepack() {
		for(int i=0; i<Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof LifePack) { // quer dizer que esta vendo um lifepack
				if(Entity.isColliding(this, e)) {
					life+=10;
					if(life>=100)
						life = 100;
					Game.entities.remove(e);
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
					
			if(dir == right_dir) {
				if(!hasUniform) {
					g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(rightHero[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}
			else if(dir == left_dir) {
				
				if(!hasUniform) {
					g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(leftHero[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}
			
			if(dir == up_dir) {
				
				if(!hasUniform) {
					g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(upHero[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}
			else if(dir == down_dir) {
				
				if(!hasUniform) {
					g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(downHero[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}
		}else {
			
			if(!hasUniform) {
				g.drawImage(playerDamage, this.getX() -Camera.x, this.getY() - Camera.y, null);
			}else {
				g.drawImage(heroDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
		
	}

	

}
