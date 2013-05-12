package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

/**
 * This class represents a scene in a theater play. It consists of a name and a
 * list of paragraphs.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Scene {

	@XmlElement(name = "name")
	private String name;

	@XmlElementWrapper(name = "paragraph")
	@XmlElements({ @XmlElement(name = "stageDirection", type = StageDirection.class), @XmlElement(name = "passage", type = Passage.class) })
	private List<Paragraph> paragraphs = new ArrayList<Paragraph>();

	@XmlElement(name = "allRole")
	private Role allRole;

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
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the paragraphs.
	 * 
	 * @return the paragraphs
	 */
	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}

	/**
	 * Adds a paragraphs.
	 * 
	 * @param paragraph
	 *            the paragraph
	 */
	public void addParagraphs(Paragraph paragraph) {
		paragraphs.add(paragraph);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Scene [name=" + name + ", paragraphs=" + paragraphs + "]\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((paragraphs == null) ? 0 : paragraphs.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Scene other = (Scene) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (paragraphs == null) {
			if (other.paragraphs != null)
				return false;
		} else if (!paragraphs.equals(other.paragraphs))
			return false;
		return true;
	}

	/**
	 * Gets the all role. This is a special pseudo role that contains all actors
	 * in this scene. Might be null.
	 * 
	 * @return the all role
	 */
	public Role getAllRole() {
		return allRole;
	}

	/**
	 * Sets the all role.
	 * 
	 * @param allRole
	 *            the new all role
	 */
	public void setAllRole(Role allRole) {
		this.allRole = allRole;
	}

	/**
	 * Deletes a paragraph.
	 * 
	 * @param paragraph
	 *            the paragraph
	 */
	public void deleteParagraph(Paragraph paragraph) {
		paragraphs.remove(paragraph);
	}

}
