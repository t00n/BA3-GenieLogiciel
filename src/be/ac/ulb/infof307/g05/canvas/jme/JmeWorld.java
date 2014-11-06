package be.ac.ulb.infof307.g05.canvas.jme;


import com.jme3.app.SimpleApplication;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;


public class JmeWorld extends SimpleApplication {

	private Camera[] _camera = new Camera[2];
	private ViewPort[] _view = new ViewPort[2];
	private Geometry _grid = null;
	

	private void initMultiViews(){
		_camera[0] = cam;
        _view[0] = renderManager.createMainView("3D view", _camera[0]);
        _camera[0].setViewPort(0.0f, 1.0f, 0.0f, 1.0f);
        _camera[0].setLocation(new Vector3f(5f, 5f, 5f));
        _camera[0].lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 1f, 0f));
        _view[0].setBackgroundColor(ColorRGBA.White);
        _view[0].setClearFlags(true, true, true);

        
        _camera[1] = _camera[0].clone();
        _camera[1].setViewPort(0.0f, 1.0f, 0.0f, 1.0f);
        _camera[1].setLocation(new Vector3f(0.0f, 10.0f, 0.0f));
        _camera[1].lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 1f, 0f));
        _view[1] = renderManager.createMainView("2D view", _camera[1]);
        _view[1].setClearFlags(true, true, true);
        _camera[1].setParallelProjection(true);

	}

	public void setViews(boolean set2d, boolean set3d){
		if(set2d && set3d){
			_view[0].setEnabled(true);
			_view[1].setEnabled(true);

			_camera[1].setViewPort(0.66f, 1.0f, 0.0f, 0.33f);
	        float aspect = (float) _camera[1].getWidth() / _camera[1].getHeight();
	        float zoom = _camera[1].getFrustumNear()*10; 							//FIXME replace 10 by the size of the biggest object
	        _camera[1].setFrustum(1, 1000, -aspect * zoom, aspect * zoom, zoom, -zoom);
		}else if(set2d){
			_view[0].setEnabled(false);
			_view[1].setEnabled(true);

			_camera[1].setViewPort(0.0f, 1.0f, 0.0f, 1.0f);
	        float aspect = (float) _camera[1].getWidth() / _camera[1].getHeight();
	        float zoom = _camera[1].getFrustumNear()*10; 							//FIXME replace 10 by the size of the biggest object
	        _camera[1].setFrustum(1, 1000, -aspect * zoom, aspect * zoom, zoom, -zoom);
		}else if(set3d){
			_view[0].setEnabled(true);
			_view[1].setEnabled(false);
		}
	}
	
    private Geometry createGrid(Vector3f pos, int gridSize, float squareSize, ColorRGBA color){
        Geometry grid = new Geometry("wireframe grid", new Grid(gridSize, gridSize, squareSize) );
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        grid.setMaterial(mat);
        grid.center().move(pos);
        return grid;
      }
	
	public void setGrid(Node father, boolean attach){
		if(_grid == null)
			_grid = createGrid(new Vector3f(), 100, 0.5f, ColorRGBA.Blue); //Crée une grille de 50x50 carrés de taille 0,5 de couleure bleu
		
		if(attach == false)
			father.detachChild(_grid);
		else
			father.attachChild(_grid);
	}

    private void redefineKeys() {
        inputManager.deleteMapping("FLYCAM_Forward");
        inputManager.deleteMapping("FLYCAM_Backward");
        inputManager.deleteMapping("FLYCAM_StrafeLeft");
        inputManager.deleteMapping("FLYCAM_StrafeRight");
        inputManager.deleteMapping("FLYCAM_Lower");
        inputManager.deleteMapping("FLYCAM_Rise");
        
        inputManager.addMapping("FLYCAM_Forward", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("FLYCAM_Backward", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("FLYCAM_StrafeRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("FLYCAM_StrafeLeft", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("FLYCAM_Lower", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("FLYCAM_Rise", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addListener(flyCam, new String[] {"FLYCAM_Forward", "FLYCAM_Backward", "FLYCAM_StrafeRight", "FLYCAM_StrafeLeft", "FLYCAM_Lower", "FLYCAM_Rise"});
        flyCam.setMoveSpeed(10f);
    }
	
	public void simpleInitApp(){
	   guiViewPort.setEnabled(false);
	   
	   initMultiViews();
	   setViews(true, true);
	   setGrid(rootNode, true);
	   flyCam.setDragToRotate(true);
	   test();
	   
	   
	   
	   stateManager.attach(new AbstractAppState() {
           public void initialize(AppStateManager stateManager, Application app) {
                   super.initialize(stateManager, app);
                   redefineKeys();
                   stateManager.detach(this);
           }
       });
	}
	
	void test(){
		Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);


        _view[0].attachScene(rootNode);
        _view[1].attachScene(rootNode);
	}
}
