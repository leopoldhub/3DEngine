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

/*TODO
 * Il faut retirer javafx
 */



public class Rotation extends Application{
	
	boolean rotation = false;
	int angle = 1;
	
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage primaryStage) {
		
		Pane pane = new Pane();
		Scene scene = new Scene(pane,800,500);
		
		Rectangle carre = new Rectangle();
		carre.setX(100);
		carre.setY(100);
		carre.setFill(Paint.valueOf("black"));
		carre.setWidth(300.0f); 
	    carre.setHeight(150.0f);
	    
	    
	    Point point0 = new Point(50, 50, 0);
	    Point point1 = new Point(100, 50, 0 );
	    Point point2 = new Point(100, 100, 0);
	    Point point3 = new Point(50, 100, 0);
	    Point point4 = new Point(50, 50, 100);
	    Point point5 = new Point(100, 50, 100);
	    Point point6 = new Point(100, 100, 100);
	    Point point7 = new Point(50, 100, 100);
	    
	    Circle p1 = new Circle(point0.getX(),point0.getY(),10);
	    Circle p2 = new Circle(point1.getX(), point1.getY(),10);
	    Circle p3 = new Circle(point2.getX(), point2.getY(),10);
	    Circle p4 = new Circle(point3.getX(), point3.getY(),10);
	    Circle p5 = new Circle(point4.getX(), point4.getY(),10);
	    Circle p6 = new Circle(point5.getX(), point5.getY(),10);
	    Circle p7 = new Circle(point6.getX(), point6.getY(),10);
	    Circle p8 = new Circle(point7.getX(), point7.getY(),10);

	    
	    //Box cube = new Box(1,1,1);
		
	    Thread thread;
		Button tap = new Button("rotation 90");
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				rotate(carre, angle);
				rotate(p1,point0,angle);
				rotate(p2,point1,angle);
				rotate(p3,point2,angle);
				rotate(p4,point3,angle);
				rotate(p5,point4,angle);
				rotate(p6,point5,angle);
				rotate(p7,point6,angle);
				rotate(p8,point7,angle);



				//carre.setX(carre.getX()+angle);
				angle+=1;
				System.out.println("loop");

				try {
					Thread.sleep(1000);
					Thread.currentThread().run();
					System.out.println("ok");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		
		thread = new Thread(runnable);

		tap.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				thread.start();
				
				
			}
		});
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        Platform.exit();
		        System.exit(0);
		    }
		});

		
		pane.getChildren().addAll(/*carre,*/tap,p1,p2,p3,p4,p5,p6,p7,p8);

		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
	}
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
	public Matrix translate(Matrix m) {//marche pas
		double[][] tmp = 
			{{1, 0, -50},
			{0, 1, -50},
			{0, 0, 1}};
		
		return new Matrix(tmp).Multiply(m);
	}
	public Matrix project(Matrix m) {
		double[][] tmp = 
			{{1, 0, 0},
			{0, 1, 0},
			{0, 0, 0}};
		
		return new Matrix(tmp).Multiply(m);
	}

}

