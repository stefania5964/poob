package aplicacion.onePlayer;

public class TableroException extends Exception {
	
	public static final String NO_CLASE = "No existe clase compatible";
	public static final String ARCHIVO= "No se puede abrir el archivo";
	public static final String INCOMPATIBLE= "El archivo es incompatible";
	    
	public TableroException(String mensaje) {
		super(mensaje);
	}
	
}
