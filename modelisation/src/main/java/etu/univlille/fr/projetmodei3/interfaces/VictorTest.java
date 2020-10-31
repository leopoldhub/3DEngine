package etu.univlille.fr.projetmodei3.interfaces;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class VictorTest extends Application {  
	   @Override     
	   public void start(Stage primaryStage) throws Exception { 
           FXMLLoader loader = new FXMLLoader(getClass().getResource("affichage.fxml"));
           Parent root = loader.load();

           Scene scene = new Scene(root);
           primaryStage.setTitle("Accueil");
           primaryStage.setScene(scene);
           primaryStage.setResizable(false);
           primaryStage.show();
	   }         
	   public static void main(String args[]){           
	      launch(args);      
	   } 
	} 
