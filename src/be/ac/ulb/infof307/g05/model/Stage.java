package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "stages")
public class Stage extends Database<Stage> {
	protected Stage() {
		
	}
	
	public Stage(Project project, int level, double height) {
		this.project = project;
		this.level = level;
		this.height = height;
	}
	
	@DatabaseField (generatedId = true)
	protected int id_stage;
	
	@DatabaseField (canBeNull = false)
	protected int level;
	
	@DatabaseField (canBeNull = false)
	protected double height;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Project project;
	
	@ForeignCollectionField (eager = false)
	protected ForeignCollection<SceneObject> sceneObjects;
	
	public ForeignCollection<SceneObject> getSceneObjects() { return this.sceneObjects; }
}
