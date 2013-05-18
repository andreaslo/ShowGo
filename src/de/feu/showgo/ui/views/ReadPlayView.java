package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.io.ParsingException;
import de.feu.showgo.io.PlayParser;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;
import de.feu.showgo.ui.WindowColors;

/**
 * This view allows the user to read a theater play from a file. After selecting
 * and reading the file he may edit the role requirements.
 */
public class ReadPlayView extends JPanel {

	private static final long serialVersionUID = 1L;
	private TheaterPlay model;
	private MainWindow mainWindow;
	private static final Logger log = Logger.getLogger(ReadPlayView.class);
	private JLabel currentMessage;
	private RolePanel roleSelect;

	/**
	 * Instantiates a new read play view.
	 *
	 * @param mainWindow the main window
	 */
	public ReadPlayView(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createComponent();
		setName("Bühnenstück einlesen");
	}

	private void createComponent() {
		double size[][] = { { 20, TableLayout.FILL, 20 }, { 20, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, 30 } };
		setLayout(new TableLayout(size));

		JPanel fileSelectPanel = createFileSelectPanel();

		add(fileSelectPanel, "1,1");
	}

	private JPanel createFileSelectPanel() {
		JPanel fileSelectPanel = new JPanel();
		double size[][] = { { TableLayout.FILL, 20, TableLayout.PREFERRED }, { 30, 30 } };
		fileSelectPanel.setLayout(new TableLayout(size));

		final JTextField fileInput = new JTextField("");
		final JButton selectFileButton = new JButton("Durchsuchen");

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

		final JButton doReadPlayButton = new JButton("Stück einlesen");

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
				JOptionPane.showMessageDialog(mainWindow, "Das Stück " + parsedPlay.getName() + " wurde erfolgreich eingelesen.",
						parsedPlay.getName() + " erfoglreich eingelesen", JOptionPane.INFORMATION_MESSAGE);

				roleSelect = new RolePanel(mainWindow, model);
				roleSelect.setChangePseudoEnabled(true);
				add(roleSelect, "1,2");

				JPanel submitPanel = createSubmitPanel();
				add(submitPanel, "1,3");

				doReadPlayButton.setEnabled(false);
				fileInput.setEnabled(false);
				selectFileButton.setEnabled(false);
				
				revalidate();
				repaint();
			}
		});

		fileSelectPanel.add(fileInput, "0,0,f,c");
		fileSelectPanel.add(selectFileButton, "2,0,r,c");
		fileSelectPanel.add(doReadPlayButton, "0,1,l,c");

		return fileSelectPanel;
	}

	private JPanel createSubmitPanel() {
		JPanel submitPanel = new JPanel();
		submitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton saveButton = new JButton("Speichern");
		submitPanel.add(saveButton);

		final JPanel viewPanel = this;
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				log.debug("validating");
				List<RolePanelRow> rolePanels = roleSelect.getRolePanelRows();
				for (RolePanelRow panel : rolePanels) {
					if (!panel.isValid()) {
						showMessage(panel.getValidationErrorMessage(), WindowColors.ERROR);
						return;
					}
				}

				log.debug("saving roles to backing model");
				for (RolePanelRow panel : rolePanels) {
					panel.saveRole();
				}

				ShowGoDAO.getShowGo().addPlay(model);

				log.debug("disabeling all components");
				enableComponents(viewPanel, false);

				mainWindow.getNavTree().refreshPlays();
				ShowGoDAO.autosave();

				showMessage("Das Stück " + model.getName() + " wurde erfolgreich angelegt.", WindowColors.SUCCESS);
			}
		});

		return submitPanel;
	}

	/**
	 * This method recursively sets the enabled state for a container.
	 * 
	 * @param container
	 * @param enable
	 */
	private void enableComponents(Container container, boolean enable) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			component.setEnabled(enable);
			if (component instanceof Container) {
				enableComponents((Container) component, enable);
			}
		}
	}

	private void showMessage(String message, Color background) {
		removeMessage();

		log.debug("showing message " + message);
		currentMessage = new JLabel(message);
		currentMessage.setBorder(BorderFactory.createEtchedBorder());
		currentMessage.setHorizontalAlignment(SwingConstants.CENTER);
		currentMessage.setBackground(background);
		currentMessage.setOpaque(true);
		this.add(currentMessage, "1,4");
		this.revalidate();
		this.repaint();
	}

	private void removeMessage() {
		if (currentMessage != null) {
			this.remove(currentMessage);
			this.revalidate();
			this.repaint();
		}
	}

}
