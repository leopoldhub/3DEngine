package etu.univlille.fr.projetmodei3.interfaces;

import java.io.File;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.FolderParser;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Parser;
import etu.univlille.fr.projetmodei3.objects.Point;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ControllerDesImpots {
	@FXML 
	AnchorPane anchorPane;
	@FXML
	Menu listeModele;
	
	
	private Model3D modele;
	
	
	public void affichage(Model3D modele) {
		anchorPane.getChildren().clear();
		Polygon forme;
		anchorPane.setTranslateX(anchorPane.getWidth()/2);
		anchorPane.setTranslateY(anchorPane.getHeight()/2);
		for(Face f : modele.getFaces()) {
			forme = new Polygon();
			for(Point p : f.getPoints()) {
				System.out.println(p);
				forme.getPoints().add(p.getX());
				forme.getPoints().add(p.getY());
			}
			forme.setStroke(Color.BLACK);
			forme.setFill(Color.RED);
			anchorPane.getChildren().add(forme);
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
		modele = Parser.parse(new File(getClass().getResource("/cube.ply").toURI()));
		autoResize(anchorPane.getWidth(), anchorPane.getHeight());
		affichage(modele);
	}
	
	public void autoResize(double width, double height) {
		System.out.println("center: "+modele.getCenter());
		double mw = 0;
		double mh = 0;
		Point mcent = modele.getCenter();
		
		for(Point pt:modele.getPoints()) {
			if(Math.abs(pt.getX())-Math.abs(mcent.getX()) > mw)mw = Math.abs(pt.getX())-Math.abs(mcent.getX());
			if(Math.abs(pt.getY())-Math.abs(mcent.getY()) > mh)mh = Math.abs(pt.getY())-Math.abs(mcent.getY());
		}
		
		mw = (width/2)/mw/2;
		mh = (height/2)/mh/2;
		
		modele.zoom(mw < mh?mw:mh);
	}	
		
}

