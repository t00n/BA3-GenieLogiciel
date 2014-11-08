package be.ac.ulb.infof307.g05.model;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author segolene & daniele
 * @version 0.1
 */
public class IteratorComposite implements Iterator<Component> {
	private Stack<Iterator<Component>> pile = new Stack<Iterator<Component>>();

	/**
	 * Pushes a component iterator into a stack.
	 * @param iterateur
	 * 			Iterator of the component pushed into the stack.
	 */
	public IteratorComposite(Iterator<Component> iterateur) {
		this.pile.push(iterateur);
	}
	
	/**
	 * Returns the next component in the stack.
	 * @return Returns the next component in the stack, or null if the stack has no other component.
	 */
	@Override
	public Component next() {
		if (hasNext()) {
			Iterator<Component> iterateur = (Iterator<Component>) this.pile.peek();
			Component comp = (Component) iterateur.next();
			this.pile.push(comp.makeIterator());
			return comp;}
		else {
			return null;
		}
	}
	
	/**
	 * Tells whether this is still a component on the stack.
	 * @return Returns true or false, depending whether the stack has a next component.
	 */
	@Override
	public boolean hasNext() {
		if (this.pile.empty()) {
			return false;
		}
		else {
			Iterator<Component> iterateur = (Iterator<Component>) this.pile.peek();
			if (!iterateur.hasNext()) {
				this.pile.pop();
				return hasNext();
			}
			else {
				return true;
			}
		}
	}
	
	/**
	 * Method inherited from java.util.iterator which doesn't make sense here.
	 * @throws UnsupportedOperationException if this method is called.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
