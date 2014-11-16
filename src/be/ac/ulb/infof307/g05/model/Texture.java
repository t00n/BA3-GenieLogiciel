package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "textures")
public class Texture extends Database {
	public Texture() {
		
	}
	
	public Texture(String name, String fileLocation) {
		this.name = name;
		this.fileLocation = fileLocation;
	}
	
	@DatabaseField (generatedId = true)
	private int id_texture;
	
	@DatabaseField (canBeNull = false)
	private String name;
	
	@DatabaseField (canBeNull = false)
	private String fileLocation;
}
