package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "view_objects")
public class ViewObject extends Database {
	public ViewObject() {
		
	}
	
	@DatabaseField (generatedId = true)
	private int id_viewObject;

	@DatabaseField (canBeNull = false, foreign = true)
	private Vertex position;
	
	@DatabaseField (canBeNull = false, foreign = true)
	private Vertex orientation;
	
	@DatabaseField (canBeNull = false, foreign = true)
	private Mesh mesh;
	
	@DatabaseField (canBeNull = true, foreign = true)
	private Texture texture;
}
