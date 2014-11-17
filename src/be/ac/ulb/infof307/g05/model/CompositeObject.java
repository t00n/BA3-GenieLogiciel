package be.ac.ulb.infof307.g05.model;

import java.util.Iterator;
import java.util.Vector;


public abstract class CompositeObject implements Iterable<SceneObject> {
	
	private Vector<SceneObject> _child = new Vector<SceneObject>();

	
	@Override
	public Iterator<SceneObject> iterator(){
		/** this method make the object iterable **/
		return _child.iterator();
	}
	
	public void add(SceneObject object){
		/** this method add a child **/
		_child.add(object);
	}

	public boolean remove(CompositeObject object){
		/** this method search recursively an child an remove it if found **/
		boolean isFound = false;

		if(_child.contains(object)){
			_child.removeElement(object);
		}else{
			for(CompositeObject child:_child){
				if(!isFound)
					isFound = child.remove(object);
			}
		}
		return isFound;
	}
	
	public int size(){
		return _child.size();
	}
}
