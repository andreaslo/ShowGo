package de.feu.showgo.io;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;

public class PseudoRoleRecognition {

	private static final Logger log = Logger.getLogger(PseudoRoleRecognition.class);
	
	public void recognizePseudoRoles(TheaterPlay play) {
		log.debug("recognizing pseudo roles");

		for (Role curRole : play.getRoles()) {
			log.debug("Recognizing role " + curRole);

			Pattern p = Pattern.compile("(\\w+) und (\\w+)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(curRole.getName());

			if (m.find()) {
				log.debug("Pattern and matches");
				String personName1 = m.group(1);
				String personName2 = m.group(2);
				log.debug("person 1: " + personName1);
				log.debug("person 2: " + personName2);

				Role role1 = findRoleByName(play.getRoles(), personName1);

				Role role2 = findRoleByName(play.getRoles(), personName2);

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
			}

		}

	}

	private Role findRoleByName(List<Role> haystack, String needle) {
		for (Role curRole : haystack) {
			if (curRole.getName().equals(needle)) {
				return curRole;
			}
		}
		return null;
	}
	
}
