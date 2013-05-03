package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.feu.showgo.model.Gender;
import de.feu.showgo.model.Person;
import de.feu.showgo.ui.listener.PersonEvent;

public class PersonsTable extends JPanel {

	private JPanel parentView;
	private List<Person> persons;
	private List<PersonEvent> personEvents = new LinkedList<PersonEvent>();
	private String roleActionName;

	public PersonsTable(JPanel parentView, List<Person> persons, String roleActionName) {
		this.parentView = parentView;
		this.persons = persons;
		this.roleActionName = roleActionName;
		createComponent();
	}
	
	public void addPersonEvent(PersonEvent event){
		personEvents.add(event);
	}
	
	private void createComponent(){
		double size[][] = { { TableLayout.FILL }, { 25 } };
		TableLayout layout = new TableLayout(size);
		setLayout(layout);

		JPanel header = new JPanel();
		double sizeHeader[][] = { { 100, TableLayout.FILL, 120, 100, 150 }, { 25 } };
		header.setLayout(new TableLayout(sizeHeader));
		header.add(new JLabel("Name"), "1,0");
		header.add(new JLabel("Geburtstag"), "2,0");
		header.add(new JLabel("Geschlecht"), "3,0");
		header.add(new JLabel("Merfähigkeit in Wörtern"), "4,0");

		add(header, "0,0");

		for (Person person : persons) {
			layout.insertRow(1, TableLayout.FILL);
			JPanel row = createRow(person);
			add(row, "0,1");
		}
	}
	
	private JPanel createRow(final Person person){
		JPanel row = new JPanel();
		double size[][] = { { 100, TableLayout.FILL, 120, 100, 150 }, { 25 } };
		row.setLayout(new TableLayout(size));

		JButton roleActionButton = new JButton(roleActionName);
		roleActionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(PersonEvent event : personEvents){
					event.personEvent(person);
				}
			}
		});
		
		row.add(roleActionButton, "0,0,l,c");
		row.add(new JLabel(person.getName()), "1,0");
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		
		row.add(new JLabel(formatter.format(person.getBirthday())), "2,0");
		if(person.getGender() == Gender.MALE){
			row.add(new JLabel("Männlich"), "3,0");
		}else if(person.getGender() == Gender.FEMALE){
			row.add(new JLabel("Weiblich"), "3,0");
		}
		
		row.add(new JLabel(person.getWordsRetention() + ""), "4,0");
		
		return row;
	}

	public void update(List<Person> persons) {
		this.persons = persons;
		removeAll();
		createComponent();
		revalidate();
		repaint();
	}
	
}
