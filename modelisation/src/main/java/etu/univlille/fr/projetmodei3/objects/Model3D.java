package etu.univlille.fr.projetmodei3.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.PointCloud;
import etu.univlille.fr.projetmodei3.utils.MathsUtils;
/**
 * Classe représentant un modèle 3D, elle est composée de Faces ainsi que différent paramètre d'affichage
 * utilisé par la classe Affichage.java
 * 
 * @author Leopold HUBERT, Maxime BOUTRY, Guilhane BOURGOING, Luca FAUBOURG
 *
 */
public class Model3D implements PointCloud{

	/**
	 * La liste des faces du modèle, elle sert à appliquer les transformations sur chaque point du modèle
	 */
	private List<Face> faces;
	/**
	 * La vue que ce modèle notifiera à chaque transformation appliqué sur lui
	 */
	private Affichage vue;

	/**
	 * La position de la lumière par rapport au modèle, de base elle est situé à droite du modèle
	 */
	Point posLumiere = new Point(500,0,0);
	
	/**
	 * Boolean servant à l'affichage pour savoir si on affiche les faces ou non
	 */
	private boolean voirFace = true;
	
	/**
	 * Boolean servant à l'affichage pour savoir si on affiche les arrêtes ou non
	 */
	private boolean voirArrete = true;
	
	/**
	 * Constructeur de base du modèle enrichie par une liste de face, Utilisé souvent par le Parser
	 * @param faces
	 */
	public Model3D(Face... faces) {
		this.faces = new ArrayList<>();
		addFaces(faces);
	}

	/**
	 * Methode permettant d'ajouter une liste de face au modèle
	 * @param faces Les faces à ajouter
	 */
	public void addFaces(Face... faces) {
		for (Face face : faces) {
			if (face != null && !this.faces.contains(face))
				this.faces.add(face);
		}
	}

	/**
	 * Methode permettant de retirer un liste de face du modèle
	 * @param faces Les faces à retirer
	 */
	public void remFaces(Face... faces) {
		this.faces.removeAll(Arrays.asList(faces));
	}

	
	
	/**
	 * Methode permettant de recuperer la liste de face du modèle triés par ordre de profondeur (la valeur du plus petit Z)
	 * @return Liste de Faces triées
	 */
	public List<Face> getFaces() {
		Collections.sort(this.faces);
		List<Face> showfaces = new ArrayList<>();
		
		for(Face face:this.faces) {
				showfaces.add(face);
		}
		return showfaces;
	}

	
	/**
	 * Methode retournant la liste des points unique du modèle (les faces partageant souvent le même points)
	 * une vérification est effectuée
	 */
	public List<Point> getPoints() {
		List<Point> points = new ArrayList<>();
		for(Face face:this.faces) {
			for(Point point:face.getPoints()) {
				if(!points.contains(point))points.add(point);
			}
		}
		return points;
	}

	
	/**
	 * Methode retournant le centre de la figure, utilisé pour placer le modèle au point (0,0,0) lorsqu'il
	 * est traduit d'un fichier .ply pour pouvior effectuer les transformation de rotation et de zoom sans problème
	 */
	public Point getCenter() {
		List<Point> points = this.getPoints();
		if(points.size() == 0)return new Point(0, 0, 0);
		
		double minx = points.get(0).getX();
		double miny = points.get(0).getY();
		double minz = points.get(0).getZ();
		double maxx = points.get(0).getX();
		double maxy = points.get(0).getY();
		double maxz = points.get(0).getZ();
		
		for(Point point:points) {
			minx = point.getX()<minx?point.getX():minx;
			miny = point.getY()<miny?point.getY():miny;
			minz = point.getZ()<minz?point.getZ():minz;
			
			maxx = point.getX()>maxx?point.getX():maxx;
			maxy = point.getY()>maxy?point.getY():maxy;
			maxz = point.getZ()>maxz?point.getZ():maxz;
		}
		
		return new Point(MathsUtils.getSegmentCenter(minx, maxx), MathsUtils.getSegmentCenter(miny, maxy), MathsUtils.getSegmentCenter(minz, maxz));
	}

	/**
	 * Methode de rotation globale, elle fait appel au trois méthode de transformation rotateX, Y, Z
	 * @param x
	 * @param y
	 * @param z
	 */
	public void rotate(double x, double y, double z) {

		Point centre = getCenter();
		System.out.println(centre);
		//this.translate(-centre.getX(),-centre.getY(),-centre.getZ());
		for(Point p : getPoints()) {
			if(x != 0) rotateX(p,x);
			if(y != 0) rotateY(p,y);
			if(z != 0) rotateZ(p,z);

		}
		//this.translate(centre.getX(),centre.getY(),centre.getZ());
		if(vue != null)
			vue.affichage();
	}

	public void translate(double x, double y, double z) {
		for (Point p : this.getPoints()) {
			p.setX(p.getX() + x);
			p.setY(p.getY() + y);
			p.setZ(p.getZ() + z);
		}
		if(vue != null)
			vue.affichage();
	}
	
	public void zoom(double valeur) { 
		
		
		System.out.println("Centre avant : "+getCenter());
		Point centre = getCenter();
		this.translate(-centre.getX(),-centre.getY(),-centre.getZ());

		System.out.println("centre : "+getCenter());
		
		for(Point p : getPoints()) {
			p.setX(p.getX()*valeur);
			p.setY(p.getY()*valeur);
			p.setZ(p.getZ()*valeur);
		}
		
		this.translate(centre.getX(),centre.getY(),centre.getZ());
		
		
		System.out.println("Centre après : "+getCenter());
		if(vue != null)
			vue.affichage();
	}
	
	
	public void rotateX(Point p,double degree) { 
		Point psauv = new Point();
		psauv.setX(p.getX());
		psauv.setY(p.getY());
		psauv.setZ(p.getZ());
		p.setX(psauv.getX()) ;
		p.setY(psauv.getY() * Math.cos(degree) - psauv.getZ() * Math.sin(degree));
		p.setZ(psauv.getY()  * Math.sin(degree) + (psauv.getZ() * Math.cos(degree)));
	}
	public void rotateY(Point p, double degree) {
		Point psauv = new Point();
		psauv.setX(p.getX());
		psauv.setY(p.getY());
		psauv.setZ(p.getZ());
		p.setX(psauv.getX() *  Math.cos(degree) + psauv.getZ()*  Math.sin(degree)) ;
		p.setY(psauv.getY());
		p.setZ(-psauv.getX() * Math.sin(degree) + psauv.getZ() * Math.cos(degree));

	}
	public void rotateZ(Point p, double degree) {
		Point psauv = new Point();
		psauv.setX(p.getX());
		psauv.setY(p.getY());
		psauv.setZ(p.getZ());
		p.setX(psauv.getX()  * Math.cos(degree) - psauv.getY() * Math.sin(degree)) ;
		p.setY(psauv.getY() *  Math.cos(degree) + psauv.getX() *  Math.sin(degree));
		p.setZ(psauv.getZ());
	}
	
	public void reset() {
		Point centre = this.getCenter();
		this.translate(-centre.getX(),-centre.getY(),-centre.getZ());
	}

	
	public void setVue(Affichage vue) {
		this.vue = vue;
		vue.setModele(this);
		vue.affichage();
	}
	
	public Point getLumiere() {
		return this.posLumiere;
	}
	
	public boolean vueFaceOn() {
		return this.voirFace;
	}
	public boolean vueArreteOn() {
		return this.voirArrete;
	}
	public void setLumiere(double x, double y, double z) {
		this.posLumiere.setX(x+ vue.getWidth()/2);
		this.posLumiere.setY(y+ vue.getWidth()/2);
		this.posLumiere.setZ(z);
		vue.affichage();
	}
	
	public Affichage getVue() {
		return this.vue;
	}
	
	public void switchVueFace() {
		this.voirFace = !voirFace;
		vue.affichage();
	}
	public void switchVueArrete() {
		this.voirArrete = !voirArrete;
		vue.affichage();
	}
	@Override
	public String toString() {
		return "Model3D [faces=" + faces + "]";
	}

}
