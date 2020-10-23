package etu.univlille.fr.projetmodei3.objects;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;

public class ModelViewer extends AnchorPane{
	
	private Model3D modele;
	
	
	public ModelViewer(Model3D modele) {
		this.modele = modele;
	}
	
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
	
	public void rotate(double x, double y, double z) {
		this.modele.rotate(x,y,z);
	}
}
