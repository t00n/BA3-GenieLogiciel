package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

// TODO: Auto-generated Javadoc
/**
 * The Class Texture.
 */
@DatabaseTable (tableName = "textures")
public class Texture extends Database<Texture> {
	
	/**
	 * Instantiates a new texture.
	 */
	protected Texture() {
		
	}
	
	/**
	 * Instantiates a new texture.
	 *
	 * @param name the name
	 * @param fileLocation the file location
	 */
	public Texture(String name, String fileLocation) {
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
