package etu.univlille.fr.projetmodei3.objects;


import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileDescriptor {
	public StringProperty name;
	public StringProperty creator;
	public StringProperty face;
	public StringProperty vertex;
	private File f;
	
	public FileDescriptor(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	
	public StringProperty NameProperty() {
		if (name == null) name = new SimpleStringProperty(this, "name");
        return name; 
	}


	public String getName() {
		return name.get();
	}


	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}





	
	public StringProperty CreatorProperty() {
		if (creator == null) creator = new SimpleStringProperty(this, "Creator");
        return creator; 
	}


	public String getCreator() {
		String tmp;
		try {
			tmp = this.creator.get();
		}catch(Exception e) {
			tmp = "none";
		}
		return tmp;
	}


	public void setCreator(String creator) {
		this.creator = new SimpleStringProperty(creator);
	}







	public StringProperty FaceProperty() {
		if (face == null) face = new SimpleStringProperty(this, "Face");
        return face; 
	}


	public String getFace() {
		return face.get();
	}


	public void setFace(String face) {
		this.face = new SimpleStringProperty(face);
	}





	public StringProperty VertexProperty() {
		if (vertex == null) vertex = new SimpleStringProperty(this, "Vertex");
        return vertex; 
	}


	public String getVertex() {
		return vertex.get();
	}


	public void setVertex(String vertex) {
		this.vertex = new SimpleStringProperty(vertex);
	}
	
	public void setFile(File f) {
		this.f = f;
	}
	
	public File getFile() {
		return this.f;
	}
}
