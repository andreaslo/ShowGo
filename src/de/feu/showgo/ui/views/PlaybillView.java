package de.feu.showgo.ui.views;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Production;
import de.feu.showgo.model.Role;
import de.feu.showgo.ui.MainWindow;

/**
 * This view shows a textarea with the playbill of a production. The user can
 * select a file destination and save it to a .txt file.
 */
public class PlaybillView extends JPanel {

	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(EnsembleView.class);
	private MainWindow mainWindow;
	private Production production;
	private JTextArea playbillArea;

	/**
	 * Instantiates a new playbill view.
	 *
	 * @param mainWindow the main window
	 * @param production the production
	 */
	public PlaybillView(MainWindow mainWindow, Production production) {
		log.debug("showing playbill view for " + production);
		this.mainWindow = mainWindow;
		this.production = production;
		setName("Programmheft für " + production.getName());
		createComponent();
	}

	private void createComponent() {
		double size[][] = { { 20, TableLayout.FILL, 20 }, { 20, TableLayout.PREFERRED, 10, TableLayout.PREFERRED } };
		setLayout(new TableLayout(size));

		JButton saveButton = new JButton("In Datei speichern");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir"))) {
					private static final long serialVersionUID = 1L;

					@Override
					public void approveSelection() {
						File f = getSelectedFile();
						if (!f.getName().endsWith(".txt")) {
							f = new File(f.getParentFile(), f.getName() + ".txt");
						}
						if (f.exists() && getDialogType() == SAVE_DIALOG) {
							int result = JOptionPane.showConfirmDialog(this, "Die Datei \"" + f.getName() + "\" existiert bereits. Überschreiben?",
									"Datei existiert bereits", JOptionPane.YES_NO_CANCEL_OPTION);
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
				FileFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
				fc.addChoosableFileFilter(filter);

				int returnVal = fc.showSaveDialog(mainWindow);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();

					try {
						String path = file.getCanonicalPath();
						if (!path.endsWith(".txt")) {
							file = new File(path + ".txt");
						}

						log.debug("Saving: " + file.getCanonicalPath());

						PrintWriter out = new PrintWriter(file);
						out.println(playbillArea.getText());
						out.flush();
						out.close();
					} catch (IOException e1) {
						log.error("", e1);
						JOptionPane.showMessageDialog(mainWindow, "Die Datei konnte leider nicht geschrieben werden.", "Fehler beim Speichern",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		playbillArea = new JTextArea();
		playbillArea.setBorder(BorderFactory.createEtchedBorder());
		playbillArea.setText(generatePlaybillText(production));

		add(saveButton, "1,1,l,c");
		add(playbillArea, "1,3");

		revalidate();
		repaint();
	}

	private String generatePlaybillText(Production prod) {
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

		for (Role role : prod.getPlay().getRoles()) {
			if (role.isPseudoRole() || role.getCast() == null || role.getCast().isEmpty()) {
				continue;
			}
			playbill.append(printCast(role));
			playbill.append("\n");
			playbill.append("\n");
		}
		playbill.append("Besetzung der Nicht-Darstellerrollen:");
		playbill.append("\n");
		playbill.append("\n");
		for (Role role : prod.getNonActorRoles()) {
			if (role.isPseudoRole() || role.getCast() == null || role.getCast().isEmpty()) {
				continue;
			}
			playbill.append(printCast(role));
			playbill.append("\n");
			playbill.append("\n");
		}

		return playbill.toString();
	}

	private String printCast(Role role) {
		StringBuilder roleOutput = new StringBuilder();
		roleOutput.append(role.getName());
		roleOutput.append(": ");
		if (role.getCast().size() == 1) {
			roleOutput.append(role.getCast().get(0).getName());
		} else if (role.getCast().size() == 2) {
			roleOutput.append(role.getCast().get(0).getName());
			roleOutput.append(" und ");
			roleOutput.append(role.getCast().get(1).getName());
			roleOutput.append(" als zweite Besetzung");
		} else {
			roleOutput.append(role.getCast().get(0).getName());
			roleOutput.append("\nWeitere Besetzugen: ");
			for (int i = 1; i < role.getCast().size(); i++) {
				roleOutput.append(role.getCast().get(i).getName());
				if (i == role.getCast().size() - 2) {
					roleOutput.append(" und ");
				} else {
					roleOutput.append(", ");
				}
			}
		}
		return roleOutput.toString();
	}

}
