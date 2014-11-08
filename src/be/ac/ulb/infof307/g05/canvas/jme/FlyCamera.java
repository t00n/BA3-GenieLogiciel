package be.ac.ulb.infof307.g05.canvas.jme;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

public class FlyCamera extends FlyByCamera {

	private Camera _cam2d;
	private Camera _cam3d;
	private Vector3f _initialUpVec2d;
	private Vector3f _initialUpVec3d;
	private boolean  _cam2dEnabled = false;
	private boolean  _cam3dEnabled = false;
	private Vector2f  _lastPostClick;
	
	
	public FlyCamera(Camera cam2d, Camera cam3d, InputManager inputManager, AppStateManager stateManager){
		super(cam3d);
		_cam2d = cam2d;
		_cam3d = cam3d;
		_initialUpVec2d = _cam2d.getUp().clone();
		_initialUpVec3d = _cam3d.getUp().clone();

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
        
        inputManager.addMapping("FLYCAM_Left", new MouseAxisTrigger(0, true));
        inputManager.addMapping("FLYCAM_Right", new MouseAxisTrigger(0, false));
        inputManager.addMapping("FLYCAM_Up", new MouseAxisTrigger(1, false));
        inputManager.addMapping("FLYCAM_Down", new MouseAxisTrigger(1, true));
        
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
		System.out.println(_lastPostClick);
		
		if(canRotate && (_lastPostClick.getX() > (_cam3d.getWidth()*_cam2d.getViewPortLeft())) && (_lastPostClick.getY() < (_cam3d.getHeight()*_cam2d.getViewPortTop())) )
			isIn2d = true;
		else if(!canRotate && (position.getX() > (_cam3d.getWidth()*_cam2d.getViewPortLeft())) && (position.getY() < (_cam3d.getHeight()*_cam2d.getViewPortTop())) )
			isIn2d = true;
		
		return isIn2d;
	}
	
    public void onAnalog(String name, float value, float tpf) {
    	if(_cam2dEnabled && _cam3dEnabled){
    		if(isIn2dViewport()){
    			this.cam = _cam2d;
    			this.initialUpVec = _initialUpVec2d;
    		}else{
    			this.cam = _cam3d;
    			this.initialUpVec = _initialUpVec3d;
    		}
    	}else if(_cam2dEnabled){
			this.cam = _cam2d;
			this.initialUpVec = _initialUpVec2d;
		}else if(_cam3dEnabled){
			this.cam = _cam3d;
			this.initialUpVec = _initialUpVec3d;
		}

		if(enabled){
	        if (name.equals("FLYCAM_Left")){
	            rotateCamera(value, initialUpVec);
	        }else if (name.equals("FLYCAM_Right")){
	            rotateCamera(-value, initialUpVec);
	        }else if (name.equals("FLYCAM_Up")){
	            rotateCamera(-value, cam.getLeft());
	        }else if (name.equals("FLYCAM_Down")){
	            rotateCamera(value, cam.getLeft());
	        }else if (name.equals("FLYCAM_Forward")){
	            moveCamera(value, false);
	        }else if (name.equals("FLYCAM_Backward")){
	            moveCamera(-value, false);
	        }else if (name.equals("FLYCAM_StrafeLeft")){
	            moveCamera(value, true);
	        }else if (name.equals("FLYCAM_StrafeRight")){
	            moveCamera(-value, true);
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

    public void onAction(String name, boolean value, float tpf) {
        if (enabled)
	        if (name.equals("FLYCAM_RotateDrag") && dragToRotate){
	            canRotate = value;
	            inputManager.setCursorVisible(!value);
	    		
	            if(canRotate && _cam2dEnabled && _cam3dEnabled)
	            	_lastPostClick = inputManager.getCursorPosition().clone();
	        }
    }
}
