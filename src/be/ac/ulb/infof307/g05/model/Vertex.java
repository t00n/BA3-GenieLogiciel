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
	public float x;
	
	/** The y. */
	@DatabaseField (canBeNull = false)
	public float y;
	
	/** The z. */
	@DatabaseField (canBeNull = false)
	public float z;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected CompositeObject object;
	
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
	
	public Vertex(Vector3f vec) {
		this.init(null, vec);
	}
	
	public Vertex(CompositeObject object, Vector3f vec) {
		this.init(object, vec);
	}
	
	private void init(CompositeObject object, Vector3f vec) {
		this.object = object;
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
