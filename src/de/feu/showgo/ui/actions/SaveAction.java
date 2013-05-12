package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.dialogs.ShowGoSaveFileChooser;

/**
 * This action saves the showgo singleton to the current save file. If no save
 * file is set, a file save dialog is shown.
 */
public class SaveAction implements ActionListener {

	private final static Logger log = Logger.getLogger(SaveAction.class);
	private MainWindow mainWindow;

	/**
	 * Instantiates a new save action.
	 * 
	 * @param mainWindow
	 *            the main window
	 */
	public SaveAction(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// If a save file exists, overwrite it
		File target = ShowGoDAO.getSaveFile();

		if (ShowGoDAO.getSaveFile() == null) {
			// Ask user to select a file
			ShowGoSaveFileChooser fileChooser = new ShowGoSaveFileChooser(mainWindow);
			fileChooser.showDialog();
			if (fileChooser.isApproved()) {
				target = fileChooser.getSelectedFile();
			} else {
				log.debug("Aborted by user");
				return;
			}
		}

		saveShowGo(target);
	}

	private void saveShowGo(File file) {
		try {
			ShowGoDAO.saveToDisc(file);
		} catch (JAXBException e) {
			log.error("", e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainWindow, "Es ist ein Fehler beim Speichern aufgetreten: " + e.getMessage(), "Fehler beim Speichern",
					JOptionPane.ERROR_MESSAGE);
		}
		ShowGoDAO.setSaveFile(file);
		mainWindow.setTitleFilename(file.getName());
	}
}
