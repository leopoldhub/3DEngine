package etu.univlille.fr.projetmodei3.utils;

public class MathsUtils {

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
