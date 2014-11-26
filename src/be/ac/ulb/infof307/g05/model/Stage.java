package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.Collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

/**
 * The Class Stage.
 */
@DatabaseTable (tableName = "stages")
public class Stage extends Database<Stage> {
	
	/**
	 * Instantiates a new stage.
	 */
	protected Stage() {
		
	}
	
	/**
	 * Instantiates a new stage.
	 *
	 * @param project the project
	 * @param level the level
	 */
	public Stage(Project project, int level) {
		this.project = project;
		this.level = level;
		this.isNew = true;
	}

	public int getLevel() {
		return this.level;
	}
	
	public int getId() { return this.id_stage; }
	
	public Room getRoomByCollisionId(int id) {
		for (Room room : this.getRooms()) {
			if (room.ownsId(id)){
				return room;
			}
		}
		return null;
	}
	
	public Collection<Room> getRooms() {
		if (this.rooms == null) {
			Dao<Room, Integer> dao = Room.getDao(Room.class);
			try {
				this.rooms = dao.queryForEq("stage_id", this.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.rooms;
	}
	
	public Room getRoom(String name) {
		for (Room room : this.getRooms()) {
			if (room.getName() == name)
				return room;
		}
		return null;
	}
	
	public void addRoom(String name, Collection<Vector3f> vertices){
		this.getRooms().add(new Room(this, name, vertices));
	}

    /* (non-Javadoc)
     * @see be.ac.ulb.infof307.g05.model.Database#save()
     */
    @Override
    public void save() {
        super.save();
        for (Room room : this.getRooms()) {
        	room.save();
        }
    }
	
	/** The id_stage. */
	@DatabaseField (generatedId = true)
	protected int id_stage;
	
	/** The level. */
	@DatabaseField (canBeNull = false)
	protected int level;
	
	/** The project. */
	@DatabaseField (canBeNull = false, foreign = true)
	protected Project project;
	
	protected Collection<Room> rooms;
}
