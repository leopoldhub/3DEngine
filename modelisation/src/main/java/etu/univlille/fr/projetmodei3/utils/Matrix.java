package etu.univlille.fr.projetmodei3.utils;


import etu.univlille.fr.projetmodei3.objects.Point;


/*TODO
 * transformer un point -> matrice.
 * 
 */
public class Matrix {
	private double[][] matrice;
	private double[][] tmp = 
		{{1, 0, 0},
		{0, 1, 0},
		{0, 0, 0}};
	
	

	public Matrix(double[][] matrice) {
		this.matrice = new double[matrice[0].length][matrice.length];
		this.matrice = matrice;
	}
	public Matrix(Point p) {
		this.matrice = new double[3][1];//
		this.matrice[0][0] = (double) p.getX();
		this.matrice[1][0] = (double) p.getY();
		this.matrice[2][0] = (double) p.getZ();
	}
	
	
	public Matrix Multiply(Matrix ma, Matrix mb) {
		double[][] a = ma.getMatrice();
		double[][] b = mb.getMatrice();
		
		int colsA = a[0].length;
		  int rowsA = a.length;
		  int colsB = b[0].length;
		  int rowsB = b.length;

		  if (colsA != rowsB) {
		    System.out.println("Columns of A must match rows of B");
		    return null;
		  }

		  double result[][] = new double[rowsA][colsB];

		  for (int i = 0; i < rowsA; i++) {
		    for (int j = 0; j < colsB; j++) {
		      double sum = 0;
		      for (int k = 0; k < colsA; k++) {
		        sum += a[i][k] * b[k][j];
		      }
		      result[i][j] = sum;
		    }
		  }
		  return doubleToMatrix(result);
	}
	public Matrix Multiply(double a[][], double b[][]) {
		int colsA = a[0].length;
		  int rowsA = a.length;
		  int colsB = b[0].length;
		  int rowsB = b.length;

		  if (colsA != rowsB) {
		    System.out.println("Columns of A must match rows of B");
		    return null;
		  }

		  double result[][] = new double[rowsA][colsB];

		  for (int i = 0; i < rowsA; i++) {
		    for (int j = 0; j < colsB; j++) {
		      double sum = 0;
		      for (int k = 0; k < colsA; k++) {
		        sum += a[i][k] * b[k][j];
		      }
		      result[i][j] = sum;
		    }
		  }
		  return doubleToMatrix(result);
	}
	public Matrix Multiply(Matrix m) {
		return Multiply(this,m);
	}
	
	
	
	public double[][] getMatrice() {
		return matrice;
	}
	public Matrix doubleToMatrix(double[][] tab) {
		return new Matrix(tab);
	}
	
	public Matrix project() {		
		return Multiply(tmp,this.matrice);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(double[] f : this.matrice) {
			for (int i = 0; i < f.length; i++) {
				str.append(f[i]+";");
			}
		}
		return str.toString();
	}
	public Matrix getRotateXmatrix(double x){
		double[][] tmp = {
				  {1,0			,0			,0},
				  {0,Math.cos(x),-Math.sin(x),0},
				  {0,Math.sin(x),Math.cos(x),0},								
				  {0,0			,0			,1}};
		return new Matrix(tmp);
	}
	public Matrix getRotateYmatrix(double y){
		double[][] tmp ={ 
				{Math.cos(y),0,Math.sin(y) ,0},
			  	{0			,1,0		   ,0},
			  	{-Math.sin(y),0,Math.cos(y),0},								
			  	{0			 ,0,0		   ,1}};
		return new Matrix(tmp);
	}
	public Matrix getRotateZmatrix(double z){
		double[][] tmp ={ 
				{Math.cos(z),-Math.sin(z),0,0},
				{Math.sin(z),Math.cos(z) ,0,0},
				{0			,0			 ,1,0},								
				{0			,0			 ,0,1}};
		return new Matrix(tmp);
	}

	
	
}
