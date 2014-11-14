package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "vertexes")
public class Vertex {
	public Vertex() {
		
	}
	
	@DatabaseField (generatedId = true)
	private int id_vertex;

	@DatabaseField (canBeNull = false)
	private double x;
	
	@DatabaseField (canBeNull = false)
	private double y;
	
	@DatabaseField (canBeNull = false)
	private double z;

}
