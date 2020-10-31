package etu.univlille.fr.projetmodei3.interfaces;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
import javafx.fxml.FXML;
import javafx.scene.shape.Polygon;

public class ControllerDesImpots {
	@FXML 
	javafx.scene.layout.AnchorPane AnchorPane;
	
	private Model3D modele;
	
	

	
	public void pressButton(){
		AnchorPane.getChildren().clear();
		Polygon forme;
		for(Face f : modele.getFaces()) {
			System.out.println("face f "+f);
			forme = new Polygon();
			for(Point p : f.getPoints()) {
				forme.getPoints().add(p.getX());
				forme.getPoints().add(p.getY());
			}
			forme.setStroke(javafx.scene.paint.Color.BLACK);
			forme.setFill(javafx.scene.paint.Color.RED);
			AnchorPane.getChildren().add(forme);
		}
		//System.out.println("Nombre face : "+this.getChildren().size());
	
		
	}
}
