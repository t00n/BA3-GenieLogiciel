package be.ac.ulb.infof307.g05.model;

import java.util.ArrayList;

/**
 * <p>
 * The class Component may contain others Components. This class uses the pattern Composite
 * associated with a special Iterator (IteratorComposite) which allows to iterate between
 * each child of a component.
 * </p>
 * <p>
 * e.g :
 * <ul>
 * 		<li> A Wall may contain Doors, Windows, Mural decorations, ...</li>
 *  	<li> A floor may contain some objects like a table, some chairs, ...</li>
 *  </ul>
 *  </p>
 *  
 * @author segolene & daniele
 * @version 0.1
 */
public abstract class Component {

	protected ArrayList<Component> m_components = new ArrayList<Component>();
	
	//public operations
	
	/**
	 * Adds a component to this component.
	 * @param comp
	 * 			New component added to this component.
	 */
	public void add(Component comp) {
		this.m_components.add(comp);
	}
	
	/**
	 * Removes a component to this component.
	 * @param comp
	 * 			New component added to this component.
	 */
	public void remove(Component comp) {
		this.m_components.remove(comp);
	}
	
	/**
	 * Returns the child (Component) of this component, referenced by the parameter nbr.
	 * @param nbr
	 * 			Number of the child being accessed.
	 * @return Returns the child referenced by the parameter.
	 */
	public Component getChild(int nbr) {
		return this.m_components.get(nbr);
	}
	
	/**
	 * Returns a composite iterator pointing to the components of this component.
	 * @return Returns a composite iterator pointing to the components of this component.
	 * @see IteratorComposite.
	 */
	public IteratorComposite makeIterator() {
		return new IteratorComposite(m_components.iterator());
	}
	
}