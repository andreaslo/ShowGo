package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;

public class EnsembleView extends JPanel {
	
	private final static Logger log = Logger.getLogger(EnsembleView.class);
	private MainWindow mainWindow;
	private Ensemble model;
	private PersonsTable assignedPersonsTable;
	private PersonsTable availablePersonsTable;
	
	public EnsembleView(MainWindow mainWindow) {
		log.debug("showing ensemble view");
		this.mainWindow = mainWindow;
		setName("Ensemble anlegen");
		model = new Ensemble();
		createComponent();
	}


	private void createComponent() {
		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30 } };
		setLayout(new TableLayout(size));
	
		JPanel ensembleNamePanel = createEnsembleNamePanel();
		assignedPersonsTable = createAssignedPersonsPanel();
		availablePersonsTable = createAvailablePersonsPanel();
		
		
		add(ensembleNamePanel, "1,1");
		add(assignedPersonsTable, "1,2");
		add(availablePersonsTable, "1,3");
		
		revalidate();
		repaint();
	}
	
	private JPanel createEnsembleNamePanel(){
		JPanel ensembleNamePanel = new JPanel();
		double size[][] = { { 120, TableLayout.FILL }, { 30 } };
		TableLayout layout = new TableLayout(size);
		ensembleNamePanel.setLayout(layout);
		
		ensembleNamePanel.add(new JLabel("Ensemble Name:"), "0,0");
		
		JTextField ensembleNameInput = new JTextField();
		ensembleNamePanel.add(ensembleNameInput, "1,0,f,c");

		return ensembleNamePanel;
	}

	private PersonsTable createAvailablePersonsPanel(){
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
	
	private PersonsTable createAssignedPersonsPanel(){
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
	
	private List<Person> getAvailablePersons(){
		List<Person> avaiblablePersons = new LinkedList<Person>();
		for(Person p : ShowGoDAO.getShowGo().getPersons()){
			if(!model.getMembers().contains(p)){
				avaiblablePersons.add(p);
			}
		}
		return avaiblablePersons;
	}
	

}