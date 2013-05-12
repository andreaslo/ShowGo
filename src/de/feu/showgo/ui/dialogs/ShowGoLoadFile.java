package de.feu.showgo.ui.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import de.feu.showgo.ui.MainWindow;

/**
 * A customized JFileChooser load dialog. The class displays a JFileDialog that asks
 * for confirmation on loading a file and only accepts .showgo files.
 */
public class ShowGoLoadFile {
	private final static Logger log = Logger.getLogger(ShowGoLoadFile.class);
	private MainWindow mainWindow;
	private JFileChooser fc;
	private File selectedFile;
	private boolean approved;

	/**
	 * Instantiates a new show go load file dialogs.
	 *
	 * @param mainWindow the main window
	 */
	public ShowGoLoadFile(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		fc = new JFileChooser(new File(System.getProperty("user.dir"))) {
			private static final long serialVersionUID = 1L;

			@Override
			public void approveSelection() {
				File f = getSelectedFile();
				if (!f.getName().endsWith(".showgo")) {
					f = new File(f.getParentFile(), f.getName() + ".showgo");
				}
				if (f.exists() && getDialogType() == OPEN_DIALOG) {
					int result = JOptionPane.showConfirmDialog(this, "Sind Sie sicher, dass Sie die Datei \"" + f.getName()
							+ "\" öffnen möchten? Ungesicherte Änderungen gehen verloren.", "Datei öffnen", JOptionPane.YES_NO_CANCEL_OPTION);
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

		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setAcceptAllFileFilterUsed(false);

		FileFilter filter = new FileNameExtensionFilter("ShowGo-Datei", "showgo");
		fc.addChoosableFileFilter(filter);
	}

	/**
	 * Opens the dialog.
	 */
	public void showDialog() {
		int returnVal = fc.showOpenDialog(mainWindow);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			try {
				String path = file.getCanonicalPath();
				if (!path.endsWith(".showgo")) {
					file = new File(path + ".showgo");
				}

				log.debug("Opening: " + file.getCanonicalPath());
				this.selectedFile = file;
				this.approved = true;
			} catch (IOException e) {
				log.error("", e);
				JOptionPane.showMessageDialog(mainWindow, "Die Datei konnte leider nicht geöffnet werden.", "Fehler beim Öffnen",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Checks if it is approved.
	 *
	 * @return true, if approved
	 */
	public boolean isApproved() {
		return approved;

	}

	/**
	 * Gets the selected file.
	 *
	 * @return the selected file
	 */
	public File getSelectedFile() {
		return selectedFile;

	}
}
