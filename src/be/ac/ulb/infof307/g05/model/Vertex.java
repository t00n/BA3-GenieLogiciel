package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "vertexes")
public class Vertex extends Database {
	public Vertex() {
		
	}
	
	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
