package com.alonerpg.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.alonerpg.entities.Enemy;
import com.alonerpg.entities.Entity;
import com.alonerpg.entities.Lightning;
import com.alonerpg.entities.Player;
import com.alonerpg.graficos.Spritesheet;
import com.alonerpg.graficos.UI;
import com.alonerpg.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static int WIDTH = 240; //Largura da janela grafica
	public static int HEIGHT = 160; // Altura da janela grafica
	private final int SCALE = 5; // Escala da janela grafica
	
	private int curLevel = 1, maxLevel = 2;
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Lightning> lightnings; 
	
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public UI ui;
	private int framesGameOver = 0;
	private boolean showGameOver = true;
	private boolean restartGame = false;
	
	public static String gameState = "GameOver"; 
		
	public Game(){
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		
		/*Inicializando objetos*/
		ui = new UI();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB); //largura, altura, tipo da imagem
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		lightnings = new ArrayList<Lightning>();
		spritesheet = new Spritesheet("/jamal.png");
		player= new Player(16, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		world = new World("/level1.png");
		
	}
	
	public void initFrame() {
		frame = new JFrame("Teste"); //Inicializa o objeto e pode deixar o nome na janela
		frame.add(this); //Tudo pertence ao Canvas, isso permite pegar todas as informaçoes de "frame"
		frame.setResizable(false); // Para o usuario não consegui redimencionar a janela
		frame.pack(); //Metodo do frames para quando colocar o canvas calcular certas dimensoes e mostrar
		frame.setLocationRelativeTo(null); //Para a janela ficar no centro
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Para quando fechar a janela, fechar tambem o programa
		frame.setVisible(true); //Para quando inicializar a janela ja estar visivel
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start(); // Aqui o jogo inicia de fato
	}
	
	/*Cuida da logica do jogo*/
	public void tick() {
		if(gameState == "Normal") {
		
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			for(int i = 0; i < lightnings.size(); i++) {
				Entity e = lightnings.get(i);
				e.tick();
			}
			
			if(enemies.size() == 0) {
				System.out.println("Proximo nivel");
				curLevel++;
				if(curLevel > maxLevel) {
					curLevel = 1;
				}
				String newWorld = "/level"+curLevel+".png";
				World.restartGame(newWorld);
			}
		}else if(gameState == "GameOver") {
			this.framesGameOver++;
			if(this.framesGameOver == 40) {
				this.framesGameOver = 0;
				if(this.showGameOver )
					this.showGameOver = false;
				else
					this.showGameOver = true;
			}
		}
		
		if(restartGame) {
			restartGame = false;
			gameState = "Normal";
			curLevel = 1;
			String newWorld = "/level"+curLevel+".png";
			World.restartGame(newWorld);
		}
	}
	
	/*Cuida da renderizaçao*/
	public void render() {
		BufferStrategy bs = this.getBufferStrategy(); // Uma sequencia de buffers que coloca na tela pra otimizar a renderizacao
		if(bs == null) { //Vai servir para criar o buffer na primeira vez que renderizar, nas proximas vezes não entra mais nessa condicao 
			this.createBufferStrategy(3);
			return; //Esta funcionando como um break
			
		}
		
		Graphics g = image.getGraphics(); //Para começar a renderizar na tela
		g.setColor(Color.darkGray); //Para definir a cor padrao da tela 
		g.fillRect(0, 0, WIDTH, HEIGHT); //Renderizando um retangulo
		
		/*g.setColor(Color.blue); 
		g.fillOval(0, 0, 100, 100);*/
		
		/*g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.WHITE);
		g.drawString("Olá mundo", 60, 60);*/
		
		/*Graphics2D g2 = (Graphics2D) g; //Transforma o objeto no Graphics2D e permite que crie animaçoes e efeitos 
		g2.setColor(new Color(0, 0, 0, 200));
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		g2.rotate(Math.toRadians(0), 90+8, 90+8);*/
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i < lightnings.size(); i++) {
			Entity e = lightnings.get(i);
			e.render(g);
		}
		
		ui.render(g);
		
		/***/
		g.dispose(); //Limpar dados que tem na imagem que nao precisa que ja foram usado antes (melhora a performance)
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		if(gameState == "GameOver") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 150));
			g2.fillRect(0, 0, (WIDTH*SCALE), (HEIGHT*SCALE));
			g.setFont(new Font("arial", Font.BOLD, 50));
			g.setColor(Color.white);
			g.drawString("GAME OVER", (WIDTH*SCALE/2) - 130, (HEIGHT*SCALE/2) - 50);
			g.setFont(new Font("arial", Font.BOLD, 30));
			if(showGameOver)
				g.drawString("Pressione start para reiniciar...", (WIDTH*SCALE/2) - 200, (HEIGHT*SCALE/2));
		}
		
		bs.show(); //Para mostrar de fato os graficos
	}
	
	
	@Override
	public void run() {
		long lastTime = System.nanoTime(); //Pega a hora atual do nosso computador e transforma em nanossegundos para ter precisao
		double amountOfTicks = 60.0; // frames per second
		double ns = 1000000000 / amountOfTicks; // dividindo 1 segundo
		double delta = 0;
		//int frames = 0;
		double timer = System.currentTimeMillis();//Pega a hora atual tambem, apenas mais leve e menos preciso
		while(isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns; //Para que a cada segundo dê um update
			lastTime = now;
			if(delta >= 1) { // para que atualize o jogo a cada segundo
				tick(); //primeiro atualiza
				render(); //depois renderiza
				//frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){ // Passou um segundo desde que mostrou a ultima mensagem
				//System.out.println("FPS: "+ frames);
				//frames = 0;
				timer += 1000; // Para mostrar a cada segundo
			}
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
			e.getKeyCode() == KeyEvent.VK_D) {
				player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
			e.getKeyCode() == KeyEvent.VK_A) {
				player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
			e.getKeyCode() == KeyEvent.VK_W) {
				player.up = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
			e.getKeyCode() == KeyEvent.VK_S) {
				player.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_J ||
			e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
			e.getKeyCode() == KeyEvent.VK_D) {
				player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
			e.getKeyCode() == KeyEvent.VK_A) {
				player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
			e.getKeyCode() == KeyEvent.VK_W) {
				player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
			e.getKeyCode() == KeyEvent.VK_S) {
				player.down = false;
		}
				
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.mx = (e.getX()/SCALE);
		player.my = (e.getY()/SCALE);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
