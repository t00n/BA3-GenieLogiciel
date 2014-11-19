package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


@DatabaseTable (tableName = "composite_objects")
public class CompositeObject extends Database<CompositeObject> implements Iterable<CompositeObject> {
	
	@DatabaseField (generatedId = true)
	private Integer id_compositeObject;
	@DatabaseField (canBeNull = true, foreign = true)
	private CompositeObject parent;
	@DatabaseField (canBeNull = true, foreign = true, foreignAutoRefresh = true)
	private Texture texture;
	@ForeignCollectionField (eager = false)
	protected Collection<Vertex> vertices;
	@ForeignCollectionField (eager = false)
	protected Collection<Order> meshOrder;
	@ForeignCollectionField (eager = false)
	protected Collection<CompositeObject> childs;
	
	
	protected CompositeObject() {
		
	}

    @Override
    public void save() {
        if (this.texture != null)
            this.texture.save();
        try {
			this.update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.vertices != null && !this.vertices.isEmpty())
            for (Vertex position : this.vertices)
                position.save();
        if (this.meshOrder != null && !this.meshOrder.isEmpty())
            for (Order order : this.meshOrder)  
                order.save();
    }
    
    public void createAll() throws SQLException {
        if (this.texture != null)
            this.texture.save();
        try {
			this.create();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (this.vertices != null && !this.vertices.isEmpty())
            for (Vertex position : this.vertices) {
            	position.setReferent(this);
                position.create();
            }
        if (this.meshOrder != null && !this.meshOrder.isEmpty())
            for (Order order : this.meshOrder) {
            	order.setReferent(this);
                order.create();
            }
    }
    
	public CompositeObject(CompositeObject parent, Collection<Vector3f> vertices, Collection<Integer> meshOrder) {
		this.parent = parent;
		this.vertices = toVertex(vertices);
		this.meshOrder = toOrder(meshOrder);
		this.texture = null;
	}
	
	private Collection<Vertex> toVertex(Collection<Vector3f> vertices) {
		Collection<Vertex> ret = new Vector<Vertex>();
		for (Vector3f vec: vertices) {
			Vertex vertex = new Vertex(vec);
			ret.add(vertex);
		}
		return ret;
	}
	
	private Collection<Order> toOrder(Collection<Integer> meshOrder) {
		Collection<Order> ret = new Vector<Order>();
		for (Integer order: meshOrder) {
			Order ord = new Order(order);
			ret.add(ord);
		}
		return ret;
	}

	public Integer getId() {
		return this.id_compositeObject;
	}


	public Texture getTexture() {
		return this.texture;
	}
	
	public void setPositions(Collection<Vertex> col) {
		this.vertices = col;
	}
	
	public void setVertices(Collection<Vector3f> vertices) {
		System.out.println("id : " + getId());
		this.vertices = toVertex(vertices);
	}
	public void setMeshOrder(Collection<Integer> col) {
		System.out.println("id : " + getId());
		this.meshOrder = toOrder(col);
	}
	public Collection<Vertex> getPositions() {
		return this.vertices;
	}
	
	public Vector3f[] getVectors() {
		Vector3f[] ret = new Vector3f[this.vertices.size()];
		int i = 0;
		for (Vertex vertex: this.vertices) {
			ret[i] = vertex.getVector();
			++i;
		}
		return ret;
	}
	
	public Collection<Order> getMeshOrder() {
		return this.meshOrder;
	}
	
	public int[] getOrdersAsIntegers() {
		int[] ret = new int[this.meshOrder.size()];
		int i = 0;
		for (Order order: this.meshOrder)
		{
			ret[i] = order.getOrder();
			++i;
		}
		return ret;
	}

	public Collection<CompositeObject> getChilds() {
		return this.childs;
	}

	@Override
	public Iterator<CompositeObject> iterator(){
		/** this method make the object iterable **/
		return this.childs.iterator();
	}
	
	public void add(CompositeObject object){
		/** this method add a child **/
		childs.add(object);
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
}
