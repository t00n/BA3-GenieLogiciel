package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

@DatabaseTable (tableName = "floors")
public class Floor extends Database<Floor> {
	public Floor() {
		
	}
	
	public Floor(Collection<Vector3f> vector3fs) {
		this.vertices = new ArrayList<Vertex>();
		for (Vector3f vec : vector3fs) {
			vertices.add(new Vertex(this, vec));
		}
		this.height = 0.2f;
		this.isNew = true;
		this.setId();
	}
	
	public int getId() {
		return this.id_floor;
	}
	
	public void setId() {
		this.id_floor = Static_ID.getFloorID();
	}
	
	public void setHeight(float height) {
		this.height = height;
	}

	public float getHeight() {
		return this.height;
	}
	
	public void setVertices(Collection<Vector3f> mouseClicks) {
		//FIXME delete old vertices from db
		this.vertices = new ArrayList<Vertex>();
		for (Vector3f vec : mouseClicks) {
			vertices.add(new Vertex(this, vec));
		}
	}
	
	public Collection<Vertex> getVertices() {
		if (this.vertices == null) {
			Dao<Vertex, Integer> dao = Vertex.getDao(Vertex.class);
			try {
				this.vertices = dao.queryForEq("floor_id", this.id_floor);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.vertices;
	}
	
	@Override
	public void save() {
		super.save();
		for (Vertex v : this.getVertices()) {
			v.save();
		}
	}
	
	@DatabaseField (id = true)
	protected int id_floor;
	
	@DatabaseField (canBeNull = false)
	protected float height;
	
	protected Collection<Vertex> vertices;
}
