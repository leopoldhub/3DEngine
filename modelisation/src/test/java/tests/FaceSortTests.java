package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;

class FaceSortTests {

	@Test
	void testSortFaces() {
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
		List<Face> facesOrdered = model.getFaces();
		assertEquals(facearriere, facesOrdered.get(0));
		assertEquals(faceavant, facesOrdered.get(1));
	}

}
