package be.ac.ulb.infof307.g05.model;

import java.util.ArrayList;

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

	public void moveTo(Vector3f difference) {
		this.beginning.x -= difference.x;
		this.beginning.y -= difference.y;
		this.beginning.z -= difference.z;
		this.end.x -= difference.x;
		this.end.y -= difference.y;
		this.end.z -= difference.z;
	}
	
	public void rotateAroundY(float angle) {
		float posX;
		float posZ;
		posX = this.beginning.x;
		posZ = this.beginning.z;
		this.beginning.x = (float) ((posX*Math.cos(angle)) - (posZ*Math.sin(angle)));
		this.beginning.z = (float) ((posZ*Math.cos(angle)) + (posX*Math.sin(angle)));
		posX = this.end.x;
		posZ = this.end.z;
		this.end.x = (float) ((posX*Math.cos(angle)) - (posZ*Math.sin(angle)));
		this.end.z = (float) ((posZ*Math.cos(angle)) + (posX*Math.sin(angle)));
	}
	
}
