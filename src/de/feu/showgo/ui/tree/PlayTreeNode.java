package de.feu.showgo.ui.tree;


import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.ShowReadPlayAction;

public class PlayTreeNode extends TreeElement {

	private MainWindow mainWindow;
	
	public PlayTreeNode(Object customObject, MainWindow mainWindow) {
		super(customObject);
		this.mainWindow = mainWindow;
	}
	private static final long serialVersionUID = 1L;

	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		
		JMenuItem readPlay = new JMenuItem("Stück einlesen");
		readPlay.addActionListener(new ShowReadPlayAction(mainWindow));
		
		popup.add(readPlay);
		return popup;
	}
	
}
