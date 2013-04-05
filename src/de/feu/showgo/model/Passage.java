package de.feu.showgo.model;

public class Passage extends Paragraph {

	private Role role;
	private String text;
	
	

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Passage [role=" + role + ", text=" + text + "]\n";
	}
	
	
	
}
