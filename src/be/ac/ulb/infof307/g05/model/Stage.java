package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "stages")
public class Stage extends Database<Stage> {
	protected Stage() {
		
	}
	
	public Stage(Project project, int level, CompositeObject floor) {
		this.project = project;
		this.level = level;
		this.current_id = 1;
		this.floor = floor;
	}
	
	public Stage(Project project, int level) {
		this.project = project;
		this.level = level;
		this.current_id = 1;
	}

    @Override
    public void save() {
        if (this.floor != null)
            this.floor.save();
        try {
			this.update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@DatabaseField (generatedId = true)
	protected int id_stage;
	
	@DatabaseField (canBeNull = false)
	protected int level;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Project project;
	
	@DatabaseField (canBeNull = false)
	protected int current_id;
	
	@DatabaseField (canBeNull = false, foreign = true, foreignAutoRefresh = true, unique = true)
	protected CompositeObject floor;
	public CompositeObject getFloor() { return floor; }

	public void setFloor(CompositeObject floor) {
		this.floor = floor;
		floor.setId(this.current_id);
		this.current_id++;
	}
	
	public void addObject(CompositeObject parent, CompositeObject child) {
		this.floor.addChild(parent, child, this.current_id);
		this.current_id++;
	}

	public int getLevel() {
		return this.level;
	}
}
