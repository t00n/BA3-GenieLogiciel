package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

@DatabaseTable (tableName = "walls")
public class Wall extends Database<Wall> {
	public Wall() {
		
	}
	
	public Wall(Room room, Vector3f beginning, Vector3f end) {
		this.room = room;
		this.width = 0.2f;
		this.height = 2;
		this.beginning = new Vertex(beginning);
		this.end = new Vertex(end);
		this.isNew = true;
		this.setId();
	}
	
	@DatabaseField (generatedId = true)
	protected int id_wall;
	
	@DatabaseField (canBeNull = false)
	protected float width;
	
	@DatabaseField (canBeNull = false)
	protected float height;
	
	@DatabaseField (canBeNull = false, foreign = true, foreignAutoRefresh = true)
	protected Vertex beginning;
	
	@DatabaseField (canBeNull = false, foreign = true, foreignAutoRefresh = true)
	protected Vertex end;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Room room;
	
	public int getId() { return this.id_wall; }
	
	public void setId() {
		this.id_wall = Static_ID.getObjectID();
	}

	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public Vertex getBeginning() {
		return this.beginning;
	}
	
	public Vertex getEnd() {
		return this.end;
	}
	
	@Override
	public void save() {
		this.beginning.save();
		this.end.save();
		super.save();
	}

	public void moveTo(Vector3f position) {
		this.beginning.x += position.x;
		this.beginning.y += position.y;
		this.beginning.z += position.z;
		this.end.x += position.x;
		this.end.y += position.y;
		this.end.z += position.z;
	}
	
}
