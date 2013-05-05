package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
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
			log.debug("creating cast selection panel");
			this.mainWindow = mainWindow;
			this.roles = roles;
			this.availablePersons = availablePersons;
			createComponent();
		}
		
		private void createComponent(){
			setLayout(new TableLayout(generateLayoutSize(roles.size())));
			int rowCounter = 0;
			for(Role role : roles){
				if(!role.isPseudoRole()){
					CastRowPanel row = new CastRowPanel(role, availablePersons);
					add(row,"0,"+rowCounter);
					rowCounter++;
				}
			}
			
		}

		
		private double[][] generateLayoutSize(int numRows){		
			double[][] size = new double[2][];
			double[] width = {TableLayout.FILL};
			size[0] = width;
			double[] height = new double[numRows];
			size[1] = height;
					
			for(int i = 0; i < height.length; i++){
				height[i] = TableLayout.PREFERRED;
			}
			
			return size;
		}
		
}
