package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;

public abstract class SceneObject extends Database {
	protected SceneObject() {
		
	}

	public SceneObject(Stage stage, Vertex position, Vertex orientation, Mesh mesh, Texture texture) {
		this.stage = stage;
		this.parent = null;
		this.position = position;
		this.orientation = orientation;
		this.mesh = mesh;
		this.texture = texture;
	}
	
	public SceneObject(Stage stage, Vertex position, Vertex orientation, Mesh mesh) {
		this.stage = stage;
		this.parent = null;
		this.position = position;
		this.orientation = orientation;
		this.mesh = mesh;
		this.texture = null;
	}

	public SceneObject(CompositeObject parent, Vertex position, Vertex orientation, Mesh mesh, Texture texture) {
		this.stage = null;
		this.parent = parent;
		this.position = position;
		this.orientation = orientation;
		this.mesh = mesh;
		this.texture = texture;
	}

	public SceneObject(CompositeObject parent, Vertex position, Vertex orientation, Mesh mesh) {
		this.stage = null;
		this.parent = parent;
		this.position = position;
		this.orientation = orientation;
		this.mesh = mesh;
		this.texture = null;
	}
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected Stage stage;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected CompositeObject parent;

	@DatabaseField (canBeNull = false, foreign = true)
	protected Vertex position;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Vertex orientation;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Mesh mesh;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected Texture texture;

}
