package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import etu.univlille.fr.projetmodei3.objects.Affichage;
import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;

public class transformMethodsTests {

	
	//Pour le moment on se contentera simplement d'un rep�re orthonorm� basique
	//Si il faut projeter les coordonn�es sur d'autre rep�re il faudra travailler dessus
	
	//Pour le moment on utilisera simplement une petite pyramide � base carr�
	// 5 points, 5 faces 
	
	Point p1,p2,p3,p4,p5;
	Face f1,f2,f3,f4,f5;
	Model3D obj;
	
	@BeforeEach
	public void setup() {
		//Sommets de la base carr� 
		p1 = new Point(-1, 0, -1);
		p2 = new Point( 1, 0, -1);
		p3 = new Point( 1, 0,  1);
		p4 = new Point(-1, 0, -1);
		//Sommet de la pointe
		p5 = new Point( 0, 2,  0);
		
		//Base carr�
		f1 = new Face(new Point[] {p1,p2,p3,p4});
		//Autres face
		f2 = new Face(new Point[] {p1,p2,p5});
		f3 = new Face(new Point[] {p2,p3,p5});
		f4 = new Face(new Point[] {p3,p4,p5});
		f5 = new Face(new Point[] {p4,p1,p5});
		
		obj = new Model3D(new Face[] {f1,f2,f3,f4,f5});
	}
	
	@Test
	public void translateTest() {
		obj.translate(5, 3, 1);
		assertEquals(4, p1.getX());
		assertEquals(4, f1.getPoints().get(0).getX());
		
		assertEquals(3, p2.getY());
		assertEquals(3, p1.getY());
		assertEquals(3, p3.getY());
		assertEquals(0, p4.getY());
		assertNotEquals(3, p5.getY());		
		
	}
	
}
