package etu.univlille.fr.projetmodei3;

import etu.univlille.fr.projetmodei3.objects.Affichage;
import etu.univlille.fr.projetmodei3.objects.Face;
import etu.univlille.fr.projetmodei3.objects.Model3D;
import etu.univlille.fr.projetmodei3.objects.Point;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Point p1, p2, p3, p4, p5;
		Face f1, f2, f3, f4, f5;
		Model3D obj;

		// Sommets de la base carré
		p1 = new Point(100, 200, 100);
		p2 = new Point(100, 200, 300);
		p3 = new Point(300, 200, 100);
		p4 = new Point(300, 200, 300);
		// Sommet de la pointe
		p5 = new Point(200, 400, 200);

		// Base carré
		f1 = new Face(new Point[] { p1, p2, p4, p3 });
		// Autres face

		f2 = new Face(new Point[] { p1, p2, p5 });
		f3 = new Face(new Point[] { p2, p4, p5 });
		f4 = new Face(new Point[] { p4, p3, p5 });
		f5 = new Face(new Point[] { p3, p1, p5 });

		obj = new Model3D(new Face[] { f1, f2, f3, f4, f5 });

		//FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichage.fxml"));
		
		Parent root =new Affichage();

		Scene scene = new Scene(root);
		primaryStage.setTitle("Accueil");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(e->{
			System.exit(0);
		});
		primaryStage.show();

	}

}
