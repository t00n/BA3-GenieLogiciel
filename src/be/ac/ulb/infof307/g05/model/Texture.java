package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "textures")
public class Texture extends Database<Texture> {
	protected Texture() {
		
	}
	
	public Texture(String name, String fileLocation) {
		this.name = name;
		this.fileLocation = fileLocation;
	}
	
	@DatabaseField (generatedId = true)
	protected int id_texture;
	
	@DatabaseField (canBeNull = false)
	protected String name;
	
	@DatabaseField (canBeNull = false)
	protected String fileLocation;
}
