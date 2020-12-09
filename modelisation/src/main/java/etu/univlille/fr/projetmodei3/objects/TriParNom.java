package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.util.Collections;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.Tri;

public class TriParNom implements Tri{

	@Override
	public void trier(List<File> files) {
		Collections.sort(files,this);
	}

	@Override
	public int compare(File o1, File o2) {
		return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
	}
	public String toString() {
		return "nom";
	}
}
