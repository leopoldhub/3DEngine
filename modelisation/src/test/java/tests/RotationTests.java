package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
import etu.univlille.fr.projetmodei3.utils.MathsUtils;


public class RotationTests {
	
	private Point p1,p2,p3,p4,p5;
	private Face f1,f2,f3,f4,f5;
	private Model3D obj;
	
	@BeforeEach
	public void setup() {
		
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
	public void rotationTest() { //bien an degre pas en rad
		
		//Test rotation de 90° sur l'axe X
		obj.rotateX(p1,MathsUtils.degreeToRad(90));
		
		assertEquals(obj.getPoints().get(0).getX(),-1);
		assertEquals(obj.getPoints().get(0).getY(),1);
		assertEquals(obj.getPoints().get(0).getZ(),0);
		
		//Test rotation de 90° sur l'axe Y
		obj.rotateY(p1,MathsUtils.degreeToRad(90));
		
		assertEquals(obj.getPoints().get(0).getX(),-1);
		assertEquals(obj.getPoints().get(0).getY(),0);
		assertEquals(obj.getPoints().get(0).getZ(),1);
		
		//Test rotation de 90° sur l'axe Z
		obj.rotateZ(p1,MathsUtils.degreeToRad(90));
		
		assertEquals(obj.getPoints().get(0).getX(),0);
		assertEquals(obj.getPoints().get(0).getY(),-1);
		assertEquals(obj.getPoints().get(0).getZ(),-1);


	}
}
