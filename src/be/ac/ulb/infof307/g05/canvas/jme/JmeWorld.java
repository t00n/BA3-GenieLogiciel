package be.ac.ulb.infof307.g05.canvas.jme;

import org.lwjgl.openal.AL;

import be.ac.ulb.infof307.g05.EventController;
import be.ac.ulb.infof307.g05.model.Project;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.system.JmeCanvasContext;

/**
 * The Class JmeWorld which take care of the JME (camera, viewport, reference, flycam, events, etc..)
 */
public class JmeWorld extends SimpleApplication {

	/** The _camera. */
	private Camera[]   _camera = new Camera[2];
	
	/** The _view. */
	private ViewPort[] _view = new ViewPort[2];
	
	/** The _reference. */
	private Reference  _reference;
	
	/** The _fly cam. */
	private FlyCamera  _flyCam;
	
	/** The _event controller. */
	private EventController _eventController;
	
	/** The _converter. */
	private JmeConverter _jmeConverter = new JmeConverter();

	
	/**
	 * Instantiates a new jme world.
	 *
	 * @param eventController the event controller
	 */
	public JmeWorld(EventController eventController){
		_eventController = eventController;
	}
	
	/**
	 * Inits the views by creating viewports (2D & 3D)
	 */
	private void initViews(){
		_camera[0] = cam;
        _view[0] = renderManager.createMainView("3D view", _camera[0]);
        _camera[0].setViewPort(0.0f, 1.0f, 0.0f, 1.0f);
        _camera[0].setLocation(new Vector3f(-5f, 5f, -5f));
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

	/**
	 * Sets the viewports (2D and/or 3D).
	 *
	 * @param set2d the set2d
	 * @param set3d the set3d
	 */
	public void setViews(boolean set2d, boolean set3d){
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

	/* (non-Javadoc)
	 * @see com.jme3.app.SimpleApplication#simpleInitApp()
	 */
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
       AL.destroy();
	}
	
	/**
	 * this method redraw all the scene
	 */
	public void draw() {
		rootNode.detachAllChildren();
    	
    	Project project = _eventController.getProject();
    	_reference = new Reference(assetManager, project.getWidth(), project.getLength());
    	_reference.setNode(rootNode, true);
    	
    	
    	if (_eventController.getStage() != null){
	    	rootNode.attachChild(_jmeConverter.convert(_eventController.getStage(), assetManager));
    	}
    	rootNode.updateGeometricState();
	}
	
    /* (non-Javadoc)
     * @see com.jme3.app.SimpleApplication#simpleUpdate(float)
     */
    public void simpleUpdate(float tpf) {
    	/** this method is automatically called after each event **/
    	draw();
	}
}
