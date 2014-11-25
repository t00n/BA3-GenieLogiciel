package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "rooms")
public class Room extends Database<Room> {
	public Room() {
		
	}
	
	@DatabaseField (generatedId = true)
	protected int id_room;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Stage stage;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Floor floor;
	
	protected Collection<CompositeObject> compositeObjects;
	protected Collection<Wall> walls;
	
	public int getId() {
		return this.id_room;
	}
	
	public Floor getFloor() {
		return this.floor;
	}
	
	public Collection<CompositeObject> getCompositeObjects() {
		if (this.compositeObjects == null) {
			Dao<CompositeObject, Integer> dao = CompositeObject.getDao(CompositeObject.class);
			try {
				this.compositeObjects = dao.queryForEq("room_id", this.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.compositeObjects;
	}
	
	public Collection<Wall> getWalls() {
		if (this.walls == null) {
			Dao<Wall, Integer> dao = Wall.getDao(Wall.class);
			try {
				this.walls = dao.queryForEq("room_id", this.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.walls;
	}
	
	@Override
	public void save() {
		this.floor.save();
		super.save();
		for (Wall w: this.getWalls()) {
			w.save();
		}
		for (CompositeObject obj: this.getCompositeObjects()) {
			obj.save();
		}
	}
}
