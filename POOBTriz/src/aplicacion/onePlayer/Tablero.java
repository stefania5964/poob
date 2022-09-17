package aplicacion.onePlayer;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;

import presentacion.POOBTrizGUI;

/**
 * Santiago Fetecua - Stefania Giraldo 
 * Proyecto Final POOB
 */
public class Tablero extends JPanel implements Serializable, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int PLAYING = 0;
	public static int PAUSE = 1;
	public static int GAME_OVER = 2;
	protected int state = PLAYING;
	
	private static int FPS = 60;
	private static int delay = 1000/ FPS;
	public static final int W_TABLERO = 10, H_TABLERO = 20;
	public static final int T_CUADRO = 30;
	private Timer looper;
	protected static Color[][] tablero = new Color[H_TABLERO][W_TABLERO];
	
	private Figura[] figuras = new Figura[7];
	
	private Buffo[] buffos = new Buffo[0];
	
	private int[][][] formas = {{{1, 1, 1, 1}}, {{1, 1, 1},{0, 1, 0}}, {{1, 1, 1},{1, 0, 0}}, {{1, 1, 1},{0, 0, 1}}, {{0, 1, 1}, {1, 1, 0}}, {{1, 1, 0}, {0, 1, 1}}, {{1, 1},{1, 1}}}; 

	
	private Color[] coloresF = {Color.decode("#24D3FF"), Color.decode("#FF24E2"), Color.decode("#FF8C00"), 
	        Color.decode("#FF8C00"), Color.decode("#15FF00"), Color.decode("#15FF00"), Color.decode("#FFFB00")};
	
	private Color[] coloresB = {Color.YELLOW, Color.PINK,Color.GREEN,Color.ORANGE};
	
	private int type;
	
	private int typeB;
	protected Figura fActual;
	protected Figura FSiguiente;
	protected Buffo bActual;
	private Random random;
	protected String nombre;
	protected String nombre2;
	protected int score = 0;
	protected boolean isOk = false;
	protected int nBuffos;
	private int tiempo = 100;
	private boolean b = true;
	private int velocidad;
	private int sAntiguo = 0;
	
	/**
	 * create of board
	 * @param nombre
	 * @param nombre2
	 */
	public Tablero(String nombre, String nombre2, String numbuffos, int velocidad) {
		random = new Random();
		this.nombre = nombre;
		this.nombre2 = nombre2;
		this.nBuffos = Integer.parseInt(numbuffos);
		this.velocidad = velocidad;
		this.addKeyListener(this);
		setType();
		setTypeB();
        fActual = figuras[random.nextInt(figuras.length)]; 
        setFSiguiente();
        isOk = true;
        looper =new Timer(delay, new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    				update();
    				setType();
    				createB();
    				repaint();
    				revalidate();
    			}
    		});
    		looper.start();
	}
	
	/**
	 * stop the timer
	 */
	public void stopGame() {
		score = 0;
		for(int row = 0; row < tablero.length; row++) {
			for(int col = 0; col < tablero[row].length; col++) {
				tablero[row][col] = null;
			}
		}
		looper.stop();
	}
	
	/**
	 * call figure movement methods and buff powers
	 */
	public void update() {
		if(state == PLAYING) {
			bActual.update();
			fActual.update();
		}	
	}
	
	public int getNBuffos() {
		return nBuffos;
	}
	
	/**
	 * creates the buffo depending on the type
	 */
	public void setTypeB() {
		this.typeB = random.nextInt(3);
		bActual = new Buffo(new int[][]{
	            {1} 
	        }, this, coloresB[typeB]);
	}
	
	/**
	 * creates the figure depending on the type
	 */
	public void setType() {
		this.type = random.nextInt(4);
		if (type == 0) {
			figuras[0] = new Figura(formas [0], this, coloresF[0], velocidad);
			figuras[1] = new Figura(formas [1], this, coloresF[1], velocidad);
			figuras[2] = new Figura(formas [2], this, coloresF[2], velocidad);
			figuras[3] = new Figura(formas [3], this, coloresF[3], velocidad);
			figuras[4] = new Figura(formas [4], this, coloresF[4], velocidad);
			figuras[5] = new Figura(formas [5], this, coloresF[5], velocidad);
			figuras[6] = new Figura(formas [6], this, coloresF[6], velocidad);
		}else if(type == 1) {
			figuras[0] = new FUseless(formas [0], this, coloresF[0], velocidad);
			figuras[1] = new FUseless(formas [1], this, coloresF[1], velocidad);
			figuras[2] = new FUseless(formas [2], this, coloresF[2], velocidad);
			figuras[3] = new FUseless(formas [3], this, coloresF[3], velocidad);
			figuras[4] = new FUseless(formas [4], this, coloresF[4], velocidad);
			figuras[5] = new FUseless(formas [5], this, coloresF[5], velocidad);
			figuras[6] = new FUseless(formas [6], this, coloresF[6], velocidad);	        
		}else if(type == 2) {
			figuras[0] = new FWinner(formas [0], this, coloresF[0], velocidad);
			figuras[1] = new FWinner(formas [1], this, coloresF[1], velocidad);
			figuras[2] = new FWinner(formas [2], this, coloresF[2], velocidad);
			figuras[3] = new FWinner(formas [3], this, coloresF[3], velocidad);
			figuras[4] = new FWinner(formas [4], this, coloresF[4], velocidad);
			figuras[5] = new FWinner(formas [5], this, coloresF[5], velocidad);
			figuras[6] = new FWinner(formas [6], this, coloresF[6], velocidad);	        
		}else if (type == 3) {
			figuras[0] = new FBomb(formas [0], this, coloresF[1], velocidad);
			figuras[1] = new FBomb(formas [1], this, coloresF[1], velocidad);
			figuras[2] = new FBomb(formas [2], this, coloresF[2], velocidad);
			figuras[3] = new FBomb(formas [3], this, coloresF[3], velocidad);
			figuras[4] = new FBomb(formas [4], this, coloresF[4], velocidad);
			figuras[5] = new FBomb(formas [5], this, coloresF[5], velocidad);
			figuras[6] = new FBomb(formas [6], this, coloresF[6], velocidad);	        
		} 
	}
	
	public void setFSiguiente() {
		FSiguiente = figuras[random.nextInt(figuras.length)];
		FSiguiente.reset();
		gameOver();
	}
	
	public void createB() {
		if(b == false && nBuffos > 1) {
			tiempo--;
			if(tiempo == 0) {
				int r = random.nextInt(99);
				if(r > 50) {
					setTypeB();
					b = true;
					nBuffos--;
				}
				tiempo = 100;
			}
		}
	}
	
	public void setBandera(boolean bandera) {
		b = bandera;
	}
	
	/**
	 * send random figure
	 */
	public void setFActual() {
		fActual = FSiguiente;
		setFSiguiente();
	}
	
	/**
	 * send random buff
	 */
	public void setBActual(){
		bActual = buffos[random.nextInt(buffos.length)];
	}
	
	/**
	 * returns the current figure
	 * @return
	 */
	public Figura getFActual() {
		return fActual;
	}
	
	/**
	 * returns the current figure
	 * @return
	 */
	public Buffo getBActual() {
		return bActual;
	}
	
	/**
	 * evaluate if the game is over
	 */
	private void gameOver() {
		int[][] coords = fActual.getCoords();
		for (int row = 0; row < coords.length; row++ ) {
			for (int col = 0; col < coords[0].length; col++ ) {
				if(coords[row][col] != 0) {
					if (tablero[row + fActual.getY()][col+ fActual.getX()] != null) {
						state = GAME_OVER;
						Message("GAME OVER \n Presione R para Reiniciar \n Score:" + score+"");
					}
				}
			}
		}
	}
	
	/**
	 * draw board and buffos
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(getGraphics());
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0,getWidth(),getHeight());
		
		
		g.setColor(FSiguiente.getColor());
		for(int row = 0 ; row < FSiguiente.getCoords().length; row++) {
			for(int col = 0 ; col < FSiguiente.getCoords()[0].length;col++) {
				if (FSiguiente.getCoords()[row][col] != 0) {
					g.fillRect((col * T_CUADRO + 350), (row * T_CUADRO + 50),T_CUADRO,T_CUADRO);
						
				}
			}
		}
		
		fActual.paint(g);
		
		Font f = new Font("SansSerifBold",Font.PLAIN,20);
		g.setFont(f);
		g.setColor(Color.BLACK);
		
		g.drawString("Siguiente Figura",POOBTrizGUI.WIDTH - 200, POOBTrizGUI.HEIGHT/10 -30);
		g.drawString(nombre,POOBTrizGUI.WIDTH - 200, POOBTrizGUI.HEIGHT/5 );
		
		for(int row = 0; row < tablero.length; row++) {
			for(int col = 0; col < tablero[row].length; col++) {
				if (tablero[row][col] != null) {
					g.setColor(tablero[row][col]);
					g.fillRect((col * T_CUADRO)+10, (row * T_CUADRO)+10,T_CUADRO,T_CUADRO);
				}
			}
		}
		// dibuja tablero
		g.setColor(Color.BLACK);
		for (int row = 0; row < H_TABLERO+1; row++ ) {
			g.drawLine(10, (T_CUADRO * row) + 10, (T_CUADRO * W_TABLERO)+10, (T_CUADRO * row)+10);
		}
		
		for (int col = 0; col < W_TABLERO + 1; col++ ) {
			g.drawLine((col * T_CUADRO)+10, 10, (T_CUADRO * col)+10, (T_CUADRO * H_TABLERO)+10);
		}
		
		g.drawString("Score", POOBTrizGUI.WIDTH - 200, POOBTrizGUI.HEIGHT/4 );
		g.drawString(score+"", POOBTrizGUI.WIDTH - 200, POOBTrizGUI.HEIGHT/4 +30);
		
		g.drawString("Mejor Score", POOBTrizGUI.WIDTH - 200, POOBTrizGUI.HEIGHT/4 + 60);
		g.drawString(sAntiguo+"", POOBTrizGUI.WIDTH - 200, POOBTrizGUI.HEIGHT/4 +90);
	}
	
	/**
	 * return the color of board
	 * @return
	 */
	public Color[][] getTablero() {
		return tablero;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	/**
	 * keyboard movement
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_S) {
			fActual.acelerar();
		}else if(e.getKeyCode() == KeyEvent.VK_D) {
			fActual.mDer();
		}else if(e.getKeyCode() == KeyEvent.VK_A) {
			fActual.mIzq();
		}	
		else if(e.getKeyCode() == KeyEvent.VK_W) {
			fActual.rotar();
		} 
		// restart
		if (state == GAME_OVER) {
			if(e.getKeyCode() == KeyEvent.VK_R) {
				for(int row = 0; row < tablero.length; row++) {
					for(int col = 0; col < tablero[row].length; col++) {
						tablero[row][col] = null;
					}
				}
				setFActual();
				setTypeB();
				state = PLAYING;
				sAntiguo = (sAntiguo < score) ? (sAntiguo = score): (sAntiguo);
				score = 0;
			}
		}
		//pause
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (state == PLAYING) {
				state = PAUSE;
			}else if(state == PAUSE) {
				state = PLAYING;
			}
		}
	}
	
	/**
	 * Movement that is disabled if the keypad key is not pressed
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_S) {
			fActual.frenar();
		}
	}
	
	public void pause() {
		state = PAUSE;
	}
	
	public void addScore() {
		score++;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int s) {
		score += s;
	}
	/**
	 * send message
	 * @param mensaje
	 */
	public void Message(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje);
    }
	
	/**
	 * save the game
	 * @param archivo
	 * @throws TableroException
	 */
	public void guardar(String archivo) throws TableroException{
		if (archivo.contains(".dat")) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("scores.dat"));
				oos.writeObject(score);
				oos.close();
			}catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}else {
			throw new TableroException (TableroException.INCOMPATIBLE);
		}
	}
	
	
	public boolean ok() {
		return isOk;
	}
	
}
