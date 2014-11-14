package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "composite_objects")
public class CompositeObject extends SceneObject {
	public CompositeObject() {
		
	}
	
	@DatabaseField (generatedId = true)
	private int id_compositeObject;
	
	@ForeignCollectionField (eager = false)
	public ForeignCollection<SceneObject> childs;
}
