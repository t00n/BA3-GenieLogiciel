package be.ac.ulb.infof307.g05.canvas.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Floor;
import be.ac.ulb.infof307.g05.model.Room;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.model.Wall;

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
		return new Node();
	}
	
	public Node convert(Room room, AssetManager assetManager) {
		return new Node();
		
	}
	
	public Node convert(Floor floor, AssetManager assetManager) {
		return new Node();
		
	}
	
	public Node convert(Wall wall, AssetManager assetManager) {
		return new Node();
		
	}
	
	public Node convert(CompositeObject object, AssetManager assetManager) {
		return new Node();
		
	}

	private Geometry toGeometry(Floor floor, AssetManager assetManager) {
		return new Geometry();
	}
	
	private Geometry toGeometry(Wall wall, AssetManager assetManager) {
		return new Geometry();
	}

	
	private Geometry toGeometry(CompositeObject object, AssetManager assetManager){
		/** this method convert a CompositeObject to a geometry **/
		Mesh mesh = new Mesh();
		//FIXME mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(object.getVerticesAsVector3f()));
		//FIXME mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(object.getMeshOrderAsInt()));
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
