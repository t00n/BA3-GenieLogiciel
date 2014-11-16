package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "meshes")
public class Mesh extends Database {
	public Mesh() {
		
	}
	@DatabaseField (generatedId = true)
	private int id_mesh;
	
	@DatabaseField (canBeNull = false)
	private String name;
	
	@DatabaseField (canBeNull = false)
	private String fileLocation;
}
