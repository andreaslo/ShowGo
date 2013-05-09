package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Production;
import de.feu.showgo.model.Role;
import de.feu.showgo.ui.MainWindow;

public class PlaybillView extends JPanel {

	private final static Logger log = Logger.getLogger(EnsembleView.class);
	private MainWindow mainWindow;
	private Production production;
	
	public PlaybillView(MainWindow mainWindow, Production production) {
		log.debug("showing playbill view for " + production);
		this.mainWindow = mainWindow;
		this.production = production;
		setName("Programmheft für " + production.getName());
		createComponent();
	}
	
	private void createComponent(){
		double size[][] = { { 20, TableLayout.FILL, 20 },
				{ 20, TableLayout.PREFERRED, 10, TableLayout.PREFERRED } };
		setLayout(new TableLayout(size));
		
		JButton saveButton = new JButton("In Datei speichern");
		
		
		JTextArea playbillArea = new JTextArea();
		playbillArea.setBorder(BorderFactory.createEtchedBorder());
		playbillArea.setText(generatePlaybillText(production));
		
		add(saveButton,"1,1,l,c");
		add(playbillArea, "1,3");
		
		revalidate();
		repaint();
	}
	
	private String generatePlaybillText(Production prod){
		StringBuilder playbill = new StringBuilder();
		
		playbill.append(prod.getPlay().getName());
		playbill.append("\n");
		playbill.append("Ein Stück in ");
		playbill.append(prod.getPlay().getActs().size());
		playbill.append(" Akten");
		playbill.append("\n");
		playbill.append("\n");
		playbill.append("Besetzung der Darstellerrollen:");
		playbill.append("\n");
		playbill.append("\n");
		
		for(Role role : prod.getPlay().getRoles()){
			if(role.isPseudoRole()){
				continue;
			}
			playbill.append(printCast(role));
			playbill.append("\n");
			playbill.append("\n");
		}
		playbill.append("Besetzung der Nicht-Darstellerrollen:");
		playbill.append("\n");
		playbill.append("\n");
		for(Role role : prod.getNonActorRoles()){
			if(role.isPseudoRole()){
				continue;
			}
			playbill.append(printCast(role));
			playbill.append("\n");
			playbill.append("\n");
		}
		
		return playbill.toString();
	}
	
	private String printCast(Role role){
		StringBuilder roleOutput = new StringBuilder();
		roleOutput .append(role.getName());
		roleOutput.append(": ");
		if(role.getCast().size() == 1){
			roleOutput.append(role.getCast().get(0).getName());
		}else if(role.getCast().size() == 2){
			roleOutput.append(role.getCast().get(0).getName());
			roleOutput.append(" und ");
			roleOutput.append(role.getCast().get(1).getName());
			roleOutput.append(" als zweite Besetzung");
		}else{
			roleOutput.append(role.getCast().get(0).getName());
			roleOutput.append("\nWeitere Besetzugen: ");
			for(int i = 1; i < role.getCast().size(); i++){
				roleOutput.append(role.getCast().get(i).getName());
				if(i == role.getCast().size() - 2){
					roleOutput.append(" und ");
				}else{
					roleOutput.append(", ");
				}
			}
		}
		return roleOutput.toString();
	}
	
}
