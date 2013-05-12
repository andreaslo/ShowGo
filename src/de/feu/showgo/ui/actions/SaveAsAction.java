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
 * This action displays a save file dialog and writes the showgo singleton into
 * the selected file.
 * 
 */
public class SaveAsAction implements ActionListener {
	private final static Logger log = Logger.getLogger(SaveAsAction.class);
	private MainWindow mainWindow;

	/**
	 * Instantiates a new "save as" action.
	 *
	 * @param mainWindow the main window
	 */
	public SaveAsAction(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		ShowGoSaveFileChooser fileChooser = new ShowGoSaveFileChooser(mainWindow);
		fileChooser.showDialog();
		if (fileChooser.isApproved()) {
			File file = fileChooser.getSelectedFile();
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
}
