package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.util.Collections;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.Tri;
/**
 * Classe faisant partie du Patron strategy, permettant le tri des fichiers par leur nombre de Faces
 * implémente l'interface Tri
 * @author Leopold HUBERT, Maxime BOUTRY, Guilhane BOURGOING, Luca FAUBOURG
 *
 */
public class TriParNombreFace implements Tri{

	/**
	 * Surcharge de la methode compare issue de l'interface comparator
	 */
	@Override
	public int compare(File o1, File o2) {
		return ParserUtils.parseNb(o1,"face")-ParserUtils.parseNb(o2,"face");
	}

	/**
	 * Methode de Tri d'une liste de modele .ply
	 * Issue de l'interface Tri
	 */
	@Override
	public void trier(List<File> files) {
		Collections.sort(files,this);
	}
	
	
	/**
	 * Affichage du type de tri, utilisé dans Affichage.java pour afficher la méthode de tri courante
	 */
	public String toString() {
		return "nb face";
	}
	
	
	
}
