package aplicacion.onePlayer;

import java.awt.Color;

public class FWinner extends Figura {
	/**
	 * create of figure
	 * @param coords
	 * @param tablero
	 * @param color
	 */
	public FWinner(int[][] coords, Tablero tablero, Color color, int speed) {
		super(coords, tablero, color, speed);
		this.borde = Color.YELLOW;
	}

}
