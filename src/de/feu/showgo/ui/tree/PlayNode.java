package de.feu.showgo.ui.tree;

import javax.swing.JPopupMenu;

import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;

/**
 * This class represents a play in the navigation tree. The menu pop is empty as
 * there are now actions defined on plays.
 */
public class PlayNode extends TreeElement {

	@SuppressWarnings("unused")
	private MainWindow mainWindow;
	private TheaterPlay play;

	/**
	 * Instantiates a new play node.
	 *
	 * @param play the play
	 * @param mainWindow the main window
	 */
	public PlayNode(TheaterPlay play, MainWindow mainWindow) {
		super(play);
		this.play = play;
		this.mainWindow = mainWindow;
	}

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see de.feu.showgo.ui.tree.TreeElement#getPopupMenu()
	 */
	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		return popup;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString() {
		return play.getName();
	}

}
