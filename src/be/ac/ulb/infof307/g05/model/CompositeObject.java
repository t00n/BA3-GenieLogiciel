package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

import java.sql.SQLException;
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
		this.meshOrder = toOrder(meshOrder);
		this.texture = null;
		this.isNew = true;
	}
	
	private Collection<Vertex> toVertex(Collection<Vector3f> vertices) {
		Collection<Vertex> ret = new Vector<Vertex>();
		for (Vector3f vec: vertices) {
			Vertex vertex = new Vertex(this, vec);
			ret.add(vertex);
		}
		return ret;
	}
	
	private Collection<Order> toOrder(Collection<Integer> meshOrder) {
		Collection<Order> ret = new Vector<Order>();
		for (Integer order: meshOrder) {
			Order ord = new Order(this, order);
			ret.add(ord);
		}
		return ret;
	}

	public Integer getId() {
		return this.id_compositeObject;
	}
	
	public void setId(Integer id) {
		this.id_compositeObject = id;
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
		for (Order order: this.getMeshOrder())
		{
			ret[i] = order.getOrder();
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
	
	public Collection<Order> getMeshOrder() {
		if (this.meshOrder == null) {
			Dao<Order, Integer> dao = Order.getDao(Order.class);
			try {
				this.meshOrder = dao.queryForEq("referent_id", this.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
        for (Order order : this.getMeshOrder())  
            order.save();
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
			child.setId(id);
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
	@DatabaseField (unique = true, id=true)
	private Integer id_compositeObject;
	@DatabaseField (canBeNull = true, foreign = true)
	private CompositeObject parent;
	@DatabaseField (canBeNull = true, foreign = true, foreignAutoRefresh = true)
	private Texture texture;

	protected Collection<Vertex> vertices;
	protected Collection<Order> meshOrder;
	protected Collection<CompositeObject> childs;
}
