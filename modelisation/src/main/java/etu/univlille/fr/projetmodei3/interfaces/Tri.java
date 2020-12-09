package etu.univlille.fr.projetmodei3.interfaces;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public interface Tri extends Comparator<File>{
	
	public void trier(List<File> files);
}
