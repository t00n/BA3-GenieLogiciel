package be.ac.ulb.infof307.g05.canvas.jme;

import java.util.Iterator;
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

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Order;


public class JmeCompositeObject implements Iterable<JmeCompositeObject> {

	private Geometry _myGeometry;
	private Vector<JmeCompositeObject> _childs = new Vector<JmeCompositeObject>();
	
	
	public JmeCompositeObject(CompositeObject root, AssetManager assetManager){
		/** constructor **/
		build(root, assetManager);
	}
	
	private Geometry toGeometry(CompositeObject object, AssetManager assetManager){
		/** this method convert a CompositeObject to a geometry **/
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(object.getVectors()));
		mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(object.getOrders()));
		
		Geometry geo = new Geometry(object.getId(), mesh);
		if(object.getTexture() == null){
			Material texture = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			texture.setColor("default_color", ColorRGBA.Gray);
			geo.setMaterial(texture);
		}else{
			//FIXME how to set a different texture ?
		}
		
		return geo;
	}
	
	private void build(CompositeObject me, AssetManager assetManager){
		/** this method builds recursively all children **/
		_myGeometry = toGeometry(me, assetManager);
				
		for(CompositeObject child:me){
			_childs.add(new JmeCompositeObject(child, assetManager));
		}
	}
	
	public Node getNode(AssetManager assetManager){
		/** this method builds recursively the tree of nodes **/
		Node node = new Node();
		node.attachChild(_myGeometry);
		
		for(JmeCompositeObject child:this){
			node.attachChild(child.getNode(assetManager));
		}
		return node;

	}
	
	public int size(){
		return _childs.size();
	}
	
	@Override
	public Iterator<JmeCompositeObject> iterator() {
		return _childs.iterator();
	}
}
