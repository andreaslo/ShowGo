package de.feu.showgo.model;

import java.util.Date;

public class Person {

	public enum Gender {
		MALE, FEMALE
	}
	
	private String name;
	private Date birthday;
	private Gender gender;
	private int wordsRetention;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", birthday=" + birthday + ", gender="
				+ gender + ", wordsRetention=" + wordsRetention + "]";
	}
	public int getWordsRetention() {
		return wordsRetention;
	}
	public void setWordsRetention(int wordsRetention) {
		this.wordsRetention = wordsRetention;
	}
	
	

	
}
