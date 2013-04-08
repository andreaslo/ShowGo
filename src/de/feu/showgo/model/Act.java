package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Act {

	@XmlElement(name="name")
	private String name;
	@XmlElement(name="scene")
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
