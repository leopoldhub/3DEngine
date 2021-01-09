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
	/**
	 * Constructeur basique qui crée un descripteur de fichier à partir du nom du fichier
	 * @param name the name of the file
	 */
	public FileDescriptor(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	/**
	 * Utilisé pour la TableView
	 * @return le nom du fichier
	 */
	public StringProperty NameProperty() {
		if (name == null) name = new SimpleStringProperty(this, "name");
        return name; 
	}

	/**
	 * 
	 * @return le nom
	 */
	public String getName() {
		return name.get();
	}

/**
 * 
 * @param name le nom du fichier
 */
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}





	/**
	 * Utilisé pour la TableView
	 * @return creator
	 */
	public StringProperty CreatorProperty() {
		if (creator == null) creator = new SimpleStringProperty(this, "Creator");
        return creator; 
	}

	/**
	 * 
	 * @return le createur du fichier si précisé sinon none
	 */
	public String getCreator() {
		String tmp;
		try {
			tmp = this.creator.get();
		}catch(Exception e) {
			tmp = "none";
		}
		return tmp;
	}

/**
 * 
 * @param creator le créateur du fichier
 */
	public void setCreator(String creator) {
		this.creator = new SimpleStringProperty(creator);
	}






	/**
	 * Utilisé pour la TableView
	 * @return face
	 */
	public StringProperty FaceProperty() {
		if (face == null) face = new SimpleStringProperty(this, "Face");
        return face; 
	}

/**
 * 
 * @return le nombre de faces
 */
	public String getFace() {
		return face.get();
	}

/**
 * 
 * @param face, le nombre de faces
 */
	public void setFace(String face) {
		this.face = new SimpleStringProperty(face);
	}




	/**
	 * Utilisé pour la TableView
	 * @return vertex
	 */
	public StringProperty VertexProperty() {
		if (vertex == null) vertex = new SimpleStringProperty(this, "Vertex");
        return vertex; 
	}

/**
 * 
 * @return vertex, le nombre de sommets
 */
	public String getVertex() {
		return vertex.get();
	}

/**
 * 
 * @param vertex, le nombre de sommets
 */
	public void setVertex(String vertex) {
		this.vertex = new SimpleStringProperty(vertex);
	}
/**
 * 	
 * @param f, le fichier ply
 */
	public void setFile(File f) {
		this.f = f;
	}
/**
 * 	
 * @return f, le fichier ply
 */
	public File getFile() {
		return this.f;
	}
}
