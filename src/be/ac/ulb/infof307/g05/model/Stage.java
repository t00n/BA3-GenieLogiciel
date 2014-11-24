package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The Class Stage.
 */
@DatabaseTable (tableName = "stages")
public class Stage extends Database<Stage> {
	
	/**
	 * Instantiates a new stage.
	 */
	protected Stage() {
		
	}
	
	/**
	 * Instantiates a new stage.
	 *
	 * @param project the project
	 * @param level the level
	 */
	public Stage(Project project, int level) {
		this.project = project;
		this.level = level;
		this.isNew = true;
	}

	public int getLevel() {
		return this.level;
	}
	
	public CompositeObject getFloor() { return floor; }
	
	/**
	 * Adds the object.
	 *
	 * @param parent the parent
	 * @param child the child
	 */
	public void addObject(CompositeObject parent, CompositeObject child) {
		if (this.floor == null) {
			this.floor = child;
			this.floor.setId();
		}
		else {
			this.floor.addChild(parent, child);
		}
	}

    /* (non-Javadoc)
     * @see be.ac.ulb.infof307.g05.model.Database#save()
     */
    @Override
    public void save() {
        if (this.floor != null) {
        	this.floor.save();
        }
        super.save();
    }
	
	/** The id_stage. */
	@DatabaseField (generatedId = true)
	protected int id_stage;
	
	/** The level. */
	@DatabaseField (canBeNull = false)
	protected int level;
	
	/** The project. */
	@DatabaseField (canBeNull = false, foreign = true)
	protected Project project;
	
	/** The floor. */
	@DatabaseField (canBeNull = true, foreign = true, foreignAutoRefresh = true)
	protected CompositeObject floor;
}
