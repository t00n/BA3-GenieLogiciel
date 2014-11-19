package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

// TODO: Auto-generated Javadoc
/**
 * The Class Project.
 */
@DatabaseTable (tableName = "projects")
public class Project extends Database<Project> {
	
	/**
	 * Instantiates a new project.
	 */
	protected Project() {
		
	}
	
	/**
	 * Instantiates a new project.
	 *
	 * @param name the name
	 * @param width the width
	 * @param length the length
	 */
	public Project(String name, int width, int length) {
		this.setName(name);
		this.width = width;
		this.length = length;
		this.creationDate = new Date();
		this.modificationDate = new Date();
		this.setCurrent(true);
		this.isNew = true;
	}

	public int getId() {
		return id_project;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return this.width;
	}

	public int getLength() {
		return this.length;
	}
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public Date getModificationDate() {
		return this.modificationDate;
	}

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}
	
	public Collection<Stage> getStages() {
		if (this.stages == null) {
			Dao<Stage, Integer> dao = Stage.getDao(Stage.class);
			try {
				this.stages = dao.queryForEq("project_id", this.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.stages;
	}
	
	/**
	 * Gets the stage.
	 *
	 * @param level the level
	 * @return the stage
	 */
	public Stage getStage(int level) {
		for (Stage stage: this.getStages()) {
			if (stage.getLevel() == level) {
				return stage;
			}
		}
		return null;
	}

	/**
	 * Adds the stage.
	 *
	 * @param level the level
	 */
	public void addStage(int level) {
		this.getStages().add(new Stage(this, level));
	}

    /* (non-Javadoc)
     * @see be.ac.ulb.infof307.g05.model.Database#save()
     */
    @Override
    public void save() {
    	super.save();
        for (Stage stage : this.getStages())
            stage.save();
    }
	
	/** The id_project. */
	@DatabaseField (generatedId = true)
	private int id_project;
	
	/** The name. */
	@DatabaseField (canBeNull = false)
	private String name;
	
	/** The width. */
	@DatabaseField (canBeNull = false)
	private int width;
	
	/** The length. */
	@DatabaseField (canBeNull = false)
	private int length;
	
	/** The creation date. */
	@DatabaseField (canBeNull = false)
	protected Date creationDate;
	
	/** The modification date. */
	@DatabaseField (canBeNull = false, version = true)
	protected Date modificationDate;
	
	/** The current. */
	@DatabaseField (canBeNull = false)
	private Boolean current;
	
	/** The stages. */
	protected Collection<Stage> stages;
}
