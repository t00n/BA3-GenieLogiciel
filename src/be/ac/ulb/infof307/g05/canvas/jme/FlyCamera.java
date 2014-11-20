package be.ac.ulb.infof307.g05.canvas.jme;


import java.awt.event.ActionEvent;

import be.ac.ulb.infof307.g05.EventController;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 * The Class FlyCamera let the user to use the flycam on the CanvasJme
 */
public class FlyCamera extends FlyByCamera {

	/** The _cam2d. */
	private Camera _cam2d;
	
	/** The _cam3d. */
	private Camera _cam3d;
	
	/** The _node. */
	private Node _node;
	
	/** The _event controller. */
	private EventController _eventController;
	
	/** The _cam2d enabled. */
	private boolean  _cam2dEnabled = false;
	
	/** The _cam3d enabled. */
	private boolean  _cam3dEnabled = false;
	
	/** The _collisions. */
	private CollisionResults _collisions = new CollisionResults();
	
	/** The _last screen click. */
	private Vector2f _lastScreenClick = new Vector2f();
	
	/** The _last mouse click. */
	private Vector3f _lastMouseClick = new Vector3f(0,0,0);
	
	/** The _is pressed. */
	private boolean _isPressed = true;
	
	
	/**
	 * Instantiates a new fly camera.
	 *
	 * @param cam2d the cam2d
	 * @param cam3d the cam3d
	 * @param node the node
	 * @param inputManager the input manager
	 * @param stateManager the state manager
	 * @param eventController the event controller
	 */
	public FlyCamera(Camera cam2d, Camera cam3d, Node node, InputManager inputManager, AppStateManager stateManager, EventController eventController){
		super(cam3d);
		_cam2d = cam2d;
		_cam3d = cam3d;
		_node = node;
		_eventController = eventController;
		
		this.inputManager = inputManager;
		this.setDragToRotate(true);
		
		stateManager.attach(new AbstractAppState() {
			public void initialize(AppStateManager stateManager, Application app) {
				super.initialize(stateManager, app);
	            redefineKeys();
	            stateManager.detach(this);
	        }
	    });
	}
	
	/**
	 * Redefine keys to move the FlyCam.
	 */
	public void redefineKeys(){
		inputManager.clearMappings();
        
        inputManager.addMapping("FLYCAM_Left", new MouseAxisTrigger(0, false));
        inputManager.addMapping("FLYCAM_Right", new MouseAxisTrigger(0, true));
        inputManager.addMapping("FLYCAM_Up", new MouseAxisTrigger(1, true));
        inputManager.addMapping("FLYCAM_Down", new MouseAxisTrigger(1, false));
        
        inputManager.addMapping("FLYCAM_ZoomIn", new MouseAxisTrigger(2, false));
        inputManager.addMapping("FLYCAM_ZoomOut", new MouseAxisTrigger(2, true));
        inputManager.addMapping("FLYCAM_RotateDrag", new MouseButtonTrigger(0));
        
        inputManager.addMapping("FLYCAM_Forward", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("FLYCAM_Backward", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("FLYCAM_StrafeRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("FLYCAM_StrafeLeft", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("FLYCAM_Lower", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("FLYCAM_Rise", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("FLYCAM_Enter", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("FLYCAM_Escape", new KeyTrigger(KeyInput.KEY_ESCAPE));


        inputManager.addListener(this, new String[] {"FLYCAM_Left","FLYCAM_Right","FLYCAM_Up","FLYCAM_Down","FLYCAM_ZoomIn","FLYCAM_ZoomOut","FLYCAM_RotateDrag","FLYCAM_Forward", "FLYCAM_Backward", "FLYCAM_StrafeRight", "FLYCAM_StrafeLeft", "FLYCAM_Lower", "FLYCAM_Rise", "FLYCAM_Enter", "FLYCAM_Escape"});
        inputManager.setCursorVisible(dragToRotate);
	}
	
	/**
	 * Sets the cam enable for the 2D view, the 3D view, or the two views at the same time
	 *
	 * @param cam2d the cam2d
	 * @param cam3d the cam3d
	 */
	public void setCamEnable(boolean cam2d, boolean cam3d){
		_cam2dEnabled = cam2d;
		_cam3dEnabled = cam3d;
	}
	
	private boolean isIn2dViewport(){
		boolean isIn2d = false;
		Vector2f position = inputManager.getCursorPosition();
		
		if(canRotate && (_lastScreenClick.getX() > (_cam3d.getWidth()*_cam2d.getViewPortLeft())) && (_lastScreenClick.getY() < (_cam3d.getHeight()*_cam2d.getViewPortTop())) )
			isIn2d = true;
		else if(!canRotate && (position.getX() > (_cam3d.getWidth()*_cam2d.getViewPortLeft())) && (position.getY() < (_cam3d.getHeight()*_cam2d.getViewPortTop())) )
			isIn2d = true;
		
		return isIn2d;
	}

    /**
     * Move camera when the user click
     */
    protected void moveCameraMouseClick(){
    	Vector3f mousePosition = getPositionVec().clone();
    	Vector3f min = _lastMouseClick.subtract(mousePosition);
    	
    	cam.setLocation(cam.getLocation().add(min));    	
    	_lastMouseClick=getPositionVec();		
    }
    
    /**
     * Move camera with keys.
     *
     * @param value the value of the move
     * @param sideways the sideways (true or falses)
     */
    protected void moveCameraKeys(float value, boolean sideways){
        Vector3f vel = new Vector3f();
        Vector3f pos = cam.getLocation().clone();

        if (sideways){
            cam.getLeft(vel);
        }else if(cam.equals(_cam3d)){
            cam.getDirection(vel);
        }else
                cam.getUp(vel);
        
        if(cam.equals(_cam2d))
                value *= 2;
        
        vel.multLocal(value * moveSpeed);

        pos.addLocal(vel);
        if(cam.equals(_cam2d))
                pos.setY(cam.getLocation().getY());
        
        cam.setLocation(pos);
    }

 
    /**
     * Move all the geometry.
     *
     * @param geom the geom
     * @param value the value
     * @param sideways the sideways
     */
    protected void moveGeometry(Geometry geom, float value, boolean sideways){
    	Vector3f mousePosition = getPositionVec();
    	float coordY = geom.getWorldTranslation().getY(); //Retiens la position Y de la Geometry
        mousePosition.setY(coordY);
        geom.setLocalTranslation(mousePosition);      	
    }

    /* (non-Javadoc)
     * @see com.jme3.input.FlyByCamera#rotateCamera(float, com.jme3.math.Vector3f)
     */
    protected void rotateCamera(float value, Vector3f axis){
        if(canRotate){
	        Matrix3f mat = new Matrix3f();
	        mat.fromAngleNormalAxis(rotationSpeed * value, axis);
	
	        Vector3f up = cam.getUp();
	        Vector3f left = cam.getLeft();
	        Vector3f dir = cam.getDirection();
	
	        left.setY(0);
	        mat.mult(up, up);
	        mat.mult(left, left);
	        mat.mult(dir, dir);
	
	        Quaternion q = new Quaternion();
	        q.fromAxes(left, up, dir);
	        q.normalizeLocal();
	
	        cam.setAxes(q);
	    }
    }
	
    /* (non-Javadoc)
     * @see com.jme3.input.FlyByCamera#onAnalog(java.lang.String, float, float)
     */
    public void onAnalog(String name, float value, float tpf){
        _eventController.actionPerformed(new ActionEvent(this.getPositionVec(), ActionEvent.ACTION_PERFORMED, "cursor_move"));
        
        Geometry geometryToMove = getGeometryCollision();

		if(enabled){
			if(_isPressed) {
				_lastMouseClick = getPositionVec();
			}
	    	if(_cam2dEnabled && _cam3dEnabled){
	    		if(isIn2dViewport())
	    			this.cam = _cam2d;
	    		else
	    			this.cam = _cam3d;
	
	    	}else if(_cam2dEnabled)
				this.cam = _cam2d;
			else if(_cam3dEnabled)
				this.cam = _cam3d;
	        if (name.equals("FLYCAM_Left")){
	        	if(cam.equals(_cam3d))
	        		rotateCamera(value, new Vector3f(0,1,0));
	        	else if(canRotate)
	        		if((geometryToMove.getName().equals("Floor")) || (geometryToMove.getName().equals("Grid")))	        		
	        			moveCameraMouseClick();
	        		else
	        			moveGeometry(geometryToMove, value, true);
	        }else if (name.equals("FLYCAM_Right")){
	        	if(cam.equals(_cam3d))
	        		rotateCamera(-value, new Vector3f(0,1,0));
	        	else if(canRotate)
	        		if((geometryToMove.getName().equals("Floor")) || (geometryToMove.getName().equals("Grid")))	        		
	        			moveCameraMouseClick();
	        		else
	        			moveGeometry(geometryToMove, -value, true);
		    }else if (name.equals("FLYCAM_Up")){
	        	if(cam.equals(_cam3d))
	        		rotateCamera(-value, cam.getLeft());
	        	else if(canRotate)
	        		if((geometryToMove.getName().equals("Floor")) || (geometryToMove.getName().equals("Grid")))	        		
	        			moveCameraMouseClick();
	        		else
	        			moveGeometry(geometryToMove, value, false);
		    }else if (name.equals("FLYCAM_Down")){
	        	if(cam.equals(_cam3d))
	        		rotateCamera(value, cam.getLeft());
	        	else if(canRotate)
	        		if((geometryToMove.getName().equals("Floor")) || (geometryToMove.getName().equals("Grid")))	        		
	        			moveCameraMouseClick();
	        		else
	        			moveGeometry(geometryToMove, -value, false);
		    }else if (name.equals("FLYCAM_Forward")){
		    	if(cam.equals(_cam3d))
		    		moveCameraKeys(value, false);
		    	else
		    		moveCameraKeys(-value, false);
		    }else if (name.equals("FLYCAM_Backward")){
		    	if(cam.equals(_cam3d))
		    		moveCameraKeys(-value, false);
		    	else
		    		moveCameraKeys(value, false);
		    }else if (name.equals("FLYCAM_StrafeLeft")){
		    	if(cam.equals(_cam3d))
		    		moveCameraKeys(value, true);
		    	else
		    		moveCameraKeys(-value, true);
		    }else if (name.equals("FLYCAM_StrafeRight")){
		    	if(cam.equals(_cam3d))
		    		moveCameraKeys(-value, true);
		    	else
		    		moveCameraKeys(value, true);
		    }else if (name.equals("FLYCAM_Rise")){
		        riseCamera(value);
		    }else if (name.equals("FLYCAM_Lower")){
		        riseCamera(-value);
		    }else if (name.equals("FLYCAM_ZoomIn")){
		    	_eventController.actionPerformed(new ActionEvent(value,ActionEvent.ACTION_PERFORMED, "ZoomIn"));
		        zoomCamera(value);
		    }else if (name.equals("FLYCAM_ZoomOut")){
		    	_eventController.actionPerformed(new ActionEvent(value,ActionEvent.ACTION_PERFORMED, "ZoomOut"));
		        zoomCamera(-value);
		    }
		}
    }

    public Vector3f getPositionVec(){
        _collisions.clear();

    	Vector3f position = new Vector3f(0f,-1f,0f);
        Vector3f click3d = cam.getWorldCoordinates(inputManager.getCursorPosition().clone(), 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(inputManager.getCursorPosition().clone(), cam.getFrustumNear()).subtractLocal(click3d).normalizeLocal();

        _node.collideWith(new Ray(click3d, dir), _collisions);
        if(_collisions.size() != 0){
        	position  = _collisions.getClosestCollision().getContactPoint().clone();
        	position.set((float)Math.round(position.getX()*1000)/1000,(float)Math.round(position.getY()*1000)/1000, (float)Math.round(position.getZ()*1000)/1000);
        }
        return position;
    }
    
    
    public Geometry getGeometryCollision(){
    	Geometry geometry = new Geometry("NULL");
    	if(_collisions.size() != 0)
    		geometry = _collisions.getClosestCollision().getGeometry();
    	return geometry;
    }
    
    /* (non-Javadoc)
     * @see com.jme3.input.FlyByCamera#onAction(java.lang.String, boolean, float)
     */
    public void onAction(String name, boolean value, float tpf) {
        if(enabled){
	        if (name.equals("FLYCAM_RotateDrag") && dragToRotate){
            	_isPressed = !_isPressed;
	            canRotate = value;
	    		
	            if(canRotate && _cam2dEnabled && _cam3dEnabled)
	            	_lastScreenClick = inputManager.getCursorPosition().clone();
	        }
        }
        
        if(!value){
        	if(name.equals("FLYCAM_Enter")){
            	_eventController.actionPerformed(new ActionEvent(this ,ActionEvent.ACTION_PERFORMED, "ENTER"));	
        	}else if(name.equals("FLYCAM_Escape")){
            	_eventController.actionPerformed(new ActionEvent(this ,ActionEvent.ACTION_PERFORMED, "ESCAPE"));	
        	}else if(name.equals("FLYCAM_RotateDrag")){
        		_eventController.actionPerformed(new ActionEvent(getGeometryCollision().getName(), ActionEvent.ACTION_PERFORMED, "collision"));
            	_eventController.actionPerformed(new ActionEvent(this.getPositionVec(), ActionEvent.ACTION_PERFORMED, "cursor_click_up"));
        	}
        }
    }
}
