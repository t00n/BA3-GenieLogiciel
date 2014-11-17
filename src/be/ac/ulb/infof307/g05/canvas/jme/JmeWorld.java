package be.ac.ulb.infof307.g05.canvas.jme;


import be.ac.ulb.infof307.g05.EventController;
import be.ac.ulb.infof307.g05.model.SceneObject;
import be.ac.ulb.infof307.g05.model.Stage;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;


public class JmeWorld extends SimpleApplication {

	private Camera[]   _camera = new Camera[2];
	private ViewPort[] _view = new ViewPort[2];
	private Reference  _reference;
	private FlyCamera  _flyCam;
	private EventController _eventController;
	private Node _currentStage = new Node();
	
	public JmeWorld(EventController eventController){
		_eventController = eventController;
	}
	
	private void initViews(){
		_camera[0] = cam;
        _view[0] = renderManager.createMainView("3D view", _camera[0]);
        _camera[0].setViewPort(0.0f, 1.0f, 0.0f, 1.0f);
        _camera[0].setLocation(new Vector3f(5f, 5f, 5f));
        _camera[0].lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 1f, 0f));
        _camera[0].setFrustumFar(50);
        _view[0].setBackgroundColor(ColorRGBA.White);
        _view[0].setClearFlags(true, true, true);

        
        _camera[1] = _camera[0].clone();
        _camera[1].setViewPort(0.66f, 1.0f, 0.0f, 0.33f);
        _camera[1].setLocation(new Vector3f(0.0f, 5.0f, 0.0f));
        _camera[1].lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 1f, 0f));
        _view[1] = renderManager.createMainView("2D view", _camera[1]);
        _view[1].setClearFlags(true, true, true);
        _camera[1].setParallelProjection(true);
        float aspect = (float) _camera[1].getWidth() / _camera[1].getHeight();
        float zoom = _camera[1].getFrustumNear()*10; 							//FIXME replace 10 by the size of the biggest object
        _camera[1].setFrustum(1, 1000, -aspect * zoom, aspect * zoom, zoom, -zoom);
	}

	public void setViews(boolean set2d, boolean set3d){
		if(set2d && set3d){
			_view[0].setEnabled(true);
			_view[1].setEnabled(true);

			_camera[1].setViewPort(0.66f, 1.0f, 0.0f, 0.33f);
		}else if(set2d){
			_view[0].setEnabled(false);
			_view[1].setEnabled(true);

			_camera[1].setViewPort(0.0f, 1.0f, 0.0f, 1.0f);
		}else if(set3d){
			_view[0].setEnabled(true);
			_view[1].setEnabled(false);
		}
		_flyCam.setCamEnable(set2d, set3d);
	}

	public void simpleInitApp(){
	   guiViewPort.setEnabled(false);
	   flyCam.setEnabled(false);
	   initViews();
	   
	   _flyCam = new FlyCamera(_camera[1], _camera[0], rootNode, inputManager, stateManager, _eventController);

	   _reference = new Reference(assetManager, 1000);
	   _reference.setNode(rootNode, true);
	   
       _view[0].attachScene(rootNode);
       _view[1].attachScene(rootNode);
       
	   draw();
	}
	
	public void draw(){
		Node stage = _eventController.getJmeStage(assetManager);
		if(_currentStage != stage){
			rootNode.detachChild(_currentStage);
			rootNode.attachChild(stage);
			_currentStage = stage;
		}
	}
	
	public void test(){
		Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);

	}
}
