package be.ac.ulb.infof307.g05.model;

import be.ac.ulb.infof307.g05.Cube;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * The Class CompositeObject takes care of the composites objects in the database.
 */
@DatabaseTable (tableName = "composite_objects")
public class CompositeObject extends Database<CompositeObject> implements Iterable<CompositeObject> {	
	
	/**
	 * Instantiates a new composite object.
	 */
	protected CompositeObject() {
		
	}
    
	/**
	 * Instantiates a new composite object.
	 *
	 * @param parent the parent
	 * @param vertices the vertices
	 * @param meshOrder the mesh order
	 */
	public CompositeObject(CompositeObject parent, Collection<Vector3f> vertices, Collection<Integer> meshOrder) {
		this.parent = parent;
		this.vertices = toVertex(vertices);
		this.meshOrder = meshOrder;
		this.texture = null;
		this.isNew = true;
	}
	
	/**
	 * Transform a collection of vertices into vertexes
	 *
	 * @param vertices the vertices
	 * @return the collection
	 */
	private Collection<Vertex> toVertex(Collection<Vector3f> vertices) {
		Collection<Vertex> ret = new ArrayList<Vertex>();
		for (Vector3f vec: vertices) {
			Vertex vertex = new Vertex(this, vec);
			ret.add(vertex);
		}
		return ret;
	}

	public Integer getId() {
		return this.id_compositeObject;
	}
	
	/**
	 * Sets the id of the composite object.
	 * 
	 * @see CompositeObject_ID
	 */
	public void setId() {
		CompositeObject_ID id = new CompositeObject_ID();
		try {
			id.create();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.id_compositeObject = id.getId();
	}

	public Texture getTexture() {
		return this.texture;
	}
	
	public Vector3f[] getVerticesAsVector3f() {
		Vector3f[] ret = new Vector3f[this.getVertices().size()];
		int i = 0;
		for (Vertex vertex: this.getVertices()) {
			ret[i] = vertex.toVector3f();
			++i;
		}
		return ret;
	}
	
	public int[] getMeshOrderAsInt() {
		int[] ret = new int[this.getMeshOrder().size()];
		int i = 0;
		for (Integer order: this.getMeshOrder()) {
			ret[i] = order;
			++i;
		}
		return ret;
	}
	
	// Database
	public Collection<Vertex> getVertices() {
		if (this.vertices == null) {
			Dao<Vertex, Integer> dao = Vertex.getDao(Vertex.class);
			try {
				this.vertices = dao.queryForEq("referent_id", this.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.vertices;
	}
	
	public Collection<Integer> getMeshOrder() {
		ArrayList<Vertex> vectors = (ArrayList<Vertex>) this.getVertices();
		Cube cube = new Cube(vectors.get(0), vectors.get(7));
		this.meshOrder = cube.getOrder();
		return this.meshOrder;
	}

	public Collection<CompositeObject> getChilds() {
		if (this.childs == null) {
			Dao<CompositeObject, Integer> dao = CompositeObject.getDao(CompositeObject.class);
			try {
				this.childs = dao.queryForEq("parent_id", this.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.childs;
	}
	
    /* (non-Javadoc)
     * @see be.ac.ulb.infof307.g05.model.Database#save()
     */
	public CompositeObject getWithId(int id) {
		CompositeObject ret = null;
		if (id == this.getId()) {
			ret = this;
		}
		else {
			for (CompositeObject child: this.getChilds())
			{
				ret = child.getWithId(id);
			}
		}
		return ret;
	}
	
	public void extendUp(float range) {
		ArrayList<Vertex> tmp = (ArrayList<Vertex>) this.getVertices();
		for (int i = 4; i < 8; ++i) {
			tmp.get(i).y += range;
		}
	}
	
    @Override
    public void save() {
        if (this.texture != null)
            this.texture.save();
        super.save();
        for (Vertex position : this.getVertices())
            position.save();
        for (CompositeObject object : this.getChilds())
        	object.save();
    }
	
	// Iterable
	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<CompositeObject> iterator(){
		/** this method make the object iterable **/
		return this.getChilds().iterator();
	}
	
	/**
	 * Adds a child.
	 *
	 * @param object the object
	 */
	public void add(CompositeObject object){
		childs.add(object);
	}
	
	/**
	 * Adds a child.
	 *
	 * @param parent the parent
	 * @param child the child
	 * @param id the id
	 */
	public void addChild(CompositeObject parent, CompositeObject child, Integer id) {
		if (parent == this) {
			this.add(child);
			child.setId();
		}
		else
		{
			for (CompositeObject c: this) {
				c.addChild(parent, child, id);
			}
		}
	}

	/**
	 * Removes an object by searching recursively an child and remove it if found.
	 *
	 * @param object the object
	 * @return true, if successful
	 */
	public boolean remove(CompositeObject object){
		boolean isFound = false;

		if(childs.contains(object)){
			childs.remove(object);
		}else{
			for(CompositeObject child:this.childs){
				if(!isFound)
					isFound = child.remove(object);
			}
		}
		return isFound;
	}
	
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size(){
		return childs.size();
	}
	
	// members 
	/** The id_composite object. */
	@DatabaseField (id=true)
	private Integer id_compositeObject;
	
	/** The parent. */
	@DatabaseField (canBeNull = true, foreign = true)
	private CompositeObject parent;
	
	/** The texture. */
	@DatabaseField (canBeNull = true, foreign = true, foreignAutoRefresh = true)
	private Texture texture;

	/** The vertices. */
	protected Collection<Vertex> vertices;
	
	/** The mesh order. */
	protected Collection<Integer> meshOrder;

	/** The childs. */
	protected Collection<CompositeObject> childs;
}
