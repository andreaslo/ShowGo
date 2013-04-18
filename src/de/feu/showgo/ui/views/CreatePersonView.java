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

public class CreatePersonView extends JPanel implements ActionListener {

	private JTextField nameInput;
	private JTextField wordRetentionInput;
	private JCalendar birthdayInput;
	private JComboBox<String> genderSelect;
	private MainWindow mainWindow;
	private JButton saveButton;
	private JButton createAnotherButton;
	private JLabel currentMessage;
	
	private final static Logger log = Logger.getLogger(CreatePersonView.class);
	
	public CreatePersonView(MainWindow mainWindow) {
		log.debug("showing createPerson View");
		this.mainWindow = mainWindow;
		createComponent();
	}
	
	private void createComponent(){
		this.setName("Person anlegen");
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if("".equals(nameInput.getText())){
			log.debug("no username");
			nameInput.setBackground(WindowColors.ERROR);
			showMessage("Bitte geben Sie einen Namen an.", WindowColors.ERROR);
			return;
		}
		if("".equals(wordRetentionInput.getText())){
			log.debug("no word retention");
			wordRetentionInput.setBackground(WindowColors.ERROR);
			showMessage("Bitte geben Sie an, wie viele Wörter sich die Person merken kann.", WindowColors.ERROR);
			return;
		}

		int words = 0;
		try{
			words = Integer.parseInt(wordRetentionInput.getText());
		}catch(NumberFormatException notUsed){
			log.debug("word input not a number");
			wordRetentionInput.setBackground(WindowColors.ERROR);
			showMessage("Bitte geben Sie als Zahl an, wie viele Wörter sich die Person merken kann.", WindowColors.ERROR);
			return;
		}
		
		
		Person newPerson = new Person();
		newPerson.setName(nameInput.getText());
		newPerson.setBirthday(birthdayInput.getDate());
		newPerson.setWordsRetention(words);
		if(genderSelect.getSelectedIndex() == 0){
			newPerson.setGender(Person.Gender.MALE);
		}else if(genderSelect.getSelectedIndex() == 1){
			newPerson.setGender(Person.Gender.FEMALE);
		}else{
			log.error("Unknown gender selected");
		}
		log.info("Created new user: " + newPerson);
		
		disableInput();

		ShowGoDAO.getShowGo().addPerson(newPerson);
		mainWindow.getNavTree().refreshPersons();
		
		this.add(createAnotherButton, "3,9,l,c");
		showMessage("Der Benutzer " + newPerson.getName() + " wurde erfolgreich angelegt.", WindowColors.SUCCESS);

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
		this.validate();
		this.repaint();
	}
	
	private void removeMessage(){
		if(currentMessage != null){
			this.remove(currentMessage);
			this.validate();
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
	
}
