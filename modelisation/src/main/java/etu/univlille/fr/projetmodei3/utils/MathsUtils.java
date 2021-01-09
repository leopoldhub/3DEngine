package etu.univlille.fr.projetmodei3.utils;

import java.util.List;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
/**
 * Classe de methodes mathématiques utiles
 * @author grp I3
 *
 */
public class MathsUtils {
	
	/**
	 * Un triangle traverse un plan forcément en 2 points
	 */
	private final static int NBPOINTSMIN = 2;
	/**
	 * trouve le centre d'un segment
	 * @param a debut du segment
	 * @param b fin du segment
	 * @return le milieu du segment
	 * 
	 */
	public static double getSegmentCenter(double a, double b) {
		return ((b-a)/2)+a;
	}
	/**
	 * arrondie 
	 * @param value la variable à arrondir
	 * @param places le nombre de chiffres après la virgule
	 * @return value arrondie à places chiffres après la virgule
	 */
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	
	/**
	 * Vecteur allant de p1 à p2
	 * @param p1 debut du vecteur
	 * @param p2 fin du vecteur
	 * @return un vecteur partant de p1 et allant à p2
	 */
	public static Point getVecteur(Point p1, Point p2) {
		return new Point(p2.getX()-p1.getX(), p2.getY()-p1.getY(), p2.getZ()-p1.getZ());
	}
	
		/**
	 * On part du principe que TOUTES les faces sont des triangles
	 * @param vecteur1 premier vecteur
	 * @param vecteur2 second vecteur 
	 * @return resultat du produit scalaire de vecteur1 et vecteur2
	 */
	public static Point produitVectoriel(Point vecteur1, Point vecteur2) {
		return new Point(vecteur1.getY() * vecteur2.getZ() - vecteur1.getZ() * vecteur2.getY(),
						-vecteur1.getX() * vecteur2.getZ() + vecteur1.getZ() * vecteur2.getX(),
						 vecteur1.getX() * vecteur2.getY() - vecteur1.getY() * vecteur2.getX()
				);
	}
	
	/**
	 * Obtenir la norme d'un vecteur  
	 * @param produitVectoriel le point du produit vectoriel
	 * @return la norme
	 */
	public static double getNorme(Point produitVectoriel) {
		return Math.sqrt(Math.pow(produitVectoriel.getX(),2) + Math.pow(produitVectoriel.getY(),2) + Math.pow(produitVectoriel.getZ(),2));
	}


	/**
	 * Obtenir le vecteur normal unitaire d'une face
	 * @param f face de laquelle, on veut le vecteur normal
	 * @return le vecteur normal à la face
	 */
	public static Point getVecteurNormal(Face f) {
		Point vecteur1  = getVecteur(f.getPoints().get(0), f.getPoints().get(1));
		Point vecteur2  = getVecteur(f.getPoints().get(0), f.getPoints().get(2));

		Point prodVectoriel = produitVectoriel(vecteur1, vecteur2);
		normalisation(prodVectoriel);
		//System.out.println("Produit vectoriel associé  : "+prodVectoriel);


		return prodVectoriel;
	}
	/**
	 * normalise un vecteur
	 * @param vecteur le vecteur à normaliser
	 */
	public static void normalisation(Point vecteur) {
		double norme = getNorme(vecteur);
		vecteur.setX(vecteur.getX()/norme);
		vecteur.setY(vecteur.getY()/norme);
		vecteur.setZ(vecteur.getZ()/norme);
	}
	/**
	 * le taux d'eclairage
	 * @param f la face à eclairer
	 * @param vecteurVersLumiere direction de la lumière
	 * @return le taux d'eclairage de la face
	 */
	public static double tauxEclairage(Face f, Point vecteurVersLumiere) {
		Point vecteurNorm = getVecteurNormal(f);
		double resultat;
		normalisation(vecteurVersLumiere);
		//System.out.println("Vecteur vers la source de lumière : " +vecteurVersLumiere);
		resultat = (vecteurNorm.getX() * vecteurVersLumiere.getX() + vecteurNorm.getY() * vecteurVersLumiere.getY() + vecteurNorm.getZ() * vecteurVersLumiere.getZ());
		if(resultat < 0) {
			resultat = 0;
		}
		return resultat;
	}
	/**
	 * 
	 * @param modele le modele 3D
	 * @param nbTranches le nombre de tranches voulues
	 * @return un tableau de double
	 */
	public static double[] getZtranches(Model3D modele, int nbTranches) {
		double[] zTranches = new double[nbTranches];
		List<Face> faces = modele.getFaces();
		double zMax = faces.get(faces.size()-1).getPoints().get(0).getZ(), zMin = faces.get(0).getPoints().get(0).getZ();

		
		for(int i = 1; i < 3; i++) {
			if(zMin > faces.get(0).getPoints().get(i).getZ()) zMin = faces.get(0).getPoints().get(i).getZ();
			if(zMax > faces.get(faces.size()-1).getPoints().get(i).getZ()) zMin = faces.get(faces.size()-1).getPoints().get(i).getZ();
		}
		
		zTranches[0] = zMin + (zMax - zMin)/nbTranches;
		for(int i = 1 ; i < zTranches.length ; i++) {
			zTranches[i] = zTranches[i-1] + (zMax - zMin)/nbTranches;
		}
		System.out.println("Valeur des z de chaque tranches : ");
		for(double d : zTranches) {
			System.out.println(d);
		}
		
		return zTranches;
	}
	/**
	 * trouve l'intersection de la face et des points
	 * @param f la face 
	 * @param z la profondeur de la tranche
	 * @return une liste de points d'intersections 
	 */
	public static Point[] getIntersection(Face f, double z) {

		Point[] intersections = new Point[2];
		List<Point> points = f.getPoints();
		int idx = 0;
		Point resultat = intersectionDroitePlan(points.get(0), points.get(1), z);
		if(resultat != null) {
			intersections[idx] =  resultat;
			idx++;
		}
		resultat = intersectionDroitePlan(points.get(0), points.get(2), z);
		if(resultat != null) {
			intersections[idx] =  resultat;
			idx++;
		}
		
		resultat = intersectionDroitePlan(points.get(1), points.get(2), z);
		if(resultat != null) {
			intersections[idx] =  resultat;
			idx++;
		}
		
		System.out.println("getIntersection : "+idx+ " points renvoyés");
		if(idx != NBPOINTSMIN)intersections = null;
		return intersections;
	}
	/**
	 * donne l'intersection entre une droite et le plan
	 * @param p1 premier point de la droite 
	 * @param p2 deuxieme point de la droite 
	 * @param z profondeur de la tranche
	 * @return le point d'intersection
	 */
	public static Point intersectionDroitePlan(Point p1, Point p2, double z) {
		Point res = null;
		if( p1.getZ() < z && p2.getZ() > z) {
			double taux = (z-p1.getZ())/(p2.getZ() - p1.getZ());
			res = new Point(p1.getX() + (p2.getX()-p1.getX())*taux, p1.getY()+ (p2.getY()-p1.getY())*taux,z);
		} else if (p1.getZ() > z && p2.getZ() < z){
			double taux = (z-p2.getZ())/(p1.getZ() - p2.getZ());
			res = new Point(p2.getX() + (p1.getX()-p2.getX())*taux, p2.getY()+ (p1.getY()-p2.getY())*taux,z);
		} 
		return res;
	
	}
	
	
	/**
	 * Convertis de degrés en radiants
	 * @param degree la valeur à convertir
	 * @return la valeur convertie
	 */
	public static double degreeToRad(double degree) {
		return degree * Math.PI / 180;
	}
	/**
	 * convertis de randiant en degrees
	 * @param rad la valeur à convertir
	 * @return la valeur convertie
	 */
	public static double radToDegree(double rad) {
		return rad * 180 / Math.PI;
	}

}
