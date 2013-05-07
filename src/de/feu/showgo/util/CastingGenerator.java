package de.feu.showgo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;

import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Person;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;

public class CastingGenerator {

	private static final Logger log = Logger.getLogger(CastingGenerator.class);
	
	public static void generateCasting(Ensemble ensemble, TheaterPlay play){
		
		for(Role role : play.getRoles()){
			if(role.isPseudoRole()){
				continue;
			}
			
			log.debug("generating cast matches for role: " + role);
			
			DateTime startRange = DateTime.now().minusYears(role.getAgeTo());
			DateTime endRange = DateTime.now().minusYears(role.getAgeFrom());
			Interval ageRange = new Interval(startRange, endRange);
			List<PersonScorePair> scores = new ArrayList<PersonScorePair>();
			for(Person person : ensemble.getMembers()){
				double score = 0;
				if(person.getWordsRetention() > role.getWords()){
					score++;
				}
				if(person.getGender() == role.getGender()){
					score++;
				}
				
				DateTime personAge = new DateTime(person.getBirthday());
				boolean ageInRange = ageRange.contains(personAge);
				
				if(ageInRange){
					score++;
				}
				
				log.debug("start: " +startRange + " end : " + endRange + " range: " + ageRange + " contained: " + ageInRange);
				
				PersonScorePair result = new PersonScorePair();
				result.person = person;
				result.score = score;
				scores.add(result);
			}
		
			Collections.sort(scores);
			log.debug(scores);
		}
		
		
	}
	
	private static class PersonScorePair implements Comparable<PersonScorePair>{
		Person person;
		double score;
		@Override
		public String toString() {
			return "[person=" + person.getName() + ", score=" + score + "]";
		}
		
		@Override
		public int compareTo(PersonScorePair o) {
			if(score > o.score){
				return -1;
			}
			if(score < o.score){
				return 1;
			}
			return 0;
		}
		
		
	}
	
	
}
