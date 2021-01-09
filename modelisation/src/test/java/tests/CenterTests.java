package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
/**
 * classe de test des methodes de centre
 * @author grp I3
 *
 */
class CenterTests {

	@Test
	void testFaceCenterSquare() {
		Face face = new Face();
		Point ul = new Point(10, 30, 0);
		Point ur = new Point(30, 30, 0);
		Point dl = new Point(10, 10, 0);
		Point dr = new Point(30, 10, 0);
		face.addPoints(ul, ur, dl, dr);
		assertEquals(20, face.getCenter().getX(),"face.getCenter()");
		assertEquals(20, face.getCenter().getY(),"face.getCenter()");
		assertEquals(00, face.getCenter().getZ(),"face.getCenter()");
	}
	
	@Test
	void testFaceCenterCube() {
		Model3D model = new Model3D();
		Face faceavant = new Face();
		Point ul = new Point(10, 30, -10);
		Point ur = new Point(30, 30, -10);
		Point dl = new Point(10, 10, -10);
		Point dr = new Point(30, 10, -10);
		faceavant.addPoints(ul, ur, dl, dr);
		Face facearriere = new Face();
		ul = new Point(10, 30, -30);
		ur = new Point(30, 30, -30);
		dl = new Point(10, 10, -30);
		dr = new Point(30, 10, -30);
		facearriere.addPoints(ul, ur, dl, dr);
		model.addFaces(faceavant, facearriere);
		assertEquals(20, model.getCenter().getX(),"model.getCenter");
		assertEquals(20, model.getCenter().getY(),"model.getCenter");
		assertEquals(-20, model.getCenter().getZ(),"model.getCenter");
	}

}
