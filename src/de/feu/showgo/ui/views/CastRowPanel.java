package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.Component;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Person;
import de.feu.showgo.model.Role;

public class CastRowPanel extends JPanel {

	private Role role;
	private List<Person> availablePersons;
	private static final Logger log = Logger.getLogger(CastSelectionPanel.class);
	
	public CastRowPanel(Role role, List<Person> availablePersons) {
		this.role = role;
		this.availablePersons = availablePersons;
		log.debug("creating cast row panel for " + role);
		createComponent();
	}
	
	private void createComponent(){
		double size[][] = { { 150, 300 }, { 30, 30, 30 } };
		TableLayout layout = new TableLayout(size);
		setLayout(layout);
		
		add(new JLabel(role.getName()+":"), "0,0,1,0");
		add(new JLabel("1. Besetzung:"),"0,1");
		
		JComboBox<Person> personSelect = new JComboBox<Person>();
		personSelect.setRenderer(new PersonComboRederer());
		for(Person person : availablePersons){
			personSelect.addItem(person);
		}
		
		add(personSelect, "1,1,f,c");
		add(new JButton("Weitere Besetzung hinzufügen"), "0,2,1,2,l,c");
	}
	
	private class PersonComboRederer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value != null) {
				Person person = (Person) value;
				setText(person.getName());
			}

			return this;
		}
	}

}
