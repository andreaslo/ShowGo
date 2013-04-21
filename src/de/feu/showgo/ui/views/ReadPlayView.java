package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import de.feu.showgo.io.ParsingException;
import de.feu.showgo.io.PlayParser;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;

public class ReadPlayView extends JPanel {
	
	private TheaterPlay model;
	private MainWindow mainWindow;
	private static final Logger log = Logger.getLogger(ReadPlayView.class);

	public ReadPlayView(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createComponent();
		setName("Bühnenstück einlesen");
	}

	private void createComponent() {
		double size[][] = { { 20, TableLayout.FILL, 20 }, { 20, TableLayout.PREFERRED, TableLayout.PREFERRED } };
		setLayout(new TableLayout(size));

		JPanel fileSelectPanel = createFileSelectPanel();

		add(fileSelectPanel, "1,1");
	}

	private JPanel createFileSelectPanel() {
		JPanel fileSelectPanel = new JPanel();
		double size[][] = { { TableLayout.FILL, 20, TableLayout.PREFERRED }, { 30, 30 } };
		fileSelectPanel.setLayout(new TableLayout(size));

		final JTextField fileInput = new JTextField();
		JButton selectFileButton = new JButton("Durchsuchen");

		selectFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogType(JFileChooser.OPEN_DIALOG);
				fc.setAcceptAllFileFilterUsed(true);

				FileFilter filter = new FileNameExtensionFilter("HTML-Datei", "html");
				fc.addChoosableFileFilter(filter);
				int result = fc.showOpenDialog(mainWindow);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					try {
						fileInput.setText(selectedFile.getCanonicalPath());
					} catch (IOException e) {
						log.error("", e);
						JOptionPane.showMessageDialog(mainWindow, "Es ist ein Problem beim Lesen der Datei aufgetreten", "Fehler",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JButton doReadPlayButton = new JButton("Stück einlesen");

		doReadPlayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File selectedFile = new File(fileInput.getText());

				PlayParser parser = new PlayParser();
				TheaterPlay parsedPlay = null;
				try {
					parsedPlay = parser.generatePlay(selectedFile);
				} catch (IOException e) {
					log.error("", e);
					JOptionPane.showMessageDialog(mainWindow, "Die Datei \"" + selectedFile.getName() + "\" konnte nicht gelesen werden.", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					return;
				} catch (ParsingException e) {
					log.error("", e);
					JOptionPane.showMessageDialog(mainWindow, "Die Datei \"" + selectedFile.getName()
							+ "\" konnte nicht geparst werden. Es ist folgender Fehler aufgetreten: \n" + e.getMessage(), "Fehler",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				model = parsedPlay;
				JOptionPane.showMessageDialog(mainWindow, "Das Stück " + parsedPlay.getName() + " wurde erfolgreich eingelesen.", parsedPlay.getName()
						+ " erfoglreich eingelesen", JOptionPane.INFORMATION_MESSAGE);
				
				showRoleSelectPanel();				
			}
		});

		fileSelectPanel.add(fileInput, "0,0,f,c");
		fileSelectPanel.add(selectFileButton, "2,0,r,c");
		fileSelectPanel.add(doReadPlayButton, "0,1,l,c");

		return fileSelectPanel;
	}

	
	private void showRoleSelectPanel(){
		JPanel rolePanel = createRoleSelectPanel();
		add(rolePanel, "1,2");
	}
	
	private JPanel createRoleSelectPanel(){
		JPanel rolePanel = new JPanel();
		
		
		
		return rolePanel;
	}
	
	
}
