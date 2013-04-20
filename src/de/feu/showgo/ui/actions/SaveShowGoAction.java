package de.feu.showgo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.io.ShowGoIO;
import de.feu.showgo.ui.MainWindow;

public class SaveShowGoAction implements ActionListener {

	private MainWindow mainWindow;

	public SaveShowGoAction(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();
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

				System.out.println("Opening: " + file.getCanonicalPath());

				
				ShowGoIO.saveShowGo(ShowGoDAO.getShowGo(), file);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
		} else {
			System.out.println("Open command cancelled by user.");
		}

		
	}

}
