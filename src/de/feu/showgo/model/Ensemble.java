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

@XmlAccessorType(XmlAccessType.FIELD)
public class Ensemble {
	
	@XmlAttribute
    @XmlID
	private String id;
	@XmlElement(name="name")
	private String name;
	@XmlIDREF
	private List<Person> members = new ArrayList<Person>();
	
	public Ensemble(){
		id = UUID.randomUUID().toString();
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param oldEnsemble
	 */
	public Ensemble(Ensemble oldEnsemble){
		id = UUID.randomUUID().toString();
		setName(oldEnsemble.getName());
		members = new ArrayList<Person>(oldEnsemble.getMembers());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Person> getMembers() {
		return Collections.unmodifiableList(members);
	}
	
	public void assignPerson(Person person){
		members.add(person);
	}
	
	public void removePerson(Person person){
		members.remove(person);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
