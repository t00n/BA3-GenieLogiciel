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


@DatabaseTable (tableName = "composite_objects")
public class CompositeObject extends Database<CompositeObject> implements Iterable<CompositeObject> {	
	
	protected CompositeObject() {
		
	}
    
	public CompositeObject(CompositeObject parent, Collection<Vector3f> vertices, Collection<Integer> meshOrder) {
		this.parent = parent;
		this.vertices = toVertex(vertices);
		this.meshOrder = meshOrder;
		this.texture = null;
		this.isNew = true;
	}
	
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
	@Override
	public Iterator<CompositeObject> iterator(){
		/** this method make the object iterable **/
		return this.getChilds().iterator();
	}
	
	public void add(CompositeObject object){
		/** this method add a child **/
		childs.add(object);
	}
	
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

	public boolean remove(CompositeObject object){
		/** this method search recursively an child an remove it if found **/
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
	
	public int size(){
		return childs.size();
	}
	
	// members 
	@DatabaseField (id=true)
	private Integer id_compositeObject;
	@DatabaseField (canBeNull = true, foreign = true)
	private CompositeObject parent;
	@DatabaseField (canBeNull = true, foreign = true, foreignAutoRefresh = true)
	private Texture texture;

	protected Collection<Vertex> vertices;
	protected Collection<Integer> meshOrder;
	protected Collection<CompositeObject> childs;
}
