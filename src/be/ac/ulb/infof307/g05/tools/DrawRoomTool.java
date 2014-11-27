package be.ac.ulb.infof307.g05.tools;

import java.util.ArrayList;
import java.util.Collection;

import com.jme3.math.Vector3f;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Stage;

public class DrawRoomTool extends AbstractStageTool {
	public DrawRoomTool(Stage stage) {
		super(stage);
	}
	
	protected Collection<Vector3f> buildFloor(Vector3f cursor) {
		Collection<Vector3f> tmp = new ArrayList<Vector3f>();
		for (Vector3f vec : this.mouseClicks) {
			tmp.add(vec.clone());
		}
		if (cursor != null)
			tmp.add(cursor.clone());
		for (Vector3f vec : this.mouseClicks) {
			tmp.add(new Vector3f(vec).add(new Vector3f(0,.1f,0)));
		}
		if (cursor != null)
			tmp.add(new Vector3f(cursor).add(new Vector3f(0,.1f,0)));
		return tmp;
	}
	
	@Override
	public void addPosition(Vector3f cursor) {
		super.addPosition(cursor);
		if (this.mouseClicks.size() >= 2) {
			this.currentObject.setVertices(this.buildFloor(cursor));
		}
	}
	
	@Override
	public void addClick(Vector3f click) {
		super.addClick(click);
		if (this.mouseClicks.size() == 2) {
			this.currentObject = new CompositeObject(this.currentStage, this.buildFloor(click));
			this.currentObject.setId("Floor");
			this.currentStage.getCompositeObjects().add(this.currentObject);
		}
		if (this.mouseClicks.size() >= 3) {
			this.currentObject.setVertices(this.buildFloor(click));
		}
	}
	
	@Override
	public void use() {
		if (this.mouseClicks.size() >= 3) {
			this.currentStage.addCompositeObject("Floor", this.buildFloor(null));
			this.purge();
		}
	}
	
	@Override 
	public void purge() {
		super.purge();
		this.currentStage.getCompositeObjects().remove(currentObject);
	}

}
