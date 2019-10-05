package com.alonerpg.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.alonerpg.entities.Player;

public class UI {
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(10, 5, 50, 10);
		g.setColor(Color.green);
		g.fillRect(10, 5, (int)((Player.life/Player.maxLife)*50), 10);
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString((int)Player.life+"/"+(int)Player.maxLife, 22, 13);
		
	}
}
