package aplicacion.onePlayer;

import java.awt.Color;

public class FBomb extends Figura{
	/**
	 * create of figure
	 * @param coords
	 * @param tablero
	 * @param color
	 */
	public FBomb(int[][] coords, Tablero tablero, Color color, int speed) {
		super(coords, tablero, color, speed);
		this.borde = Color.RED;
	}
	
	/**
	 * figure movement and outbreak
	 */
	@Override
	public void update() {
		// condicion y
				if (colision) {
					for (int row = 0; row < coordenadas.length; row++) {
						for (int col = 0; col < coordenadas[0].length;col++) {
							if(coordenadas[row][col] != 0) {
								tablero.getTablero()[y ][x ] = null;
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
									tablero.getTablero()[y+row][x+dX+col] = null;
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
										
										tablero.getTablero()[y+row+1][x+dX+col] = null;
										
									}
								}
							}
						}
						if(!colision) {
							y++;
						}
					}else {
						colision = true;
					}
					beginTime = System.currentTimeMillis();}}
	}
	
	

