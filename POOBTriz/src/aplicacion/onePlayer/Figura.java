package aplicacion.onePlayer;

import java.awt.Graphics;
import java.awt.Color;


/**
 * Santiago Fetecua - Stefania Giraldo 
 * Proyecto Final POOB
 */
public class Figura {
	protected int x = (int)(Math.random()*6);
	protected int y  = 0;
	private int normal = 700;
	protected int velocidad;
	private int rapido = 50;
	protected long beginTime;
	public static final int T_CUADRO = 30;
	public static final int W_TABLERO = 10, H_TABLERO = 20;
	protected int dX= 0;
	protected boolean colision = false;
	
	protected int[][] coordenadas;
	
	protected Tablero tablero;
	protected Color color;
	protected Color borde;
	
	/**
	 * create of figure
	 * @param coords
	 * @param tablero
	 * @param color
	 */
	public Figura(int[][] coords, Tablero tablero, Color color, int speed) {
		this.coordenadas = coords;
		this.tablero = tablero;
		this.color = color;
		this.borde = Color.BLACK;
		this.velocidad = speed;
	}
	
	/**
	 * change the value of x
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * change the value of y
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * reset the figure
	 */
	public void reset() {
		this.x = (int)(Math.random()*6);
		this.y = 0;
		colision = false;
	}
	/**
	 * figure movement
	 */
	public void update() {
	// condicion y
		for (int row = 0; row < coordenadas.length; row++) {
			for (int col = 0; col < coordenadas[0].length;col++) {
				if(coordenadas[row][col] != 0) {
					tablero.getTablero()[y + row][x + col] = null;
				}
			}}
			if (colision) {
				for (int row = 0; row < coordenadas.length; row++) {
					for (int col = 0; col < coordenadas[0].length;col++) {
						if(coordenadas[row][col] != 0) {
							tablero.getTablero()[y + row][x + col] = color;
						}
					}
				}
				checkLine();
				tablero.addScore();
				//set figura
				tablero.setFActual();
				return;
			}
			// movimiento horizontal
			boolean moveX = true;
			if(!(x + dX + coordenadas[0].length > 10) && !(x + dX < 0)) {
				for(int row = 0; row < coordenadas.length;row++) {
					for(int col = 0; col < coordenadas[row].length;col++) {
						if(coordenadas[row][col] != 0) {
							if(tablero.getTablero()[y+row][x+dX+col] != null) {
								moveX = false;
							}	
						}
					}
				}
				if(moveX) {
					x+=dX;
				}
			}
			dX = 0;
			if(System.currentTimeMillis() - beginTime > velocidad) {
				//movimiento vertical
				if(!(y + 1 + coordenadas.length > H_TABLERO)) {
					for (int row = 0; row < coordenadas.length; row++) {
						for (int col = 0; col < coordenadas[row].length; col++) {
							if (coordenadas[row][col] != 0) {
								if(tablero.getTablero()[y+1+row][x+dX+col] != null) {
									colision = true;
								}
							}
						}
					}
					if(!colision) {
						y++;
						for (int row = 0; row < coordenadas.length; row++) {
							for (int col = 0; col < coordenadas[0].length;col++) {
								if(coordenadas[row][col] != 0) {
									tablero.getTablero()[y + row][x + col] = color;
								}
							}
						}
					}
				}else {
					colision = true;
				}
				beginTime = System.currentTimeMillis();}
				
	}
	
	/**
	 * evaluates if a row was completed
	 */
	protected void checkLine() {
		int bottomLine = tablero.getTablero().length -1;
		for (int topLine = tablero.getTablero().length -1; topLine > 0 ; topLine--) {
			int count = 0;
			for(int col= 0; col < tablero.getTablero()[0].length;col++) {
				if(tablero.getTablero()[topLine][col] != null) {
					count++;
				}
				tablero.getTablero()[bottomLine][col] = tablero.getTablero()[topLine][col];
			}
			if(count < tablero.getTablero()[0].length) {
				bottomLine--;
			}
		}
	}
	
	/**
	 * rotate the figure
	 */
	public void rotar() {
		int[][] figuraR = transposeM(coordenadas);
		reverseRows(figuraR);
		//condicion bordes
		if((x + figuraR[0].length > Tablero.W_TABLERO) ||(y+figuraR.length>20) ) {
			return;
		}
		
		//condicion choque otras figuras
		for(int row = 0; row < figuraR.length; row++ ) {
			for(int col = 0 ; col <figuraR[row].length;col++) {
				if(tablero.getTablero()[y + row][x+col] != null) {
					return;
				}		
			}
		}
		coordenadas = figuraR;
	}
	
	/**
	 * transpose the figure
	 * @param matrix
	 * @return
	 */
	private int[][] transposeM(int[][] matrix) {
		int[][] temp = new int[matrix[0].length][matrix.length];
		for(int row = 0; row < matrix.length; row++) {
			for (int col = 0; col< matrix[0].length;col++ ) {
				temp[col][row] = matrix[row][col];
			}
		}
		return temp;
	}
	
	/**
	 * reverse the rows  for matrix
	 * @param matrix
	 */
	public void reverseRows(int[][] matrix) {
		int middle = matrix.length / 2;
		for (int row = 0; row < middle; row++) {
			int[] temp = matrix[row];
			matrix[row]= matrix[matrix.length - row - 1];
			matrix[matrix.length - row -1] = temp;
		}
		
	}
	
	/**
	 * draw the figure
	 * @param g
	 */
	public void paint(Graphics g) {
		//dibuja forma
				for(int row = 0 ; row < coordenadas.length; row++) {
					for(int col = 0 ; col < coordenadas[0].length;col++) {
						if (coordenadas[row][col] != 0) {
							g.setColor(color);
							g.fillRect((col * T_CUADRO + x * T_CUADRO)+10, (row * T_CUADRO + y * T_CUADRO)+10,T_CUADRO,T_CUADRO);
							g.setColor(borde);
							g.drawRect((col * T_CUADRO + x * T_CUADRO)+11, (row * T_CUADRO + y * T_CUADRO)+11,T_CUADRO,T_CUADRO);	
													}
					}
				}
	}
	
	/**
	 * return the coordinates of the figure 
	 * @return
	 */
	public int[][] getCoords(){
		return coordenadas;
	}
	
	/**
	 * return x coordinates
	 * @return
	 */
	public int getX() {
		return x;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Color getBorde() {
		return borde;
	}
	/**
	 * return y coordinates
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * speed up the figure
	 */
	public void acelerar() {
		velocidad = rapido;
	}
	
	/**
	 * change the speed
	 * @param v
	 */
	public void setVelocidad (int v) {
		velocidad = v;
	}
	
	/**
	 * slow down the figure
	 */
	public void frenar() {
		velocidad = normal;
	}
	
	/**
	 *  move the figure to the right
	 */
	public void mDer() {
		dX = 1;
	}
	
	/**
	 * move the figure to the left 
	 */
	public void mIzq() {
		dX = -1;
	}
}
