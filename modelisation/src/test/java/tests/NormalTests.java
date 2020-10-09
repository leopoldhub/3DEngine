package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
import etu.univlille.fr.projetmodei3.utils.MathsUtils;

class NormalTests {

	@Test
	void testSortFaces() {
		Face face = new Face();
		Point ul = new Point(0, 0, 0);
		Point ur = new Point(0, 1, 0);
		Point dl = new Point(0, 1, 1);
		face.addPoints(ul, ur, dl);
		
		System.out.println(MathsUtils.getNormal(face.getPoints()));
	}

}
