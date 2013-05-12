package de.feu.showgo.ui;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.io.LastSaveProperties;
import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Person;
import de.feu.showgo.model.Production;
import de.feu.showgo.ui.actions.LoadAction;
import de.feu.showgo.ui.actions.NewAction;
import de.feu.showgo.ui.actions.SaveAction;
import de.feu.showgo.ui.actions.SaveAsAction;
import de.feu.showgo.ui.actions.ShowCreatePersonAction;
import de.feu.showgo.ui.actions.ShowEnsembleViewAction;
import de.feu.showgo.ui.actions.ShowProductionViewAction;
import de.feu.showgo.ui.actions.ShowReadPlayAction;
import de.feu.showgo.ui.tree.NavTree;
import de.feu.showgo.ui.views.EnsembleView;
import de.feu.showgo.ui.views.PersonManagementView;
import de.feu.showgo.ui.views.PlaybillView;
import de.feu.showgo.ui.views.ProductionView;
import de.feu.showgo.ui.views.ReadPlayView;
import de.feu.showgo.ui.views.StartupView;

/**
 * This class represents the main window of the application. It shows a
 * navigation tree on the left side and a panel on the right side as well as a
 * menu bar. The nav tree is represented by the NavTree class. The panel on the
 * right side is called view in the application and may be set using the methods
 * provided by this class.
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private final static String TITLE = "ShowGo - Andreas Lösche / 8614989";

	private JPanel currentView;
	private JScrollPane scrolledView;
	private NavTree navTree;
	private static final Logger log = Logger.getLogger(MainWindow.class);
	private JFrame mainWindow = this;

	/**
	 * Instantiates a new main window.
	 */
	public MainWindow() {

	}

	/**
	 * Creates the components and sets the frame visible.
	 */
	public void init() {

		navTree = new NavTree(this);

		this.setSize(1150, 800);
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle(TITLE);
		this.setJMenuBar(createMenu());

		double size[][] = { { 10, 350, 10, TableLayout.FILL, 10 }, { 10, TableLayout.FILL, 10 } };
		this.setLayout(new TableLayout(size));

		this.add(navTree.getTree(), "1,1");

		displayView(new StartupView());

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				handleClose();
			}
		});

		this.setVisible(true);
	}

	private JMenuBar createMenu() {
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem newTheater = new JMenuItem("Neues Theater");
		newTheater.addActionListener(new NewAction(this));
		JMenuItem load = new JMenuItem("Laden");
		load.addActionListener(new LoadAction(this));
		JMenuItem save = new JMenuItem("Speichern");
		save.addActionListener(new SaveAction(this));
		JMenuItem saveAs = new JMenuItem("Speichern unter");
		saveAs.addActionListener(new SaveAsAction(this));
		JMenuItem close = new JMenuItem("Schließen");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
			}
		});
		fileMenu.add(newTheater);
		fileMenu.add(load);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.addSeparator();
		fileMenu.add(close);

		JMenu actionMenu = new JMenu("Aktionen");
		JMenuItem addPerson = new JMenuItem("Person hinzufügen");
		addPerson.addActionListener(new ShowCreatePersonAction(this));
		JMenuItem readPlay = new JMenuItem("Stück einlesen");
		readPlay.addActionListener(new ShowReadPlayAction(this));
		JMenuItem createEnsemble = new JMenuItem("Ensemble anlegen");
		createEnsemble.addActionListener(new ShowEnsembleViewAction(this));
		JMenuItem createProduction = new JMenuItem("Inszenierung anlegen");
		createProduction.addActionListener(new ShowProductionViewAction(this));

		actionMenu.add(addPerson);
		actionMenu.add(readPlay);
		actionMenu.add(createEnsemble);
		actionMenu.add(createProduction);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(actionMenu);

		return menuBar;
	}

	/**
	 * Displays a JPanel as current view on the right side of the window.
	 *
	 * @param newView the new view
	 */
	public void displayView(JPanel newView) {
		if (scrolledView != null) {
			this.remove(scrolledView);
		}
		currentView = newView;

		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), currentView.getName());
		titledBorder.setTitlePosition(TitledBorder.ABOVE_TOP);

		currentView.setBorder(titledBorder);

		scrolledView = new JScrollPane(currentView);
		scrolledView.setBorder(null);

		this.add(scrolledView, "3,1");
		this.revalidate();
		this.repaint();
	}

	/**
	 * Shows the create person view.
	 */
	public void showCreatePersonView() {
		displayView(new PersonManagementView(this));
	}

	/**
	 * Shows the edit person.
	 *
	 * @param person the person
	 */
	public void showEditPersonView(Person person) {
		displayView(new PersonManagementView(this, person));
	}

	/**
	 * Shows the read play view.
	 */
	public void showReadPlayView() {
		System.out.println("read play");
		displayView(new ReadPlayView(this));
	}

	/**
	 * Gets the nav tree.
	 *
	 * @return the nav tree
	 */
	public NavTree getNavTree() {
		return navTree;
	}

	/**
	 * Gets the current view.
	 *
	 * @return the current view
	 */
	public JPanel getCurrentView() {
		return currentView;
	}

	/**
	 * Shows the startup view.
	 */
	public void showStartupView() {
		displayView(new StartupView());
	}

	/**
	 * Displays the current save file in the title bar.
	 *
	 * @param filename the new title filename
	 */
	public void setTitleFilename(String filename) {
		if (filename == null) {
			setTitle(TITLE);
		} else {
			setTitle(TITLE + " - " + filename);
		}
	}

	/**
	 * Show the ensemble view.
	 */
	public void showEnsembleView() {
		displayView(new EnsembleView(this));
	}

	/**
	 * Show the edit ensemble view.
	 *
	 * @param ensemble the ensemble
	 * @param editable the editable
	 */
	public void showEditEnsemble(Ensemble ensemble, boolean editable) {
		displayView(new EnsembleView(this, ensemble, editable));
	}

	/**
	 * Show the production view.
	 */
	public void showProductionView() {
		displayView(new ProductionView(this));
	}

	/**
	 * Show the edit production view.
	 *
	 * @param production the production
	 */
	public void showEditProduction(Production production) {
		displayView(new ProductionView(this, production));
	}

	private void handleClose() {
		log.debug("closing window");
		int choice = JOptionPane.showConfirmDialog(mainWindow, "Möchten Sie ShowGo wirklich beenden? Nicht gespeicherte Änderungen gehen verloren.",
				"ShowGo beenden", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			if (ShowGoDAO.getSaveFile() != null) {
				LastSaveProperties.writeLastSaveFile(ShowGoDAO.getSaveFile());
			}
			dispose();
		}
	}

	/**
	 * Shows the playbill view.
	 *
	 * @param production the production
	 */
	public void showPlaybillView(Production production) {
		displayView(new PlaybillView(this, production));
	}

}
