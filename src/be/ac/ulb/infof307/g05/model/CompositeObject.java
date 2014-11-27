package be.ac.ulb.infof307.g05.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jme3.math.Vector3f;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.lang.Math;

/**
 * The Class CompositeObject takes care of the composites objects in the database.
 */
@DatabaseTable (tableName = "composite_objects")
public class CompositeObject extends Database<CompositeObject> implements Iterable<CompositeObject> {	
	
	@DatabaseField (id=true)
	protected Integer id_compositeObject;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected CompositeObject parent;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected Room room;
	
	@DatabaseField (canBeNull = true, foreign = true)
	protected Resource texture;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Resource mesh;
	
	@DatabaseField (canBeNull = false, foreign = true)
	protected Vertex position;

	protected Collection<CompositeObject> childs;
	
	protected CompositeObject() {
		
	}
    
	public CompositeObject(CompositeObject parent, Resource mesh) {
		this.init(parent, null, mesh, null);
	}
	
	public CompositeObject(CompositeObject parent, Resource mesh, Resource texture) {
		this.init(parent, null, mesh, texture);
	}
	
	public CompositeObject(Room room, Resource mesh) {
		this.init(null, room, mesh, null);
	}
	
	public CompositeObject(Room room, Resource mesh, Resource texture) {
		this.init(null, room, mesh, texture);
	}
	
	private void init(CompositeObject parent, Room room, Resource mesh, Resource texture) {
		this.parent = parent;
		this.room = room;
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

	public Resource getTexture() {
		return this.texture;
	}
	
	public Resource getMesh() {
		return this.mesh;
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
	
	public boolean ownsId(int id) {
		if (id == this.getId()) {
			return true;
		}
		else {
			for (CompositeObject child: this.getChilds())
			{
				if (child.ownsId(id)) {
					return true;
				}
			}
		}
		return false;
	}
	
//	public void rotateAroundZ(float range) {
//		ArrayList<Vertex> tmp = (ArrayList<Vertex>) this.getVertices();
//		float posX;
//		float posY;
//		for (int i = 0; i < (tmp.size()); ++i) {
//			posX = tmp.get(i).x;
//			posY = tmp.get(i).y;
//			tmp.get(i).x = (float) ((posX*Math.cos(range)) - (posY*Math.sin(range)));
//			tmp.get(i).y = (float) ((posY*Math.cos(range)) + (posX*Math.sin(range)));
//		}
//	}
//
//	public void rotateAroundY(float range) {
//		ArrayList<Vertex> tmp = (ArrayList<Vertex>) this.getVertices();
//		float posX;
//		float posZ;
//		for (int i = 0; i < (tmp.size()); ++i) {
//			posX = tmp.get(i).x;
//			posZ = tmp.get(i).z;
//			tmp.get(i).x = (float) ((posX*Math.cos(range)) - (posZ*Math.sin(range)));
//			tmp.get(i).z = (float) ((posZ*Math.cos(range)) + (posX*Math.sin(range)));
//		}
//	}
//	
//	public void rotateAroundX(float range) {
//		ArrayList<Vertex> tmp = (ArrayList<Vertex>) this.getVertices();
//		float posY;
//		float posZ;
//		for (int i = 0; i < (tmp.size()); ++i) {
//			posY = tmp.get(i).y;
//			posZ = tmp.get(i).z;
//			tmp.get(i).y = (float) ((posY*Math.cos(range)) - (posZ*Math.sin(range)));
//			tmp.get(i).z = (float) ((posZ*Math.cos(range)) + (posY*Math.sin(range)));
//		}
//	}
	
    @Override
    public void save() {
        if (this.texture != null)
            this.texture.save();
        this.mesh.save();
        super.save();
        for (CompositeObject object : this.getChilds())
        	object.save();
    }
	
	@Override
	public Iterator<CompositeObject> iterator(){
		/** this method make the object iterable **/
		return this.getChilds().iterator();
	}
	
	public void add(CompositeObject object){
		childs.add(object);
	}

	public void addChild(CompositeObject parent, CompositeObject child) {
		if (parent == this) {
			this.add(child);
			child.setId();
		}
		else
		{
			for (CompositeObject c: this) {
				c.addChild(parent, child);
			}
		}
	}

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
	
	public int size(){
		return childs.size();
	}

	public void moveTo(Vector3f difference) {
		this.position.x += difference.x;
		this.position.y += difference.y;
		this.position.z += difference.z;
	}
}
