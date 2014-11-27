package be.ac.ulb.infof307.g05.tools;

import java.util.ArrayList;

import be.ac.ulb.infof307.g05.model.CompositeObject;

import com.jme3.math.Vector3f;

public abstract class AbstractTool {
	protected ArrayList<Vector3f> mousePositions;
	protected ArrayList<Vector3f> mouseClicks;
	protected ArrayList<String> idCollisions;
	
	protected static ArrayList<CompositeObject> selectedObjects;
	
	public AbstractTool() {
		mousePositions = new ArrayList<Vector3f>();
		mouseClicks = new ArrayList<Vector3f>();
		idCollisions = new ArrayList<String>();
	}
	
	public void purge() {
		this.mouseClicks.clear();
		this.mousePositions.clear();
		this.idCollisions.clear();
	}
	
	public void addPosition(Vector3f vector) {
		mousePositions.add(vector);
	}
	
	public void addClick(Vector3f vector) {
		mouseClicks.add(vector);
	}
	
	public void addCollision(String id) {
		this.idCollisions.add(id);
	}
	
	public void addObject(CompositeObject object) {
		selectedObjects.add(object);
	}
	
	public abstract void use();
}
