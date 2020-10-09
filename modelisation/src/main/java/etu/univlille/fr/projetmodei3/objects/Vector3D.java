package etu.univlille.fr.projetmodei3.objects;

import org.apache.commons.math3.util.FastMath;

public class Vector3D {

	private double x;
	private double y;
	private double z;
	
	public Vector3D() {}
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getNorm() {
        return FastMath.sqrt (x * x + y * y + z * z);
    }
	
	public Vector3D add(double x, double y, double z) {
		return new Vector3D(this.x + x, this.y + y, this.z + z);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
}
