package be.ac.ulb.infof307.g05.canvas.jme;

import be.ac.ulb.infof307.g05.EventController;
import be.ac.ulb.infof307.g05.model.Project;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.JmeCanvasContext;


public class JmeWorld extends SimpleApplication {

	private Camera[]   _camera = new Camera[2];
	private ViewPort[] _view = new ViewPort[2];
	private Reference  _reference;
	private FlyCamera  _flyCam;
	private EventController _eventController;
	private Node _currentStage = new Node();
	private JmeConverter _converter = new JmeConverter();

	
	public JmeWorld(EventController eventController){
		_eventController = eventController;
	}
	
	private void initViews(){
		/** this method create viewports (2D & 3D) **/
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
		/** this method set viewports (2D and/or 3D) **/
		if((set2d != _view[1].isEnabled()) || (set3d != _view[0].isEnabled())){
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
			((JmeCanvasContext) this.getContext()).getCanvas().requestFocusInWindow();
		}
	}

	public void simpleInitApp(){
		/** this method init the jme world **/
	   guiViewPort.setEnabled(false);
	   flyCam.setEnabled(false);
	   initViews();
	   
	   _flyCam = new FlyCamera(_camera[1], _camera[0], rootNode, inputManager, stateManager, _eventController);

	   Project project = _eventController.getProject();
	   _reference = new Reference(assetManager, project.getWidth(), project.getLength());
	   _reference.setNode(rootNode, true);
	   
       _view[0].attachScene(rootNode);
       _view[1].attachScene(rootNode);
	}
	
	private void draw() {
		/** this method redraw all the scene **/
    	rootNode.detachChild(_currentStage);
    	_currentStage.detachAllChildren();
    	_converter.convert(_eventController.getStage(), _currentStage, assetManager);
    	rootNode.attachChild(_currentStage);
	}
	
    public void simpleUpdate(float tpf) {
    	/** this method is automatically called after each event **/
    	draw();
	}
}
