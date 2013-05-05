package de.feu.showgo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class Production {

	@XmlElement(name="name")
	private String name;
	@XmlElement(name="play")
	private TheaterPlay play;
	@XmlElement(name="nonActorRole")
	private List<Role> nonActorRoles;
	@XmlIDREF
	private Ensemble ensemble;

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

	public Ensemble getEnsamble() {
		return ensemble;
	}

	public void setEnsemble(Ensemble ensamble) {
		this.ensemble = ensamble;
	}
	
	
	
}
