package etu.univlille.fr.projetmodei3.objects;
/**
 * 
 * @author Guilhane Bourgoin
 * enum servant pour trier les faces
 */
public enum EnumTri {
	
	NOM("Nom"),FACE("Face"),POINTS("Points");
	
	/**
	 * Ceci est le nom
	 */
	String nom;

	/**
	 * constructeur 
	 * @param nom utilisé pour le tri
	 */
	EnumTri(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return this.nom;
	}
}
