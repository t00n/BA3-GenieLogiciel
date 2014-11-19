package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "projects")
public class Project extends Database<Project> {
	protected Project() {
		
	}
	
	public Project(String name) {
		this.setName(name);
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
	
	public Stage getStage(int level) {
		for (Stage stage: this.getStages()) {
			if (stage.getLevel() == level) {
				return stage;
			}
		}
		return null;
	}

	public void addStage(int level) {
		this.getStages().add(new Stage(this, level));
	}

    @Override
    public void save() {
    	super.save();
        for (Stage stage : this.getStages())
            stage.save();
    }
	
	@DatabaseField (generatedId = true)
	private int id_project;
	
	@DatabaseField (canBeNull = false)
	private String name;
	
	@DatabaseField (canBeNull = false)
	protected Date creationDate;
	
	@DatabaseField (canBeNull = false, version = true)
	protected Date modificationDate;
	
	@DatabaseField (canBeNull = false)
	private Boolean current;
	
	protected Collection<Stage> stages;
}
