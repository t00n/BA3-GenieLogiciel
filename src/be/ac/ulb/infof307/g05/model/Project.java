package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import be.ac.ulb.infof307.g05.model.Order;

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
		this.setCurrent(true);
	}
	
	public static Project buildNewProject(String name, Float width, Float length) {
		Project newProject = new Project(name);
		Stage newStage = new Stage(newProject, 0);
		Collection<Vertex> vertices = new Vector<Vertex>(); //FIXME should get it from Cube
		Collection<Order> meshOrder = new Vector<Order>(); // FIXME should get it from Cube
		CompositeObject newFloor = new CompositeObject(null, vertices, meshOrder);
		return newProject;
	}

    @Override
    public void save() {
        try {
			this.update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.stages != null && !this.stages.isEmpty())
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
	private Boolean current;
	
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

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}
}
