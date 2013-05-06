package de.feu.showgo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

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
			
			List<PersonScorePair> scores = new ArrayList<PersonScorePair>();
			for(Person person : ensemble.getMembers()){
				double score = 0;
				if(person.getWordsRetention() > role.getWords()){
					score++;
				}
				if(person.getGender() == role.getGender()){
					score++;
				}
				
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
