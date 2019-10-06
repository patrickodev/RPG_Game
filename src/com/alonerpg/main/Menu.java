package com.alonerpg.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	public String[] options = {"Novo jogo", "Carregar jogo", "Creditos", "Sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean down = false, up = false;
	
	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		/*Fundo preto*/
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		/*Nome do game*/
		g.setColor(Color.blue);
		g.setFont(new Font("arial", Font.BOLD, 50));
		g.drawString("Jamalzin dos shock", (Game.WIDTH*Game.SCALE)/2 - 250, (Game.HEIGHT*Game.SCALE)/2-150);
		/*Opçoes do menu*/
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE)/2 - 90, (Game.HEIGHT*Game.SCALE)/2+50);
		g.drawString("Carregar jogo", (Game.WIDTH*Game.SCALE)/2 - 115, (Game.HEIGHT*Game.SCALE)/2+100);
		g.drawString("Creditos", (Game.WIDTH*Game.SCALE)/2 - 77, (Game.HEIGHT*Game.SCALE)/2+150);
		g.drawString("Sair", (Game.WIDTH*Game.SCALE)/2 - 45, (Game.HEIGHT*Game.SCALE)/2+200);
		
		if(options[currentOption] == "Novo jogo") {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 130, (Game.HEIGHT*Game.SCALE)/2+50);
		}else if(options[currentOption] == "Carregar jogo") {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 155, (Game.HEIGHT*Game.SCALE)/2+100);
		}else if(options[currentOption] == "Creditos") {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 117, (Game.HEIGHT*Game.SCALE)/2+150);
		}else if(options[currentOption] == "Sair") {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 85, (Game.HEIGHT*Game.SCALE)/2+200);
		}
	}
}
