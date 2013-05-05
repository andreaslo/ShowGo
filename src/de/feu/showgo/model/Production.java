package de.feu.showgo.model;

import java.util.List;

public class Production {

	private String name;
	private TheaterPlay play;
	private List<Role> nonActorRoles;

	public TheaterPlay getPlay() {
		return play;
	}

	public void setPlay(TheaterPlay play) {
		this.play = play;
	}

	public List<Role> getNonActorRoles() {
		return nonActorRoles;
	}

	public void setNonActorRoles(List<Role> nonActorRoles) {
		this.nonActorRoles = nonActorRoles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
