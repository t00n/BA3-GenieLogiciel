package be.ac.ulb.infof307.g05.canvas.tab;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import be.ac.ulb.infof307.g05.EventController;
import be.ac.ulb.infof307.g05.model.CompositeObject;

/**
 * The Class LayerTab contains the different layers like the objects in the house and the stages.
 */
public class LayerTab extends AbstractTab {
		
	private int _childObjectsNumber = 0;
	/**
	 * Contains the object's tree.
	 */
	private JTree m_objects;
	
	/**
	 * Contains only the stage's tree.
	 */
	private JTree m_stages; // TODO
	
	/**
	 * Make an object tree with the current stage childs.
	 * @return A new tree to be shown
	 */
	private JTree makeTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		DefaultMutableTreeNode currentStage = null;
		if (_eventController.getStage() != null) {
			for (CompositeObject cobj : _eventController.getStage().getCompositeObjects()) {
				if (cobj.getId() != 10000) {//.contains("Etage")) { // TODO avec les noms
					currentStage = new DefaultMutableTreeNode(cobj.getId());
				}
				if (currentStage != null) {
					currentStage.add(new DefaultMutableTreeNode(cobj.getId())); // TODO Add a method to retieve the name
				}
				root.add(currentStage);
			}
			_childObjectsNumber = root.getChildCount();
		}
		return new JTree(root);
	}
	
	/**
	 * Instantiates a new layer tab.
	 */
	public LayerTab(EventController eventController){
		_name = new String("Layers");
		_eventController = eventController;
		
		this.m_objects = makeTree();
		setActionsAvailablesForTheTreeObjects(this.m_objects);
		this.add(m_objects);
		
	}
	
	/**
	 * Add actions management to the JTree
	 * @note We can't directly add an event listener to the JTree directly because it's not a Component.
	 * @param tree
	 */
	private void setActionsAvailablesForTheTreeObjects(JTree tree) {
		tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
	        public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
	            jTreeActionListener(evt);
	        	String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	        	System.out.println(node);
	        }
	    });		
	}
	
	/**
	 * Action Listener when we select an element on the list.
	 * @param tse
	 */
	private void jTreeActionListener(TreeSelectionEvent tse ) {
	    String node = tse.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if (node.contains("Etage")) {
	    	// TODO changer d'etage
	    	System.out.println("Étage sélectionné" + node);
	    }
	    else {
	    	// TODO selectionner l'objet
	    	System.out.println("Selectionne l'objet " + node);
	    }
	}

	public String getName(){
		return _name;
	}
	
	public void update() { // TODO trop d'updates <- améilloré.. mais le code est un peu dégueulasse a refaire.
		int currentChildsNumber = this._childObjectsNumber;
		JTree objects = makeTree(); // will modify _childObjectsNumber
		
		if (currentChildsNumber != this._childObjectsNumber) {
			this.remove(m_objects);
			this.m_objects = objects;
			setActionsAvailablesForTheTreeObjects(this.m_objects);
			this.add(m_objects);
		}
	}
}
