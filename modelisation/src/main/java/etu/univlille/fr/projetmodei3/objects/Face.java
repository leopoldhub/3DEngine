package etu.univlille.fr.projetmodei3.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.PointCloud;
import etu.univlille.fr.projetmodei3.utils.MathsUtils;

public class Face implements PointCloud, Comparable<Face>{

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
