package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.dialogs.ShowGoFileChooser;

public class SaveAsAction implements ActionListener {
	private final static Logger log = Logger.getLogger(SaveShowGoAction.class);
	private MainWindow mainWindow;

	public SaveAsAction(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		ShowGoFileChooser fileChooser = new ShowGoFileChooser(mainWindow);
		fileChooser.showDialog();
		if (fileChooser.isApproved()) {
			File file = fileChooser.getSelectedFile();
			try {
				ShowGoDAO.saveToDisc(file);
			} catch (JAXBException e) {
				log.error("",e);
				e.printStackTrace();
				JOptionPane.showMessageDialog(mainWindow, "Es ist ein Fehler beim Speichern aufgetreten: " + e.getMessage(), "Fehler beim Speichern",
						JOptionPane.ERROR_MESSAGE);
			}
			ShowGoDAO.setSaveFile(file);
		}

	}
}
