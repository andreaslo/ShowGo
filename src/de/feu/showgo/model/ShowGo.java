package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class ShowGo {

	private List<Person> persons = new ArrayList<Person>();
	
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
			log.warn("could not delte user " + person.getName());
		}
	}
	
	
	
}
