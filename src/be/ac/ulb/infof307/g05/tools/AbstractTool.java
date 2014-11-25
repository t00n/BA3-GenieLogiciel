package be.ac.ulb.infof307.g05.tools;

import java.util.ArrayList;

import com.jme3.math.Vector3f;

public abstract class AbstractTool {
	protected ArrayList<Vector3f> mousePositions;
	protected ArrayList<Vector3f> mouseClicks;
	
	public AbstractTool() {
		mousePositions = new ArrayList<Vector3f>();
		mouseClicks = new ArrayList<Vector3f>();
	}
	
	public void purge() {
		this.mouseClicks.clear();
		this.mousePositions.clear();
	}
	
	public void addPosition(Vector3f vector) {
		mousePositions.add(vector);
	}
	
	public void addClick(Vector3f vector) {
		mouseClicks.add(vector);
	}
	
	public abstract void use();
}
