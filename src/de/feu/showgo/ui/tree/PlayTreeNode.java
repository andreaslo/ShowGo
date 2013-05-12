package de.feu.showgo.ui.tree;


import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.ShowReadPlayAction;

/**
 * This class represents the play root node in the navigation tree. All
 * plays are children of it. The menu pop shows the read play action.
 */
public class PlayTreeNode extends TreeElement {

	private MainWindow mainWindow;
	
	/**
	 * Instantiates a new play tree node.
	 *
	 * @param customObject the custom object
	 * @param mainWindow the main window
	 */
	public PlayTreeNode(Object customObject, MainWindow mainWindow) {
		super(customObject);
		this.mainWindow = mainWindow;
	}
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see de.feu.showgo.ui.tree.TreeElement#getPopupMenu()
	 */
	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		
		JMenuItem readPlay = new JMenuItem("Stück einlesen");
		readPlay.addActionListener(new ShowReadPlayAction(mainWindow));
		
		popup.add(readPlay);
		return popup;
	}
	
}
