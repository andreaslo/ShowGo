package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.ShowProductionViewAction;

/**
 * This class represents the production root node in the navigation tree. All
 * productions are children of it. The menu pop shows the create production action.
 */
public class ProductionManagementTreeNode extends TreeElement{

	private MainWindow mainWindow;
	
	/**
	 * Instantiates a new production management tree node.
	 *
	 * @param customObject the custom object
	 * @param mainWindow the main window
	 */
	public ProductionManagementTreeNode(Object customObject, MainWindow mainWindow) {
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
		
		JMenuItem createProduction = new JMenuItem("Neue Inszenierung anlegen");
		createProduction.addActionListener(new ShowProductionViewAction(mainWindow));
		
		popup.add(createProduction);
		return popup;
	}

}
