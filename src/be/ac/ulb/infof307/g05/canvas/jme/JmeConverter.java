package be.ac.ulb.infof307.g05.canvas.jme;

import java.util.ArrayList;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.model.Vertex;
import be.ac.ulb.infof307.g05.view.Polyhedron;

/**
 * The Class JmeConverter converts a CompositeObject into a JME one to take the objects from the database
 * and show them in the canvasJME.
 */
public class JmeConverter {
	
	/**
	 * Instantiates a new jme converter.
	 */
	public JmeConverter(){
		
	}

	public Node convert(Stage stage, AssetManager assetManager) {
		Node node = new Node();
		for (CompositeObject object : stage.getCompositeObjects()) {
			node.attachChild(this.convert(object, assetManager));
		}
		return node;
	}
	
	public Node convert(CompositeObject object, AssetManager assetManager) {
		Node node = new Node();
		node.attachChild(this.toGeometry(object, assetManager));
		for (CompositeObject child : object.getChilds()) {
			node.attachChild(this.convert(child, assetManager));
		}
		return node;
	}

	
	private Geometry toGeometry(CompositeObject object, AssetManager assetManager){
		/** this method convert a CompositeObject to a geometry **/
		ArrayList<Vector3f> tmp = new ArrayList<Vector3f>();
		for (Vertex vertex : object.getVertices()) {
			tmp.add(vertex.toVector3f());
		}
		Polyhedron poly = new Polyhedron(tmp);
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(poly.getVectors()));
		mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(poly.getMeshOrder()));
		mesh.updateBound();
		mesh.setStatic();
		
		Geometry geo = new Geometry(Integer.toString(object.getId()), mesh);
		if(object.getTexture() == null){
			Material texture = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			texture.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
			texture.setColor("Color", ColorRGBA.Gray);
			geo.setMaterial(texture);
		}else{
			//FIXME how to set a different texture ?
		}
		
		return geo;
	}
}
