package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
import etu.univlille.fr.projetmodei3.utils.MathsUtils;
/**
 * classe de test de la lumière
 * @author grp I3
 *
 */
public class TestLumiere {
	/**
	 * les points du modele
	 */
	private Point p1,p2,p3,p4,p5,v12,v34,v45;
	/**
	 * les faces du modele
	 */
	private Face f1,f2,f3,f4,f5;
	/**
	 * le modele
	 */
	private Model3D obj;
	
	@BeforeEach
	/**
	 * set up du modele 
	 */
	private void setup() {
		
		p1 = new Point(-1, 0, -1);
		p2 = new Point( 1, 0, -1); 
		p3 = new Point( 1, 0,  1);
		p4 = new Point(-1, 0, 1); 
		p5 = new Point( 0, 2,  0);
		
		v12 = MathsUtils.getVecteur(p1, p2); //2,0,0
		v34 = MathsUtils.getVecteur(p3, p4); //2,0,0
		v45 = MathsUtils.getVecteur(p4, p5); //1,2,-1
		
		
		f1 = new Face(new Point[] {p1,p2,p3,p4});
		f2 = new Face(new Point[] {p1,p2,p5});
		f3 = new Face(new Point[] {p2,p3,p5});
		f4 = new Face(new Point[] {p3,p4,p5});
		f5 = new Face(new Point[] {p4,p1,p5});
		
		
		
		obj = new Model3D(new Face[] {f1,f2,f3,f4,f5});
	}
	
	@Test
	
	void testProduitVectoriel() {
		assertEquals(MathsUtils.produitVectoriel(v12,v34),new Point(0,-0,0));
		assertEquals(MathsUtils.produitVectoriel(v34,v34),new Point(0,0,0));
		assertEquals(MathsUtils.produitVectoriel(v34,v45),new Point(0,2,4));
	}

	@Test
	void produitScalaire() {
		
	}
	
	@Test
	void getVecteurNormal() {
		
	}
	
	@Test
	void testNorme() {
		
	}
}
