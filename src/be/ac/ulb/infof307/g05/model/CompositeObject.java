package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

import java.util.Collection;
import java.util.Iterator;


@DatabaseTable (tableName = "composite_objects")
public class CompositeObject extends Database<CompositeObject> implements Iterable<CompositeObject> {
	
	@DatabaseField (generatedId = true)
	private Integer id_compositeObject;
	@DatabaseField (canBeNull = true, foreign = true)
	private CompositeObject parent;
	@DatabaseField (canBeNull = true, foreign = true)
	private Texture texture;
	@ForeignCollectionField (eager = true)
	protected Collection<Vertex> positions;
	@ForeignCollectionField (eager = true)
	protected Collection<Order> meshOrder;
	@ForeignCollectionField (eager = true)
	protected Collection<CompositeObject> childs;
	
	
	protected CompositeObject() {
		
	}

    @Override
    public void save() {
        if (this.texture)
            this.texture.save();
        this.createOrUpdate();
        if (this.positions && !this.positions.isEmpty())
            for (Vertex position : this.positions)
                position.save();
        if (this.meshOrder && !this.meshOrder.isEmpty())
            for (Order order : this.meshOrder)  
                order.save();
    }
	public CompositeObject(CompositeObject parent, Collection<Vertex> positions) {
		this.parent = parent;
		this.positions = positions;
		this.texture = null;
	}

	public Integer getId() {
		return this.id_compositeObject;
	}


	public Texture getTexture() {
		return this.texture;
	}
	
	public void setPositions(Collection<Vertex> col) {
		this.positions = col;
	}
	
	public Collection<Vertex> getPositions() {
		return this.positions;
	}
	
	public Vector3f[] getVectors() {
		Vector3f[] ret = new Vector3f[this.getPositions().size()];
		int i = 0;
		for (Vertex vertex: this.positions) {
			ret[i] = vertex.getVector();
			++i;
		}
		return ret;
	}
	public void setMeshOrder(Collection<Order> col) {
		this.meshOrder = col;
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
