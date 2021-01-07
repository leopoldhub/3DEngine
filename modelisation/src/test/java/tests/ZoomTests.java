package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
import etu.univlille.fr.projetmodei3.utils.Matrix;

public class ZoomTests {
	
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
	public void zoomTests() {
		obj.zoom(5);
		double xObj,yObj,zObj;
		for(int i = 0; i<obj.getPoints().size();i++) {
			xObj = obj.getPoints().get(i).getX();
			yObj = obj.getPoints().get(i).getY();
			zObj = obj.getPoints().get(i).getZ();
		}
		assertEquals(obj.getPoints().get(4).getX(),-5);
		assertEquals(obj.getPoints().get(4).getY(), 1);
		assertEquals(obj.getPoints().get(4).getZ(), -5);
		
	}
	
	private Matrix zoom(int valeur) {
		Matrix zoom = new Matrix(new double[][] {{valeur,0,0,0},
			  {0,valeur,0,0},
			  {0,0,valeur,0},
			  {0,0,0,1}});
		return zoom;
	}
}
