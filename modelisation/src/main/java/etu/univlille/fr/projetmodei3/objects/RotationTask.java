package etu.univlille.fr.projetmodei3.objects;

import java.util.TimerTask;
/**
 * Classe decrivant la rotation automatique
 * @author grp I3
 *
 */
public class RotationTask extends TimerTask{
	/**
	 * le modele à faire tourner
	 */
	private Model3D model;
	/**
	 * la sensibilite de la rotation selon les axes
	 */
	private double sensibiliteX, sensibiliteY, sensibiliteZ;
	/**
	 * la sensibilite par defaut
	 */
	private final static double SENSIBILITEDEFAULT = 60.0/360.0;
	
	RotationTask(Model3D model,double sensibiliteX, double sensibiliteY, double sensibiliteZ){
		this.model = model;
		this.sensibiliteX = sensibiliteX;
		this.sensibiliteY = sensibiliteY;
		this.sensibiliteZ = sensibiliteZ;
	}
	
	RotationTask(Model3D model,double sensibilite){
		this(model,sensibilite,sensibilite,sensibilite);
	}
	
	RotationTask(Model3D model) {
		this(model,SENSIBILITEDEFAULT,SENSIBILITEDEFAULT,SENSIBILITEDEFAULT);
	}
	
	@Override
	public void run() {
		model.rotate(sensibiliteX+1, sensibiliteY+1, sensibiliteZ+3);
		
	}

}
