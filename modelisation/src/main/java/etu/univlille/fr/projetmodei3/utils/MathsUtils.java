package etu.univlille.fr.projetmodei3.utils;

import java.util.List;

import etu.univlille.fr.projetmodei3.objects.Point;
import etu.univlille.fr.projetmodei3.objects.Vector3D;

public class MathsUtils {

	public static Vector3D getNormal(List<Point> points) {
		if(points == null || points.size() < 3)return new Vector3D(0, 0, 0);
		Point a = points.get(0);
		Point b = points.get(1);
		Point c = points.get(2);
		Vector3D ab = getVectorFromPoints(a, b);
		Vector3D bc = getVectorFromPoints(b, c);
		
		double x = ab.getY()*bc.getZ()-ab.getZ()*bc.getY();
		double y = ab.getZ()*bc.getX()-ab.getX()*bc.getZ();
		double z = ab.getX()*bc.getY()-ab.getY()*bc.getX();
		
		Vector3D vn = new Vector3D(x, y, z);
		double norm = vn.getNorm();
		
		return new Vector3D(x/norm, y/norm, z/norm);
	}
	
	public static Vector3D getVectorFromPoints(Point a, Point b) {
		return new Vector3D(b.getX()-a.getX(),b.getY()-a.getY(),b.getZ()-a.getZ());
	}
	
	public static double getSegmentCenter(double a, double b) {
		return ((b-a)/2)+a;
	}
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static double degreeToRad(double degree) {
		return degree * Math.PI / 180;
	}
	
	public static double radToDegree(double rad) {
		return rad * 180 / Math.PI;
	}

}
