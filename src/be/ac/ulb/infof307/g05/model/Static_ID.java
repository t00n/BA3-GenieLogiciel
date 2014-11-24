package be.ac.ulb.infof307.g05.model;

import java.sql.SQLException;
import java.util.HashMap;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * The Class CompositeObject_ID gives back an id of a composite object with the database
 */
@DatabaseTable (tableName = "static_ids")
public class Static_ID {
	@DatabaseField (id = true)
	private String id_static;
	
	@DatabaseField (canBeNull = false)
	private int id_iterate;
	
	private Static_ID() {
		
	}
	
	private Static_ID(String id) {
		this.id_static = id;
		this.id_iterate = 1;
	}
	
	private static Dao<Static_ID, String> dao;
	private static HashMap<String, Static_ID> id_map;
	
	private static int getID(String id) {
		if (dao == null) {
			try {
				dao = DaoManager.createDao(Database.getConnectionSource(), Static_ID.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		int ret = 0;
		try {
			Static_ID object = null;
			if (id_map.containsKey(id)) {
				object = id_map.get(id);
			}
			else {
				object = dao.queryForId(id);
				if (object == null) {
					object = new Static_ID(id);
					dao.create(object);
				}
				id_map.put(id, object);
			}
			ret = object.id_iterate;
			++object.id_iterate;
			dao.update(object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static int getCompositeObjectID() {
		return getID("composite_object");
	}
	
	public static int getRoomID() {
		return getID("room");
	}
}
