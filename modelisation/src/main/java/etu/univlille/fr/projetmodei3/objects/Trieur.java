package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.Tri;

public class Trieur{
	
	private Tri methodeTri;
	private boolean croissant;
	
	public Trieur(Tri methodeTri) {
		this.methodeTri = methodeTri;
		this.croissant = true;
	}
	public Trieur() {
		this(new TriParNom());
	}
	public void setMethodeTri(Tri methodeTri) {
		this.methodeTri = methodeTri;
	}
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
	public String toString() {
		return this.methodeTri.toString();
	}
	
	public void tri(List<File> files) {
		this.methodeTri.trier(files);
	}
	
	
	
}
