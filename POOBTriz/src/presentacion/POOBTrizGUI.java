package presentacion;

import aplicacion.*;
import aplicacion.onePlayer.*;


import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Santiago Fetecua - Stefania Giraldo
 * Proyecto Final POOB
 */
public class POOBTrizGUI extends JFrame {
	
	public static final int WIDTH = 550, HEIGHT = 690;
   
    private Tablero tablero;
   
    private JPanel MenuP;
    private JMenu menu;
    private JMenuItem guardarComo;
    private JMenuItem abrir;
    private JButton onePlayer;
    private JButton twoPlayers;
    private JButton vsMachine;
    private JMenuBar menuPrincipal;
    
    protected int lento = 1500;
	protected int normal = 700;
	protected int rapido = 50;
    
    /**
     * call other methods of the class
     */
    public POOBTrizGUI(){
    	//Menu Principal
    	prepareElementos();
    	prepareElementosMenu();
    	prepareAcciones();
    }
    
    /**
     * prepare the game for single player
     */
    public void prepareJuego1J(){
    		//Juego
            String nombre = JOptionPane.showInputDialog(this,"Ingrese su nombre");
            if (nombre == null) {
            	System.exit(0);
            }
            String buffos = JOptionPane.showInputDialog(this, "Ingrese numero de buffos");
            if (buffos == null) {
            	System.exit(0);
            }
            String speed = JOptionPane.showInputDialog(this, "Ingrese Velocidad deseada \n N: Normal. R: Rapido. L: Lento");
            if (speed == null) {
            	System.exit(0);
            }
            switch (speed) {
            	case "N":
            		tablero = new Tablero(nombre, null, buffos, normal);
            		break;
            	case "n":
            		tablero = new Tablero(nombre, null, buffos, normal);
            		break;
            	case "R":
            		tablero = new Tablero(nombre, null, buffos, rapido);
            		break;
            	case "r":
            		tablero = new Tablero(nombre, null, buffos, rapido);
            		break;
            	case "L":
            		tablero = new Tablero(nombre, null, buffos, lento);
            		break;
            	case "l":
            		tablero = new Tablero(nombre, null, buffos, lento);
            		break;
            }
            add(tablero);
            this.getContentPane().removeAll();
            this.getContentPane().add(tablero);
        	tablero.setFocusable(true);
        	tablero.requestFocus();
        	setVisible(true);
    }
    
    /**
     * create  saved games
     * @param juego
     */
    public void abreJuego1J(Tablero juego) {
    	tablero = null;
    	tablero = juego;
    	//add(tablero);
        this.getContentPane().removeAll();
        this.getContentPane().add(tablero);
    	validate();
    	tablero.setFocusable(true);
    	tablero.requestFocus();
    	setVisible(true);	
    }
    
    public void prepareJuego2J() {
    	/**
    	 * String nombreJ1 = JOptionPane.showInputDialog(this,"Nombre Jugador 1");
    	 *
    	String nombreJ2 = JOptionPane.showInputDialog(this,"Nombre Jugador 2");
    	tablero = new Tablero(nombreJ1, nombreJ2);
    	tableroJ2 = new Tablero2J(nombreJ1, nombreJ2);
    	setSize(1000, HEIGHT);
    	//Tablero 1
    	add(tablero);
        this.getContentPane().removeAll();
        this.getContentPane().add(tablero);
    	revalidate();
    	tablero.setFocusable(true);
    	tablero.requestFocus();
    	setVisible(true);
    	//Tablero 2
    	add(tableroJ2);
        this.getContentPane().add(tableroJ2);
    	revalidate();
    	tableroJ2.setFocusable(true);
    	tableroJ2.requestFocus();
    	setVisible(true);
    	*/
    }
    
    /**
     * prepare the frame
     */
    public void prepareElementos() {
    	setTitle("POOBTriz");
    	setSize(WIDTH, HEIGHT);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    	setLocationRelativeTo(null);	
    }
    
    /**
     * prepare the elements of the different menus
     */
    public void prepareElementosMenu(){
    	menuPrincipal = new JMenuBar();
    	MenuP = new JPanel();
    	menu = new JMenu("Menu");
    	onePlayer = new JButton("1 Jugador");
    	twoPlayers = new JButton("2 Jugadores");
    	vsMachine = new JButton("Vs Maquina");
    	abrir = new JMenuItem("Abrir");
    	guardarComo = new JMenuItem("Guardar Como");
    	MenuP.setLayout(new GridLayout(3,1,5,5));
    	
    	onePlayer.setForeground(Color.YELLOW);
    	twoPlayers.setForeground(Color.GREEN);
    	vsMachine.setForeground(Color.BLUE);
    	onePlayer.setBackground(Color.BLACK);
    	twoPlayers.setBackground(Color.BLACK);
    	vsMachine.setBackground(Color.BLACK);
    	
    	menu.add(abrir);
    	menu.add(guardarComo);
    	MenuP.add(onePlayer);
    	MenuP.add(twoPlayers);
    	MenuP.add(vsMachine);
    	this.getContentPane().add(MenuP);
    	revalidate();
    	setVisible(true);
    	menuPrincipal.add(menu);
        setJMenuBar(menuPrincipal);
    }
   
    
    /**
     * 
	prepares the actions to be taken with respect to what the player chooses
     */
    public void prepareAcciones(){
    	onePlayer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				prepareJuego1J();
			}
    	});
    	
    	twoPlayers.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Message("Funcionalidad en construccion");
				prepareJuego2J();
			}
    	});
    	
    	vsMachine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Message("Funcionalidad en construccion");
			}
    	});
    	abrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrir();
            }
        });

        guardarComo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                opcionguardar();
            }
        });
    }
    /**
     * takes care of opening saved games
     */
    private void abrir(){
    	try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("juego.dat"));
			Tablero d = (Tablero)in.readObject();
			//in.close();
			abreJuego1J(d);
			setVisible(true);
			//dispose();
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, TableroException.ARCHIVO);
		}catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, TableroException.NO_CLASE);
		}
    	
  }
    		
    /**
     *  saved games
     */
    private void opcionguardar() {
    	
    	
    	try {
    		tablero.guardar("juego.dat");
    	}catch(TableroException e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	}
    	
    	
        
    }
    /**
     * close the jframe
     */
    public void SalgaM(){
		dispose();
	}
    /**
     * 
send a message
     * @param mensaje
     */
    public void Message(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje);
    }
    /**
     * create new POOBTrizGUI
     * @param args
     */
    public static void main(String[] args) {
    	new POOBTrizGUI();
    }
}
