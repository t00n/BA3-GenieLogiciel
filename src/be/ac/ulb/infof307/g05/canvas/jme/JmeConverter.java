package be.ac.ulb.infof307.g05.canvas.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

import be.ac.ulb.infof307.g05.model.CompositeObject;


public class JmeConverter {
	
	public JmeConverter(){
		
	}

	public void convert(CompositeObject object, Node root, AssetManager assetManager){
		root.attachChild(toGeometry(object, assetManager));
		
		for(CompositeObject child:object){
			convert(child, root, assetManager);
		}
	}
	
	private Geometry toGeometry(CompositeObject object, AssetManager assetManager){
		/** this method convert a CompositeObject to a geometry **/
		Mesh mesh = new Mesh();
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(object.getVectors()));
		mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(object.getOrdersAsIntegers()));
		mesh.updateBound();
		mesh.setStatic();
		
		Geometry geo = new Geometry(object.getId().toString(), mesh);
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
