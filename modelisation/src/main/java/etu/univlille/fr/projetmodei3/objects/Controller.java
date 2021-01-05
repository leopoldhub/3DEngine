package etu.univlille.fr.projetmodei3.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import etu.univlille.fr.projetmodei3.utils.MathsUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Controller extends AnchorPane{
	
	
	Model3D modele;
	private double sensibilite = 60.0/360.0;
	Trieur tri;
	Timer timer = new Timer();
	Slider posLumX,posLumY,posLumZ;
	boolean rotationAuto = false;
	private boolean isRotation = false, lightOn = true;

	
	public Controller(Model3D modele) {
		this.modele = modele;
		parametrageCommande();
	}
	
	
	private void parametrageCommande() {
		
		GridPane boutons = new GridPane();
		
		Button affichageFace = new Button("Cacher Faces");
		affichageFace.addEventHandler(ActionEvent.ACTION, e->{
			if(this.modele.vueFaceOn()) {
				modele.switchVueFace() ;
				affichageFace.setText("Voir Faces");
			} else {
				modele.switchVueFace() ;
				affichageFace.setText("Cacher Faces");
			}
			//affichage(/*modele*/);
		});
		affichageFace.setTranslateY(300);
		affichageFace.setPrefWidth(130);
		affichageFace.setPrefHeight(50);

		this.getChildren().add(affichageFace);
		
		Button affichageArrete = new Button("Cacher Arretes");
		affichageArrete.addEventHandler(ActionEvent.ACTION, e->{
			if(this.modele.vueArreteOn()) {
				this.modele.switchVueArrete();
				affichageArrete.setText("Voir Arretes");
			} else {
				this.modele.switchVueArrete();
				affichageArrete.setText("Cacher Arretes");
			}
			//TODO affichage(/*modele*/);
		});
		affichageArrete.setTranslateY(400);
		affichageArrete.setPrefWidth(130);
		affichageArrete.setPrefHeight(50);

		this.getChildren().add(affichageArrete);


		posLumX = new Slider();
		posLumY = new Slider();
		posLumZ = new Slider();
		
		posLumX.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				modele.setLumiere(newValue.doubleValue(), posLumY.getValue(), posLumZ.getValue());
			}
		});
		
		posLumY.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				modele.setLumiere(posLumX.getValue(), newValue.doubleValue(), posLumZ.getValue());
			}
		});
		
		posLumY.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				modele.setLumiere(posLumX.getValue(), posLumY.getValue(), newValue.doubleValue());
			}
		});
		
		
		setSliderLumiere();
		
		Slider nbTranches = new Slider();
		nbTranches.setTranslateY(550);
		nbTranches.setMin(1);
		nbTranches.setMax(20);
		nbTranches.setOrientation(Orientation.HORIZONTAL);
		nbTranches.setValue(4);
		nbTranches.setShowTickLabels(true);
		nbTranches.setShowTickMarks(true);
		nbTranches.setMajorTickUnit(1);


		Button tranches = new Button("Vue en tranches");
		tranches.addEventHandler(ActionEvent.ACTION, e->{
			Model3D modeleTranches = new Model3D();
			Face tranche;
			/*
			Point[] intersections;
			for(double z : MathsUtils.getZtranches(modele, (int)nbTranches.getValue())) {
				tranche = new Face();
				for(Face f : modele.getFaces()) {
					intersections = MathsUtils.getIntersection(f, z);
					if(intersections != null) {
						tranche.addPoints(intersections[0]);
						tranche.addPoints(intersections[1]);
					}
				}
				modeleTranches.addFaces(tranche);
			}
			*/
			Point depart, courant;
			Point[] intersection;
			int idx;
			Affichage tmp = modele.getVue();
			List<Point[]> segments = new ArrayList<Point[]>();
			for(double z : MathsUtils.getZtranches(modele, (int)nbTranches.getValue())) {
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
				modeleTranches.addFaces(tranche);
			}
			System.out.println("Vue tranches fini");
			modeleTranches.setVue(tmp);
			modele = modeleTranches;
		});
		
		tranches.setPrefWidth(130);
		tranches.setPrefHeight(50);
		tranches.setTranslateY(500);
		
		this.getChildren().add(tranches);
		this.getChildren().add(nbTranches);
		
		
		Button resetModel = new Button("Reset translation");
		resetModel.addEventHandler(ActionEvent.ACTION, e->{
			modele.reset();	
			//affichage(/*modele*/);
		});
		resetModel.setTranslateY(700);
		resetModel.setPrefWidth(130);
		resetModel.setPrefHeight(50);
		
		this.getChildren().add(resetModel);
		
		posLumX.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				modele.getLumiere().setX(newValue.doubleValue());
			}

		});
		posLumX.setTranslateY(100);
		posLumY.setTranslateY(150);
		posLumZ.setTranslateY(200);

		Label lumX = new Label("Valeur X lumière");
		Label lumY = new Label("Valeur Y lumière");
		Label lumZ = new Label("Valeur Z lumière");

		lumX.setTranslateY(80);
		lumY.setTranslateY(130);
		lumZ.setTranslateY(180);

		
		this.getChildren().add(posLumX);
		this.getChildren().add(posLumY);
		this.getChildren().add(posLumZ);
		this.getChildren().add(lumX);
		this.getChildren().add(lumY);
		this.getChildren().add(lumZ);


		
		Button rotationHor = new Button("Rotation auto");
		
		rotationHor.addEventHandler(ActionEvent.ACTION, e->{
			
			
			TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					modele.rotate(sensibilite+1, sensibilite+1, sensibilite+3);	
					
					
				}
			};			
			if(rotationAuto) {
				timer.cancel();
				
			}else {
				timer = new Timer();
				timer.schedule(task, 0,1000);		
			}	
			this.rotationAuto = !rotationAuto;
				
			
				
		});
		
			
		rotationHor.setTranslateY(600);
		rotationHor.setPrefWidth(130);
		rotationHor.setPrefHeight(50);
		this.getChildren().add(rotationHor);

		
		Button option = new Button("\u2196 hg");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(-sensibilite*60, -sensibilite*60, 0);
			else
				modele.rotate(sensibilite,-sensibilite,-sensibilite);
			//affichage(/*modele*/);
		});
		boutons.add(option,0,0);
		
		option = new Button("\u2191 |");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(0, -sensibilite*60, 0);
			else
				modele.rotate(sensibilite,0,0);
			//affichage(/*modele*/);
		});
		boutons.add(option,1,0);
		
		option = new Button("\u2197 hd");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(sensibilite*60, -sensibilite*60, 0);
			else
				modele.rotate(sensibilite,sensibilite,sensibilite);
			//affichage(/*modele*/);
		});
		boutons.add(option,2,0);
		
		option = new Button("\u2190 -");
		option.addEventHandler(ActionEvent.ACTION,e->{
			//modele.rotate(0,-sensibilite,0);
			if(!isRotation)
				modele.translate(-sensibilite*60, 0, 0);
			else
				modele.rotate(0,-sensibilite,0);
			//affichage(/*modele*/);
		});
		boutons.add(option,0,1);
		
		
		Button middleButton = new Button("\u2928 ");
		
		
		middleButton.addEventHandler(ActionEvent.ACTION,e->{
			isRotation = ! isRotation;
			if(isRotation)
				middleButton.setText("\u27F3 ");
			else
				middleButton.setText("\u2928 ");
			modele.rotate(0,0,0);
			//affichage(/*modele*/);
		});
		boutons.add(middleButton,1,1);
		
		option = new Button("\u2192 -");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(sensibilite*60,0 ,0);
			else
				modele.rotate(0,sensibilite,0);
			//affichage(/*modele*/);
		});
		boutons.add(option,2,1);
		
		option = new Button("\u2199 bg");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(-sensibilite*60, sensibilite*60, 0);
			else
				modele.rotate(-sensibilite,-sensibilite,-sensibilite);
			//affichage(/*modele*/);
		});
		boutons.add(option,0,2);
		
		option = new Button("\u2193 |");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation)
				modele.translate(0, sensibilite*60, 0);
			else
				modele.rotate(-sensibilite,0,0);
			//affichage(/*modele*/);
		});
		boutons.add(option,1,2);
		
		option = new Button("\u2198 bd");
		option.addEventHandler(ActionEvent.ACTION,e->{
			if(!isRotation )
				modele.translate(sensibilite*60, sensibilite*60, 0);
			else
				modele.rotate(-sensibilite,sensibilite,sensibilite);
			//affichage(/*modele*/);
		});
		boutons.add(option,2,2);
		
		this.getChildren().add(boutons);
	}
	
	
	public void setSliderLumiere() {
		List<Point> points = modele.getPoints();
		if(!points.isEmpty()) {
			double xMax = points.get(0).getX(), 
					yMax = points.get(0).getY(), 
					zMax = points.get(0).getZ();
			for(Point p : modele.getPoints()) {
				if(p.getX() > xMax) xMax = p.getX();
				if(p.getY() > yMax) xMax = p.getY();
				if(p.getZ() > zMax) xMax = p.getZ();
			}
				
			posLumX.setMin(-5000);
			posLumX.setMax(5000);
			posLumY.setMin(-5000);
			posLumY.setMax(5000);
			posLumZ.setMin(-5000); 
			posLumZ.setMax(5000);
			System.out.println("Borne des Slider Lumière : x ("+posLumX.getMin()+":"+posLumX.getMax()+")");
			System.out.println("Borne des Slider Lumière : y ("+posLumY.getMin()+":"+posLumY.getMax()+")");
			System.out.println("Borne des Slider Lumière : z ("+posLumZ.getMin()+":"+posLumZ.getMax()+")");
		}
	}
	
	public void setSensibilite(double angle) {
		this.sensibilite = angle/360.0;
	}
	
	public void setModele(Model3D modele) {
		System.out.println("Set Modele fait");
		this.modele = modele;
		setSliderLumiere();
	}
	
}
