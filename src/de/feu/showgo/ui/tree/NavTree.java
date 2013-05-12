package de.feu.showgo.ui.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Person;
import de.feu.showgo.model.Production;
import de.feu.showgo.model.TheaterPlay;
import de.feu.showgo.ui.MainWindow;

/**
 * This class represents the navigation tree shown in the left half of the main
 * window. It shows persons, plays, ensembles and productions. Actions may be
 * performed using a right click on an element. These actions are defined in the
 * TreeElement subclasses. The tree should be refreshed after a model change
 * using the refreshXYZ methods.
 */
public class NavTree {

	private JTree tree;
	private MainWindow mainWindow;
	private PersonManagementTreeNode personTreeNode;
	private PlayTreeNode playsTreeNode;
	private EnsembleManagementTreeNode ensemblesTreeNode;
	private ProductionManagementTreeNode productionManagementTreeNode;

	private Logger log = Logger.getLogger(NavTree.class);

	/**
	 * Instantiates a new nav tree.
	 * 
	 * @param mainWindow
	 *            the main window
	 */
	public NavTree(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		createNavTree();
	}

	private JTree createNavTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Wurzel");

		DefaultMutableTreeNode coreData = new DefaultMutableTreeNode("Stammdaten");
		playsTreeNode = new PlayTreeNode("Stücke", mainWindow);
		personTreeNode = new PersonManagementTreeNode("Personen", mainWindow);
		coreData.add(playsTreeNode);
		coreData.add(personTreeNode);
		root.add(coreData);

		ensemblesTreeNode = new EnsembleManagementTreeNode("Ensembles", mainWindow);
		root.add(ensemblesTreeNode);

		productionManagementTreeNode = new ProductionManagementTreeNode("Inszenierungen", mainWindow);
		root.add(productionManagementTreeNode);

		tree = new JTree(root);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setBorder(BorderFactory.createLoweredBevelBorder());

		MouseAdapter ma = new MouseAdapter() {
			private void myPopupEvent(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				JTree tree = (JTree) e.getSource();
				TreePath path = tree.getPathForLocation(x, y);
				if (path == null)
					return;

				tree.setSelectionPath(path);

				DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) path.getLastPathComponent();

				if (selectedElement instanceof TreeElement) {
					TreeElement ele = (TreeElement) selectedElement;
					JPopupMenu popup = ele.getPopupMenu();
					popup.show(tree, x, y);
				}

			}

			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					myPopupEvent(e);
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					myPopupEvent(e);
			}
		};

		tree.addMouseListener(ma);
		tree.expandRow(0);

		return tree;
	}

	/**
	 * Gets the swing JTree.
	 * 
	 * @return the tree
	 */
	public JTree getTree() {
		return tree;
	}

	/**
	 * Refreshes the persons in the tree. Should be called after inserting or
	 * removing a person from the model.
	 */
	public void refreshPersons() {
		List<Person> persons = ShowGoDAO.getShowGo().getPersons();

		personTreeNode.removeAllChildren();
		for (Person person : persons) {
			personTreeNode.add(new PersonNode(person, mainWindow));
			log.debug("Adding node, childs: " + personTreeNode.getChildCount());
		}

		DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
		dtm.reload(personTreeNode);

		tree.revalidate();
		tree.repaint();

		tree.expandRow(personTreeNode.getLevel());
	}

	/**
	 * Refreshes the plays in the tree. Should be called after inserting or
	 * removing a play from the model.
	 */
	public void refreshPlays() {
		List<TheaterPlay> plays = ShowGoDAO.getShowGo().getPlays();

		playsTreeNode.removeAllChildren();
		for (TheaterPlay play : plays) {
			playsTreeNode.add(new PlayNode(play, mainWindow));
			log.debug("Adding node, childs: " + playsTreeNode.getChildCount());
		}

		DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
		dtm.reload(playsTreeNode);

		tree.revalidate();
		tree.repaint();

		tree.expandRow(playsTreeNode.getLevel());
	}

	/**
	 * Refreshes the ensembles in the tree. Should be called after inserting or
	 * removing an ensemble from the model.
	 */
	public void refreshEnsembles() {
		List<Ensemble> ensembles = ShowGoDAO.getShowGo().getEnsembles();

		ensemblesTreeNode.removeAllChildren();
		for (Ensemble ensemble : ensembles) {
			ensemblesTreeNode.add(new EnsembleNode(ensemble, mainWindow));
			log.debug("Adding node, childs: " + ensemblesTreeNode.getChildCount());
		}

		DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
		dtm.reload(ensemblesTreeNode);

		tree.revalidate();
		tree.repaint();

		tree.expandRow(ensemblesTreeNode.getLevel());
	}

	/**
	 * Refreshes the productions in the tree. Should be called after inserting
	 * or removing a production from the model.
	 */
	public void refreshProductions() {
		List<Production> productions = ShowGoDAO.getShowGo().getProductions();

		productionManagementTreeNode.removeAllChildren();
		for (Production production : productions) {
			productionManagementTreeNode.add(new ProductionNode(production, mainWindow));
			log.debug("Adding node, childs: " + productionManagementTreeNode.getChildCount());
		}

		DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
		dtm.reload(productionManagementTreeNode);

		tree.revalidate();
		tree.repaint();

		tree.expandRow(productionManagementTreeNode.getLevel());
	}

	/**
	 * Refreshes the complete tree
	 */
	public void refreshTree() {
		refreshPersons();
		refreshPlays();
		refreshEnsembles();
		refreshProductions();
	}

}
