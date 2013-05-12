package de.feu.showgo.ui.tree;

import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class TreeElement extends DefaultMutableTreeNode {
	
	private static final long serialVersionUID = 1L;
	
	Object customObject;
	
	public TreeElement(Object customObject){
		super(customObject);
		this.customObject = customObject;
	}
	
	public JPopupMenu getPopupMenu(){
		JPopupMenu popup = new JPopupMenu();
		return popup;
	}
	

	
}
