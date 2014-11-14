package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "simple_objects")
public class SimpleObject extends SceneObject {
	public SimpleObject() {
		
	}
	
	@DatabaseField (generatedId = true)
	private int id_simpleObject;
}
