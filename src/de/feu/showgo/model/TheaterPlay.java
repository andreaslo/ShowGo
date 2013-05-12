package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class contains a theater play. It consists of a name, a list of roles
 * and a list of acts.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TheaterPlay {

	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "role")
	private List<Role> roles;
	@XmlElement(name = "act")
	private List<Act> acts = new ArrayList<Act>();

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
	 * Gets the acts.
	 * 
	 * @return the acts
	 */
	public List<Act> getActs() {
		return acts;
	}

	/**
	 * Adds an act.
	 * 
	 * @param act
	 *            the act
	 */
	public void addAct(Act act) {
		acts.add(act);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TheaterPlay [name=" + name + ", roles=" + roles + ", acts=" + acts + "]";
	}

	/**
	 * Gets the roles.
	 * 
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 * 
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Gets the regular roles. These are all roles without the isPseudo flag.
	 * 
	 * @return the regular roles
	 */
	public List<Role> getRegularRoles() {
		List<Role> regularRoles = new ArrayList<Role>();
		for (Role role : roles) {
			if (!role.isPseudoRole()) {
				regularRoles.add(role);
			}
		}
		return regularRoles;
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
		result = prime * result + ((acts == null) ? 0 : acts.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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

	/**
	 * Deletes an act.
	 * 
	 * @param act
	 *            the act
	 */
	public void deleteAct(Act act) {
		acts.remove(act);
	}

	/**
	 * Deletes a role.
	 * 
	 * @param toBeDeleted
	 *            the to be deleted
	 */
	public void deleteRole(Role toBeDeleted) {
		roles.remove(toBeDeleted);

		// remove the role from pesudo roles
		for (Role role : roles) {
			if (role.isPseudoRole()) {
				role.getAssigendRoles().remove(toBeDeleted);
			}
		}

		for (Act act : acts) {
			for (Scene scene : act.getScenes()) {
				if (scene.getAllRole() != null) {
					// remove from all pseudo roles
					scene.getAllRole().getAssigendRoles().remove(toBeDeleted);

					// remove all role
					if (scene.getAllRole() == toBeDeleted) {
						scene.setAllRole(null);
					}
				}
			}
		}
	}

}
