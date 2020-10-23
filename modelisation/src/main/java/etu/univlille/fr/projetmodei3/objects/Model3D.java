package etu.univlille.fr.projetmodei3.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import etu.univlille.fr.projetmodei3.interfaces.PointCloud;
import etu.univlille.fr.projetmodei3.utils.MathsUtils;
import etu.univlille.fr.projetmodei3.utils.Matrix;

public class Model3D implements PointCloud{

	private List<Face> faces;

	public Model3D(Face... faces) {
		this.faces = new ArrayList<>();
		addFaces(faces);
		System.out.println("Nombre de faces à l'instanciation : "+this.faces.size());
	}

	public void addFaces(Face... faces) {
		for (Face face : faces) {
			if (face != null && !this.faces.contains(face))
				this.faces.add(face);
		}
	}

	public void remFaces(Face... faces) {
		this.faces.removeAll(Arrays.asList(faces));
	}

	public List<Face> getFaces() {
		Collections.sort(this.faces);
		List<Face> showfaces = new ArrayList<>();
		
		for(Face face:this.faces) {
			Vector3D vn = MathsUtils.getNormal(face.getPoints());
			Point center = face.getCenter();
			//if(center.getZ()>center.getZ()+vn.getZ()) {
				showfaces.add(face);
			//}
		}
		//System.out.println("Nombre de faces renvoyés par getFaces() : "+showfaces.size());
		return showfaces;
	}

	public List<Point> getPoints() {
		List<Point> points = new ArrayList<>();
		for(Face face:this.faces) {
			for(Point point:face.getPoints()) {
				if(!points.contains(point))points.add(point);
			}
		}
		return points;
	}

	public Point getCenter() {
		List<Point> points = this.getPoints();
		if(points.size() == 0)return new Point(0, 0, 0);
		
		double minx = points.get(0).getX();
		double miny = points.get(0).getY();
		double minz = points.get(0).getZ();
		double maxx = points.get(0).getX();
		double maxy = points.get(0).getY();
		double maxz = points.get(0).getZ();
		
		for(Point point:points) {
			minx = point.getX()<minx?point.getX():minx;
			miny = point.getY()<miny?point.getY():miny;
			minz = point.getZ()<minz?point.getZ():minz;
			
			maxx = point.getX()>maxx?point.getX():maxx;
			maxy = point.getY()>maxy?point.getY():maxy;
			maxz = point.getZ()>maxz?point.getZ():maxz;
		}
		
		return new Point(MathsUtils.getSegmentCenter(minx, maxx), MathsUtils.getSegmentCenter(miny, maxy), MathsUtils.getSegmentCenter(minz, maxz));
	}

	public void rotate(double x, double y, double z) {
		
		Matrix m;
		m = new Matrix(    new double[][] {{1,0,0,0},
										  {0,Math.cos(x),-Math.sin(x),0},
										  {0,Math.sin(x),Math.cos(x),0},								
										  {0,0,0,1}});
		m = m.Multiply(new Matrix(new double[][]{   {Math.cos(y),0,Math.sin(y),0},
												  	  {0,1,0,0},
												  	  {-Math.sin(y),0,Math.cos(y),0},								
												  	  {0,0,0,1}}),m );
		m = m.Multiply(new Matrix(new double[][]{ {Math.cos(z),-Math.sin(z),0,0},
													{Math.sin(z),Math.cos(z),0,0},
													{0,0,1,0},								
													{0,0,0,1}}),m);
		double[][] pointsModele = new double[4][getPoints().size()];
		
		for(int i = 0; i<getPoints().size();i++) {
			pointsModele[0][i] = getPoints().get(i).getX();
			pointsModele[1][i] = getPoints().get(i).getY();
			pointsModele[2][i] = getPoints().get(i).getZ();
			pointsModele[3][i] = 0;
		}
		Matrix matricePointsModele = new Matrix(pointsModele);
		matricePointsModele = matricePointsModele.Multiply(m,matricePointsModele);
		pointsModele  = matricePointsModele.getMatrice();
		
		//System.out.println("getPoints avant : "+this.getPoints());
		
		for(int i = 0; i<getPoints().size();i++) {
			getPoints().get(i).setX(pointsModele[0][i]);
			getPoints().get(i).setY(pointsModele[1][i]);
			getPoints().get(i).setZ(pointsModele[2][i]);
		}
		
		//System.out.println("getPoints après : "+this.getPoints());
	}

	public void translate(double x, double y, double z) {
		for (Point p : this.getPoints()) {
			p.setX(p.getX() + x);
			p.setY(p.getY() + y);
			p.setZ(p.getZ() + z);
		}
	}

	@Override
	public String toString() {
		return "Model3D [faces=" + faces + "]";
	}

}
