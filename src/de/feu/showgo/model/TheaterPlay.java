package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TheaterPlay {

	@XmlElement(name="name")
	private String name;
	@XmlElement(name="role")
	private List<Role> roles;
	@XmlElement(name="act")
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
