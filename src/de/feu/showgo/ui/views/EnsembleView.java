package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.WindowColors;

public class EnsembleView extends JPanel {

	private final static Logger log = Logger.getLogger(EnsembleView.class);
	private MainWindow mainWindow;
	private Ensemble model;
	private PersonsTable assignedPersonsTable;
	private PersonsTable availablePersonsTable;
	private JLabel currentMessage;
	private JTextField ensembleNameInput;
	
	public EnsembleView(MainWindow mainWindow) {
		log.debug("showing ensemble view");
		this.mainWindow = mainWindow;
		setName("Ensemble anlegen");
		model = new Ensemble();
		createComponent();
	}

	public EnsembleView(MainWindow mainWindow, Ensemble ensemble) {
		log.debug("showing ensemble view");
		this.mainWindow = mainWindow;
		setName("Ensemble " + ensemble.getName() + " bearbeiten");
		model = ensemble;
		createComponent();
	}

	private void createComponent() {
		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, 60, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30, 30 } };
		setLayout(new TableLayout(size));
	
		JPanel ensembleNamePanel = createEnsembleNamePanel();
		assignedPersonsTable = createAssignedPersonsPanel();
		availablePersonsTable = createAvailablePersonsPanel();
		

		JLabel assignedRolesLabel = new JLabel("Zugewiesene Rollen");
		Font boldFont=new Font(assignedRolesLabel.getFont().getName(),Font.BOLD,assignedRolesLabel.getFont().getSize());
		assignedRolesLabel.setFont(boldFont);
		JLabel availableRolesLabel = new JLabel("Verfügbare Rollen");
		availableRolesLabel.setFont(boldFont);
		
		add(ensembleNamePanel, "1,1,f,t");
		add(assignedRolesLabel, "1,2");
		add(assignedPersonsTable, "1,3");
		add(availableRolesLabel, "1,4");
		add(availablePersonsTable, "1,5");
		
		
		JButton saveButton = new JButton("Speichern");
		add(saveButton, "1,6,l,c");
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(ensembleNameInput.getText().equals("")){
					showMessage("Bitte geben Sie dem Ensemble einen Namen.", WindowColors.ERROR);
					return;
				}
				
				if(model.getMembers().isEmpty()){
					showMessage("Einem Ensemble muss mindestens eine Person zugewisen sein.", WindowColors.ERROR);
					return;
				}
				
				model.setName(ensembleNameInput.getText());
				
				if(!ShowGoDAO.getShowGo().getEnsembles().contains(model)){
					ShowGoDAO.getShowGo().addEnsemble(model);
				}
				
				mainWindow.getNavTree().refreshEnsembles();
				
				showMessage("Das Ensemble " + model.getName() + " wurde erfolgreich gespeichert.", WindowColors.SUCCESS);
			}
		});
		
		revalidate();
		repaint();
	}

	private JPanel createEnsembleNamePanel() {
		JPanel ensembleNamePanel = new JPanel();
		double size[][] = { { 120, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		ensembleNamePanel.setLayout(layout);

		ensembleNamePanel.add(new JLabel("Ensemble Name:"), "0,0");

		ensembleNameInput = new JTextField(model.getName());
		ensembleNamePanel.add(ensembleNameInput, "1,0,f,c");

		return ensembleNamePanel;
	}

	private PersonsTable createAvailablePersonsPanel() {
		final PersonsTable availablePersonsTable = new PersonsTable(this, getAvailablePersons(), "Hinzufügen");
		availablePersonsTable.addPersonEvent(new PersonEvent() {

			@Override
			public void personEvent(Person person) {
				log.debug("assigning person: " + person);
				model.assignPerson(person);
				assignedPersonsTable.update(model.getMembers());
				availablePersonsTable.update(getAvailablePersons());
			}
		});

		return availablePersonsTable;
	}

	private PersonsTable createAssignedPersonsPanel() {
		PersonsTable selectedPersonsTable = new PersonsTable(this, model.getMembers(), "Entfernen");
		selectedPersonsTable.addPersonEvent(new PersonEvent() {

			@Override
			public void personEvent(Person person) {
				log.debug("removing person: " + person);
				model.removePerson(person);
				assignedPersonsTable.update(model.getMembers());
				availablePersonsTable.update(getAvailablePersons());
			}
		});

		return selectedPersonsTable;
	}

	private List<Person> getAvailablePersons() {
		List<Person> avaiblablePersons = new LinkedList<Person>();
		for (Person p : ShowGoDAO.getShowGo().getPersons()) {
			if (!model.getMembers().contains(p)) {
				avaiblablePersons.add(p);
			}
		}
		return avaiblablePersons;
	}
	
	private void showMessage(String message, Color background){
		removeMessage();
		
		log.debug("showing message " + message);
		currentMessage = new JLabel(message);
		currentMessage.setBorder(BorderFactory.createEtchedBorder());
		currentMessage.setHorizontalAlignment( SwingConstants.CENTER );
		currentMessage.setBackground(background);
		currentMessage.setOpaque(true);
		this.add(currentMessage, "1,7");
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

}