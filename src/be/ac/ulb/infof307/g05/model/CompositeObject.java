package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

@DatabaseTable (tableName = "composite_objects")
public abstract class CompositeObject extends Database<CompositeObject> implements Iterable<CompositeObject> {
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
	
	@DatabaseField (canBeNull = true, foreign = true)
	private CompositeObject parent;

	@DatabaseField (canBeNull = true, foreign = true)
	private Texture texture;
	
	@ForeignCollectionField (eager = false)
	private Collection<Vertex> positions;
	protected void setPositions(Collection<Vertex> col) { this.positions = col; }
	protected Collection<Vertex> getPositions() { return this.positions; }
	
	@ForeignCollectionField (eager = false)
	private Collection<Order> meshOrder;
	protected void setMeshOrder(Collection<Order> col) { this.meshOrder = col; }
	protected Collection<Order> getMeshOrder() { return this.meshOrder; }

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
	
	public Node getJmeNode(AssetManager assetManager){
		/** this method builds recursively the tree of nodes, only called if has some children **/
		Node node = new Node();
		
		node.attachChild(this.getJmeGeometry(assetManager));
		
		for(CompositeObject object:this){
			if(object.size() == 0){
				// it's a simple object
				node.attachChild(object.getJmeGeometry(assetManager));
			}else{
				// object has some children
				node.attachChild(object.getJmeNode(assetManager));
			}
		}
		return node;
	}
	
	public int[] toIntArray(Vector<Order> vec) {
		int[] ret = new int[vec.size()];
		for (int i = 0; i < vec.size(); ++i)
		{
			ret[i] = vec.elementAt(i).order;
		}
		return ret;
	}

	
	protected Geometry getJmeGeometry(AssetManager assetManager){
		/** this method builds the geometry of this scene object **/
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer((Vector3f[]) positions.toArray()));
		mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(toIntArray((Vector<Order>)meshOrder)));
		
		Geometry object = new Geometry(this.id_compositeObject.toString(), mesh);
		if(this.texture == null){
			Material texture = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			texture.setColor("default_color", ColorRGBA.Gray);
			object.setMaterial(texture);
		}else{
			//FIXME how to set a different texture ?
		}
		
		return object;
	}
	
	protected abstract void buildMeshOrder();
		/** this method builds the order of the mesh **/
}
