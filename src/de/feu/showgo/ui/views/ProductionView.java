package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;

public class ProductionView extends JPanel {

	private final static Logger log = Logger.getLogger(ProductionView.class);
	private MainWindow mainWindow;
	private JTextField productionNameInput;
	private JComboBox<TheaterPlay> playSelect;

	public ProductionView(MainWindow mainWindow) {
		log.debug("showing production view");
		this.mainWindow = mainWindow;
		setName("Neue Inszenierung anlegen");
		createComponent();
	}

	private void createComponent() {

		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, 60, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30, 30 } };
		setLayout(new TableLayout(size));

		JPanel productionNamePanel = createProductionNamePanel();
		JPanel playSelectPanel = createPlaySelectPanel();

		add(productionNamePanel, "1,1,f,t");
		add(playSelectPanel, "1,2");

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

	private JPanel createPlaySelectPanel() {
		JPanel productionNamePanel = new JPanel();
		double size[][] = { { TableLayout.PREFERRED, 20, TableLayout.PREFERRED }, { 30, 30 } };
		TableLayout layout = new TableLayout(size);
		productionNamePanel.setLayout(layout);

		productionNamePanel.add(new JLabel("Bitte wählen Sie ein Stück aus:"), "0,0");

		playSelect = new JComboBox<TheaterPlay>();
		playSelect.setRenderer(new PlayComboRederer());
		for (TheaterPlay play : ShowGoDAO.getShowGo().getPlays()) {
			playSelect.addItem(play);
		}
		
		JButton usePlayAction = new JButton("Stück verwenden");
		usePlayAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				log.debug("selected play: " + playSelect.getSelectedItem());
			}
		});
		
		productionNamePanel.add(playSelect, "0,1,f,c");
		productionNamePanel.add(usePlayAction, "2,1,c,c");

		return productionNamePanel;
	}

	private class PlayComboRederer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value != null) {
				TheaterPlay item = (TheaterPlay) value;
				setText(item.getName());
			}

			return this;
		}
	}

}
