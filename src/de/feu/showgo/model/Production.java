package de.feu.showgo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * This class represents a production. It consists of a name, an ensemble, a play and list of
 * non-actor roles. The list of actor roles is part of the play.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Production {

	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "play")
	private TheaterPlay play;
	@XmlElement(name = "nonActorRole")
	private List<Role> nonActorRoles;
	@XmlIDREF
	private Ensemble ensemble;

	/**
	 * Gets the play.
	 * 
	 * @return the play
	 */
	public TheaterPlay getPlay() {
		return play;
	}

	/**
	 * Sets the play.
	 * 
	 * @param play
	 *            the new play
	 */
	public void setPlay(TheaterPlay play) {
		this.play = play;
	}

	/**
	 * Gets the non actor roles.
	 * 
	 * @return the non actor roles
	 */
	public List<Role> getNonActorRoles() {
		return nonActorRoles;
	}

	/**
	 * Sets the non actor roles.
	 * 
	 * @param nonActorRoles
	 *            the new non actor roles
	 */
	public void setNonActorRoles(List<Role> nonActorRoles) {
		this.nonActorRoles = nonActorRoles;
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
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the ensemble.
	 * 
	 * @return the ensemble
	 */
	public Ensemble getEnsemble() {
		return ensemble;
	}

	/**
	 * Sets the ensemble.
	 * 
	 * @param ensamble
	 *            the new ensemble
	 */
	public void setEnsemble(Ensemble ensamble) {
		this.ensemble = ensamble;
	}

}
