package etu.univlille.fr.projetmodei3;


import etu.univlille.fr.projetmodei3.objects.Point;
import etu.univlille.fr.projetmodei3.utils.Matrix;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;





public class Rotation{
	
	boolean rotation = false;
	int angle = 1;
		
	public void rotate(Rectangle carre, int degree) {
		double X=(carre.getX()*Math.cos(degree)-carre.getY()*Math.sin(degree));
		double Y=(carre.getX()*Math.sin(degree)+carre.getY()*Math.cos(degree));
//		carre.setX(carre.getY()-0.1);
//		carre.setY(carre.getX()+0.1);
		carre.relocate(X, Y);
		
	}
	public void rotate(Circle c,Point p, int degree) {
		Matrix matrice = new Matrix(p);
		matrice = rotateY(p, degree);
		matrice = project(matrice);
		double[][] tab = matrice.getMatrice();
		c.relocate(tab[0][0], tab[1][0]); //faire une methode qui retourne des points au lieu d'un tableau de doublel
	}
	public Matrix rotateX(Point p,int degree) { //faudra mettre les matrices au lieu du tableau comme ça
		double x = p.getX();
		double y =  p.getY() * Math.cos(degree) - p.getZ() * Math.sin(degree);
		double z = p.getY()  * Math.sin(degree) + p.getZ() * Math.cos(degree);
		
		Matrix m = new Matrix(new Point(x,y,z));
		return m;

	}
	public Matrix rotateY(Point p, int degree) {
		double x = p.getX() *  Math.cos(degree) + p.getZ()*  Math.sin(degree);
		double y = p.getY();
		double z = -p.getX() * Math.sin(degree) + p.getZ() * Math.cos(degree);
		
		Matrix m = new Matrix(new Point(x,y,z));
		return m;


	}
	public Matrix rotateZ(Point p, int degree) {
		double x = p.getX()  * Math.cos(degree) - p.getY() * Math.sin(degree);
		double y = p.getY() * Math.cos(degree) + p.getX() *  Math.sin(degree);
		double z = p.getZ();
		
		Matrix m = new Matrix(new Point(x,y,z));
		return m;
	}
	public Matrix project(Matrix m) {
		double[][] tmp = 
			{{1, 0, 0},
			{0, 1, 0},
			{0, 0, 0}};
		
		return new Matrix(tmp).Multiply(m);
	}

}

