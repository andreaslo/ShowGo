package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShowGo {

	@XmlElement(name="person")
	private List<Person> persons = new ArrayList<Person>();
	
	@XmlElement(name="play")
	private List<TheaterPlay> plays = new ArrayList<TheaterPlay>();
	
	@XmlElement(name="ensemble")
	private List<Ensemble> ensembles = new ArrayList<Ensemble>();
	
	private List<Production> productions = new ArrayList<Production>();
	
	private final static Logger log = Logger.getLogger(ShowGo.class);

	public List<Person> getPersons() {
		return Collections.unmodifiableList(persons);
	}

	public void addPerson(Person person) {
		persons.add(person);
		
		log.debug("person "+person.getName()+" added, list length: " + persons.size());
	}
	
	public void addEnsemble(Ensemble ensemble){
		ensembles.add(ensemble);
	}
	
	public List<Ensemble> getEnsembles() {
		return Collections.unmodifiableList(ensembles);
	}

	public void deltePerson(Person person) {
		if(persons.remove(person)){
			log.debug("person "+person.getName()+" added, list length: " + persons.size());
		}else{
			log.warn("could not delete user " + person.getName());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ensembles == null) ? 0 : ensembles.hashCode());
		result = prime * result + ((persons == null) ? 0 : persons.hashCode());
		result = prime * result + ((plays == null) ? 0 : plays.hashCode());
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
	
	public void addPlay(TheaterPlay play) {
		plays.add(play);
		
		log.debug("play "+play.getName()+" added, list length: " + plays.size());
	}

	public List<TheaterPlay> getPlays() {
		return Collections.unmodifiableList(plays);
	}

	public void delteEnsemble(Ensemble ensemble) {
		if(ensembles.remove(ensemble)){
			log.debug("ensebmble "+ensemble.getName()+" added, list length: " + ensembles.size());
		}else{
			log.warn("could not delete ensemble " + ensemble.getName());
		}
	}
	
	public void addProduction(Production production){
		productions.add(production);
	}

	public List<Production> getProductions() {
		return productions;
	}
	
}
