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
import be.ac.ulb.infof307.g05.model.Floor;
import be.ac.ulb.infof307.g05.model.Room;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.model.Vertex;
import be.ac.ulb.infof307.g05.model.Wall;
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
		for (Room room : stage.getRooms()) {
			node.attachChild(this.convert(room, assetManager));
		}
		return node;
	}
	
	public Node convert(Room room, AssetManager assetManager) {
		Node node = new Node();			
		//Room
		node.attachChild(this.convert(room.getFloor(), assetManager));
		//Walls
		for (Wall wall : room.getWalls()) {
			node.attachChild(this.convert(wall, assetManager));
		}
		//CompositeObjects
		for (CompositeObject compositeObject: room.getCompositeObjects()) {
			node.attachChild(this.convert(compositeObject, assetManager));
		}		
		return node;
	}
	
	public Node convert(Floor floor, AssetManager assetManager) {
		Node node = new Node();
		node.attachChild(this.toGeometry(floor, assetManager));
		return node;		
	}
	
	public Node convert(Wall wall, AssetManager assetManager) {
		Node node = new Node();
		node.attachChild(this.toGeometry(wall, assetManager));
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

	private Geometry toGeometry(Floor floor, AssetManager assetManager) {
		ArrayList<Vector3f> tmp = new ArrayList<Vector3f>();
		// down side
		for (Vertex vertex : floor.getVertices()) {
			tmp.add(vertex.toVector3f());
		}
		Polyhedron poly = new Polyhedron(tmp, floor.getHeight());
		
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(poly.getVectors()));
		mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(poly.getMeshOrder()));
		mesh.updateBound();
		mesh.setStatic();
		
		Geometry geo = new Geometry(Integer.toString(floor.getId()), mesh);
		Material texture = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		texture.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
		texture.setColor("Color", ColorRGBA.Brown);
		geo.setMaterial(texture);
		return geo;
	}
	
	private Geometry toGeometry(Wall wall, AssetManager assetManager) {
		ArrayList<Vector3f> tmp = new ArrayList<Vector3f>();
		// down side
		tmp.add(wall.getBeginning().toVector3f());
		tmp.add(wall.getEnd().toVector3f());
		// FIXME round() or something ?
		if (tmp.get(0).x == tmp.get(1).x) {
			tmp.get(0).x += wall.getWidth()/2;
			tmp.get(1).x += wall.getWidth()/2;
			tmp.add(new Vector3f(tmp.get(1)));
			tmp.add(new Vector3f(tmp.get(0)));
			tmp.get(2).x -= wall.getWidth()/2;
			tmp.get(3).x -= wall.getWidth()/2;
		}
		else if (tmp.get(0).z == tmp.get(1).z) {
			tmp.get(0).z += wall.getWidth()/2;
			tmp.get(1).z += wall.getWidth()/2;
			tmp.add(new Vector3f(tmp.get(1)));
			tmp.add(new Vector3f(tmp.get(0)));
			tmp.get(2).z -= wall.getWidth()/2;
			tmp.get(3).z -= wall.getWidth()/2;
		}
		Polyhedron poly = new Polyhedron(tmp, wall.getHeight());
		
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(poly.getVectors()));
		mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(poly.getMeshOrder()));
		mesh.updateBound();
		mesh.setStatic();
		
		Geometry geo = new Geometry(Integer.toString(wall.getId()), mesh);
		Material texture = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		texture.setTexture("ColorMap", assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
//		texture.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
//		texture.setColor("Color", ColorRGBA.LightGray);
		geo.setMaterial(texture);
		return geo;
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
