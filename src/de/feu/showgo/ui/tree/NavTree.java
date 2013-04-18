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

import de.feu.showgo.ShowGoDAO;
import de.feu.showgo.model.Person;
import de.feu.showgo.ui.MainWindow;

public class NavTree {

	private JTree tree;
	private MainWindow mainWindow;
	private PersonTreeNode personTreeNode;
	
	public NavTree(MainWindow mainWindow){
		this.mainWindow = mainWindow;
		createNavTree();
	}
	
	private JTree createNavTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Wurzel");
		
		DefaultMutableTreeNode coreData = new DefaultMutableTreeNode("Stammdaten");
		DefaultMutableTreeNode plays = new PlayTreeNode("Stücke", mainWindow);
		personTreeNode = new PersonTreeNode("Personen", mainWindow);
		coreData.add(plays);
		coreData.add(personTreeNode);
		root.add(coreData);
		
		DefaultMutableTreeNode ensembles = new DefaultMutableTreeNode("Ensembles");
		root.add(ensembles);
		
		DefaultMutableTreeNode production = new DefaultMutableTreeNode("Inszenierungen");
		root.add(production);
		
		tree = new JTree(root);
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

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}
	
	/**
	 * Refreshes the persons in the tree. Should be called after inserting or removing a person from the model.
	 */
	public void refreshPersons(){
		List<Person> persons = ShowGoDAO.getShowGo().getPersons();
		
		personTreeNode.removeAllChildren();
		for(Person person : persons){
			personTreeNode.add(new DefaultMutableTreeNode(person));
			System.out.println("Adding node, childs: " + personTreeNode.getChildCount());
		}
		
	    DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();  
	    dtm.reload(personTreeNode);
		
		tree.validate();
		tree.repaint();
		
		tree.expandRow(personTreeNode.getLevel());
	}
	
	
	
}
