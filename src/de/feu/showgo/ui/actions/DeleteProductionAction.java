package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Production;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.views.ProductionView;

public class DeleteProductionAction implements ActionListener {

	private Production production;
	private MainWindow mainWindow;

	public DeleteProductionAction(MainWindow mainWindow, Production production) {
		this.mainWindow = mainWindow;
		this.production = production;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int choice = JOptionPane.showConfirmDialog(mainWindow, "Möchten Sie wirklich die Inszenierung " + production.getName() + " löschen?", production.getName()
				+ " löschen", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {

			// If the deleted ensemble is currently being edited, switch to the
			// startup view.
			JPanel currentView = mainWindow.getCurrentView();
			if (currentView instanceof ProductionView) {
				Production editedProduction = ((ProductionView) currentView).getModel();
				if (editedProduction == production) {
					mainWindow.showStartupView();
				}
			}

			ShowGoDAO.getShowGo().delteProduction(production);
			mainWindow.getNavTree().refreshProductions();
		}
	}

}
