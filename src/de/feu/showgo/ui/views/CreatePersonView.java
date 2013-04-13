package de.feu.showgo.ui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import info.clearthought.layout.TableLayout;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.toedter.calendar.JCalendar;

import de.feu.showgo.Main;
import de.feu.showgo.model.Person;

public class CreatePersonView extends JPanel {

	private JTextField nameInput;
	private JCalendar birthdayInput;
	private JComboBox<String> genderSelect;
	
	private final static Logger log = Logger.getLogger(CreatePersonView.class);
	
	public CreatePersonView() {
		log.debug("showing createPerson View");
		createComponent();
		
		this.setName("Person anlegen");
	}
	
	private void createComponent(){
		
	    double size[][] = {{20,100,20,TableLayout.FILL,20},
	             		{20,30,10,TableLayout.PREFERRED,10,30,10,30}};
	    this.setLayout (new TableLayout(size));
		
		JLabel nameLabel = new JLabel("Name:");
		JLabel birthdayLabel = new JLabel("Geburtstag:");
		JLabel genderLabel = new JLabel("Geschlecht:");
		
		nameInput = new JTextField();
		birthdayInput = new JCalendar();
		
		String[] genders = { "Männlich", "Weiblich" };
		genderSelect = new JComboBox<String>(genders);
		
		JButton saveButton = new JButton("Speichern");
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Person newPerson = new Person();
				newPerson.setName(nameInput.getText());
				newPerson.setBirthday(birthdayInput.getDate());
				if(genderSelect.getSelectedIndex() == 0){
					newPerson.setGender(Person.Gender.MALE);
				}else if(genderSelect.getSelectedIndex() == 1){
					newPerson.setGender(Person.Gender.FEMALE);
				}else{
					log.error("Unknown gender selected");
				}
				log.info("Created new user: " + newPerson);
				
				Main.getShowGo().addPerson(newPerson);
			}
		});
		
		this.add(nameLabel, "1,1");
		this.add(nameInput, "3,1,f,c");
		this.add(birthdayLabel, "1,3");
		this.add(birthdayInput, "3,3,f,c");
		this.add(genderLabel, "1,5");
		this.add(genderSelect, "3,5,f,c");
		this.add(saveButton, "1,7,l,c");
	}
	
}
