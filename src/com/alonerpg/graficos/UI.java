package com.alonerpg.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import com.alonerpg.main.Game;

public class UI {
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(10, 5, 50, 10);
		g.setColor(Color.green);
		g.fillRect(10, 5, (int)((Game.player.life/Game.player.maxLife)*50), 10);
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife, 22, 13);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.setColor(Color.white);
		g.drawString("Energia: " + Game.player.energies, 180,13);
		
	}
}
