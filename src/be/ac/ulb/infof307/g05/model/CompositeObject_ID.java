package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "composite_objects_id")
public class CompositeObject_ID extends Database<CompositeObject_ID> {
	@DatabaseField (generatedId = true)
	private int id_compositeObject;
	
	public int getId() { 
		return this.id_compositeObject; 
	}
}
