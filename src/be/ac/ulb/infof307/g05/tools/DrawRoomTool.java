package be.ac.ulb.infof307.g05.tools;

import java.util.ArrayList;
import java.util.Collection;

import com.jme3.math.Vector3f;

import be.ac.ulb.infof307.g05.model.Room;
import be.ac.ulb.infof307.g05.model.Stage;

public class DrawRoomTool extends AbstractStageTool {
	public DrawRoomTool(Stage stage) {
		super(stage);
	}
	
	@Override
	public void addPosition(Vector3f cursor) {
		super.addPosition(cursor);
		if (this.mouseClicks.size() >= 2) {
			Collection<Vector3f> tmp = new ArrayList<Vector3f>();
			for (Vector3f vec : this.mouseClicks) {
				tmp.add(vec.clone());
			}
			tmp.add(cursor.clone());
			this.room.getFloor().setVertices(tmp);
		}
	}
	
	@Override
	public void addClick(Vector3f click) {
		super.addClick(click);
		if (this.mouseClicks.size() == 2) {
			this.room = new Room(null, "auto", this.mouseClicks);
			this.currentStage.getRooms().add(room);
		}
		if (this.mouseClicks.size() >= 3) {
			this.room.getFloor().setVertices(this.mouseClicks);
		}
	}
	
	@Override
	public void use() {
		if (this.mouseClicks.size() >= 3) {
			this.currentStage.addRoom(room.getName(), this.mouseClicks);
			this.purge();
		}
	}
	
	@Override 
	public void purge() {
		super.purge();
		this.currentStage.getRooms().remove(room);
	}

}
