package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

@DatabaseTable (tableName = "stages")
public class Stage extends Database<Stage> {
	protected Stage() {
		
	}
	
	public Stage(Project project, int level, CompositeObject floor) {
		this.project = project;
		this.level = level;
		this.floor = floor;
	}
	
	public Stage(Project project, int level) {
		this.project = project;
		this.level = level;
		this.floor = new CompositeObject(null, new Vector<Vertex>());
	}
	
	@DatabaseField (generatedId = true)
	protected int id_stage;
	
	@DatabaseField (canBeNull = false)
	protected int level;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Project project;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected CompositeObject floor;
}
