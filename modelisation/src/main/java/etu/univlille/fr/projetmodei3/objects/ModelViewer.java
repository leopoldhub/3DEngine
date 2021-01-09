package etu.univlille.fr.projetmodei3.objects;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;

/**
 * 
 * @author grp I3
 * Vue du model3D
 */

public class ModelViewer extends AnchorPane{
	/**
	 * le modèle 3D
	 */
	private Model3D modele;
	
	/**
	 * constructor 
	 * @param modele le modele 3D
	 */
	public ModelViewer(Model3D modele) {
		this.modele = modele;
	}
	/**
	 * affiche le modele avec les bonnes couleurs
	 */
	public void affichage() {
		this.getChildren().clear();
		Polygon forme;
		for(Face f : this.modele.getFaces()) {
			System.out.println("face f "+f);
			forme = new Polygon();
			for(Point p : f.getPoints()) {
				forme.getPoints().add(p.getX());
				forme.getPoints().add(p.getY());
			}
			forme.setStroke(javafx.scene.paint.Color.BLACK);
			forme.setFill(javafx.scene.paint.Color.RED);
			this.getChildren().add(forme);
		}
		//System.out.println("Nombre face : "+this.getChildren().size());
	}
	/**
	 * zoom sur la figure
	 * @param valeur le degré de zoom
	 */
	public void zoom(double valeur) {
		this.modele.zoom(valeur);
	}
	/**
	 * rotation autour des 3 axes 
	 * @param x degres sur l'axe des X
	 * @param y degres sur l'axe des Y
	 * @param z degres sur l'axe des X
	 */
	public void rotate(double x, double y, double z) {
		this.modele.rotate(x,y,z);
	}
}
