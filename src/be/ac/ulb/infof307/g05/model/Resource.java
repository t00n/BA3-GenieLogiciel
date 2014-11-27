package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The Class Resource.
 */
@DatabaseTable (tableName = "resources")
public class Resource extends Database<Resource> {
	
	/**
	 * Instantiates a new Resource.
	 */
	protected Resource() {
		
	}
	
	/**
	 * Instantiates a new Resource.
	 *
	 * @param name the name
	 * @param fileLocation the file location
	 */
	public Resource(String name, String fileLocation) {
		this.name = name;
		this.fileLocation = fileLocation;
		this.isNew = true;
	}
	
	/** The id_texture. */
	@DatabaseField (generatedId = true)
	protected int id_texture;
	
	/** The name. */
	@DatabaseField (canBeNull = false)
	protected String name;
	
	/** The file location. */
	@DatabaseField (canBeNull = false)
	protected String fileLocation;
}
