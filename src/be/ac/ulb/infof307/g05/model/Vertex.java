package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

/**
 * The Class Vertex.
 */
@DatabaseTable (tableName = "vertexes")
public class Vertex extends Database<Vertex> {
	
	/** The id_vertex. */
	@DatabaseField (generatedId = true)
	protected int id_vertex;
	
	/** The x. */
	@DatabaseField (canBeNull = false)
	protected float x;
	
	/** The y. */
	@DatabaseField (canBeNull = false)
	protected float y;
	
	/** The z. */
	@DatabaseField (canBeNull = false)
	protected float z;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected Floor floor;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected Wall wall;
	
	/**
	 * Instantiates a new vertex.
	 */
	protected Vertex() {
		
	}
	
	/**
	 * Instantiates a new vertex.
	 *
	 * @param referent the referent
	 * @param vec the vec
	 */
	
	public Vertex(Floor floor, Vector3f vec) {
		this.init(floor, null, vec);
	}
	
	public Vertex(Wall wall, Vector3f vec) {
		this.init(null, wall, vec);
	}
	
	private void init(Floor floor, Wall wall, Vector3f vec) {
		this.floor = floor;
		this.wall = wall;
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
		this.isNew = true;
	}
	
	/**
	 * To vector3f.
	 *
	 * @return the vector3f
	 */
	public Vector3f toVector3f() {
		return new Vector3f(this.x, this.y, this.z);
	}
}
