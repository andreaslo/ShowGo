package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.LinkedList;
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
	
	public List<Role> getRegularRoles(){
		List<Role> regularRoles = new ArrayList<Role>();
		for(Role role : roles){
			if(!role.isPseudoRole()){
				regularRoles.add(role);
			}
		}
		return regularRoles;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acts == null) ? 0 : acts.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		TheaterPlay other = (TheaterPlay) obj;
		if (acts == null) {
			if (other.acts != null)
				return false;
		} else if (!acts.equals(other.acts))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}
	public void deleteAct(Act act) {
		acts.remove(act);
	}
	public void deleteRole(Role toBeDeleted) {
		roles.remove(toBeDeleted);
		
		// remove the role from pesudo roles
		for(Role role : roles){
			if(role.isPseudoRole()){
				role.getAssigendRoles().remove(toBeDeleted);
			}
		}
		
		for(Act act : acts){
			for(Scene scene : act.getScenes()){
				if(scene.getAllRole() != null){
					scene.getAllRole().getAssigendRoles().remove(toBeDeleted);
				}
			}
		}
	}	
	
	
}
