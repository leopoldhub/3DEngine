package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.util.Collections;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.Tri;

/**
 * Classe faisant partie du Patron strategy, permettant le tri des fichiers par leur nom
 * implémente l'interface Tri
 * @author Leopold HUBERT, Maxime BOUTRY, Guilhane BOURGOING, Luca FAUBOURG
 *
 */
public class TriParNom implements Tri{

	/**
	 * Surcharge de la methode compare issue de l'interface comparator
	 */
	@Override
	public void trier(List<File> files) {
		Collections.sort(files,this);
	}

	
	/**
	 * Methode de Tri d'une liste de modele .ply
	 * Issue de l'interface Tri
	 */
	@Override
	public int compare(File o1, File o2) {
		return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
	}
	
	/**
	 * Affichage du type de tri, utilisé dans Affichage.java pour afficher la méthode de tri courante
	 */
	public String toString() {
		return "nom";
	}
}
