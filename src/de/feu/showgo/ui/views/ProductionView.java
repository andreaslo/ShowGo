package de.feu.showgo.ui.views;

import java.awt.Font;

import info.clearthought.layout.TableLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import de.feu.showgo.ui.MainWindow;

public class ProductionView extends JPanel {

	private final static Logger log = Logger.getLogger(ProductionView.class);
	private MainWindow mainWindow;
	private JTextField productionNameInput;
	
	public ProductionView(MainWindow mainWindow) {
		log.debug("showing production view");
		this.mainWindow = mainWindow;
		setName("Neue Inszenierung anlegen");
		createComponent();
	}
	
	private void createComponent(){
		
		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, 60, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30, 30 } };
		setLayout(new TableLayout(size));
	
		JPanel productionNamePanel = createProductionNamePanel();
		
		add(productionNamePanel, "1,1,f,t");

	}

	
	private JPanel createProductionNamePanel() {
		JPanel productionNamePanel = new JPanel();
		double size[][] = { { 180, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		productionNamePanel.setLayout(layout);

		productionNamePanel.add(new JLabel("Name der Inszenierung:"), "0,0");

		productionNameInput = new JTextField();
		productionNamePanel.add(productionNameInput, "1,0,f,c");

		return productionNamePanel;
	}
	
}
