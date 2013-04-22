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
import de.feu.showgo.ui.WindowColors;

public class PersonManagementView extends JPanel implements ActionListener {

	private JTextField nameInput;
	private JTextField wordRetentionInput;
	private JCalendar birthdayInput;
	private JComboBox<String> genderSelect;
	private MainWindow mainWindow;
	private JButton saveButton;
	private JButton createAnotherButton;
	private JLabel currentMessage;
	private Person model;
	
	private final static Logger log = Logger.getLogger(PersonManagementView.class);
	
	public PersonManagementView(MainWindow mainWindow) {
		log.debug("showing create person view");
		this.mainWindow = mainWindow;
		this.setName("Person anlegen");
		this.model = null;
		createComponent();
	}
	
	public PersonManagementView(MainWindow mainWindow, Person person) {
		log.debug("showing edit person view");
		this.mainWindow = mainWindow;
		this.setName(person.getName() + " bearbeiten");
		this.model = person;
		createComponent();
		
		nameInput.setText(person.getName());
		birthdayInput.setDate(person.getBirthday());
		if(person.getGender() == Person.Gender.MALE){
			log.debug("male");
			genderSelect.setSelectedIndex(0);
		}else if(person.getGender() == Person.Gender.FEMALE){
			log.debug("female");
			genderSelect.setSelectedIndex(1);
		}else{
			log.error("unknown gender");
		}
		wordRetentionInput.setText(person.getWordsRetention()+"");
		
	}

	private void createComponent(){		
	    double size[][] = {{20,150,20,TableLayout.FILL,20},
	             		{20,30,10,TableLayout.PREFERRED,10,30,10,30,10,30,10,30}};
	    this.setLayout (new TableLayout(size));
		
		JLabel nameLabel = new JLabel("Name:");
		JLabel birthdayLabel = new JLabel("Geburtstag:");
		JLabel genderLabel = new JLabel("Geschlecht:");
		JLabel wordRetentionLabel = new JLabel("Wörter Merkfähigkeit:");
		
		nameInput = new JTextField();
		birthdayInput = new JCalendar();
		wordRetentionInput = new JTextField();
		
		String[] genders = { "Männlich", "Weiblich" };
		genderSelect = new JComboBox<String>(genders);
		
		createAnotherButton = new JButton("Weiteren Benutzer anlegen");
		createAnotherButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.showCreatePersonView();
			}
		});
		
		saveButton = new JButton("Speichern");
		
		nameInput.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				nameInput.setBackground(Color.WHITE);
				removeMessage();
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				nameInput.setBackground(Color.WHITE);
				removeMessage();
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				nameInput.setBackground(Color.WHITE);
				removeMessage();
			}
		});
		wordRetentionInput.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				wordRetentionInput.setBackground(Color.WHITE);
				removeMessage();
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				wordRetentionInput.setBackground(Color.WHITE);
				removeMessage();
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				wordRetentionInput.setBackground(Color.WHITE);
				removeMessage();
			}
		});
		
		saveButton.addActionListener(this);
		
		this.add(nameLabel, "1,1");
		this.add(nameInput, "3,1,f,c");
		this.add(birthdayLabel, "1,3");
		this.add(birthdayInput, "3,3,f,c");
		this.add(genderLabel, "1,5");
		this.add(genderSelect, "3,5,f,c");
		this.add(wordRetentionLabel, "1,7");
		this.add(wordRetentionInput, "3,7,f,c");
		this.add(saveButton, "1,9,l,c");

	}

	private boolean valid(){
		if("".equals(nameInput.getText())){
			log.debug("no username");
			nameInput.setBackground(WindowColors.ERROR);
			showMessage("Bitte geben Sie einen Namen an.", WindowColors.ERROR);
			return false;
		}
		if("".equals(wordRetentionInput.getText())){
			log.debug("no word retention");
			wordRetentionInput.setBackground(WindowColors.ERROR);
			showMessage("Bitte geben Sie an, wie viele Wörter sich die Person merken kann.", WindowColors.ERROR);
			return false;
		}

		try{
			int words = Integer.parseInt(wordRetentionInput.getText());
		}catch(NumberFormatException notUsed){
			log.debug("word input not a number");
			wordRetentionInput.setBackground(WindowColors.ERROR);
			showMessage("Bitte geben Sie als Zahl an, wie viele Wörter sich die Person merken kann.", WindowColors.ERROR);
			return false;
		}
		
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!valid()){
			return;
		}
		
		if(model == null){
			log.debug("creating new user");
			model = new Person();
			ShowGoDAO.getShowGo().addPerson(model);
			fillPerson(model);
			disableInput();
			this.add(createAnotherButton, "3,9,l,c");
			showMessage("Der Benutzer " + model.getName() + " wurde erfolgreich angelegt.", WindowColors.SUCCESS);
		}else{
			log.debug("modifying user");
			fillPerson(model);
			showMessage("Der Benutzer " + model.getName() + " wurde erfolgreich aktualisiert.", WindowColors.SUCCESS);
		}
		
		mainWindow.getNavTree().refreshPersons();
	}
	
	private void fillPerson(Person newPerson){
		newPerson.setName(nameInput.getText());
		newPerson.setBirthday(birthdayInput.getDate());
		newPerson.setWordsRetention(Integer.valueOf(wordRetentionInput.getText()));
		if(genderSelect.getSelectedIndex() == 0){
			newPerson.setGender(Person.Gender.MALE);
		}else if(genderSelect.getSelectedIndex() == 1){
			newPerson.setGender(Person.Gender.FEMALE);
		}else{
			log.error("Unknown gender selected");
		}
	}
	
	private void showMessage(String message, Color background){
		removeMessage();
		
		log.debug("showing message " + message);
		currentMessage = new JLabel(message);
		currentMessage.setBorder(BorderFactory.createEtchedBorder());
		currentMessage.setHorizontalAlignment( SwingConstants.CENTER );
		currentMessage.setBackground(background);
		currentMessage.setOpaque(true);
		this.add(currentMessage, "1,11,3,11");
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
	
	private void disableInput(){
		nameInput.setEnabled(false);
		birthdayInput.setEnabled(false);
		genderSelect.setEnabled(false);
		saveButton.setEnabled(false);
		wordRetentionInput.setEnabled(false);
	}

	public Person getModel() {
		return model;
	}

	public void setModel(Person model) {
		this.model = model;
	}
	
	
	
}
