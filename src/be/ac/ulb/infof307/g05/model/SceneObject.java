package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;

public abstract class SceneObject {
	public SceneObject() {
		
	}
	
	@DatabaseField (canBeNull = true, foreign = true)
	public Stage stage;
	
	@DatabaseField (canBeNull = true, foreign = true)
	public CompositeObject parent;
	
	@DatabaseField (canBeNull = false, foreign = true, unique = true)
	private ViewObject object;

}
