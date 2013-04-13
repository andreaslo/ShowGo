package de.feu.showgo.ui;

import info.clearthought.layout.TableLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.feu.showgo.ui.actions.ShowCreatePersonAction;
import de.feu.showgo.ui.actions.ShowReadPlayAction;
import de.feu.showgo.ui.tree.PersonTreeNode;
import de.feu.showgo.ui.tree.PlayTreeNode;
import de.feu.showgo.ui.tree.TreeElement;
import de.feu.showgo.ui.views.CreatePersonView;
import de.feu.showgo.ui.views.ReadPlayView;
import de.feu.showgo.ui.views.StartupView;

public class MainWindow extends JFrame {

	private JPanel currentView;
	
	public MainWindow() {

	}

	public void init() {
		
		
		this.setSize(800, 600);
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ShowGo - Andreas Lösche / 8614989");
		this.setJMenuBar(createMenu());

		
	    double size[][] = {{10,200,10,TableLayout.FILL,10},
         		{10,TableLayout.FILL,10}};
	    this.setLayout (new TableLayout(size));

		this.add(createNavTree(), "1,1");

		displayView(new StartupView());

		this.setVisible(true);
	}

	private JTree createNavTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Wurzel");
		
		DefaultMutableTreeNode coreData = new DefaultMutableTreeNode("Stammdaten");
		DefaultMutableTreeNode plays = new PlayTreeNode("Stücke", this);
		DefaultMutableTreeNode persons = new PersonTreeNode("Personen", this);
		coreData.add(plays);
		coreData.add(persons);
		root.add(coreData);
		
		DefaultMutableTreeNode ensembles = new DefaultMutableTreeNode("Ensembles");
		root.add(ensembles);
		
		DefaultMutableTreeNode production = new DefaultMutableTreeNode("Inszenierungen");
		root.add(production);
		
		JTree tree = new JTree(root);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setBorder(BorderFactory.createLoweredBevelBorder());
		
		
		MouseAdapter ma = new MouseAdapter() {
			private void myPopupEvent(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				JTree tree = (JTree)e.getSource();
				TreePath path = tree.getPathForLocation(x, y);
				if (path == null)
					return;	

				tree.setSelectionPath(path);

				DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) path.getLastPathComponent();

				if(selectedElement instanceof TreeElement){
					TreeElement ele = (TreeElement) selectedElement;
					JPopupMenu popup = ele.getPopupMenu();
					popup.show(tree, x, y);
				}

			}
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) myPopupEvent(e);
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) myPopupEvent(e);
			}
		};
		
		tree.addMouseListener(ma);
		
		return tree;
	}

	private JMenuBar createMenu() {
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem close = new JMenuItem("Schließen");
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
		if(currentView != null){
			this.remove(currentView);
		}
		currentView = newView;

		TitledBorder titledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(), currentView.getName());
		titledBorder.setTitlePosition(TitledBorder.ABOVE_TOP);

		currentView.setBorder(titledBorder);
		this.add(currentView, "3,1");
		this.validate();
		this.repaint();
	}
	
	public void showCreatePersonView(){
		displayView(new CreatePersonView());
	}
	
	public void showReadPlayView(){
		System.out.println("read play");
		displayView(new ReadPlayView());
	}

	
	
}
