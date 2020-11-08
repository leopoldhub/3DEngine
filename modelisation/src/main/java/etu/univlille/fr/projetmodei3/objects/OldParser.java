package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OldParser {
	public static Model3D parse(File file) {
		Model3D model= new Model3D();
		String line;
		String vertex;
		String face;
		int nbVertex;
		int nbFace;
		System.out.println("creation variables");
		List<Point> points = new ArrayList<Point>();
		System.out.println("creation listes");
		
		try {
			Scanner sc = new Scanner(new FileReader(file));
			vertex = sc.next();
			while (!vertex.equals("vertex")) {
				vertex = sc.next();
			}
			nbVertex= Integer.parseInt(sc.next());
			face = sc.next();
			while (!face.equals("face")) {
				face = sc.next();
			}
			nbFace= Integer.parseInt(sc.next());
			line = sc.nextLine();
			while (!line.equals("end_header")) {
				line = sc.nextLine();
			}
			for (int i = 0; i < nbVertex; i++) {
				points.add(new Point(Double.parseDouble(sc.next()),Double.parseDouble(sc.next()),Double.parseDouble(sc.next())));
				sc.nextLine();
			}
			for(int i=0; i<nbFace;i++) {
				Face fc= new Face();
				int nbpts = Integer.parseInt(sc.next());
				for(int j=0;j< nbpts;j++) {
					fc.addPoints(points.get(Integer.parseInt(sc.next())));
				}
				model.addFaces(fc);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return model;
	}

}
