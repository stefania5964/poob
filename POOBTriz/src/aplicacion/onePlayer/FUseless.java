package aplicacion.onePlayer;

import java.awt.Color;

public class FUseless extends Figura{
	/**
	 * create of figure
	 * @param coords
	 * @param tablero
	 * @param color
	 */
	public FUseless(int[][] coords, Tablero tablero, Color color, int speed) {
		super(coords, tablero, color, speed);
		this.borde = Color.WHITE;
	}
	
	/**
	 * at the time of collision it does not verify if I complete a row
	 */
	@Override
	protected void checkLine() {
	}
	
}
