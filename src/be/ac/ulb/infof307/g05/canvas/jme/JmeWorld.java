package be.ac.ulb.infof307.g05.canvas.jme;


import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;


public class JmeWorld extends SimpleApplication {

	private Camera[] _camera = new Camera[2];
	private ViewPort[] _view = new ViewPort[2];
	
	public JmeWorld(){
	}

	private void createViews(){
		_camera[0] = cam;
        _camera[0].setViewPort(0.0f, 1.0f, 0f, 0.5f);
        _camera[0].setLocation(new Vector3f(5f, 5f, 5f));
        _camera[0].lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 1f, 0f));
        _view[0] = viewPort;
        _view[0].setBackgroundColor(ColorRGBA.White);
        
        _camera[1] = _camera[0].clone();
        _camera[1].setViewPort(0.0f, 1.0f, 0.5f, 1.0f);
        _camera[1].setLocation(new Vector3f(0.0f, 0.0f, 5.0f));
        _camera[1].lookAt(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 1f, 0f));
        _view[1] = renderManager.createMainView("Top", _camera[1]);

	}

	public void simpleInitApp(){
	   guiViewPort.setEnabled(false);
	   createViews();
	   
	   test();
	}
	
	private void disable2d(){
		_camera[0].setViewPortTop(1);
		_camera[1].setViewPortBottom(0);
		_view[1].setEnabled(false);		
	}
	
	void test(){
		Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);

        
        flyCam.setDragToRotate(true);

        _view[0].attachScene(rootNode);
        _view[1].attachScene(rootNode);
        disable2d();
	}
}
