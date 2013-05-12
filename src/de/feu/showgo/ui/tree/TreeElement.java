package de.feu.showgo.ui.tree;

import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This abstract class represents an element in the navigation tree. It defines
 * the abstract getPopupMenu method. This menu is displayed if a users right
 * clicks an element.
 */
public abstract class TreeElement extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new tree element.
	 *
	 * @param customObject the custom object
	 */
	public TreeElement(Object customObject) {
		super(customObject);
	}

	/**
	 * Gets the popup menu. This menu is displayed on right click.
	 *
	 * @return the popup menu
	 */
	public abstract JPopupMenu getPopupMenu();

}
