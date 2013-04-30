package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.ShowEnsembleViewAction;


public class EnsembleManagementTreeNode extends TreeElement{

	private MainWindow mainWindow;
	
	public EnsembleManagementTreeNode(Object customObject, MainWindow mainWindow) {
		super(customObject);
		this.mainWindow = mainWindow;
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		
		JMenuItem createEnsemble = new JMenuItem("Neues Ensemble anlegen");
		createEnsemble.addActionListener(new ShowEnsembleViewAction(mainWindow));
		
		popup.add(createEnsemble);
		return popup;
	}

}
