package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.toedter.calendar.JCalendar;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;

public class CreatePersonView extends JPanel {

	private JTextField nameInput;
	private JCalendar birthdayInput;
	private JComboBox<String> genderSelect;
	private MainWindow mainWindow;
	
	private final static Logger log = Logger.getLogger(CreatePersonView.class);
	
	public CreatePersonView(MainWindow mainWindow) {
		log.debug("showing createPerson View");
		this.mainWindow = mainWindow;
		createComponent();
	}
	
	private void createComponent(){
		this.setName("Person anlegen");
		
	    double size[][] = {{20,100,20,TableLayout.FILL,20},
	             		{20,30,10,TableLayout.PREFERRED,10,30,10,30,10,30}};
	    this.setLayout (new TableLayout(size));
		
		JLabel nameLabel = new JLabel("Name:");
		JLabel birthdayLabel = new JLabel("Geburtstag:");
		JLabel genderLabel = new JLabel("Geschlecht:");
		
		nameInput = new JTextField();
		birthdayInput = new JCalendar();
		
		String[] genders = { "Männlich", "Weiblich" };
		genderSelect = new JComboBox<String>(genders);
		
		final JButton createAnotherButton = new JButton("Weiteren Benutzer anlegen");
		createAnotherButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.showCreatePersonView();
			}
		});
		
		final JButton saveButton = new JButton("Speichern");
		
		nameInput.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				nameInput.setBackground(Color.WHITE);
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				nameInput.setBackground(Color.WHITE);				
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				nameInput.setBackground(Color.WHITE);
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if("".equals(nameInput.getText())){
					nameInput.setBackground(new Color(0xff,0x45,0x45));
					return;
				}
				
				
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
				
				nameInput.setEnabled(false);
				birthdayInput.setEnabled(false);
				genderSelect.setEnabled(false);
				saveButton.setEnabled(false);
				
				ShowGoDAO.getShowGo().addPerson(newPerson);
				mainWindow.getNavTree().refreshPersons();
				
				add(createAnotherButton, "3,7,l,c");
				
				JLabel successLabel = new JLabel("Der Benutzer " + newPerson.getName() + " wurde erfolgreich angelegt.");
				successLabel.setBorder(BorderFactory.createEtchedBorder());
				successLabel.setHorizontalAlignment( SwingConstants.CENTER );
				successLabel.setBackground(Color.GREEN);
				successLabel.setOpaque(true);
				add(successLabel, "1,9,3,9");
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
