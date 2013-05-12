package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.ShowEnsembleViewAction;

/**
 * This class represents the ensemble root node in the navigation tree. All
 * ensembles are children of it. The menu pop shows the create ensemble action.
 */
public class EnsembleManagementTreeNode extends TreeElement {

	private MainWindow mainWindow;

	/**
	 * Instantiates a new ensemble management tree node.
	 * 
	 * @param customObject
	 *            the custom object
	 * @param mainWindow
	 *            the main window
	 */
	public EnsembleManagementTreeNode(Object customObject, MainWindow mainWindow) {
		super(customObject);
		this.mainWindow = mainWindow;
	}

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.feu.showgo.ui.tree.TreeElement#getPopupMenu()
	 */
	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();

		JMenuItem createEnsemble = new JMenuItem("Neues Ensemble anlegen");
		createEnsemble.addActionListener(new ShowEnsembleViewAction(mainWindow));

		popup.add(createEnsemble);
		return popup;
	}

}
