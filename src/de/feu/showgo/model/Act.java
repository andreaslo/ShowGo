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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((scenes == null) ? 0 : scenes.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Act other = (Act) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (scenes == null) {
			if (other.scenes != null)
				return false;
		} else if (!scenes.equals(other.scenes))
			return false;
		return true;
	}
	public void deleteScene(Scene scene) {
		scenes.remove(scene);
	}	
	
}
