package etu.univlille.fr.projetmodei3.objects;


/**
 * La classe Point, elle contient toutes les informations nécéssaires afin de représenter un point dans un espace à 3 dimensions,
 * cela consiste en simplement 3 coordonnées X,Y et Z
 * La classe Point sert aussi à représenter des vecteurs
 * 
 * @author Leopold HUBERT, Maxime BOUTRY, Guilhane BOURGOING, Luca FAUBOURG
 *
 */
public class Point {

	/**
	 * La coordonnées sur l'axe X, l'axe horizontal
	 */
	private double x;
	/**
	 * La coordonnées sur l'axe Y, l'axe Vertical
	 */
	private double y;
	/**
	 * La coordonnées sur l'axe Z, l'axe représentant la profondeur, principalement utilisé dans l'algorithme du peintre
	 * servant à afficher le modèle
	 */
	private double z;
	
	/**
	 * Constructeur de base d'une instance de point : toutes ses coordonnées seront mises à 0
	 */
	public Point() {
		this(0, 0, 0);
	}
	
	/**
	 * Constructeur pour une instance de Point enrichi par les coordonnées x,y,z
	 * Principalement utilisé lors de l'utilisation du Parser
	 * @param x la position sur l'axe horizontal
	 * @param y la position sur l'axe vertical
	 * @param z la position sur l'axe de profondeur
	 */
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = true;
		if (this == obj)
			result=  true;
		if (obj == null)
			result= false;
		if (getClass() != obj.getClass())
			result= false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			result= false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			result= false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			result= false;
		return result;
	}

	
	/**
	 * Getter de la coordonnée X
	 * @return valeur de la coordonée X
	 */
	public double getX() {
		return x;
	}

	/**
	 * Setter de la coordonée X
	 * @param x Nouvelle valeur de la coordonée X
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Getter de la coordonnée Y
	 * @return valeur de la coordonée Y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setter de la coordonée Y
	 * @param y Nouvelle valeur de la coordonée Y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Getter de la coordonnée Z
	 * @return valeur de la coordonée Z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Setter de la coordonée Z
	 * @param z Nouvelle valeur de la coordonée Z
	 */
	public void setZ(double z) {
		this.z = z;
	}

	
	/**
	 * Surchage de toString(), qui affiche les 3 coordonées de l'instance
	 */
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
}
