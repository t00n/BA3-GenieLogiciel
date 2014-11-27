package be.ac.ulb.infof307.g05.canvas.tab;

import java.awt.event.ActionEvent;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import be.ac.ulb.infof307.g05.EventController;
import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Stage;

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
	private JTree m_stages; // TODO ajouter les étages
	
	/**
	 * Make an object tree with the current stage childs.
	 * @return A new tree to be shown
	 */
	private JTree makeTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		DefaultMutableTreeNode currentStageNode = null;
		Stage currentStage = _eventController.getStage();
		int childsPerStage = 0;
		if (currentStage != null) {
			for (Stage stage : _eventController.getProject().getStages()) {
				currentStageNode = new DefaultMutableTreeNode("Stage " + stage.getLevel());
				for (CompositeObject cobj : stage.getCompositeObjects()) {
					currentStageNode.add(new DefaultMutableTreeNode(cobj.getName()));
					childsPerStage++;
				}
				root.add(currentStageNode);
			}
			_childObjectsNumber = root.getChildCount() + childsPerStage;
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
	        }
	    });		
	}
	
	/**
	 * Action Listener when we select an element on the list.
	 * @param tse
	 */
	private void jTreeActionListener(TreeSelectionEvent tse ) {
	    String node = tse.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if (node.contains("Stage")) {
	    	// TODO Refactorer en plus joli..
	    	/*int uniqueId = (int) System.currentTimeMillis();
	    	ActionEvent action = new ActionEvent(this, uniqueId, "SelectStageFromTree");
	    	System.out.println(action.getActionCommand());
	    	*/
	    	String[] parts = node.split(" ");
	    	_eventController.setStageByName(parts[1]); /*
	    	_eventController.actionPerformed(action);*/
	    	System.out.println("Étage sélectionné" + parts[1]);
	    }
	    else {
	    	// TODO selectionner l'objet
	    	int uniqueId = (int) System.currentTimeMillis();
	    	ActionEvent action = new ActionEvent(this, uniqueId, "SelectObjectFromTree");
	    	System.out.println(action.getActionCommand());
	    	_eventController.actionPerformed(action);
	    	System.out.println("Selectionne l'objet " + node);
	    }
	}

	public String getName(){
		return _name;
	}
	
	public void update() { // TODO trop d'updates <- améilloré.. mais le code est un peu dégueulasse.. à refaire.
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
