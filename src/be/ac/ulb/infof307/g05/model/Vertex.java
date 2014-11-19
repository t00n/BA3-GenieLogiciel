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
	
	public Vertex(CompositeObject referent, Vector3f vec) {
		this.referent = referent;
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
	}
	
	public Vector3f toVector3f() {
		return new Vector3f(this.x, this.y, this.z);
	}

	@Override
    public void save() {
		try {
			this.update();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	@Override
    public void createAll() {
		try {
			this.create();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
