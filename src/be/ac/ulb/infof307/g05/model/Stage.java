package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "stages")
public class Stage extends Database {
	public Stage() {
		
	}
	
	@DatabaseField (generatedId = true)
	private int id_stage;
	
	@DatabaseField (canBeNull = false)
	private int level;
	
	@DatabaseField (canBeNull = false)
	private double height;
	
	@DatabaseField (canBeNull = false, foreign = true)
	public Project project;
	
	@ForeignCollectionField (eager = false)
	public ForeignCollection<SceneObject> sceneObjects;
}
