package etu.univlille.fr.projetmodei3;

import java.io.File;

import etu.univlille.fr.projetmodei3.interfaces.ControllerDesImpots;
import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.ModelViewer;
import etu.univlille.fr.projetmodei3.objects.Parser;
import etu.univlille.fr.projetmodei3.objects.Point;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	Point p1,p2,p3,p4,p5;
		Face f1,f2,f3,f4,f5;
		Model3D obj;
		
		//Sommets de la base carré 
		p1 = new Point(100, 200, 100);
		p2 = new Point(100, 200, 300);
		p3 = new Point(300, 200, 100);
		p4 = new Point(300, 200, 300);
		//Sommet de la pointe
		p5 = new Point(200,400, 200);
					
		//Base carré
		f1 = new Face(new Point[] {p1,p2,p4,p3});
		//Autres face
		
		f2 = new Face(new Point[] {p1,p2,p5});
		f3 = new Face(new Point[] {p2,p4,p5});
		f4 = new Face(new Point[] {p4,p3,p5});
		f5 = new Face(new Point[] {p3,p1,p5});
		
		obj = new Model3D(new Face[] {f1,f2,f3,f4,f5});
		
		System.out.println("Debut de parse");
		//obj = Parser.parse(new File(System.getProperty("user.dir")+"/src/main/resources"+File.separator+"Crane.ply"));
		
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichage.fxml"));
          Parent root = loader.load();

          Scene scene = new Scene(root);
          primaryStage.setTitle("Accueil");
          primaryStage.setScene(scene);
          primaryStage.setResizable(false);
          primaryStage.show();
		
		/*
		ModelViewer vue = new ModelViewer(obj);
		vue.setHeight(450);
		vue.setWidth(900);
		System.out.println("debut de translate");
		vue.translate(200,200,200);
		System.out.println("Debut de zoom");
		vue.zoom(10);
		System.out.println("affichage");
		vue.affichage();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ev -> {
			//vue.rotate(10.0/360.0,10.0/360.0,10.0/360.0);
			//vue.zoom(1.01);
			//vue.translate(1,1,1);
			//System.out.println(object.getFaces());
			//vue.affichage();
		}));
		
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		*/
		//AnchorPane ap = new AnchorPane(vue);
		


	}

	
	
	
}
