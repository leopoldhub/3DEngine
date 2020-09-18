package etu.univlille.fr.projetmodei3.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Face {

	private List<Point> points;
	
	private Color color;
	
	public Face(Point... points) {
		this(new Color(), points);
	}
	
	public Face(Color color, Point... points) {
		this.points = new ArrayList<>();
		this.color = color;
		addPoints(points);
	}
	
	public void addPoints(Point... points) {
		for(Point point:points) {
			if(point != null && !this.points.contains(point))this.points.add(point);
		}
	}
	
	public void remPoints(Point... points) {
		this.points.removeAll(Arrays.asList(points));
	}
	
	public List<Point> getPoints() {
		return points;
		//TODO: A changer victor...
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Face [points=" + points + "]";
	}

}
