package etu.univlille.fr.projetmodei3.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.PointCloud;
import javafx.scene.paint.Color;
/**
 * 
 * @author guilhane bourgoin
 * objet face, correspondant à une face du model 3D
 */
public class Face implements PointCloud, Comparable<Face>{
	/**
	 * liste des points de la face
	 */
	private List<Point> points;
	/**
	 * couleur de la face
	 */
	private Color color;
	/**
	 * constructeur
	 * @param points tableau ou liste ou variable de points de la face
	 */
	public Face(Point... points) {
		this(Color.WHITE, points);
	}
	/**
	 * 
	 * @param color la couleur de la face
	 * @param points tableau ou liste ou variable de points de la face
	 */
	public Face(Color color, Point... points) {
		this.points = new ArrayList<>();
		this.color = color;
		addPoints(points);
	}
	/**
	 * ajouter des points à la face
	 * @param points tableau ou liste ou variable de points de la face
	 */
	public void addPoints(Point... points) {
		for(Point point:points) {
			if(point != null && !this.points.contains(point))this.points.add(point);
		}
	}
	/**
	 * supprimer des points de la face
	 * @param points tableau ou liste ou variable de points de la face
	 */
	public void remPoints(Point... points) {
		this.points.removeAll(Arrays.asList(points));
	}
	
	public List<Point> getPoints() {
		return points;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Face [points=" + points + "]\n Couleur : "+this.color;
	}
	/**
	 * avoir le centre de la face
	 */
	public Point getCenter() {
		double centerx = 0,centery = 0,centerz = 0;
		
		for(Point point:this.points) {
			centerx += point.getX();
			centery += point.getY();
			centerz += point.getZ();
		}
		
		return new Point(centerx/this.points.size(), centery/this.points.size(), centerz/this.points.size());
	}

	@Override
	public int compareTo(Face other) {
		return Double.compare(this.getCenter().getZ(), other.getCenter().getZ());
	}
	
	
}
