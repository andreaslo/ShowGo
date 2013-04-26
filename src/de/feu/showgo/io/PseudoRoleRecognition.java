package de.feu.showgo.io;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Act;
import de.feu.showgo.model.Paragraph;
import de.feu.showgo.model.Passage;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.Scene;
import de.feu.showgo.model.TheaterPlay;

public class PseudoRoleRecognition {

	private static final Logger log = Logger.getLogger(PseudoRoleRecognition.class);

	public void recognizePseudoRoles(TheaterPlay play) {
		log.debug("recognizing pseudo roles");

		for (Role curRole : play.getRoles()) {
			log.debug("Recognizing role " + curRole);

			recognizeAndPattern(curRole, play.getRoles());
			recognizeEnumeration(curRole, play.getRoles());
		}

		fillAllRoles(play);
	}

	private Role findRoleByName(List<Role> haystack, String needle) {
		for (Role curRole : haystack) {
			if (curRole.getName().equals(needle)) {
				return curRole;
			}
		}
		return null;
	}

	private boolean recognizeEnumeration(Role curRole, List<Role> allRoles) {
		Pattern p = Pattern.compile("([0-9])\\.,+ ([0-9])\\. und ([0-9])\\. (\\w+)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(curRole.getName());

		if (m.find()) {
			log.debug("Pattern enumeration matches");

			List<String> personNames = new ArrayList<String>();
			for (int i = 1; i < m.groupCount(); i++) { // i=0 contains all
														// matches, the last
														// group the name
				String personName = m.group(i) + ". " + m.group(m.groupCount());
				log.debug("person name " + i + ": " + personName);
				personNames.add(personName);
			}

			curRole.setPseudoRole(true);
			for (String personName : personNames) {
				Role role = findRoleByName(allRoles, personName);
				if (role == null) {
					log.debug(personName + " not found in role list");
				} else {
					curRole.assignRole(role);
				}
			}
			return true;
		}

		return false;
	}

	private boolean recognizeAndPattern(Role curRole, List<Role> allRoles) {
		Pattern p = Pattern.compile("(\\w+) und (\\w+)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(curRole.getName());

		if (m.find()) {
			log.debug("Pattern and matches");
			String personName1 = m.group(1);
			String personName2 = m.group(2);
			log.debug("person 1: " + personName1);
			log.debug("person 2: " + personName2);

			Role role1 = findRoleByName(allRoles, personName1);

			Role role2 = findRoleByName(allRoles, personName2);

			curRole.setPseudoRole(true);
			if (role1 == null) {
				log.debug(personName1 + " not found in role list");
			} else {
				curRole.assignRole(role1);
			}
			if (role2 == null) {
				log.debug(personName2 + " not found in role list");
			} else {
				curRole.assignRole(role2);
			}

			return true;
		}

		return false;
	}

	private void fillAllRoles(TheaterPlay play){
		for (Act act : play.getActs()) {
			for (Scene scene : act.getScenes()) {
				Role allRole = scene.getAllRole();
				if (allRole != null) {
					allRole.setPseudoRole(true);
					for (Paragraph paragraph : scene.getParagraphs()) {
						if (paragraph instanceof Passage) {
							Passage curPassage = (Passage) paragraph;
							Role passageRole = curPassage.getRole();
							if (!allRole.getAssigendRoles().contains(passageRole) && passageRole != allRole) {
								allRole.assignRole(passageRole);
							}
						}
					}
				}
			}
		}
	}
	
}
