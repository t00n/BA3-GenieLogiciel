package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "walls")
public class Wall extends Database<Wall> {
	public Wall() {
		
	}
	
	@DatabaseField (generatedId = true)
	protected int id_wall;
	
	@DatabaseField (canBeNull = false)
	protected int width;
	
	@DatabaseField (canBeNull = false)
	protected int height;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Room parent;
}
