package test;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

import javax.swing.text.AttributeSet.ColorAttribute;

import aplicacion.onePlayer.Buffo;
import aplicacion.onePlayer.Figura;
import aplicacion.onePlayer.Tablero;

public class POOBTrizTest {
	private Tablero t1;
	
	@Test
	public void shouldCreateOk() {
		t1= new Tablero(null,null,"1", 700);
		assertTrue(t1.ok());
		return;
	}
	
	@Test
	public void shouldMoveDerOk() {
		t1= new Tablero(null,null,"1", 700);
		Figura f = t1.getFActual();
		int px = f.getX();
		f.mDer();
		t1.update();
		int pxA = f.getX();
		assertEquals(px+1,pxA);
		return;
	}
	
	@Test
	public void shouldColorOk() {
		t1= new Tablero(null,null,"1", 700);
		Figura f = t1.getFActual();
		Color c = f.getColor();
		Figura F1 = new Figura(new int[][]{
            {1, 1, 1, 1} 
        }, t1, c, 0);
		assertEquals(F1.getColor(),c);
		return;
	}
	
	@Test
	public void shouldMoveDownOk() {
		t1= new Tablero(null,null,"1", 700);
		Figura f = t1.getFActual();
		int py = f.getY();
		t1.update();
		int pyA = f.getY();
		assertEquals(py+1,pyA);
		return;
	}
	
	@Test
	public void shouldMoveDer2TimesOk() {
		t1= new Tablero(null,null,"1", 700);
		Figura f = t1.getFActual();
		int px = f.getX();
		f.mDer();
		f.mDer();
		int pxA = f.getX();
		assertEquals(pxA,px);
		return;
	}
	
	@Test
	public void shouldMoveIzqOk() {
		t1= new Tablero(null,null,"1", 700);
		Figura f = t1.getFActual();
		int px = f.getX();
		f.mIzq();
		t1.update();
		int pxA = f.getX();
		if (px-1 >= 0) {
			assertEquals(pxA,px-1);
		}
		return;
	}
		
}
