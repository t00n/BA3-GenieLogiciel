package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

@DatabaseTable (tableName = "vertexes")
public class Vertex extends Database<Vertex> {
	protected Vertex() {
		
	}
	
	public Vertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vertex(Vector3f vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public Vector3f getVector() { return this.toVector3f(); }
	
	@DatabaseField (generatedId = true)
	protected int id_vertex;

    public void save() {
        try {
			this.update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public int getId() {
		return this.id_vertex;
	}

	@DatabaseField (canBeNull = false)
	protected float x;
	public void setX(Float x) {
		this.x = x;
	}
	
	@DatabaseField (canBeNull = false)
	protected float y;
	public void setY(Float y) {
		this.y = y;
	}
	
	@DatabaseField (canBeNull = false)
	protected float z;
	public void setZ(Float z) {
		this.z = z;
	}
	
	@DatabaseField (canBeNull = false, foreign = true)
	private CompositeObject referent;

	public Vector3f toVector3f() {
		return new Vector3f(this.x, this.y, this.z);
	}

	public CompositeObject getReferent() {
		return referent;
	}

	public void setReferent(CompositeObject referent) {
		this.referent = referent;
	}
	
}
