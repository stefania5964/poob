package aplicacion.onePlayer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Buffo {
	protected int x = (int)(Math.random()*5) + 2;
	protected int y = (int)(Math.random()*6)+2;
	protected Color color;
	protected Color borde;
	protected boolean colision = false;
	protected int[][] coordenadas;
	protected Tablero tablero;
	
	private Color[] coloresB = {Color.YELLOW, Color.PINK,Color.GREEN,Color.ORANGE};
	
	/**
	 * Creator of buff
	 * @param coords
	 * @param tablero
	 * @param color
	 */
	public Buffo(int[][] coords, Tablero tablero, Color color) {
		this.coordenadas = coords;
		this.tablero = tablero;
		this.color = color;
		this.borde = color;
		tablero.getTablero()[y][x] = color;
	}
	
	/**
	 * change values for after reset
	 */
	public void reset() {
		this.x = (int)(Math.random()*5) + 1;
		this.y = (int)(Math.random()*6)+2;
		colision = false;
		tablero.getTablero()[y][x] = null;
	}
	
	/**
	 * see if there is a collision with a figure and apply a power to it
	 */
	public void update() {
		if(tablero.getTablero()[y-1][x] != null) {
			if(color == coloresB[0]) {
				colision = true;
				tablero.getFActual().setVelocidad(4000);
				final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		        final Runnable runnable = new Runnable() {
		            int countdownStarter = 3;
		            public void run() {
		                countdownStarter--;

		                if (countdownStarter < 0) {
		                    tablero.getFActual().setVelocidad(700);
		                    scheduler.shutdown();
		                }
		            }
		        };
		        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);  
			}else if(color == coloresB[1]) {
				colision = true;
				tablero.getFActual().setVelocidad(25000);	
			}else if(color == coloresB[2]) {
				colision = true;
				tablero.getFActual().setVelocidad(2000);
				final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		        final Runnable runnable = new Runnable() {
		            int countdownStarter = 4;
		            public void run() {
		                countdownStarter--;
		                if (countdownStarter < 0) {
		                    tablero.getFActual().setVelocidad(700);
		                    scheduler.shutdown();
		                }
		            }
		        };
		        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);			 
			}else if(color == coloresB[3]) {
				colision = true;
				tablero.getFActual().setVelocidad(350);
				final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		        final Runnable runnable = new Runnable() {
		            int countdownStarter = 3;
		            public void run() {
		                countdownStarter--;
		                if (countdownStarter < 0) {
		                    tablero.getFActual().setVelocidad(700);
		                    scheduler.shutdown();
		                }
		            }
		        };
		        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
			}
			tablero.getTablero()[y][x] = null;
			tablero.setBandera(false);
			reset();
			}
	}	
		
	/**
	 * return x coordinates
	 * @return
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * return y coordinates
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	public void setColision(boolean c) {
		colision = c;
	}
	
	public boolean getColision() {
		return colision;
	}
	
	public Color getColor() {
		return color;
	}
	
}
