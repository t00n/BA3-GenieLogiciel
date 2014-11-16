package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "simple_objects")
public class SimpleObject extends SceneObject {
	protected SimpleObject() {
		
	}
	public SimpleObject(Stage stage, Vertex position,
			Vertex orientation, Mesh mesh, Texture texture) {
		super(stage, position, orientation, mesh, texture);
	}
	public SimpleObject(Stage stage, Vertex position,
			Vertex orientation, Mesh mesh) {
		super(stage, position, orientation, mesh);
	}	
	public SimpleObject(CompositeObject parent, Vertex position,
			Vertex orientation, Mesh mesh, Texture texture) {
		super(parent, position, orientation, mesh, texture);
	}	
	public SimpleObject(CompositeObject parent, Vertex position,
			Vertex orientation, Mesh mesh) {
		super(parent, position, orientation, mesh);
	}
	
	@DatabaseField (generatedId = true)
	protected int id_simpleObject;
}
