package be.ac.ulb.infof307.g05.model;

import java.util.Collection;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "projects")
public class Project extends Database<Project> {
	protected Project() {
		
	}
	
	public Project(String name) {
		this.setName(name);
		this.creationDate = new Date();
		this.modificationDate = new Date();
		this.current = true;
	}

    @Override
    public void save() {
        this.createOrUpdate();
        if (this.stages && !this.stages.isEmpty())
            for (Stage stage : this.stages)
                stage.save();
    }
	
	@DatabaseField (generatedId = true)
	protected int id_project;
	
	@DatabaseField (canBeNull = false)
	private String name;
	
	@DatabaseField (canBeNull = false)
	protected Date creationDate;
	
	@DatabaseField (canBeNull = false, version = true)
	protected Date modificationDate;
	
	@DatabaseField (canBeNull = false)
	protected Boolean current;
	
	@ForeignCollectionField (eager = true)
	protected Collection<Stage> stages;
	
	// TODO change ForeignCollection<>
	public Collection<Stage> getStages() { return this.stages; }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
