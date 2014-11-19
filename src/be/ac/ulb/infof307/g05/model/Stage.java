package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "stages")
public class Stage extends Database<Stage> {
	protected Stage() {
		
	}
	
	public Stage(Project project, int level) {
		this.project = project;
		this.level = level;
		this.current_id = 1;
		this.isNew = true;
	}

	public int getLevel() {
		return this.level;
	}
	
	public CompositeObject getFloor() { return floor; }
	
	public void setFloor(CompositeObject object) {
		this.addObject(null,object);
	}
	
	public void addObject(CompositeObject parent, CompositeObject child) {
		if (this.floor == null) {
			this.floor = child;
			this.floor.setId(this.current_id);
		}
		else {
			this.floor.addChild(parent, child, this.current_id);
		}
		this.current_id++;
	}

    @Override
    public void save() {
        if (this.floor != null) {
        	this.floor.save();
        }
        super.save();
    }
	
	@DatabaseField (generatedId = true)
	protected int id_stage;
	
	@DatabaseField (canBeNull = false)
	protected int level;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Project project;
	
	@DatabaseField (canBeNull = false)
	protected int current_id;
	
	@DatabaseField (canBeNull = true, foreign = true, foreignAutoRefresh = true, unique = true)
	protected CompositeObject floor;
}
