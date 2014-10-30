package be.ac.ulb.infof307.g05.canvas.jme;


import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;


public class JmeWorld extends SimpleApplication {

	
	public JmeWorld(){
	}

	
	public void simpleInitApp(){
        // create the geometry and attach it
        Geometry teaGeom = (Geometry) assetManager.loadModel("Models/Teapot/Teapot.obj");
        teaGeom.scale(3);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(Vector3f.UNIT_XYZ.negate());

        rootNode.addLight(dl);
        rootNode.attachChild(teaGeom);

        // Setup first view
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        cam.setViewPort(0.0f, 1.0f, 0f, 0.5f);
        cam.setLocation(new Vector3f(3.3212643f, 4.484704f, 4.2812433f));
        cam.setRotation(new Quaternion(-0.07680723f, 0.92299235f, -0.2564353f, -0.27645364f));
        
        flyCam.setDragToRotate(true);
        
        // Setup second view
        Camera cam2 = cam.clone();
        cam2.setViewPort(0.0f, 1.0f, 0.5f, 1.0f);
        cam2.setLocation(new Vector3f(-0.10947256f, 1.5760219f, 4.81758f));
        cam2.setRotation(new Quaternion(0.0010108891f, 0.99857414f, -0.04928594f, 0.020481428f));

        ViewPort view2 = renderManager.createMainView("Bottom Left", cam2);
        view2.setClearFlags(true, true, true);
        view2.attachScene(rootNode);


        //test multiview for gui
        guiViewPort.getCamera().setViewPort(.5f, 1f, .5f, 1f);

        // Setup second gui view
        Camera guiCam2 = guiViewPort.getCamera().clone();
        guiCam2.setViewPort(0f, 0.5f, 0f, 0.5f);
        ViewPort guiViewPort2 = renderManager.createPostView("Gui 2", guiCam2);
        guiViewPort2.setClearFlags(false, false, false);
        guiViewPort2.attachScene(guiViewPort.getScenes().get(0));
	}
}
