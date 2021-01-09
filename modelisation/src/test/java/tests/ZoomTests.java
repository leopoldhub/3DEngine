package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
/**
 * classe de test du zoom
 * @author grp I3
 *
 */
public class ZoomTests {
	/**
	 * points du modele
	 */
	private Point p1,p2,p3,p4,p5;
	/**
	 * faces du modele
	 */
	private Face f1,f2,f3,f4,f5;
	/**
	 * modele 3D
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
	void zoomTests() {
		obj.zoom(5);

		assertEquals(obj.getPoints().get(4).getX(),-5,"(obj.getPoints().get(4).getX()");
		assertEquals(obj.getPoints().get(4).getY(), 1,"(obj.getPoints().get(4).getY()");
		assertEquals(obj.getPoints().get(4).getZ(), -5,"(obj.getPoints().get(4).getZ()");
		
	}
	

}
