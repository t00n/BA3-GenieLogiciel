package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

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
	
	public int getId() { return this.id_stage; }
	
	public CompositeObject getById(int id) {
		for (CompositeObject object : this.getCompositeObjects()) {
			CompositeObject ret = object.getById(id);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}
	
	public Collection<CompositeObject> getCompositeObjects() {
		if (this.compositeObjects == null) {
			Dao<CompositeObject, Integer> dao = CompositeObject.getDao(CompositeObject.class);
			try {
				this.compositeObjects = dao.queryForEq("stage_id", this.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.compositeObjects;
	}
	
	public void addCompositeObject(Collection<Vector3f> vectors) {
		this.getCompositeObjects().add(new CompositeObject(this, vectors));
	}
	
	public void addCompositeObject(CompositeObject parent, Collection<Vector3f> vectors) {
		for (CompositeObject object : this.getCompositeObjects()) {
			object.addChild(parent, vectors);
		}
	}

    /* (non-Javadoc)
     * @see be.ac.ulb.infof307.g05.model.Database#save()
     */
    @Override
    public void save() {
        super.save();
        for (CompositeObject object : this.getCompositeObjects()) {
        	object.save();
        }
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
	
	protected Collection<CompositeObject> compositeObjects;
}
