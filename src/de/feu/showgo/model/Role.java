package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * A class representing a role in a theater play.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Role {

	@XmlAttribute
    @XmlID
    private String id;
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="words")
	private int words;
	@XmlElement(name="ageFrom")
	private int ageFrom;
	@XmlElement(name="ageTo")
	private int ageTo;
	@XmlElement(name="gender")
	private Gender gender;
	
	@XmlIDREF
	private List<Person> cast;
	
	
	@XmlElement(name="pseudoRole")
	private boolean pseudoRole;
	@XmlIDREF
	private List<Role> assigendRoles = new ArrayList<Role>();
	
	/**
	 * Instantiates a new role.
	 */
	public Role(){
		id = UUID.randomUUID().toString();
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



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", words=" + words + ", ageFrom=" + ageFrom + ", ageTo=" + ageTo + ", gender=" + gender
				+ ", pseudoRole=" + pseudoRole + ", assigendRoles=" + assigendRoles + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assigendRoles == null) ? 0 : assigendRoles.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (pseudoRole ? 1231 : 1237);
		result = prime * result + words;
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
		Role other = (Role) obj;
		if (assigendRoles == null) {
			if (other.assigendRoles != null)
				return false;
		} else if (!assigendRoles.equals(other.assigendRoles))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pseudoRole != other.pseudoRole)
			return false;
		if (words != other.words)
			return false;
		return true;
	}

	/**
	 * Gets the number of words.
	 *
	 * @return the words
	 */
	public int getWords() {
		return words;
	}

	/**
	 * Sets the number of words.
	 *
	 * @param words the new words
	 */
	public void setWords(int words) {
		this.words = words;
	}

	/**
	 * Checks if it is a pseudo role.
	 *
	 * @return true, if the role is a pseudo role
	 */
	public boolean isPseudoRole() {
		return pseudoRole;
	}

	/**
	 * Sets the pseudo role flag.
	 *
	 * @param pseudoRole the new pseudo role
	 */
	public void setPseudoRole(boolean pseudoRole) {
		if(!pseudoRole){
			assigendRoles.clear();
		}
		
		this.pseudoRole = pseudoRole;
	}

	/**
	 * Assign role to a pseudo role.
	 *
	 * @param role the role
	 */
	public void assignRole(Role role){
		assigendRoles.add(role);
	}
	
	/**
	 * Gets the assigend roles. Null for non-pseudo roles.
	 *
	 * @return the assigend roles
	 */
	public List<Role> getAssigendRoles() {
		return assigendRoles;
	}


	/**
	 * Gets the age from.
	 *
	 * @return the age from
	 */
	public int getAgeFrom() {
		return ageFrom;
	}


	/**
	 * Sets the age from.
	 *
	 * @param ageFrom the new age from
	 */
	public void setAgeFrom(int ageFrom) {
		this.ageFrom = ageFrom;
	}


	/**
	 * Gets the age to.
	 *
	 * @return the age to
	 */
	public int getAgeTo() {
		return ageTo;
	}


	/**
	 * Sets the age to.
	 *
	 * @param ageTo the new age to
	 */
	public void setAgeTo(int ageTo) {
		this.ageTo = ageTo;
	}


	/**
	 * Sets the assigend roles. Only applicable for pseudo roles.
	 *
	 * @param assigendRoles the new assigend roles
	 */
	public void setAssigendRoles(List<Role> assigendRoles) {
		this.assigendRoles = assigendRoles;
	}


	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}


	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}


	/**
	 * Gets the cast.
	 *
	 * @return the cast
	 */
	public List<Person> getCast() {
		return cast;
	}


	/**
	 * Sets the cast.
	 *
	 * @param cast the new cast
	 */
	public void setCast(List<Person> cast) {
		this.cast = cast;
	}


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	
	
	
}
