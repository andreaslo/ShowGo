package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ensemble {

	private String name;
	private List<Person> members = new ArrayList<Person>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Person> getMembers() {
		return Collections.unmodifiableList(members);
	}
	
	
	
	
	
}
