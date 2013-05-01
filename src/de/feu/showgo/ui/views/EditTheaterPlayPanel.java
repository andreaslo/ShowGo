package de.feu.showgo.ui.views;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Act;
import de.feu.showgo.model.Paragraph;
import de.feu.showgo.model.Scene;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;

public class EditTheaterPlayPanel extends JPanel{

	private MainWindow mainWindow;
	private TheaterPlay play;
	private static final Logger log = Logger.getLogger(EditTheaterPlayPanel.class);

	public EditTheaterPlayPanel(MainWindow mainWindow, TheaterPlay play){
		this.mainWindow = mainWindow;
		this.play = play;
		createComponent();
	}
	
	
	
	private void createComponent(){
		for(Act act : play.getActs()){
			for(Scene scene : act.getScenes()){
				for(Paragraph paragraph : scene.getParagraphs()){
					
				}
			}
		}
	}

}
