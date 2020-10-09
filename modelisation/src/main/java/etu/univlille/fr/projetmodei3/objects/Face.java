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
		double minx = points.get(0).getX();
		double miny = points.get(0).getY();
		double minz = points.get(0).getZ();
		double maxx = points.get(0).getX();
		double maxy = points.get(0).getY();
		double maxz = points.get(0).getZ();
		
		for(Point point:this.points) {
			minx = point.getX()<minx?point.getX():minx;
			miny = point.getY()<miny?point.getY():miny;
			minz = point.getZ()<minz?point.getZ():minz;
			
			maxx = point.getX()>maxx?point.getX():maxx;
			maxy = point.getY()>maxy?point.getY():maxy;
			maxz = point.getZ()>maxz?point.getZ():maxz;
		}
		
		return new Point(MathsUtils.getSegmentCenter(minx, maxx), MathsUtils.getSegmentCenter(miny, maxy), MathsUtils.getSegmentCenter(minz, maxz));
	}

	@Override
	public int compareTo(Face other) {
		return Double.compare(this.getCenter().getZ(), other.getCenter().getZ());
	}
	
	
}
