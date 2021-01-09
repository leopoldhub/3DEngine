package etu.univlille.fr.projetmodei3.interfaces;

import java.io.File;
import java.util.Comparator;
import java.util.List;

/**
 * Interface tri indiquant que la classe sert à trier, toutes les classes implémentants cette interface suivent la nomenclature
 * suivante Tri<Valeur de tri> et implemente une methode servant à trier une liste de fichier .ply
 * Pour ce faire on s'aide de l'interface Comparator<>
 * 
 * @author Leopold HUBERT, Maxime BOUTRY, Guilhane BOURGOING, Luca FAUBOURG
 *
 */
public interface Tri extends Comparator<File>{
	
	public void trier(List<File> files);
}
