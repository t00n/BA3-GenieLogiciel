package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;

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

    @Override
    public void save() {
        try {
			this.update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@DatabaseField (generatedId = true)
	protected int id_texture;
	
	@DatabaseField (canBeNull = false)
	protected String name;
	
	@DatabaseField (canBeNull = false)
	protected String fileLocation;
}
