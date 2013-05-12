package de.feu.showgo.model;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

/**
 * This class represents a person in a theater.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
	
	@XmlAttribute
    @XmlID
	private String id;
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="birthday")
	private Date birthday;
	@XmlElement(name="gender")
	private Gender gender;
	@XmlElement(name="words")
	private int wordsRetention;
	
	/**
	 * Instantiates a new person.
	 */
	public Person() {
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
	
	/**
	 * Gets the birthday.
	 *
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	
	/**
	 * Sets the birthday.
	 *
	 * @param birthday the new birthday
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Person [name=" + name + ", birthday=" + birthday + ", gender="
				+ gender + ", wordsRetention=" + wordsRetention + "]";
	}
	
	/**
	 * Gets the words retention.
	 *
	 * @return the words retention
	 */
	public int getWordsRetention() {
		return wordsRetention;
	}
	
	/**
	 * Sets the words retention.
	 *
	 * @param wordsRetention the new words retention
	 */
	public void setWordsRetention(int wordsRetention) {
		this.wordsRetention = wordsRetention;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + wordsRetention;
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
		Person other = (Person) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (gender != other.gender)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (wordsRetention != other.wordsRetention)
			return false;
		return true;
	}
	
	

	
}
