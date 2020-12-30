package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import etu.univlille.fr.projetmodei3.utils.MathsUtils;

import java.util.Map.Entry;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;



public class Affichage extends VBox{
	
	MenuBar menu;
	Model3D modele;
	Canvas vue;
	AnchorPane commande;
	HBox vueCommande;
	
	Point posLumiere = new Point(500,0,0);
	private double sensibilite = 60.0/360.0;
	boolean voirFace = true, voirArrete = true;
	Trieur tri;
	private boolean isRotation = false;
	
	
	public Affichage() {
		this.menu = new MenuBar();
		this.modele = new Model3D();
		this.vue = new Canvas();
		this.vueCommande = new HBox();
		this.commande = new AnchorPane();
		tri = new Trieur();
		parametrageTailles();
		parametrageMenu();
		parametrageCommande();
		posLumiere.setX(posLumiere.getX()+ vue.getWidth()/2);
		posLumiere.setY(posLumiere.getY()+ vue.getHeight()/2);
		
	}
	
	private void parametrageTailles() {
		this.setPrefWidth(1124);
		this.setPrefHeight(775);
		this.getChildren().add(menu);
		this.vueCommande.getChildren().add(vue);
		this.vueCommande.getChildren().add(commande);
		this.vueCommande.setPrefWidth(1124);
		this.vueCommande.setPrefHeight(720);
		this.getChildren().add(vueCommande);
		this.vue.setWidth(984);
		this.vue.setHeight(720);
		this.commande.setPrefWidth(140);
		this.commande.setPrefHeight(720);
	}
	
	
	private void parametrageMenu() {
		this.menu.getMenus().add(new Menu("fichier"));
		MenuItem fichier = new MenuItem("fichier");
		
		fichier.addEventHandler(ActionEvent.ACTION,e->{
			selectModel();
		});
		this.menu.getMenus().get(0).getItems().add(fichier);
		this.menu.getMenus().get(0).getItems().get(0);
		
		MenuItem nouvelleVue = new MenuItem("Ouvrir dans une nouvelle fenetre");
		nouvelleVue.addEventHandler(ActionEvent.ACTION,e->{
			Affichage vue = new Affichage();
			Stage fenetre = new Stage();
			Scene truc = new Scene(vue);
			fenetre.setScene(truc);
			fenetre.show();
		});
		this.menu.getMenus().get(0).getItems().add(nouvelleVue);

	}
	
	private void parametrageCommande() {
		
		GridPane boutons = new GridPane();
		
		Button affichageFace = new Button("Cacher les faces");
		affichageFace.addEventHandler(ActionEvent.ACTION, e->{
			if(voirFace) {
				voirFace = false;
				affichageFace.setText("Voir les faces");
			} else {
				voirFace = true;
				affichageFace.setText("Cacher les faces");
			}
			affichage(/*modele*/);
		});
		affichageFace.setTranslateY(300);
		affichageFace.setPrefWidth(130);
		affichageFace.setPrefHeight(50);

		this.commande.getChildren().add(affichageFace);
		
		Button affichageArrete = new Button("Ne pas voir les arretes");
		affichageArrete.addEventHandler(ActionEvent.ACTION, e->{
			if(voirArrete) {
				voirArrete = false;
				affichageArrete.setText("Voir les Arretes");
			} else {
				voirArrete = true;
				affichageArrete.setText("Cacher les Arretes");
			}
			affichage(/*modele*/);
		});
		affichageArrete.setTranslateY(400);
		affichageArrete.setPrefWidth(130);
		affichageArrete.setPrefHeight(50);

		this.commande.getChildren().add(affichageArrete);

		Button tranches = new Button("Activer les tranches");

		tranches.setPrefWidth(130);
		tranches.setPrefHeight(50);
		tranches.setTranslateY(500);
		
		TextField nbTranches = new TextField();
		nbTranches.setTranslateY(550);

		this.commande.getChildren().add(tranches);
		this.commande.getChildren().add(nbTranches);
		
		Button option = new Button("↖ hg");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(-sensibilite*60, -sensibilite*60, 0);
			else
				modele.rotate(sensibilite,-sensibilite,-sensibilite);
			//affichage(/*modele*/);
		});
		boutons.add(option,0,0);
		
		option = new Button("↑ |");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(0, -sensibilite*60, 0);
			else
				modele.rotate(sensibilite,0,0);
			//affichage(/*modele*/);
		});
		boutons.add(option,1,0);
		
		option = new Button("↗ hd");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(sensibilite*60, -sensibilite*60, 0);
			else
				modele.rotate(sensibilite,sensibilite,sensibilite);
			//affichage(/*modele*/);
		});
		boutons.add(option,2,0);
		
		option = new Button("← -");
		option.addEventHandler(ActionEvent.ACTION,e->{
			//modele.rotate(0,-sensibilite,0);
			if(!isRotation)
				modele.translate(-sensibilite*60, 0, 0);
			else
				modele.rotate(0,-sensibilite,0);
			//affichage(/*modele*/);
		});
		boutons.add(option,0,1);
		
		
		Button middleButton = new Button("⤨ ");
		
		
		middleButton.addEventHandler(ActionEvent.ACTION,e->{
			isRotation = ! isRotation;
			if(isRotation)
				middleButton.setText("⟳ ");
			else
				middleButton.setText("⤨ ");
			modele.rotate(0,0,0);
			//affichage(/*modele*/);
		});
		boutons.add(middleButton,1,1);
		
		option = new Button("→ -");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(sensibilite*60,0 ,0);
			else
				modele.rotate(0,sensibilite,0);
			//affichage(/*modele*/);
		});
		boutons.add(option,2,1);
		
		option = new Button("↙ bg");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(-sensibilite*60, sensibilite*60, 0);
			else
				modele.rotate(-sensibilite,-sensibilite,-sensibilite);
			//affichage(/*modele*/);
		});
		boutons.add(option,0,2);
		
		option = new Button("↓ |");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(0, sensibilite*60, 0);
			else
				modele.rotate(-sensibilite,0,0);
			//affichage(/*modele*/);
		});
		boutons.add(option,1,2);
		
		option = new Button("↘ bd");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation )
				modele.translate(sensibilite*60, sensibilite*60, 0);
			else
				modele.rotate(-sensibilite,sensibilite,sensibilite);
			//affichage(/*modele*/);
		});
		boutons.add(option,2,2);
		
		commande.getChildren().add(boutons);
	}
	
	//PENSER A RAJOUTER UN BOUTON CROISSANT DECROISSANT
	public void selectModel() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/src/main/resources/"));
	    File selectedDirectory = directoryChooser.showDialog(null);
	  	Stage selStage = new Stage();
	  	  VBox vb = new VBox();

	    if(selectedDirectory != null) {
	  	  selStage.setTitle("select your model");
	  	  mettreBouton(vb,selStage);
		};
			
	  	/*
	  	List<File> fichier = new ArrayList<File>();
	  	  
	  	for(File f : new File(System.getProperty("user.dir") + "/src/main/resources/").listFiles() ){
	  		if(f.getName().contains(".ply"))fichier.add(f);
	  	}
	  	tri.tri(fichier);
	  	System.out.println(fichier);
	  	for(File f : fichier) {
	  		Button btn = new Button(f.getName());
	  		StringJoiner sj = new StringJoiner("\n");
	  		for(String line : FolderParser.getFileInfos(f)) {
	  			sj.add(line);
	  		}
	  		btn.setTooltip(new Tooltip(sj.toString()));
	  		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
	  			@Override
	  			public void handle(MouseEvent event) {
	  				try {
	  					selStage.close();
	  					modele = Parser.parse(f);
						Point centre = modele.getCenter();
						modele.translate(-centre.getX(),-centre.getY(),-centre.getZ());
						autoResize(vue.getWidth(), vue.getHeight());
						affichage(modele);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	  		 vb.getChildren().add(btn);
	  	}
	  	*/
	  	
	  	  /*
	  	  for(Entry<File, List<String>> entry:FolderParser.getCompatibleFiles(new File(System.getProperty("user.dir") + "/src/main/resources/")).entrySet()) {
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
							modele = Parser.parse(entry.getKey());
							Point centre = modele.getCenter();
							modele.translate(-centre.getX(),-centre.getY(),-centre.getZ());
							autoResize(vue.getWidth(), vue.getHeight());
							affichage(modele);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	  		  vb.getChildren().add(btn);
	  	  }
	  	  */
	  	  selStage.setScene(new Scene(vb));
	  	  selStage.setWidth(1000);
	  	  selStage.setWidth(500);
	  	  selStage.show();
	    }
	
	

  	public void mettreBouton(VBox listeBouton, Stage fenetreChoix) {
  		List<File> fichier = new ArrayList<File>();
	  	listeBouton.getChildren().clear();
	  	ToolBar toolBar = new ToolBar();
	  	System.out.println("vbox add ok");
	  	  
	  	for(EnumTri et : EnumTri.values()) {
	  		Button tmp = new Button(et.getNom());
	  		tmp.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					tri.setMethodeTri(et.getNom());
					mettreBouton(listeBouton, fenetreChoix);
					
				}
	  			
			});
	  		
	  		toolBar.getItems().add(tmp);
	  	}

	  	
	  	listeBouton.getChildren().add(toolBar);
	  	Label info = new Label("Tri par "+tri);
	  	listeBouton.getChildren().add(info);
	  	for(File f : new File(System.getProperty("user.dir") + "/src/main/resources/").listFiles() ){
	  		if(f.getName().contains(".ply"))fichier.add(f);
	  	}
	  	tri.tri(fichier);
	  	System.out.println(fichier);
	  	
	  	
	  	
	  	ObservableList<FileDescriptor> models = FXCollections.observableArrayList();
	  	
	  	TableView<FileDescriptor> table = new TableView<FileDescriptor>();
	  	
	  	
	  	TableColumn<FileDescriptor, String> fileName = new TableColumn<FileDescriptor, String>("file name");
	  	fileName.setCellValueFactory(new PropertyValueFactory<>("name"));
	  	
	  	TableColumn<FileDescriptor, String> creatorColumn = new TableColumn<FileDescriptor, String>("creator");
	  	creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
	  	
	  	TableColumn<FileDescriptor, String> vertexColumn = new TableColumn<FileDescriptor, String>("vertex");
	  	vertexColumn.setCellValueFactory(new PropertyValueFactory<>("vertex"));

	  	
	  	TableColumn<FileDescriptor, String> faceColumn = new TableColumn<FileDescriptor, String>("face");
	  	faceColumn.setCellValueFactory(new PropertyValueFactory<>("face"));

	  	
	  	

	  	
	  	
	  	
	  	for(File f : fichier) {
	  		StringJoiner sj = new StringJoiner("\n");
	  		FileDescriptor fdTmp = new FileDescriptor(f.getName());
	  		
	  		
	  		models.add(fdTmp);
	  		
	  		
	  		int i = 0;
	  		for(String line : FolderParser.getFileInfos(f,fdTmp)) {
	  			sj.add(line);
	  			i++;
	  		}
	  		fdTmp.setFile(f);
	  		Affichage vue= this;
	  		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
	  			@Override
	  			public void handle(MouseEvent event) {
	  				try {
	  					TableView c = (TableView) event.getSource();
	  					int index = c.getSelectionModel().getFocusedIndex();
	  					
	  					if(event.getClickCount() == 2) {
	  						fenetreChoix.close();
		  					modele = Parser.parse(models.get(index).getFile());
		  					modele.setVue(vue);
							Point centre = modele.getCenter();
							modele.translate(-centre.getX(),-centre.getY(),-centre.getZ());
							autoResize(vue.getWidth(), vue.getHeight());
							affichage(/*modele*/);
	  					}
	  					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	  		
	  		
	  		
	  	}
	  	table.setItems(models);
	  	table.getColumns().addAll(fileName,creatorColumn,vertexColumn,faceColumn);
	  	
  		listeBouton.getChildren().add(table);
	  	
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
	public void affichage() {
		GraphicsContext gc = this.vue.getGraphicsContext2D();
		//this.modele = modele;
		
		gc.clearRect(0,0,this.vue.getWidth(),this.vue.getHeight());
		
		Polygon forme;
		//vue.setTranslateX(vue.getWidth()/2);
		//vue.setTranslateY(vue.getHeight()/2);
		
		int idx = 0;
		double[] xPoints,yPoints;
		
		for(Face f : this.modele.getFaces()) {
			forme = new Polygon();
			idx = 0;
			xPoints = new double[10];
			yPoints = new double[10];
			for(Point p : f.getPoints()) {
				//forme.getPoints().add(p.getX());
				//forme.getPoints().add(p.getY());
				xPoints[idx] = p.getX()+vue.getWidth()/2;
				yPoints[idx] = p.getY()+vue.getHeight()/2;
				idx++;
			}
			double tauxAffichage = MathsUtils.tauxEclairage(f, MathsUtils.getVecteur(f.getCenter(), this.posLumiere));
			System.out.println("Taux affichage pour la face : "+tauxAffichage);
			forme.setStroke(Color.BLACK);
			if(f.getColor() != null) {
				forme.setFill(new Color(f.getColor().getRed()/255.0,f.getColor().getBlue()/255.0,f.getColor().getGreen()/255.0,f.getColor().getAlpha()/255.0));
			} else {
				forme.setFill(Color.RED);
			}
			
			gc.setStroke(Color.BLACK);
			if(f.getColor() != null) {
				gc.setFill(new Color(f.getColor().getRed() * tauxAffichage/255.0,f.getColor().getBlue() * tauxAffichage/255.0,f.getColor().getGreen() * tauxAffichage/255.0,f.getColor().getAlpha()/255.0));
			} else {
				gc.setFill(Color.RED);
			}
			if(voirFace) gc.fillPolygon(xPoints,yPoints,idx);
			if(voirArrete)gc.strokePolygon(xPoints,yPoints,idx);

			//vue.getChildren().add(forme);
		}

	}	
	
	public void setSensibilite(double angle) {
		this.sensibilite = angle/360.0;
	}
}
