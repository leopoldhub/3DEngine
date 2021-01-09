package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
import etu.univlille.fr.projetmodei3.utils.MathsUtils;

/**
 * classe de test de la rotation
 * @author grp I3
 *
 */
public class RotationTests {
	/**
	 * les points du modele
	 */
	private Point p1,p2,p3,p4,p5;
	/**
	 * la face du modele
	 */
	private Face f1,f2,f3,f4,f5;
	/**
	 * le modele
	 */
	private Model3D obj;
	
	@BeforeEach
	private void setup() {
		
		p1 = new Point(-1, 0, -1);
		p2 = new Point( 1, 0, -1);
		p3 = new Point( 1, 0,  1);
		p4 = new Point(-1, 0, -1);
		p5 = new Point( 0, 2,  0);
		
		f1 = new Face(new Point[] {p1,p2,p3,p4});
		f2 = new Face(new Point[] {p1,p2,p5});
		f3 = new Face(new Point[] {p2,p3,p5});
		f4 = new Face(new Point[] {p3,p4,p5});
		f5 = new Face(new Point[] {p4,p1,p5});
		
		obj = new Model3D(new Face[] {f1,f2,f3,f4,f5});
	}
	
	@Test
	void rotationTest() { //bien an degre pas en rad
		
		//Test rotation de 90° sur l'axe X
		obj.rotate(90, 0, 0);
		
		assertEquals(obj.getPoints().get(0).getX(),-1,"obj.getPoints().get(0).getX() 90°X");
		assertEquals(MathsUtils.round(obj.getPoints().get(0).getY(),0),1,"obj.getPoints().get(0).getY() 90°X");
		assertEquals(MathsUtils.round(obj.getPoints().get(0).getZ(),0),0,"obj.getPoints().get(0).getZ() 90°X");
		
		//Test rotation de 90° sur l'axe Y
		obj.rotate(0, 90, 0);
		
		assertEquals(MathsUtils.round(obj.getPoints().get(0).getX(),0),1,"obj.getPoints().get(0).getX() 90°Y");
		assertEquals(MathsUtils.round(obj.getPoints().get(0).getY(),0),1,"obj.getPoints().get(0).getY() 90°Y");
		assertEquals(MathsUtils.round(obj.getPoints().get(0).getZ(),0),1,"obj.getPoints().get(0).getZ() 90°Y");
		
		//Test rotation de 90° sur l'axe Z
		obj.rotate(0, 0, 90);
		
		assertEquals(MathsUtils.round(obj.getPoints().get(0).getX(),0),-1,"obj.getPoints().get(0).getX() 90°Z");
		assertEquals(MathsUtils.round(obj.getPoints().get(0).getY(),0),0,"obj.getPoints().get(0).getY() 90°Z");
		assertEquals(MathsUtils.round(obj.getPoints().get(0).getZ(),0),1,"obj.getPoints().get(0).getZ() 90°Z");


	}
}
