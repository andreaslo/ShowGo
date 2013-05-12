package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

/**
 * This method is the root data store of the application. It contains a list of
 * persons, theater plays, ensembles and productions. There should only be one
 * instances of this class in the application, which is managed by the ShowGoDAO
 * class.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShowGo {

	@XmlElement(name = "person")
	private List<Person> persons = new ArrayList<Person>();

	@XmlElement(name = "play")
	private List<TheaterPlay> plays = new ArrayList<TheaterPlay>();

	@XmlElement(name = "ensemble")
	private List<Ensemble> ensembles = new ArrayList<Ensemble>();

	@XmlElement(name = "production")
	private List<Production> productions = new ArrayList<Production>();

	private final static Logger log = Logger.getLogger(ShowGo.class);

	/**
	 * Returns the list of persons
	 * 
	 * @return the persons
	 */
	public List<Person> getPersons() {
		return Collections.unmodifiableList(persons);
	}

	/**
	 * Adds a person.
	 * 
	 * @param person
	 *            the person
	 */
	public void addPerson(Person person) {
		persons.add(person);

		log.debug("person " + person.getName() + " added, list length: " + persons.size());
	}

	/**
	 * Adds an ensemble.
	 * 
	 * @param ensemble
	 *            the ensemble
	 */
	public void addEnsemble(Ensemble ensemble) {
		ensembles.add(ensemble);
	}

	/**
	 * Returns all ensembles.
	 * 
	 * @return the ensembles
	 */
	public List<Ensemble> getEnsembles() {
		return Collections.unmodifiableList(ensembles);
	}

	/**
	 * Deltes a person.
	 * 
	 * @param person
	 *            the person
	 */
	public void deltePerson(Person person) {
		if (persons.remove(person)) {
			log.debug("person " + person.getName() + " added, list length: " + persons.size());
		} else {
			log.warn("could not delete user " + person.getName());
		}
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
		result = prime * result + ((ensembles == null) ? 0 : ensembles.hashCode());
		result = prime * result + ((persons == null) ? 0 : persons.hashCode());
		result = prime * result + ((plays == null) ? 0 : plays.hashCode());
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
		ShowGo other = (ShowGo) obj;
		if (ensembles == null) {
			if (other.ensembles != null)
				return false;
		} else if (!ensembles.equals(other.ensembles))
			return false;
		if (persons == null) {
			if (other.persons != null)
				return false;
		} else if (!persons.equals(other.persons))
			return false;
		if (plays == null) {
			if (other.plays != null)
				return false;
		} else if (!plays.equals(other.plays))
			return false;
		return true;
	}

	/**
	 * Adds a theater play.
	 * 
	 * @param play
	 *            the play
	 */
	public void addPlay(TheaterPlay play) {
		plays.add(play);

		log.debug("play " + play.getName() + " added, list length: " + plays.size());
	}

	/**
	 * Gets the plays.
	 * 
	 * @return the plays
	 */
	public List<TheaterPlay> getPlays() {
		return Collections.unmodifiableList(plays);
	}

	/**
	 * Deletes an ensemble.
	 * 
	 * @param ensemble
	 *            the ensemble
	 */
	public void delteEnsemble(Ensemble ensemble) {
		if (ensembles.remove(ensemble)) {
			log.debug("ensebmble " + ensemble.getName() + " added, list length: " + ensembles.size());
		} else {
			log.warn("could not delete ensemble " + ensemble.getName());
		}
	}

	/**
	 * Adds a production.
	 * 
	 * @param production
	 *            the production
	 */
	public void addProduction(Production production) {
		productions.add(production);
	}

	/**
	 * Return the productions.
	 * 
	 * @return the productions
	 */
	public List<Production> getProductions() {
		return productions;
	}

	/**
	 * Deletes a production.
	 * 
	 * @param production
	 *            the production
	 */
	public void delteProduction(Production production) {
		productions.remove(production);
	}

}
