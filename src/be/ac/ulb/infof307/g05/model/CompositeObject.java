package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.lang.Math;

/**
 * The Class CompositeObject takes care of the composites objects in the database.
 */
@DatabaseTable (tableName = "composite_objects")
public class CompositeObject extends Database<CompositeObject> implements Iterable<CompositeObject> {	
	
	@DatabaseField (id=true)
	protected int id_compositeObject;
	
	@DatabaseField (unique = true)
	protected String name;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected Resource texture;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected Resource mesh;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected CompositeObject parent;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected Stage stage;

	protected Collection<CompositeObject> childs;
	
	protected Collection<Vertex> vertices;
	
	protected CompositeObject() {
		
	}
    
	public CompositeObject(Collection<Vector3f> vertices) {
		this.init(null, null, vertices, null, null);
	}
	
	public CompositeObject(Stage stage, Collection<Vector3f> vertices) {
		this.init(null, stage, vertices, null, null);
	}
	
	public CompositeObject(CompositeObject parent, Collection<Vector3f> vertices) {
		this.init(parent, null, vertices, null, null);
	}
	
	public CompositeObject(CompositeObject parent, Resource mesh) {
		this.init(parent, null, null, mesh, null);
	}
	
	public CompositeObject(CompositeObject parent, Resource mesh, Resource texture) {
		this.init(parent, null, null, mesh, texture);
	}
	
	private void init(CompositeObject parent, Stage stage, Collection<Vector3f> vertices, Resource mesh, Resource texture) {
		this.parent = parent;
		this.stage = stage;
		this.vertices = new ArrayList<Vertex>();
		for (Vector3f vec : vertices) {
			this.vertices.add(new Vertex(this, vec));
		}
		this.mesh = mesh;
		this.texture = texture;
		this.isNew = true;
	}
	
	public int getId() {
		return this.id_compositeObject;
	}
	
	public void setId() {
		this.id_compositeObject = Static_ID.getObjectID();
	}
	
	public String getName() {
		return this.name;
	}

	public Resource getTexture() {
		return this.texture;
	}
	
	public Resource getMesh() {
		return this.mesh;
	}

	public CompositeObject getById(int id) {
		if (this.getId() == id) {
			return this;
		}
		else {
			for (CompositeObject child : this.getChilds()) {
				CompositeObject ret = child.getById(id);
				if (ret != null)
				{
					return ret;
				}
			}
		}
		return null;
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
	
	public Collection<Vertex> getVertices() {
		if (this.vertices == null) {
			Dao<Vertex, Integer> dao = Vertex.getDao(Vertex.class);
			try {
				this.vertices = dao.queryForEq("object_id", this.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.vertices;
	}

	public void setVertices(Collection<Vector3f> vectors) {
		this.vertices = new ArrayList<Vertex>();
		for (Vector3f vec : vectors) {
			this.vertices.add(new Vertex(this, vec));
		}
		
	}
	
	public Vector3f getDifference(Vector3f position) {
		return ((Vertex)this.getVertices().toArray()[0]).toVector3f().subtract(position);
	}

	public void moveTo(Vector3f difference) {
		for (Vertex vertex : this.getVertices()) {
			vertex.x -= difference.x;
			vertex.y -= difference.y;
			vertex.z -= difference.z;
		}
	}

	public void rotateAroundY(float range) {
		ArrayList<Vertex> tmp = (ArrayList<Vertex>) this.getVertices();
		float posX;
		float posZ;
		for (int i = 0; i < (tmp.size()); ++i) {
			posX = tmp.get(i).x;
			posZ = tmp.get(i).z;
			tmp.get(i).x = (float) ((posX*Math.cos(range)) - (posZ*Math.sin(range)));
			tmp.get(i).z = (float) ((posZ*Math.cos(range)) + (posX*Math.sin(range)));
		}
	}
	
    @Override
    public void save() {
        if (this.texture != null)
            this.texture.save();
        if (this.mesh != null)
        	this.mesh.save();
        super.save();
        for (CompositeObject object : this.getChilds())
        	object.save();
        for (Vertex vertex : this.getVertices())
        	vertex.save();
    }
	
	@Override
	public Iterator<CompositeObject> iterator(){
		/** this method make the object iterable **/
		return this.getChilds().iterator();
	}
	
	public void add(CompositeObject object){
		this.getChilds().add(object);
	}

	public void addChild(CompositeObject parent, Collection<Vector3f> vectors) {
		if (parent == this) {
			CompositeObject child = new CompositeObject(parent, vectors);
			this.add(child);
			child.setId();
		}
		else
		{
			for (CompositeObject c: this) {
				c.addChild(parent, vectors);
			}
		}
	}

	public boolean remove(CompositeObject object){
		boolean isFound = false;

		if(this.getChilds().contains(object)){
			this.getChilds().remove(object);
		}else{
			for(CompositeObject child:this.getChilds()){
				if(!isFound)
					isFound = child.remove(object);
			}
		}
		return isFound;
	}
	
	public int size(){
		return this.getChilds().size();
	}
}
