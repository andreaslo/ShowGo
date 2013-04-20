package de.feu.showgo.ui;

import info.clearthought.layout.TableLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import de.feu.showgo.model.Person;
import de.feu.showgo.ui.actions.SaveAsAction;
import de.feu.showgo.ui.actions.SaveAction;
import de.feu.showgo.ui.actions.ShowCreatePersonAction;
import de.feu.showgo.ui.actions.ShowReadPlayAction;
import de.feu.showgo.ui.tree.NavTree;
import de.feu.showgo.ui.views.PersonManagementView;
import de.feu.showgo.ui.views.ReadPlayView;
import de.feu.showgo.ui.views.StartupView;

public class MainWindow extends JFrame {

	private JPanel currentView;
	private NavTree navTree;

	public MainWindow() {

	}

	public void init() {

		navTree = new NavTree(this);

		this.setSize(1000, 600);
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ShowGo - Andreas Lösche / 8614989");
		this.setJMenuBar(createMenu());

		double size[][] = { { 10, 350, 10, TableLayout.FILL, 10 }, { 10, TableLayout.FILL, 10 } };
		this.setLayout(new TableLayout(size));

		this.add(navTree.getTree(), "1,1");

		displayView(new StartupView());

		this.setVisible(true);
	}

	private JMenuBar createMenu() {
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem newTheater = new JMenuItem("Neues Theater");
		JMenuItem load = new JMenuItem("Laden");
		JMenuItem save = new JMenuItem("Speichern");
		save.addActionListener(new SaveAction(this));
		JMenuItem saveAs = new JMenuItem("Speichern unter");
		saveAs.addActionListener(new SaveAsAction(this));
		JMenuItem close = new JMenuItem("Schließen");
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
		actionMenu.add(addPerson);
		actionMenu.add(readPlay);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(actionMenu);

		return menuBar;
	}

	public void displayView(JPanel newView) {
		if (currentView != null) {
			this.remove(currentView);
		}
		currentView = newView;

		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), currentView.getName());
		titledBorder.setTitlePosition(TitledBorder.ABOVE_TOP);

		currentView.setBorder(titledBorder);
		this.add(currentView, "3,1");
		this.validate();
		this.repaint();
	}

	public void showCreatePersonView() {
		displayView(new PersonManagementView(this));
	}

	public void showEditPerson(Person person) {
		displayView(new PersonManagementView(this, person));
	}

	public void showReadPlayView() {
		System.out.println("read play");
		displayView(new ReadPlayView());
	}

	public NavTree getNavTree() {
		return navTree;
	}

	public JPanel getCurrentView() {
		return currentView;
	}

	
	public void showStartupView(){
		displayView(new StartupView());
	}

}
