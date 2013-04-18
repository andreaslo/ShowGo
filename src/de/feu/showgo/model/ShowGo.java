package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowGo {

	private List<Person> persons = new ArrayList<Person>();

	public List<Person> getPersons() {
		return Collections.unmodifiableList(persons);
	}

	public void addPerson(Person person) {
		persons.add(person);
		
		System.out.println("person added, list length: " + persons.size());
	}
	
	
	
}
