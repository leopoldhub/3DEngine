package etu.univlille.fr.projetmodei3.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model3D {

	private List<Face> faces;
	
	public Model3D(Face... faces) {
		this.faces = new ArrayList<>();
		addFaces(faces);
	}
	
	public void addFaces(Face... faces) {
		for(Face face:faces) {
			if(face != null && !this.faces.contains(face))this.faces.add(face);
		}
	}
	
	public void remFaces(Face... faces) {
		this.faces.removeAll(Arrays.asList(faces));
	}

	public List<Face> getFaces() {
		return faces;
	}
	
	public List<Point> getPoints(){
		//TODO: A changer victor...
		return null;
	}
	
	public Point getCenter() {
		//TODO: a faire apres le getPoints();
		return null;
	}

	public void rotate(double x, double y, double z) {
		
	}
	
	public void translate(double x, double y, double z) {
		
	}
	
	@Override
	public String toString() {
		return "Model3D [faces=" + faces + "]";
	}
	
}
