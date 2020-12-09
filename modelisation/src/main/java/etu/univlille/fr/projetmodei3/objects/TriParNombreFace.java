package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.util.Collections;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.Tri;

public class TriParNombreFace implements Tri{

	@Override
	public int compare(File o1, File o2) {
		return Parser.parseNb(o1,"face")-Parser.parseNb(o2,"face");
	}

	@Override
	public void trier(List<File> files) {
		Collections.sort(files,this);
	}
	
	
	public String toString() {
		return "nb face";
	}
	
	
	
}
