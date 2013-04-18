package de.feu.showgo;

import de.feu.showgo.model.ShowGo;

public class ShowGoDAO {

	private static ShowGo showGo = new ShowGo();
	
	public static ShowGo getShowGo() {
		return showGo;
	}
	
}
