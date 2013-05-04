package de.feu.showgo.ui.views;

import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Person;
import de.feu.showgo.model.Role;
import de.feu.showgo.ui.MainWindow;

public class CastSelectionPanel extends JPanel {

		private MainWindow mainWindow;
		private List<Role> roles;
		private List<Person> availablePersons;
		private static final Logger log = Logger.getLogger(CastSelectionPanel.class);
	
		public CastSelectionPanel(MainWindow mainWindow, List<Role> roles, List<Person> availablePersons){
			this.mainWindow = mainWindow;
			this.roles = roles;
			this.availablePersons = availablePersons;
			createComponent();
		}
		
		private void createComponent(){		
			
		}

}
