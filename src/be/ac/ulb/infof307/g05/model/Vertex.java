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
		this.vector.x = x;
		this.vector.y = y;
		this.vector.z = z;
	}
	
	public Vertex(CompositeObject referent, Vector3f vec) {
		this.referent = referent;
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.vector = vec;
	}
	
	private Vector3f vector;
	
	public Vector3f getVector() { return this.vector; }
	
	@DatabaseField (generatedId = true)
	protected int id_vertex;

	@DatabaseField (canBeNull = false)
	protected float x;
	public void setX(Float x) {
		this.x = x;
		vector.x = x;
	}
	
	@DatabaseField (canBeNull = false)
	protected float y;
	public void setY(Float y) {
		this.y = y;
		vector.y = y;
	}
	
	@DatabaseField (canBeNull = false)
	protected float z;
	public void setZ(Float z) {
		this.z = z;
		vector.z = z;
	}
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected CompositeObject referent;

	public Vector3f toVector3f() {
		return new Vector3f(this.x, this.y, this.z);
	}
	
}
