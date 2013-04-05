package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

public class TheaterPlay {

	private String name;
	private List<Role> roles;
	private List<Act> acts = new ArrayList<Act>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Act> getActs() {
		return acts;
	}
	public void addAct(Act act) {
		acts.add(act);
	}
	
	

	@Override
	public String toString() {
		return "TheaterPlay [name=" + name + ", roles=" + roles + ", acts="
				+ acts + "]";
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
	
}
