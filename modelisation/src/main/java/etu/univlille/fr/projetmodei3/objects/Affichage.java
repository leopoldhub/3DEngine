package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;
import java.util.Timer;
import java.util.TimerTask;

import etu.univlille.fr.projetmodei3.utils.MathsUtils;

import java.util.Map.Entry;
import java.util.ServiceConfigurationError;
import java.util.Set;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
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
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
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
	Controller commande;
	HBox vueCommande;
	Timer timer = new Timer();

	private int nbTranches = 2;
	private double sensibilite = 60.0/360.0;
	boolean rotationAuto = false;
	Trieur tri;
	private boolean isRotation = false, lightOn = true;
	
	
	public Affichage() {
		this.menu = new MenuBar();
		this.modele = new Model3D();
		this.vue = new Canvas();
		this.vueCommande = new HBox();
		//this.commande = new AnchorPane();
		this.commande = new Controller(modele);
		tri = new Trieur();
		parametrageTailles();
		parametrageMenu();
		//parametrageCommande();
		this.modele.posLumiere.setX(this.modele.posLumiere.getX()+ vue.getWidth()/2);
		this.modele.posLumiere.setY(this.modele.posLumiere.getY()+ vue.getHeight()/2);
		
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
		this.menu.getMenus().add(new Menu("edition"));
		this.menu.getMenus().add(new Menu("parametres"));

		MenuItem fichier = new MenuItem("fichier");
		MenuItem lights = new MenuItem("on/off lumières");
		MenuItem param = new MenuItem("parametres");

		fichier.addEventHandler(ActionEvent.ACTION,e->{
			selectModel();
		});
		
		lights.addEventHandler(ActionEvent.ACTION,e->{
			lightOn = !lightOn;
			affichage();
		});
		

		param.addEventHandler(ActionEvent.ACTION, e->{
			commande.settings();
		});

		this.menu.getMenus().get(0).getItems().add(fichier);
		this.menu.getMenus().get(0).getItems().get(0);
		
		this.menu.getMenus().get(1).getItems().add(lights);
		this.menu.getMenus().get(2).getItems().add(param);

		
		MenuItem nouvelleVue = new MenuItem("Ouvrir une nouvelle fenetre");
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
		
		Button affichageFace = new Button("Cacher Faces");
		affichageFace.addEventHandler(ActionEvent.ACTION, e->{
			if(this.modele.vueFaceOn()) {
				modele.switchVueFace();
				affichageFace.setText("Voir Faces");
			} else {
				modele.switchVueFace();
				affichageFace.setText("Cacher Faces");
			}
			affichage(/*modele*/);
		});
		affichageFace.setTranslateY(300);
		affichageFace.setPrefWidth(130);
		affichageFace.setPrefHeight(50);

		this.commande.getChildren().add(affichageFace);
		
		Button affichageArrete = new Button("Cacher Arretes");
		affichageArrete.addEventHandler(ActionEvent.ACTION, e->{
			if(modele.vueArreteOn()) {
				modele.switchVueArrete();
				affichageArrete.setText("Voir Arretes");
			} else {
				modele.switchVueArrete();
				affichageArrete.setText("Cacher Arretes");
			}
			affichage(/*modele*/);
		});
		affichageArrete.setTranslateY(400);
		affichageArrete.setPrefWidth(130);
		affichageArrete.setPrefHeight(50);

		this.commande.getChildren().add(affichageArrete);


		Slider posLumX = new Slider(),posLumY = new Slider(),posLumZ = new Slider();


		Button tranches = new Button("Vue en tranches");
		Affichage vue = this;
		tranches.addEventHandler(ActionEvent.ACTION, e->{
			Model3D modeleTranches = new Model3D();
			Face tranche;
			Point depart, courant;
			Point[] intersection;
			int idx;
			List<Point[]> segments = new ArrayList<Point[]>();
			for(double z : MathsUtils.getZtranches(modele, nbTranches)) {
				tranche = new Face();
				for(Face f: modele.getFaces()) {
					intersection = MathsUtils.getIntersection(f, z);
					if(intersection != null) segments.add(intersection);
				}
				System.out.println("Liste intersection : "+segments);
				depart = segments.get(0)[0];
				courant = segments.get(0)[1];
				tranche.addPoints(depart);
				tranche.addPoints(courant);

				System.out.println("Segment : "+segments.get(0)[0]+"\nDepart : "+depart+"\ncourant"+courant);
				segments.remove(0);
				while(!segments.isEmpty()) {
					idx = 1;	
					while(idx < segments.size() && !courant.equals(segments.get(idx-1)[0]) && !courant.equals(segments.get(idx-1)[1])) {
						idx++;
					}
					if(idx < segments.size()) {
						System.out.println("dans le if");
						if(courant.equals(segments.get(idx-1)[0])) {
							courant = segments.get(idx-1)[1];
							tranche.addPoints(courant);
						} else {
							courant = segments.get(idx-1)[0];
							tranche.addPoints(courant);
						}
						segments.remove(idx-1);
						System.out.println("Fin du if");
					}	else {
						tranche.addPoints(depart);
						segments.clear();
					}
				}
				tranche.addPoints(depart);
				System.out.println("Face fini");
				modeleTranches.addFaces(tranche);
			}
			
			modeleTranches.setVue(vue);
			modele = modeleTranches;
		});
		
		tranches.setPrefWidth(130);
		tranches.setPrefHeight(50);
		tranches.setTranslateY(500);
		
		this.commande.getChildren().add(tranches);
		//this.commande.getChildren().add(sliderTranche);
		
		
		Button resetModel = new Button("Reset translation");
		resetModel.addEventHandler(ActionEvent.ACTION, e->{
			modele.reset();	
			affichage(/*modele*/);
		});
		resetModel.setTranslateY(700);
		resetModel.setPrefWidth(130);
		resetModel.setPrefHeight(50);
		
		this.commande.getChildren().add(resetModel);
		
	

	}

	
	//PENSER A RAJOUTER UN BOUTON CROISSANT DECROISSANT
	public void selectModel() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/src/main/resources/"));
	    File selectedDirectory = directoryChooser.showDialog(null);
	  	Stage selStage = new Stage();

	  	VBox vb = new VBox();
	  	System.out.println(directoryChooser.toString()+"selected directory null");

	    if(selectedDirectory != null) {
	  	  selStage.setTitle("select your model");
	  	  mettreBouton(vb,selStage);
		}else{
			 Platform.runLater(() -> selStage.close());
		}
			
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
	  	creatorColumn.setPrefWidth(215);
	  	
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
							commande.setModele(modele);
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
			xPoints = new double[1000];
			yPoints = new double[1000];
			for(Point p : f.getPoints()) {
				//forme.getPoints().add(p.getX());
				//forme.getPoints().add(p.getY());
				xPoints[idx] = p.getX()+vue.getWidth()/2;
				yPoints[idx] = p.getY()+vue.getHeight()/2;
				idx++;
			}
			double tauxAffichage;
			if(lightOn)
				tauxAffichage = MathsUtils.tauxEclairage(f, MathsUtils.getVecteur(f.getCenter(), this.modele.getLumiere()));
			else
				tauxAffichage = 1;
			//System.out.println("Taux affichage pour la face : "+tauxAffichage);
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
			if(this.modele.vueFaceOn()) gc.fillPolygon(xPoints,yPoints,idx);
			if(modele.vueArreteOn())gc.strokePolygon(xPoints,yPoints,idx);

			//vue.getChildren().add(forme);
		}

	}	
	
	public void setModele(Model3D modele) {
		this.modele = modele;
	}
	
	public void setSliderLumiere(Slider x, Slider y, Slider z) {
		if(x == null) {
			x = new Slider();
			y = new Slider();
			z = new Slider();
		}
		List<Point> points = modele.getPoints();
		double xMin = points.get(0).getX(), xMax = points.get(0).getX(), 
				yMin = points.get(0).getY(), yMax = points.get(0).getY(), 
				zMin = points.get(0).getZ(), zMax = points.get(0).getZ();
		for(Point p : modele.getPoints()) {
			if(p.getX() < xMin) xMin = p.getX();
			if(p.getX() > xMax) xMax = p.getX();
			if(p.getX() < yMin) xMin = p.getY();
			if(p.getX() > yMin) xMax = p.getY();
			if(p.getX() < zMin) xMin = p.getZ();
			if(p.getX() > zMax) xMax = p.getZ();
		}
		
		x.setMin(xMin * 0.8);
		x.setMax(xMax * 1.2);
		x.setMin(yMin * 0.8);
		x.setMin(yMax * 1.2);
		x.setMin(zMin * 0.8);
		x.setMin(zMax * 1.2);

	}

}
