package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import etu.univlille.fr.projetmodei3.utils.MathsUtils;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


/**
 * Classe d'affichage, elle sert de vue au patron Architectural Modèle-Vue-Controller. Elle contient toute les méthodes servant à
 * afficher un modèle ou de le changer. Elle se compose d'une Barre de menu, un Canvas et un Controller (cf classe Controller)
 * 
 * @author Leopold HUBERT, Maxime BOUTRY, Guilhane BOURGOING, Luca FAUBOURG
 *
 */
public class Affichage extends VBox{
	
	/**
	 * La barre de menu : elle contient 3 options : -Fichier qui sert à changer le modèle à afficher ou à générer une nouvelle fenêtre de vue sur un nouveau modèle
	 * -Edition : Permet d'activer ou non la lumière
	 * -Paramètre : Permettant de changer la sensibilité des rotations sur chacun des 3 axes formant l'espace 3D
	 */
	private MenuBar menu;
	/**
	 * Le modèle à afficher, le modèle possède une instance de cet Affichage afin de le notifier lors d'une transformation
	 */
	private Model3D modele;
	/**
	 * La node sur laquelle sera dessiner les faces du modèle correspondant à l'algorithme du peintre
	 */
	private Canvas vue;
	/**
	 * Le panneau de commande qui permet d'appliquer des transformations sur le modele, qui notifie cette vue et qui affiche les modifications
	 */
	private Controller commande;
	/**
	 * La HBox qui contient à gauche le Canvas "vue" et à droite le panneau de commande "commande"
	 */
	private HBox vueCommande;
	
	/**
	 * La méthode de tri des fichiers lorsqu'on affiche les modèle disponible
	 */
	private Trieur tri;
	/**
	 * nombre de clicks pour un double click
	 */
	private final int doubleClick = 2;
	/**
	 * sert à savoir si la lumière est allumée
	 */
	private boolean lightOn = true;
	
	/**
	 * Le constructeur d'affichage : il met en place toutes les nodes de l'instance, ces dernières sont inamovibles de l'exterieur
	 * Pour changer la taille des nodes il suffit d'aller les changer dans la méthode parametrageTailles()
	 * Ce constructeur met aussi en place la lumière en car celle-ci dépendra de la taille de la fenêtre lors de l'instanciation
	 */
	public double prevx, prevy;
	
	public Affichage() {
		this.menu = new MenuBar();
		this.modele = new Model3D();
		this.vue = new Canvas();
		this.vueCommande = new HBox();
		this.commande = new Controller(modele);
		this.tri = new Trieur();
		parametrageTailles();
		parametrageMenu();
		this.modele.posLumiere.setX(this.modele.posLumiere.getX()+ vue.getWidth()/2);
		this.modele.posLumiere.setY(this.modele.posLumiere.getY()+ vue.getHeight()/2);
		
		this.vue.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				prevx = event.getX();
				prevy = event.getY();
				event.setDragDetect(true);
			}
		});
		
		this.vue.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				event.setDragDetect(false);
			}
		});
		
		this.vue.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(modele != null) {
					if(event.isPrimaryButtonDown()) {
						modele.translate(event.getX()-prevx, event.getY()-prevy, 0);
					}else {
						modele.rotate((prevy-event.getY())*0.02, (event.getX()-prevx)*0.02, 0);
					}
				}
				
				prevx = event.getX();
				prevy = event.getY();
			}
		});
		
		this.vue.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if(modele != null) {
					double factor = 0.2;
					double deltaY = event.getDeltaY();
		            modele.zoom(deltaY<0?1-factor:1+factor);
				}
			}
		});
		
		this.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println(event.getCharacter());
				if(event.getCharacter() == null || event.getCharacter().length() == 0 || event.getCharacter().length() > 1)return;
				char c = event.getCharacter().toUpperCase().charAt(0);
				switch (c) {
				case 'F': 
					commande.switchVueFace();
					break;
				case 'A': 
					commande.switchVueArrete();
					break;
				case 'R': 
					modele.reset();
					break;
				case 'L':
					lightOn = !lightOn;
					affichage();
					break;
				default:
					break;
				}
			}
		});
		
	}
	
	/**
	 * Methode privée permettant de gérer les tailles des différentes nodes
	 */
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
	
	/**
	 * Méthode privée permettant de gérer les différentes options de la barre de Menu
	 */
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

	/**
	 * Méthode appelée lorsque l'option "fichier" du menu est utilisée
	 * Elle permet de sélectionner un fichier .ply parmis ceux que le répertoire au préalable sélectionné par l'utilisateur
	 * possède
	 */
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
	
	
	/**
	 * Methode servant à réagencer les différents fichier .ply lorsqu'on les tri 
	 * @param listeBouton
	 * @param fenetreChoix
	 */
  	private void mettreBouton(VBox listeBouton, Stage fenetreChoix) {
  		List<File> fichier = new ArrayList<>();
	  	listeBouton.getChildren().clear();
	  	ToolBar toolBar = new ToolBar();
	  	System.out.println("vbox add ok");
	  	tri.tri(fichier);
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
	  	
	  	TableView<FileDescriptor> table = new TableView<>();
	  	
	  	
	  	TableColumn<FileDescriptor, String> fileName = new TableColumn<>("file name");
	  	fileName.setCellValueFactory(new PropertyValueFactory<>("name"));
	  	
	  	TableColumn<FileDescriptor, String> creatorColumn = new TableColumn<>("creator");
	  	creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
	  	creatorColumn.setPrefWidth(215);
	  	
	  	TableColumn<FileDescriptor, String> vertexColumn = new TableColumn<>("vertex");
	  	vertexColumn.setCellValueFactory(new PropertyValueFactory<>("vertex"));

	  	
	  	TableColumn<FileDescriptor, String> faceColumn = new TableColumn<>("face");
	  	faceColumn.setCellValueFactory(new PropertyValueFactory<>("face"));

	  	
	  	

	  	
	  	
	  	
	  	for(File f : fichier) {
	  		StringJoiner sj = new StringJoiner("\n");
	  		FileDescriptor fdTmp = new FileDescriptor(f.getName());
	  		
	  		
	  		models.add(fdTmp);
	  		

	  		for(String line : FolderParserUtils.getFileInfos(f,fdTmp)) {
	  			sj.add(line);
	  		}
	  		fdTmp.setFile(f);
	  		Affichage vue= this;
	  		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
	  			@Override
	  			public void handle(MouseEvent event) {
	  				try {
	  						  					 
	  					
	  					TableView c = (TableView) event.getSource();
	  					int index = c.getSelectionModel().getFocusedIndex();
	  					
	  					if(event.getClickCount() == doubleClick) {
	  						fenetreChoix.close();
		  					modele = ParserUtils.parse(models.get(index).getFile());
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
	
  	/**
  	 * Méthode permettant de centrer le modèle pour qu'a chaque fois qu'on parse un modèle, il puisse prendre toute la 
  	 * place disponible dans la fenêtre
  	 * @param width la longueur de la fenêtre
  	 * @param height la hauteur de la fenêtre 
  	 */
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
	
	/**
	 * Methode qui va peindre le modèle sur le Canvas, il efface le modèle précédent puis dessine le nouveau
	 * La méthode d'affichage correspond à l'algorithme du peintre
	 */
	public void affichage() {
		GraphicsContext gc = this.vue.getGraphicsContext2D();
		gc.clearRect(0,0,this.vue.getWidth(),this.vue.getHeight());
		Polygon forme;
		int idx;
		double[] xPoints,yPoints;
		
		for(Face f : this.modele.getFaces()) {
			forme = new Polygon();
			idx = 0;
			xPoints = new double[1000];
			yPoints = new double[1000];
			for(Point p : f.getPoints()) {
				xPoints[idx] = p.getX()+vue.getWidth()/2;
				yPoints[idx] = p.getY()+vue.getHeight()/2;
				idx++;
			}
			double tauxAffichage;
			if(lightOn)
				tauxAffichage = MathsUtils.tauxEclairage(f, MathsUtils.getVecteur(f.getCenter(), this.modele.getLumiere()));
			else
				tauxAffichage = 1;
			
			forme.setStroke(Color.BLACK);
			forme.setFill(new Color(f.getColor().getRed(),f.getColor().getBlue(),f.getColor().getGreen(),f.getColor().getOpacity()));

			
			gc.setStroke(Color.BLACK);
			gc.setFill(new Color(f.getColor().getRed() * tauxAffichage,f.getColor().getBlue() * tauxAffichage,f.getColor().getGreen() * tauxAffichage,f.getColor().getOpacity()));

			 
			if(this.modele.vueFaceOn()) gc.fillPolygon(xPoints,yPoints,idx);
			if(modele.vueArreteOn())gc.strokePolygon(xPoints,yPoints,idx);
		}

	}	
	/**
	 * Methode permettant de changer le modele que la vue affiche
	 * @param modele nouveau modele
	 */
	public void setModele(Model3D modele) {
		this.modele = modele;
	}
	

}
