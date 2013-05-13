package de.feu.showgo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Person;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;

/**
 * This class assigns persons to roles based on the requirements of the roles
 * and the persons skills. For each role a score is computed for every person.
 * Thereby it is checked, whether the age, number of words and gender matches.
 * For each match a score is increased by one.
 * 
 * Afterwards the persons are assigned based on the score. If there is only one
 * person with the highest possible score it is assigned to the role. In order
 * to have different roles played by different persons the score for each other
 * role is slightly reduced after an assignment.
 * 
 * As last step all the person with the highest score is assigned to each role.
 */
public class CastingGenerator {

	private static final Logger log = Logger.getLogger(CastingGenerator.class);

	/**
	 * Computes the casting for the roles in the play. The ensemble provides the
	 * available persons, that are assigned to the roles in the play. The
	 * information is written into the cast list of the roles in the play.
	 * 
	 * @param ensemble
	 *            the ensemble
	 * @param play
	 *            the play
	 */
	public static void generateCasting(Ensemble ensemble, TheaterPlay play) {

		List<RoleScorePair> roleScores = new ArrayList<CastingGenerator.RoleScorePair>();

		for (Role role : play.getRoles()) {
			if (role.isPseudoRole()) {
				continue;
			}

			log.debug("generating cast matches for role: " + role);

			DateTime startRange = DateTime.now().minusYears(role.getAgeTo());
			DateTime endRange = DateTime.now().minusYears(role.getAgeFrom());
			Interval ageRange = new Interval(startRange, endRange);
			List<PersonScorePair> scores = new ArrayList<PersonScorePair>();
			for (Person person : ensemble.getMembers()) {
				double score = 0;
				if (person.getWordsRetention() > role.getWords()) {
					score++;
				}
				if (person.getGender() == role.getGender()) {
					score++;
				}

				DateTime personAge = new DateTime(person.getBirthday());
				boolean ageInRange = ageRange.contains(personAge);

				if (ageInRange) {
					score++;
				}

				log.debug("start: " + startRange + " end : " + endRange + " range: " + ageRange + " contained: " + ageInRange);

				PersonScorePair result = new PersonScorePair();
				result.person = person;
				result.score = score;
				scores.add(result);
			}

			Collections.sort(scores);
			log.debug(scores);

			RoleScorePair roleScore = new RoleScorePair();
			roleScore.role = role;
			roleScore.scores = scores;
			roleScores.add(roleScore);
		}

		/*
		 * If there is only one person with the highest score for a role assign
		 * this person to the role and slightly reduce the score for each other
		 * role. This favors persons that are not yet assigned to a role.
		 */
		for (RoleScorePair rolePair : roleScores) {
			log.debug("matching role: " + rolePair.role.getName());

			if (!rolePair.scores.isEmpty()) {
				Collections.sort(rolePair.scores);

				double highest = rolePair.scores.get(0).score;
				int numWithHighestScore = 0;
				for (PersonScorePair scorePair : rolePair.scores) {
					if (highest == scorePair.score) {
						numWithHighestScore++;
					}
				}
				if (numWithHighestScore == 1) {
					Person assignedPerson = rolePair.scores.get(0).person;
					List<Person> cast = new LinkedList<Person>();
					cast.add(assignedPerson);
					rolePair.role.setCast(cast);

					log.debug("assigning person " + assignedPerson + " to role " + rolePair.role.getName());
					reduceScore(roleScores, assignedPerson, 0.1);
				}
			}
		}

		/*
		 * Assign all others roles using a greedy approach.
		 */
		for (RoleScorePair rolePair : roleScores) {
			Role role = rolePair.role;
			if (role.getCast() == null || role.getCast().isEmpty()) {
				if (rolePair.scores.isEmpty()) {
					continue;
				}
				Collections.sort(rolePair.scores);
				Person assignedPerson = rolePair.scores.get(0).person;
				List<Person> cast = new LinkedList<Person>();
				cast.add(assignedPerson);
				role.setCast(cast);

				log.debug("assigning person " + assignedPerson.getName() + " with score " + rolePair.scores.get(0).score + " to role "
						+ rolePair.role.getName());
				reduceScore(roleScores, assignedPerson, 0.1);
			}
		}

	}

	private static void reduceScore(List<RoleScorePair> roleScores, Person personToReduce, double amount) {
		log.debug("reducing score of " + personToReduce);
		for (RoleScorePair rolePair : roleScores) {
			for (PersonScorePair scorePair : rolePair.scores) {
				if (scorePair.person == personToReduce) {
					scorePair.score -= amount;
				}
			}
		}
	}

	private static class PersonScorePair implements Comparable<PersonScorePair> {
		Person person;
		double score;

		@Override
		public String toString() {
			return "[person=" + person.getName() + ", score=" + score + "]";
		}

		@Override
		public int compareTo(PersonScorePair o) {
			if (score > o.score) {
				return -1;
			}
			if (score < o.score) {
				return 1;
			}
			return 0;
		}
	}

	private static class RoleScorePair {
		Role role;
		List<PersonScorePair> scores;
	}

}
