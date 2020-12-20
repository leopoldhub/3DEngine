package etu.univlille.fr.projetmodei3.utils;

import java.util.List;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Point;
import etu.univlille.fr.projetmodei3.objects.Vector3D;

public class MathsUtils {

	public static Vector3D getNormal(List<Point> points) {
		if(points == null || points.size() < 3)return new Vector3D(0, 0, 0);
		Point a = points.get(0);
		Point b = points.get(1);
		Point c = points.get(2);
		Vector3D ab = getVectorFromPoints(a, b);
		Vector3D bc = getVectorFromPoints(b, c);
		
		double x = ab.getY()*bc.getZ()-ab.getZ()*bc.getY();
		double y = ab.getZ()*bc.getX()-ab.getX()*bc.getZ();
		double z = ab.getX()*bc.getY()-ab.getY()*bc.getX();
		
		Vector3D vn = new Vector3D(x, y, z);
		double norm = vn.getNorm();
		
		return new Vector3D(x/norm, y/norm, z/norm);
	}
	
	public static Vector3D getVectorFromPoints(Point a, Point b) {
		return new Vector3D(b.getX()-a.getX(),b.getY()-a.getY(),b.getZ()-a.getZ());
	}
	
	public static double getSegmentCenter(double a, double b) {
		return ((b-a)/2)+a;
	}
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	//Vecteur allant de p1 à p2
	public static Point getVecteur(Point p1, Point p2) {
		return new Point(p2.getX()-p1.getX(), p2.getY()-p1.getY(), p2.getZ()-p1.getZ());
	}
	
	//On part du principe que TOUTES les faces sont des triangles (eeeerrrrfffff)
	public static Point produitVectoriel(Point vecteur1, Point vecteur2) {
		return new Point(vecteur1.getY() * vecteur2.getZ() - vecteur1.getZ() * vecteur2.getY(),
						-vecteur1.getX() * vecteur2.getZ() + vecteur1.getZ() * vecteur2.getX(),
						 vecteur1.getX() * vecteur2.getY() - vecteur1.getY() * vecteur2.getX()
				);
	}
	//Obtenir la norme d'un vecteur 
	public static double getNorme(Point produitVectoriel) {
		return Math.sqrt(Math.pow(produitVectoriel.getX(),2) + Math.pow(produitVectoriel.getY(),2) + Math.pow(produitVectoriel.getZ(),2));
	}

	//Obtenir le vecteur normal unitaire d'une face
	public static Point getVecteurNormal(Face f) {
		System.out.println(" Centre de la face f : "+f.getCenter());
		Point vecteur1  = getVecteur(f.getPoints().get(0), f.getPoints().get(1));
		Point vecteur2  = getVecteur(f.getPoints().get(0), f.getPoints().get(2));

		Point prodVectoriel = produitVectoriel(vecteur1, vecteur2);
		normalisation(prodVectoriel);
		System.out.println("Produit vectoriel associé  : "+prodVectoriel);


		return prodVectoriel;
	}
	
	public static void normalisation(Point vecteur) {
		double norme = getNorme(vecteur);
		vecteur.setX(vecteur.getX()/norme);
		vecteur.setY(vecteur.getY()/norme);
		vecteur.setZ(vecteur.getZ()/norme);
	}
	
	public static double tauxEclairage(Face f, Point vecteurVersLumiere) {
		Point vecteurNorm = getVecteurNormal(f);
		normalisation(vecteurVersLumiere);
		System.out.println("Vecteur vers la source de lumière : " +vecteurVersLumiere);
		double resultat = (vecteurNorm.getX() * vecteurVersLumiere.getX() + vecteurNorm.getY() * vecteurVersLumiere.getY() + vecteurNorm.getZ() * vecteurVersLumiere.getZ());
		if(resultat > 0) {
			return resultat;
		} else {
			return 0;
		}
	}
	
	
	
	public static double degreeToRad(double degree) {
		return degree * Math.PI / 180;
	}
	
	public static double radToDegree(double rad) {
		return rad * 180 / Math.PI;
	}

}
