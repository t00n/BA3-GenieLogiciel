package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

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
	
	public Vertex(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vertex(CompositeObject referent, Vector3f vec) {
		this.referent = referent;
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
	}
	
	public Vector3f getVector() {
		return this.toVector3f();
	}

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


	public void setX(Float x) {
		this.x = x;
	}
	
	public void setY(Float y) {
		this.y = y;
	}
	
	public void setZ(Float z) {
		this.z = z;
	}

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
