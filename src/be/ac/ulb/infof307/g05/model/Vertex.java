package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

@DatabaseTable (tableName = "vertexes")
public class Vertex extends Database<Vertex> {
	
	@DatabaseField (generatedId = true)
	protected int id_vertex;
	@DatabaseField (canBeNull = false)
	protected float x;
	@DatabaseField (canBeNull = false)
	protected float y;
	@DatabaseField (canBeNull = false)
	protected float z;
	@DatabaseField (canBeNull = false, foreign = true)
	private CompositeObject referent;
	
	protected Vertex() {
		
	}
	
	public Vertex(CompositeObject referent, Vector3f vec) {
		this.referent = referent;
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
		this.isNew = true;
	}
	
	public Vector3f toVector3f() {
		return new Vector3f(this.x, this.y, this.z);
	}
}
