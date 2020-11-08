package etu.univlille.fr.projetmodei3.interfaces;

import java.io.File;
import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.FolderParser;
import etu.univlille.fr.projetmodei3.objects.Parser;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Polygon;

public class ControllerDesImpots {
	@FXML 
	javafx.scene.layout.AnchorPane AnchorPane;
	@FXML
	Menu listeModele;
	
	
	private Model3D modele;
	
	
	public void affichage(Model3D modele) {
		//AnchorPane.getChildren().clear();
		Polygon forme;
		AnchorPane.setTranslateX(AnchorPane.getWidth()/2);
		AnchorPane.setTranslateY(AnchorPane.getHeight()/2);
		modele.zoom(4);
		for(Face f : modele.getFaces()) {
			forme = new Polygon();
			for(Point p : f.getPoints()) {
				System.out.println(p);
				forme.getPoints().add(p.getX());
				forme.getPoints().add(p.getY());
			}
			forme.setStroke(javafx.scene.paint.Color.BLACK);
			forme.setFill(javafx.scene.paint.Color.RED);
			AnchorPane.getChildren().add(forme);
		}
		actuListeModele();
	}


	public void actuListeModele() {
		//System.out.println(listeModele);
		listeModele.getItems().clear();
		MenuItem ajout;
		for(File f : FolderParser.getCompatibleFiles(new File(System.getProperty("user.dir") + "/src/main/resources/")).keySet()) {
			ajout = new MenuItem(f.getName());
			ajout.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					System.out.println("appui sur le bouton");
					try {
						affichage(Parser.parse(new File(System.getProperty("user.dir") + "/src/main/resources/"+f.getName())));
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				
			});
			System.out.println("ajout de "+f.getName());
			listeModele.getItems().add(ajout);
		}
		System.out.println("Actu cool cool");
	}
	
	
	
	public void dragonButton() throws Exception{
		modele = Parser.parse(new File(System.getProperty("user.dir") + "/src/main/resources/Dragon 2.5_ply.ply"));
		affichage(modele);
		
	}	
		//System.out.println("Nombre face : "+this.getChildren().size());
	
		
}

