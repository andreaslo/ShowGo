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
	
	public Role(){
		id = UUID.randomUUID().toString();
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", words=" + words + ", ageFrom=" + ageFrom + ", ageTo=" + ageTo + ", gender=" + gender
				+ ", pseudoRole=" + pseudoRole + ", assigendRoles=" + assigendRoles + "]";
	}

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

	public int getWords() {
		return words;
	}

	public void setWords(int words) {
		this.words = words;
	}

	public boolean isPseudoRole() {
		return pseudoRole;
	}

	public void setPseudoRole(boolean pseudoRole) {
		if(!pseudoRole){
			assigendRoles.clear();
		}
		
		this.pseudoRole = pseudoRole;
	}

	public void assignRole(Role role){
		assigendRoles.add(role);
	}
	
	public List<Role> getAssigendRoles() {
		return assigendRoles;
	}


	public int getAgeFrom() {
		return ageFrom;
	}


	public void setAgeFrom(int ageFrom) {
		this.ageFrom = ageFrom;
	}


	public int getAgeTo() {
		return ageTo;
	}


	public void setAgeTo(int ageTo) {
		this.ageTo = ageTo;
	}


	public void setAssigendRoles(List<Role> assigendRoles) {
		this.assigendRoles = assigendRoles;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public List<Person> getCast() {
		return cast;
	}


	public void setCast(List<Person> cast) {
		this.cast = cast;
	}


	public String getId() {
		return id;
	}

	
	
	
}
