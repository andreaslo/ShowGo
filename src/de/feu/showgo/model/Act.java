package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

public class Act {

	private String name;
	private List<Scene> scenes = new ArrayList<Scene>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Scene> getScenes() {
		return scenes;
	}
	public void addScene(Scene scene) {
		scenes.add(scene);
	}
	
	@Override
	public String toString() {
		return "Act [name=" + name + ", scenes=" + scenes + "]\n";
	}
	
	
}
