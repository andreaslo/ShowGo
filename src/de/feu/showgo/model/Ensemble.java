package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * A class representing an ensemble in a theater. It contains a name and a
 * member list.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Ensemble {

	@XmlAttribute
	@XmlID
	private String id;
	@XmlElement(name = "name")
	private String name;
	@XmlIDREF
	private List<Person> members = new ArrayList<Person>();

	/**
	 * Instantiates a new ensemble.
	 */
	public Ensemble() {
		id = UUID.randomUUID().toString();
	}

	/**
	 * Copy constructor.
	 *
	 * @param oldEnsemble the old ensemble
	 */
	public Ensemble(Ensemble oldEnsemble) {
		id = UUID.randomUUID().toString();
		setName(oldEnsemble.getName());
		members = new ArrayList<Person>(oldEnsemble.getMembers());
	}

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
	 * Gets the members.
	 *
	 * @return the members
	 */
	public List<Person> getMembers() {
		return Collections.unmodifiableList(members);
	}

	/**
	 * Assign person.
	 *
	 * @param person the person
	 */
	public void assignPerson(Person person) {
		members.add(person);
	}

	/**
	 * Remove the person.
	 *
	 * @param person the person
	 */
	public void removePerson(Person person) {
		members.remove(person);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Ensemble other = (Ensemble) obj;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
