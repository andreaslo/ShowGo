package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.feu.showgo.model.Person;

public class PersonsTable extends JPanel {

	private JPanel parentView;
	private List<Person> persons;

	public PersonsTable(JPanel parentView, List<Person> persons) {
		this.parentView = parentView;
		this.persons = persons;
		createComponent();
	}
	
	private void createComponent(){
		double size[][] = { { TableLayout.FILL }, { 25 } };
		TableLayout layout = new TableLayout(size);
		setLayout(layout);

		JPanel header = new JPanel();
		double sizeHeader[][] = { {  TableLayout.FILL, 120, 100, 150 }, { 25 } };
		header.setLayout(new TableLayout(sizeHeader));
		header.add(new JLabel("Name"), "0,0");
		header.add(new JLabel("Geburtstag"), "1,0");
		header.add(new JLabel("Geschlecht"), "2,0");
		header.add(new JLabel("Merfähigkeit in wörtern"), "3,0");

		add(header, "0,0");

		for (Person person : persons) {
			layout.insertRow(1, TableLayout.FILL);
			JPanel row = createRow(person);
			add(row, "0,1");
		}
	}
	
	private JPanel createRow(final Person person){
		JPanel row = new JPanel();
		double size[][] = { { TableLayout.FILL, 120, 100, 150 }, { 25 } };
		row.setLayout(new TableLayout(size));
		
		
		row.add(new JLabel(person.getName()), "0,0");
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		
		row.add(new JLabel(formatter.format(person.getBirthday())), "1,0");
		row.add(new JLabel(person.getGender().toString()), "2,0");
		row.add(new JLabel(person.getWordsRetention() + ""), "3,0");
		
		return row;
	}
	
}
