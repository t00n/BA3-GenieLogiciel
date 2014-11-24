package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "floors")
public class Floor extends Database<Floor> {
	public Floor() {
		
	}
	
	@DatabaseField (generatedId = true)
	protected int id_floor;
	
	@DatabaseField (canBeNull = false)
	protected int height;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Room parent;
}
