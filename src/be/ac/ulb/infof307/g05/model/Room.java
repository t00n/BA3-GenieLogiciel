package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "rooms")
public class Room extends Database<Room> {
	public Room() {
		
	}
	
	@DatabaseField (generatedId = true)
	protected int id_room;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Stage parent;
}
