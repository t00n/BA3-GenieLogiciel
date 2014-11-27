package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

@DatabaseTable (tableName = "walls")
public class Wall extends Database<Wall> {
	public Wall() {
		
	}
	
	public Wall(Room room, Collection<Vector3f> vectors) {
		this.room = room;
		this.width = 0.2f;
		this.height = 2;
		for (Vector3f vec : vectors) {
			this.getVertices().add(new Vertex(this, vec));
		}
		this.isNew = true;
		this.setId();
	}
	
	@DatabaseField (generatedId = true)
	protected int id_wall;
	
	@DatabaseField (canBeNull = false)
	protected float width;
	
	@DatabaseField (canBeNull = false)
	protected float height;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Room room;
	
	protected Collection<Vertex> vertices;
	
	public int getId() { return this.id_wall; }
	
	public void setId() {
		this.id_wall = Static_ID.getObjectID();
	}

	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public Collection<Vertex> getVertices() {
		if (this.vertices == null) {
			Dao<Vertex, Integer> dao = Vertex.getDao(Vertex.class);
			try {
				this.vertices = dao.queryForEq("wall_id", this.getId());
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

	public void moveTo(Vector3f position) {
		// TODO Auto-generated method stub
		
	}
	
}
