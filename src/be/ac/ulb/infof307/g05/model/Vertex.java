package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

@DatabaseTable (tableName = "vertexes")
public class Vertex extends Database<Vertex> {
	protected Vertex() {
		
	}
	
	public Vertex(CompositeObject referent, float x, float y, float z) {
		this.referent = referent;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vertex(CompositeObject referent, Vector3f vec) {
		this.referent = referent;
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	@DatabaseField (generatedId = true)
	protected int id_vertex;

	@DatabaseField (canBeNull = false)
	protected float x;
	
	@DatabaseField (canBeNull = false)
	protected float y;
	
	@DatabaseField (canBeNull = false)
	protected float z;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected CompositeObject referent;

	public Vector3f toVector3f() {
		return new Vector3f(this.x, this.y, this.z);
	}
	
}
