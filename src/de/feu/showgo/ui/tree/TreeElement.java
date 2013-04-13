package de.feu.showgo.ui.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeElement extends DefaultMutableTreeNode {
	
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
