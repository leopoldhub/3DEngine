package etu.univlille.fr.projetmodei3.objects;

public enum EnumTri {
	
	NOM("Nom"),FACE("Face"),POINTS("Points");
	
	
	String nom;

	
	private EnumTri(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
}
