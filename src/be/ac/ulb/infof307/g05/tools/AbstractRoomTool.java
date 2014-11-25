package be.ac.ulb.infof307.g05.tools;

import be.ac.ulb.infof307.g05.model.Room;

public abstract class AbstractRoomTool extends AbstractTool {
	protected Room currentRoom;
	
	public AbstractRoomTool(Room room) {
		this.currentRoom = room;
	}
}
