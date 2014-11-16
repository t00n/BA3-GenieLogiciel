package be.ac.ulb.infof307.g05.model;

import java.util.Date;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "projects")
public class Project extends Database {
	public Project() {
		
	}
	
	public Project(String name) {
		this.name = name;
		this.creationDate = new Date();
		this.modificationDate = new Date();
		this.current = true;
	}
	
	@DatabaseField (generatedId = true)
	private int id_project;
	
	@DatabaseField (canBeNull = false)
	private String name;
	
	@DatabaseField (canBeNull = false)
	private Date creationDate;
	
	@DatabaseField (canBeNull = false)
	private Date modificationDate;
	
	@DatabaseField (canBeNull = false)
	private Boolean current;
	
	@ForeignCollectionField (eager = false)
	public ForeignCollection<Stage> stages;
}
