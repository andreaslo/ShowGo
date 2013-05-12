package de.feu.showgo.ui.tree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.feu.showgo.model.Ensemble;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.actions.CopyEnsembleAction;
import de.feu.showgo.ui.actions.DeleteEnsembleAction;
import de.feu.showgo.ui.actions.EditEnsembleAction;
import de.feu.showgo.ui.actions.ViewEnsembleAction;

/**
 * This class represents an ensemble in the navigation tree. The menu pop
 * shows the view, edit, copy and delete ensemble actions.
 */
public class EnsembleNode extends TreeElement {

	private MainWindow mainWindow;
	private Ensemble ensemble;

	/**
	 * Instantiates a new ensemble node.
	 *
	 * @param ensemble the ensemble
	 * @param mainWindow the main window
	 */
	public EnsembleNode(Ensemble ensemble, MainWindow mainWindow) {
		super(ensemble);
		this.ensemble = ensemble;
		this.mainWindow = mainWindow;
	}

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see de.feu.showgo.ui.tree.TreeElement#getPopupMenu()
	 */
	@Override
	public JPopupMenu getPopupMenu() {
		JPopupMenu popup = new JPopupMenu();

		JMenuItem showEnsemble = new JMenuItem("Anzeigen");
		showEnsemble.addActionListener(new ViewEnsembleAction(mainWindow, ensemble));

		JMenuItem editEnsemble = new JMenuItem("Bearbeiten");
		editEnsemble.addActionListener(new EditEnsembleAction(mainWindow, ensemble));

		JMenuItem copyEnsemble = new JMenuItem("Kopieren");
		copyEnsemble.addActionListener(new CopyEnsembleAction(mainWindow, ensemble));

		JMenuItem deleteEnsemble = new JMenuItem("Löschen");
		deleteEnsemble.addActionListener(new DeleteEnsembleAction(mainWindow, ensemble));

		popup.add(showEnsemble);
		popup.add(editEnsemble);
		popup.addSeparator();
		popup.add(copyEnsemble);
		popup.addSeparator();
		popup.add(deleteEnsemble);
		return popup;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	@Override
	public String toString() {
		return ensemble.getName();
	}

}
