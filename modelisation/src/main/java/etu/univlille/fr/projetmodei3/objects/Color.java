package etu.univlille.fr.projetmodei3.objects;
/**
 * 
 * @author Guilhane bourgoin 
 * classe pour les couleurs 
 */
public class Color {

	/**
	 * variable correspondant à la couleur rouge 
	 */
	private int red;
	/**
	 * variable correspondant à la couleur verte
	 */
	private int green;
	/**
	 * variable correspondant à la couleur bleue
	 */
	private int blue;
	/**
	 * variable correspondant à la transparence 
	 */
	private int alpha;
	/**
	 * constructeur basique
	 */
	public Color() {
		this(0, 0, 0, 0);
	}
	
	/**
	 * 
	 * @param red taux de rouge 
	 * @param green taux de vert
	 * @param blue taux de bleu
	 */
	public Color(int red, int green, int blue) {
		this(red, green, blue, 255);
	}
	
	/**
	 * 
	 * @param red taux de rouge
	 * @param green taux de vert
	 * @param blue taux de bleu
	 * @param alpha taux de transparence
	 */
	public Color(int red, int green, int blue, int alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}
/**
 * valeur maximale prise par une couleur
 */
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
