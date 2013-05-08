package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.io.ParseUtil;
import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Production;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.WindowColors;
import de.feu.showgo.util.CastingGenerator;

public class ProductionView extends JPanel {

	private final static Logger log = Logger.getLogger(ProductionView.class);
	private MainWindow mainWindow;
	private JTextField productionNameInput;
	private JComboBox<Ensemble> ensembleSelect;
	private JComboBox<TheaterPlay> playSelect;
	private JLabel currentMessage;
	private Production model;
	private CastSelectionPanel castSelectionPanel;
	private EditTheaterPlayPanel editPlayPanel;
	private CastSelectionPanel nonActorSelection;
	private boolean alreadyExists;
	
	public ProductionView(MainWindow mainWindow) {
		log.debug("showing production view");
		this.mainWindow = mainWindow;
		setName("Neue Inszenierung anlegen");
		model = new Production();
		createComponent();
	}
	
	public ProductionView(MainWindow mainWindow, Production production) {
		log.debug("showing production view");
		this.mainWindow = mainWindow;
		setName("Neue Inszenierung anlegen");
		model = production;
		alreadyExists = true;
		createComponentEditMode();
	}

	private void createComponent() {

		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, 60, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30 } };
		setLayout(new TableLayout(size));

		JPanel productionNamePanel = createProductionNamePanel();
		JPanel ensembleSelect = createEnsembleSelectPanel();
		JPanel playSelectPanel = createPlaySelectPanel();

		
		add(productionNamePanel, "1,1,f,t");
		add(ensembleSelect, "1,2,f,t");
		add(playSelectPanel, "1,3");
	}
	
	private void createComponentEditMode(){
		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, 60, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30 } };
		setLayout(new TableLayout(size));

		TheaterPlay copy = null;
		try {
			copy = ParseUtil.copyPlay(model.getPlay());
		} catch (JAXBException e1) {
			log.error("", e1);
		} catch (IOException e1) {
			log.error("", e1);
		}
		
		JPanel productionNamePanel = createProductionNamePanel();
		JPanel ensembleSelectPanel = createEnsembleSelectPanel();
		ensembleSelect.setEnabled(false);
		
		castSelectionPanel = new CastSelectionPanel(mainWindow, copy.getRoles(), model.getEnsamble().getMembers(), "Besetzung der Darstellerrollen");
		
		editPlayPanel = new EditTheaterPlayPanel(mainWindow, copy);
		editPlayPanel.getRoleDisplay().addRoleDeleteEventListener(castSelectionPanel);
		editPlayPanel.addRoleDeleteListener(castSelectionPanel);
		
		nonActorSelection = new CastSelectionPanel(mainWindow, model.getNonActorRoles(), model.getEnsamble().getMembers(), "Besetzung der Nicht-Darstellerrollen");
		
		JPanel submitPanel = createSubmitPanel();
		
		add(productionNamePanel, "1,1,f,t");
		add(ensembleSelectPanel, "1,2,f,t");
		add(castSelectionPanel,"1,4");
		add(nonActorSelection,"1,5");
		add(editPlayPanel, "1,6");
		add(submitPanel, "1,7");
	}

	private JPanel createProductionNamePanel() {
		JPanel productionNamePanel = new JPanel();
		double size[][] = { { 180, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		productionNamePanel.setLayout(layout);

		productionNamePanel.add(new JLabel("Name der Inszenierung:"), "0,0");

		productionNameInput = new JTextField(model.getName());
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

		final JButton usePlayAction = new JButton("Stück und Ensemble verwenden");
		usePlayAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playSelect.getSelectedItem() != null) {
					TheaterPlay copy = null;
					try {
						copy = ParseUtil.copyPlay((TheaterPlay) playSelect.getSelectedItem());
					} catch (JAXBException e1) {
						log.error("", e1);
					} catch (IOException e1) {
						log.error("", e1);
					}

					if (copy == null) {
						JOptionPane.showMessageDialog(mainWindow,
								"Es ist ein Problem beim Kopieren des Stücks aufgtreten. Für weitere Informationen s. das Logfile", "Fehler",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					model.setPlay(copy);
					
					usePlayAction.setEnabled(false);
					playSelect.setEnabled(false);
					ensembleSelect.setEnabled(false);
					
					Ensemble selectedEnsemble = (Ensemble) ensembleSelect.getSelectedItem();
					CastingGenerator.generateCasting(selectedEnsemble, copy);
					castSelectionPanel = new CastSelectionPanel(mainWindow, copy.getRoles(), selectedEnsemble.getMembers(), "Besetzung der Darstellerrollen");
					
					editPlayPanel = new EditTheaterPlayPanel(mainWindow, copy);
					editPlayPanel.getRoleDisplay().addRoleDeleteEventListener(castSelectionPanel);
					editPlayPanel.addRoleDeleteListener(castSelectionPanel);
					
					nonActorSelection = createNonActorPersonAssignment();
					
					JPanel submitPanel = createSubmitPanel();
					
					add(castSelectionPanel,"1,4");
					add(nonActorSelection,"1,5");
					add(editPlayPanel, "1,6");
					add(submitPanel, "1,7");
					revalidate();
					repaint();
				}
			}
		});

		productionNamePanel.add(playSelect, "0,1,f,c");
		productionNamePanel.add(usePlayAction, "2,1,c,c");

		return productionNamePanel;
	}
	
	private CastSelectionPanel createNonActorPersonAssignment(){
		List<Role> roles = new ArrayList<Role>();
		
		Role r = new Role();
		r.setName("Regie");
		roles.add(r);
		
		r = new Role();
		r.setName("Regieassistenz");
		roles.add(r);
		
		r = new Role();
		r.setName("Kostüme");
		roles.add(r);
		
		r = new Role();
		r.setName("Requisite");
		roles.add(r);
		
		r = new Role();
		r.setName("Bühnenbild");
		roles.add(r);
		
		r = new Role();
		r.setName("Techniker");
		roles.add(r);
		
		r = new Role();
		r.setName("Helfer");
		roles.add(r);
		
		Ensemble selectedEnsemble = (Ensemble) ensembleSelect.getSelectedItem();
		CastSelectionPanel selectionPanel = new CastSelectionPanel(mainWindow, roles, selectedEnsemble.getMembers(), "Besetzung der Nicht-Darstellerrollen");
		return selectionPanel;		
	}
	
	private JPanel createEnsembleSelectPanel() {
		JPanel ensembleSelectPanel = new JPanel();
		double size[][] = { { TableLayout.PREFERRED, 20, TableLayout.PREFERRED }, { 30, 30 } };
		TableLayout layout = new TableLayout(size);
		ensembleSelectPanel.setLayout(layout);

		ensembleSelectPanel.add(new JLabel("Bitte wählen Sie ein Ensemble aus:"), "0,0");

		ensembleSelect = new JComboBox<Ensemble>();
		ensembleSelect.setRenderer(new EnsembleComboRederer());
		for (Ensemble ensemble : ShowGoDAO.getShowGo().getEnsembles()) {
			log.debug("ading ensemble: " + ensemble.getName());
			ensembleSelect.addItem(ensemble);
		}
		
		if(model.getEnsamble() != null){
			ensembleSelect.setSelectedItem(model.getEnsamble());
		}

		ensembleSelectPanel.add(ensembleSelect, "0,1,f,c");

		return ensembleSelectPanel;
	}
	
	private JPanel createSubmitPanel() {
		JPanel submitPanel = new JPanel();
		submitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton saveButton = new JButton("Inszenierung Speichern");
		submitPanel.add(saveButton);

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				log.debug("saving production");
				
				if(productionNameInput.getText().equals("")){
					showMessage("Bitte geben Sie einen Namen für die Inszenierung an.", WindowColors.ERROR);
					return;
				}
				
				castSelectionPanel.saveCastToBackingModel();
				nonActorSelection.saveCastToBackingModel();
				editPlayPanel.saveToBackingModel();
				
				TheaterPlay copy = null;
				try {
					copy = ParseUtil.copyPlay(editPlayPanel.getPlay());
				} catch (JAXBException e) {
					log.error("",e);
				} catch (IOException e) {
					log.error("",e);
				}
				
				model.setPlay(copy);
				model.setNonActorRoles(nonActorSelection.getRoles());
				model.setName(productionNameInput.getText());
				model.setEnsemble((Ensemble) ensembleSelect.getSelectedItem());
				
				if(!alreadyExists){
					ShowGoDAO.getShowGo().addProduction(model);
				}
				
				mainWindow.getNavTree().refreshProductions();
				ShowGoDAO.autosave();
				
				showMessage("Die Inszenierung " + model.getName() + " wurde erfolgreich gespeichert.", WindowColors.SUCCESS);
			}
		});

		return submitPanel;
	}
	
	private void showMessage(String message, Color background){
		removeMessage();
		
		log.debug("showing message " + message);
		currentMessage = new JLabel(message);
		currentMessage.setBorder(BorderFactory.createEtchedBorder());
		currentMessage.setHorizontalAlignment( SwingConstants.CENTER );
		currentMessage.setBackground(background);
		currentMessage.setOpaque(true);
		this.add(currentMessage, "1,8");
		this.revalidate();
		this.repaint();
	}
	
	private void removeMessage(){
		if(currentMessage != null){
			this.remove(currentMessage);
			this.revalidate();
			this.repaint();
		}
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
	
	private class EnsembleComboRederer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value != null) {
				Ensemble item = (Ensemble) value;
				setText(item.getName());
			}

			return this;
		}
	}

	public Production getModel() {
		return model;
	}

}
