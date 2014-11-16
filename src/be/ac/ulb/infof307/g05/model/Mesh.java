package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "meshes")
public class Mesh extends Database<Mesh> {
	protected Mesh() {
		
	}
	
	public Mesh(String name, String fileLocation) {
		this.name = name;
		this.fileLocation = fileLocation;
	}
	
	@DatabaseField (generatedId = true)
	protected int id_mesh;
	
	@DatabaseField (canBeNull = false)
	protected String name;
	
	@DatabaseField (canBeNull = false)
	protected String fileLocation;
}
