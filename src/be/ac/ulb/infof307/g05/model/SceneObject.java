package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;


public abstract class SceneObject extends CompositeObject {
	protected Vector<Vector3f> _vectors = new Vector<Vector3f>();
	protected int[]            _meshOrder;
	protected String           _texture = new String();
	protected ColorRGBA		   _color = ColorRGBA.Gray;
	protected String           _id;
	protected boolean          _isModified = false;
	
	
	public void setId(String id){
		_id = id;
	}
	
	public Node getJmeNode(AssetManager assetManager){
		/** this method builds recursively the tree of nodes, only called if has some children **/
		Node node = new Node();
		
		node.attachChild(this.getJmeGeometry(assetManager));
		
		for(SceneObject object:this){
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

	
	protected Geometry getJmeGeometry(AssetManager assetManager){
		/** this method builds the geometry of this scene object **/
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer((Vector3f[]) _vectors.toArray()));
		mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(_meshOrder));
		
		Geometry object = new Geometry(_id, mesh);
		if(_texture.isEmpty()){
			Material texture = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			texture.setColor("default_color", _color);
			object.setMaterial(texture);
		}else{
			//FIXME how to set a different texture ?
		}
		
		return object;
	}
	
	protected abstract void buildMeshOrder();
		/** this method builds the order of the mesh **/
}
