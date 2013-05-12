package de.feu.showgo.io;

import de.feu.showgo.model.Act;
import de.feu.showgo.model.Paragraph;
import de.feu.showgo.model.Passage;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.Scene;
import de.feu.showgo.model.TheaterPlay;

/**
 * A class containing logic for counting the number of words a role has to
 * remember.
 */
public class RoleWordCounter {

	/**
	 * This methods recursively walks though a TheaterPlay object and counts the
	 * number of words a role has to learn. Text of pseudo roles is added to
	 * every assigned role. The number of words is counted using the regex
	 * \\p{L}+ in dotall mode.
	 * 
	 * @param play
	 */
	public void updateRoleWords(TheaterPlay play) {
		for (Role role : play.getRoles()) {
			role.setWords(0);
		}

		for (Act act : play.getActs()) {
			for (Scene scene : act.getScenes()) {
				for (Paragraph paragraph : scene.getParagraphs()) {
					if (paragraph instanceof Passage) {
						Passage passage = (Passage) paragraph;
						Role passageRole = passage.getRole();
						int textCount = StringUtil.wordCounter(passage.getText());
						if (passageRole.isPseudoRole()) {
							for (Role assignedRole : passageRole.getAssigendRoles()) {
								assignedRole.setWords(assignedRole.getWords() + textCount);
							}
						} else {
							passageRole.setWords(passageRole.getWords() + textCount);
						}
					}
				}
			}
		}
	}

}
