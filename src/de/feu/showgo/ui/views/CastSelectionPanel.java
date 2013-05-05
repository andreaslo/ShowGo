package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Person;
import de.feu.showgo.model.Role;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.listener.RoleDeleteListener;

public class CastSelectionPanel extends JPanel implements RoleDeleteListener {

		private MainWindow mainWindow;
		private List<Role> roles;
		private List<Person> availablePersons;
		private List<CastRowPanel> castRows;
		private String borderTitle;
		private static final Logger log = Logger.getLogger(CastSelectionPanel.class);
	
		public CastSelectionPanel(MainWindow mainWindow, List<Role> roles, List<Person> availablePersons, String borderTitle){
			log.debug("creating cast selection panel");
			this.mainWindow = mainWindow;
			this.roles = roles;
			this.availablePersons = availablePersons;
			this.borderTitle = borderTitle;
			createComponent();
		}
		
		private void createComponent(){
			castRows = new ArrayList<CastRowPanel>();
			
			if(borderTitle != null){
				TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), borderTitle);
				setBorder(titledBorder);
			}
			
			setLayout(new TableLayout(generateLayoutSize(roles.size())));
			int rowCounter = 0;
			for(Role role : roles){
				if(!role.isPseudoRole()){
					CastRowPanel row = new CastRowPanel(role, availablePersons);
					add(row,"0,"+rowCounter);
					rowCounter++;
					castRows.add(row);
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

		@Override
		public void deleteRole(Role role) {
			log.debug("cast panel notified abour deleted role: " + role);
			CastRowPanel toBeRemoved = null;
			for(CastRowPanel rowPanel : castRows){
				if(rowPanel.getRole() == role){
					remove(rowPanel);
					toBeRemoved = rowPanel;
				}
			}
			
			castRows.remove(toBeRemoved);
		}
		
}
