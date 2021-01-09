package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.Tri;


/**
 * Une Classe permettant de choisir une méthode de Tri, suivant le patron de conception Strategy
 * @author Leopold HUBERT, Maxime BOUTRY, Guilhane BOURGOING, Luca FAUBOURG
 *
 */
public class Trieur{
	
	/**
	 * La méthode de tri courante
	 */
	private Tri methodeTri;

	/**
	 * Constructeur enrichi par une méthode de tri
	 * @param methodeTri methode de tri choisi
	 */
	public Trieur(Tri methodeTri) {
		this.methodeTri = methodeTri;
	}
	
	/**
	 * Constructeur de base de tri, Elle s'instancie avec une méthode de Tri de base
	 */
	public Trieur() {
		this(new TriParNom());
	}
	
	/**
	 * Setter permettant de changer la methode de tri courante avec une nouvelle methode de tri
	 * @param methodeTri Nouvelle methode de tri
	 */
	public void setMethodeTri(Tri methodeTri) {
		this.methodeTri = methodeTri;
	}
	/**
	 * Setter permettant de changer la methode de tri courante avec une nouvelle methode de tri
	 * Mais en spécifiant son nom
	 * @param methodeTri Nom de la nouvelle méthode de tri
	 */
	public void setMethodeTri(String nomMethode) {
		switch(nomMethode) {
		case "Nom":
			setMethodeTri(new TriParNom());
			break;
		
		case "Face":
			setMethodeTri(new TriParNombreFace());
			break;
		
		case "Points":
			setMethodeTri(new TriParNombrePoints());
			break;
		}
	
	
	}
	/**
	 * Affichage du nom de la méthode de tri
	 */
	public String toString() {
		return this.methodeTri.toString();
	}
	
	/**
	 * Utilisation du tri de la méthode sur une liste de fichier
	 * @param files Liste de fichier .ply à trier
	 */
	public void tri(List<File> files) {
		this.methodeTri.trier(files);
	}
	
	
	
}
