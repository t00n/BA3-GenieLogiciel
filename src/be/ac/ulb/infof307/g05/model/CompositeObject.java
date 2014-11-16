package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "composite_objects")
public class CompositeObject extends SceneObject {
	protected CompositeObject() {
		
	}
	public CompositeObject(Stage stage, Vertex position,
			Vertex orientation, Mesh mesh, Texture texture) {
		super(stage, position, orientation, mesh, texture);
	}
	public CompositeObject(Stage stage, Vertex position,
			Vertex orientation, Mesh mesh) {
		super(stage, position, orientation, mesh);
	}	
	public CompositeObject(CompositeObject parent, Vertex position,
			Vertex orientation, Mesh mesh, Texture texture) {
		super(parent, position, orientation, mesh, texture);
	}	
	public CompositeObject(CompositeObject parent, Vertex position,
			Vertex orientation, Mesh mesh) {
		super(parent, position, orientation, mesh);
	}

	@DatabaseField (generatedId = true)
	protected int id_compositeObject;
	
	@ForeignCollectionField (eager = false)
	protected ForeignCollection<SceneObject> childs;
	
	
	//TODO change ForeignCollection<> to something else
	public ForeignCollection<SceneObject> getChilds() { return this.childs; }
}
