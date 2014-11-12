package be.ac.ulb.infof307.g05.canvas.jme;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.util.BufferUtils;


public class Reference {
	private AssetManager _assetManager;
	private Node		 _node;
	private Geometry 	 _grid;
	private Node    	 _coordinate = new Node("test");
	private Geometry     _floor;

	
	public Reference(AssetManager assetManager, int size){
		_assetManager = assetManager;
		createGrid(new Vector3f(), size, 1f, ColorRGBA.Gray);
		createAxis(size, 1.5f);
		createFloor(size);
	}

	
	public void setNode(Node node, boolean attach){
		if(_node != null){
			setGridEnable(false);
			setCoordEnable(false);
			setFloorEnable(false);
		}
		_node = node;
		
		if(attach){
			setGridEnable(true);
			setCoordEnable(true);
			setFloorEnable(true);			
		}
	}
	
	public void setGridEnable(boolean enable){
		if(enable)
			_node.attachChild(_grid);
		else
			_node.detachChild(_grid);
	}
	
	public void setCoordEnable(boolean enable){
		if(enable)
			_node.attachChild(_coordinate);
		else
			_node.detachChild(_coordinate);
	}
	
	public void setFloorEnable(boolean enable){
		if(enable)
			_node.attachChild(_floor);
		else
			_node.detachChild(_floor);
	}
	
    private void createGrid(Vector3f pos, int gridSize, float squareSize, ColorRGBA color){
        _grid = new Geometry("grid", new Grid(gridSize+1, gridSize+1, squareSize) );
        Material mat = new Material(_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        _grid.setMaterial(mat);
        _grid.center().move(pos);
      }
    
    private void createFloor(int floorSize){ //Sol invisible (car pas lumière ajoutée) pour la collision
		Box b = new Box(floorSize, 0, floorSize);
        _floor = new Geometry("Floor", b);
        Material mat = new Material(_assetManager, "Common/MatDefs/Light/Lighting.j3md");
        _floor.setMaterial(mat);
    }
	

	private void createAxis(int lenght, float lineWidth){
		//Axe X
		Geometry x_axis = new Geometry("x axis", getMeshAxis((lenght/2), 0, 0, lineWidth));
		setAxisColor(x_axis, ColorRGBA.Red);
	    _coordinate.attachChild(x_axis);
		//Axe Y
		Geometry y_axis = new Geometry("y axis", getMeshAxis(0, (lenght/2), 0, lineWidth));
		setAxisColor(y_axis, ColorRGBA.Green);
		_coordinate.attachChild(y_axis);
		//Axe Z
		Geometry z_axis = new Geometry("z axis", getMeshAxis(0, 0, (lenght/2), lineWidth));
		setAxisColor(z_axis, ColorRGBA.Blue);
		_coordinate.attachChild(z_axis);
	}
	
	private void setAxisColor(Geometry axis, ColorRGBA color){
        Material mat = new Material(_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", color);
        axis.setMaterial(mat);
	}
	
	private Mesh getMeshAxis(int coordX, int coordY, int coordZ, float lineWidth){
		Mesh mesh = new Mesh();
		Vector3f[] lineVerticies = new Vector3f[2];
		lineVerticies[0]=new Vector3f(-coordX,0,-coordZ);
		lineVerticies[1]=new Vector3f( coordX, coordY, coordZ);

        mesh.setMode(Mesh.Mode.Lines);
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(lineVerticies));
        mesh.setLineWidth(lineWidth);

        return mesh;
    }
	
}
