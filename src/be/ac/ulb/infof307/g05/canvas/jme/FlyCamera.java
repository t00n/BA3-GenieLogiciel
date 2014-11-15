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

public class FlyCamera extends FlyByCamera {

	private Camera _cam2d;
	private Camera _cam3d;
	private Node _node;
	private EventController _eventControler;
	private boolean  _cam2dEnabled = false;
	private boolean  _cam3dEnabled = false;
	private CollisionResults _collisions = new CollisionResults();
	private Vector2f _lastScreenClick;
	
	
	public FlyCamera(Camera cam2d, Camera cam3d, Node node, InputManager inputManager, AppStateManager stateManager, EventController eventController){
		super(cam3d);
		_cam2d = cam2d;
		_cam3d = cam3d;
		_node = node;
		_eventControler = eventController;
		
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
        inputManager.addListener(this, new String[] {"FLYCAM_Left","FLYCAM_Right","FLYCAM_Up","FLYCAM_Down","FLYCAM_ZoomIn","FLYCAM_ZoomOut","FLYCAM_RotateDrag","FLYCAM_Forward", "FLYCAM_Backward", "FLYCAM_StrafeRight", "FLYCAM_StrafeLeft", "FLYCAM_Lower", "FLYCAM_Rise"});
        inputManager.setCursorVisible(dragToRotate);
	}
	
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
	
    protected void moveCamera(float value, boolean sideways){
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
    
    protected void moveGeometry(Geometry geom, float value, boolean sideways){
    	Vector3f vecTransition = new Vector3f();
    	Vector3f mousePosition = getPositionVec();
    	float coordY = geom.getWorldTranslation().getY(); //Retiens la position Y de la Geometry
        if(sideways){
        	vecTransition.setX((float)1);
        }
        else{
        	vecTransition.setZ((float)1);
        }
        
        value *= 2;
        vecTransition.multLocal(value * moveSpeed);
        mousePosition.addLocal(vecTransition);
        mousePosition.setY(coordY);
        geom.setLocalTranslation(mousePosition);      	
    }

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
	
    public void onAnalog(String name, float value, float tpf){
        _eventControler.actionPerformed(new ActionEvent(this.getPositionVec(), ActionEvent.ACTION_PERFORMED, "cursor_move"));
        
        Geometry geometryToMove = getGeometryCollision();

		if(enabled){
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
	        			moveCamera(value, true);
	        		else
	        			moveGeometry(geometryToMove, value, true);
	        }else if (name.equals("FLYCAM_Right")){
	        	if(cam.equals(_cam3d))
	        		rotateCamera(-value, new Vector3f(0,1,0));
	        	else if(canRotate)
	        		if((geometryToMove.getName().equals("Floor")) || (geometryToMove.getName().equals("Grid")))	        		
	        			moveCamera(-value, true);
	        		else
	        			moveGeometry(geometryToMove, -value, true);
		    }else if (name.equals("FLYCAM_Up")){
	        	if(cam.equals(_cam3d))
	        		rotateCamera(-value, cam.getLeft());
	        	else if(canRotate)
	        		if((geometryToMove.getName().equals("Floor")) || (geometryToMove.getName().equals("Grid")))	        		
	        			moveCamera(value, false);
	        		else
	        			moveGeometry(geometryToMove, value, false);
		    }else if (name.equals("FLYCAM_Down")){
	        	if(cam.equals(_cam3d))
	        		rotateCamera(value, cam.getLeft());
	        	else if(canRotate)
	        		if((geometryToMove.getName().equals("Floor")) || (geometryToMove.getName().equals("Grid")))	        		
	        			moveCamera(-value, false);
	        		else
	        			moveGeometry(geometryToMove, -value, false);
		    }else if (name.equals("FLYCAM_Forward")){
		    	if(cam.equals(_cam3d))
		    		moveCamera(value, false);
		    	else
		    		moveCamera(-value, false);
		    }else if (name.equals("FLYCAM_Backward")){
		    	if(cam.equals(_cam3d))
		    		moveCamera(-value, false);
		    	else
		    		moveCamera(value, false);
		    }else if (name.equals("FLYCAM_StrafeLeft")){
		    	if(cam.equals(_cam3d))
		    		moveCamera(value, true);
		    	else
		    		moveCamera(-value, true);
		    }else if (name.equals("FLYCAM_StrafeRight")){
		    	if(cam.equals(_cam3d))
		    		moveCamera(-value, true);
		    	else
		    		moveCamera(value, true);
		    }else if (name.equals("FLYCAM_Rise")){
		        riseCamera(value);
		    }else if (name.equals("FLYCAM_Lower")){
		        riseCamera(-value);
		    }else if (name.equals("FLYCAM_ZoomIn")){
		        zoomCamera(value);
		    }else if (name.equals("FLYCAM_ZoomOut")){
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
        //System.out.print("Pos: '" + (position.getX()) + " - " + (position.getY()) + " - " + (position.getZ()) + "'\n");
        return position;
    }
    
    
    public Geometry getGeometryCollision(){
    	Geometry geometry = new Geometry();
    	if(_collisions.size() != 0)
    		geometry = _collisions.getClosestCollision().getGeometry();   
    	return geometry;
    }
    
    public void onAction(String name, boolean value, float tpf) {
        if (enabled){
	        if (name.equals("FLYCAM_RotateDrag") && dragToRotate){
	            canRotate = value;
	    		
	            if(canRotate && _cam2dEnabled && _cam3dEnabled)
	            	_lastScreenClick = inputManager.getCursorPosition().clone();
		        	//System.out.print("Pos: '" + (_lastScreenClick.getX()) + " - " + (_lastScreenClick.getY()) + "'\n");
	        }
	    }
        
    }
}
