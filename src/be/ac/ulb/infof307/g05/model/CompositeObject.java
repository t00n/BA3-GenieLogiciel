package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@DatabaseTable (tableName = "composite_objects")
public class CompositeObject extends Database<CompositeObject> implements Iterable<CompositeObject> {
	protected CompositeObject() {
		
	}

	public CompositeObject(CompositeObject parent, Vector<Vertex> positions, Texture texture) {
		this.parent = parent;
		this.positions = positions;
		this.texture = texture;
	}

	public CompositeObject(CompositeObject parent, Vector<Vertex> positions) {
		this.parent = parent;
		this.positions = positions;
		this.texture = null;
	}

	@DatabaseField (generatedId = true)
	private Integer id_compositeObject;
	public Integer getId() { return this.id_compositeObject; }
	
	@DatabaseField (canBeNull = true, foreign = true)
	private CompositeObject parent;

	@DatabaseField (canBeNull = true, foreign = true)
	private Texture texture;
	public Texture getTexture() { return this.texture; }
	
	@ForeignCollectionField (eager = false)
	private Collection<Vertex> positions;
	protected void setPositions(Collection<Vertex> col) { this.positions = col; }
	protected Collection<Vertex> getPositions() { return this.positions; }
	
	public List<Vector3f> getVectors() {
		List<Vector3f> ret = new ArrayList<Vector3f>();
		for (Vertex vertex: positions) {
			ret.add(vertex.getVector());
		}
		return ret;
	}
	
	@ForeignCollectionField (eager = false)
	private Collection<Order> meshOrder;
	protected void setMeshOrder(Collection<Order> col) { this.meshOrder = col; }
	protected Collection<Order> getMeshOrder() { return this.meshOrder; }
	
	public List<Integer> getOrdersAsIntegers() {
		List<Integer> ret = new ArrayList<Integer>();
		for (Order order: meshOrder)
		{
			ret.add(order.getOrder());
		}
		return ret;
	}

	@ForeignCollectionField (eager = false)
	private Collection<CompositeObject> childs;
	public Collection<CompositeObject> getChilds() { return this.childs; }

	@Override
	public Iterator<CompositeObject> iterator(){
		/** this method make the object iterable **/
		return childs.iterator();
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
			for(CompositeObject child:childs){
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
