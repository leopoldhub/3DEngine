package etu.univlille.fr.projetmodei3.interfaces;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringJoiner;

import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.FolderParserUtils;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.ParserUtils;
import etu.univlille.fr.projetmodei3.objects.Point;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Controler {
	@FXML 
	AnchorPane anchorPane;
	@FXML
	Menu listeModele;
	
	
	private Model3D modele;
	
	
	public void affichage(Model3D modele) {
		this.modele = modele;
		
		anchorPane.getChildren().clear();

		Polygon forme;
		anchorPane.setTranslateX(anchorPane.getWidth()/2);
		anchorPane.setTranslateY(anchorPane.getHeight()/2);
		
		for(Face f : this.modele.getFaces()) {
			forme = new Polygon();
			for(Point p : f.getPoints()) {
				forme.getPoints().add(p.getX());
				forme.getPoints().add(p.getY());
			}
			
			forme.setStroke(Color.BLACK);
			forme.setFill(Color.RED);
			anchorPane.getChildren().add(forme);
		}

	}	
	
	public void dragonButton() throws Exception{
		modele = ParserUtils.parse(new File(getClass().getResource("/cube.ply").toURI()));
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
		
	public void rotateUp() {
		this.modele.rotate(60.0/360.0,0,0);
		affichage(modele);
	}
	public void rotateDown() {
		this.modele.rotate(-60.0/360.0,0,0);
		affichage(modele);
	}
	public void rotateRight() {
		this.modele.rotate(0,60.0/360.0,0);
		affichage(modele);
	}
	public void rotateLeft() {
		this.modele.rotate(0,-60.0/360.0,0);
		affichage(modele);
	}
	public void zoom() {
		this.modele.zoom(1.2);
		affichage(modele);
	}
	public void dezoom() {
		this.modele.zoom(0.8);
		affichage(modele);
	}
	public void translateUp() {
		this.modele.translate(0, 1, 0);
		affichage(modele);
	}
	public void translateDown() {
		this.modele.translate(0, -1, 0);
		affichage(modele);	
	}
	public void translateRight() {
		this.modele.translate(1, 0, 0);	
		affichage(modele);
	}
	public void translateLeft() {
		this.modele.translate(-1, 0, 0);		
		affichage(modele);
	}
	public void reset() {
		Point centre = modele.getCenter();
		modele.translate(-centre.getX(),-centre.getY(),-centre.getZ());
		affichage(modele);
	}
	
	public void selectModel() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/src/main/resources/"));
	    File selectedDirectory = directoryChooser.showDialog(null);
	    
	    if(selectedDirectory != null) {
	  	  Stage selStage = new Stage();
	  	  selStage.setTitle("select your model");
	  	  VBox vb = new VBox();
	  	  for(Entry<File, List<String>> entry:FolderParserUtils.getCompatibleFiles(new File(System.getProperty("user.dir") + "/src/main/resources/")).entrySet()) {
	  		  Button btn = new Button(entry.getKey().getName());
	  		  StringJoiner sj = new StringJoiner("\n");
	  		  
	  		  
	  		  for(String line:entry.getValue()) {
	  			  sj.add(line);
	  		  }
	  		  btn.setTooltip(new Tooltip(sj.toString()));
	  		  btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						try {
							selStage.close();
							modele = ParserUtils.parse(entry.getKey());
							Point centre = modele.getCenter();
							modele.translate(-centre.getX(),-centre.getY(),-centre.getZ());
							autoResize(anchorPane.getWidth(), anchorPane.getHeight());
							affichage(modele);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	  		  vb.getChildren().add(btn);
	  	  }
	  	  selStage.setScene(new Scene(vb));
	  	  selStage.show();
	    }
	}
	
}

