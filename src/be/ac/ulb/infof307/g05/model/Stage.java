package be.ac.ulb.infof307.g05.model;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;


public class Stage extends CompositeObject {

	private String _name;
	private int _level;
	
	
	public Stage(String name, int level){
		_name = name;
		_level = level;
	}
	
	public void setLevel(int level){
		_level = level;
	}
	
	public int getLevel(){
		return _level;
	}
	
	public void setName(String name){
		_name = name;
	}
	
	public String getName(String name){
		return _name;
	}
	
	public int getHeight(){
		//FIXME search recursively the tallest object
		return 0;
	}
	
	public Node getJmeNode(AssetManager assetManager){
		/** this method built recursively the tree of nodes, but stage is not a scene object **/
		Node node = new Node();
		
		for(SceneObject object:this){
			if(object.size() == 0){
				// it's a simple object
				node.attachChild(object.getJmeGeometry(assetManager));
			}else{
				// object has some children
				node.attachChild(object.getJmeNode(assetManager));
			}
		}
		return node;
	}
}
