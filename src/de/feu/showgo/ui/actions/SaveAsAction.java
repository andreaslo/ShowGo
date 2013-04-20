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

public class SaveAsAction implements ActionListener {
	private final static Logger log = Logger.getLogger(SaveShowGoAction.class);
	private MainWindow mainWindow;

	public SaveAsAction(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		final JFileChooser fc = new JFileChooser() {
			@Override
			public void approveSelection() {
				File f = getSelectedFile();
				if(!f.getName().endsWith(".showgo")){
					f = new File(f.getParentFile(), f.getName()+".showgo");
				}
				if (f.exists() && getDialogType() == SAVE_DIALOG) {
					int result = JOptionPane
							.showConfirmDialog(this, "Die Datei \"" + f.getName() + "\" existiert bereits. Überschreiben?", "Datei existiert bereits", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (result) {
					case JOptionPane.YES_OPTION:
						super.approveSelection();
						return;
					case JOptionPane.NO_OPTION:
						return;
					case JOptionPane.CLOSED_OPTION:
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					}
				}
				super.approveSelection();
			}
		};

		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setSelectedFile(new File("Mein Theater"));

		FileFilter filter = new FileNameExtensionFilter("ShowGo-Datei", "showgo");
		fc.addChoosableFileFilter(filter);

		int returnVal = fc.showSaveDialog(mainWindow);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			try {
				String path = file.getCanonicalPath();
				if (!path.endsWith(".showgo")) {
					file = new File(path + ".showgo");
				}

				log.debug("Saving: " + file.getCanonicalPath());

				ShowGoDAO.saveToDisc(file);
				ShowGoDAO.setSaveFile(file);
			} catch (IOException e) {
				log.error("",e);
				JOptionPane.showMessageDialog(mainWindow, "Die Datei konnte leider nicht geschrieben werden.", "Fehler beim Speichern", JOptionPane.ERROR_MESSAGE);
			} catch (JAXBException e) {
				log.error("",e);
				JOptionPane.showMessageDialog(mainWindow, "Es ist ein Fehler beim Speichern aufgetreten: " + e.getMessage(), "Fehler beim Speichern", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
