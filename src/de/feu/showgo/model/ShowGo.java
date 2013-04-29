package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShowGo {

	@XmlElement(name="person")
	private List<Person> persons = new ArrayList<Person>();
	
	@XmlElement(name="play")
	private List<TheaterPlay> plays = new ArrayList<TheaterPlay>();
	
	private final static Logger log = Logger.getLogger(ShowGo.class);

	public List<Person> getPersons() {
		return Collections.unmodifiableList(persons);
	}

	public void addPerson(Person person) {
		persons.add(person);
		
		log.debug("person "+person.getName()+" added, list length: " + persons.size());
	}
	
	public void deltePerson(Person person) {
		if(persons.remove(person)){
			log.debug("person "+person.getName()+" added, list length: " + persons.size());
		}else{
			log.warn("could not delete user " + person.getName());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((persons == null) ? 0 : persons.hashCode());
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
		ShowGo other = (ShowGo) obj;
		if (persons == null) {
			if (other.persons != null)
				return false;
		} else if (!persons.equals(other.persons))
			return false;
		return true;
	}
	
	public void addPlay(TheaterPlay play) {
		plays.add(play);
		
		log.debug("play "+play.getName()+" added, list length: " + plays.size());
	}
	
}
