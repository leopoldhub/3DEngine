package etu.univlille.fr.projetmodei3.objects;

public class Color {

	private int red;
	private int green;
	private int blue;
	private int alpha;
	
	public Color() {
		this(0, 0, 0, 0);
	}
	
	public Color(int red, int green, int blue) {
		this(red, green, blue, 255);
	}
	
	public Color(int red, int green, int blue, int alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}

	private static final int MAXVALUE = 255;
	
	private int getBornedValue(int value) {
		int res = value <= MAXVALUE?value:MAXVALUE;
		res = res >= 0?res:0;
		return res;
	}
	
	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = getBornedValue(red);
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = getBornedValue(green);
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = getBornedValue(blue);
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = getBornedValue(alpha);
	}
	
}
