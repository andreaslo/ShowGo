package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * A model class representing an act in a theater play. It contains a name and a
 * list of scenes.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Act {

	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "scene")
	private List<Scene> scenes = new ArrayList<Scene>();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the scenes.
	 *
	 * @return the scenes
	 */
	public List<Scene> getScenes() {
		return scenes;
	}

	/**
	 * Adds a scene.
	 *
	 * @param scene the scene
	 */
	public void addScene(Scene scene) {
		scenes.add(scene);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Act [name=" + name + ", scenes=" + scenes + "]\n";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((scenes == null) ? 0 : scenes.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/**
	 * Deletes a scene.
	 *
	 * @param scene the scene
	 */
	public void deleteScene(Scene scene) {
		scenes.remove(scene);
	}

}
