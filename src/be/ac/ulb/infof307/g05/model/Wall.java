package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "walls")
public class Wall extends Database<Wall> {
	public Wall() {
		
	}
	
	public Wall(Room room, int width, int height, Collection<Vertex> vertices) {
		this.room = room;
		this.width = width;
		this.height = height;
		this.vertices = vertices;
		this.isNew = true;
	}
	
	@DatabaseField (generatedId = true)
	protected int id_wall;
	
	@DatabaseField (canBeNull = false)
	protected int width;
	
	@DatabaseField (canBeNull = false)
	protected int height;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Room room;
	
	protected Collection<Vertex> vertices;
	
	public int getId() { return this.id_wall; }
	
	public void setId() {
		this.id_wall = Static_ID.getWallID();
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
	
}
