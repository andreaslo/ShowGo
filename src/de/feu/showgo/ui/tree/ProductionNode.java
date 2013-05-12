package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.DeleteProductionAction;
import de.feu.showgo.ui.actions.EditProductionAction;
import de.feu.showgo.ui.actions.ShowPlaybillAction;

/**
 * This class represents an production in the navigation tree. The menu pop
 * shows the edit and delete ensemble as well as the display playbill actions.
 */
public class ProductionNode extends TreeElement {

	private MainWindow mainWindow;
	private Production production;

	/**
	 * Instantiates a new production node.
	 *
	 * @param production the production
	 * @param mainWindow the main window
	 */
	public ProductionNode(Production production, MainWindow mainWindow) {
		super(production);
		this.production = production;
		this.mainWindow = mainWindow;
	}

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see de.feu.showgo.ui.tree.TreeElement#getPopupMenu()
	 */
	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();

		JMenuItem editProduction = new JMenuItem("Bearbeiten");
		editProduction.addActionListener(new EditProductionAction(mainWindow, production));

		JMenuItem showPlaybill = new JMenuItem("Programmheft anzeigen");
		showPlaybill.addActionListener(new ShowPlaybillAction(mainWindow, production));

		JMenuItem deleteProduction = new JMenuItem("Löschen");
		deleteProduction.addActionListener(new DeleteProductionAction(mainWindow, production));

		popup.add(editProduction);
		popup.addSeparator();
		popup.add(showPlaybill);
		popup.addSeparator();
		popup.add(deleteProduction);
		return popup;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString() {
		return production.getName();
	}

}
