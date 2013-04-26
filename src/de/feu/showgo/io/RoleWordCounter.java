package de.feu.showgo.io;

import de.feu.showgo.model.Act;
import de.feu.showgo.model.Paragraph;
import de.feu.showgo.model.Passage;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.Scene;
import de.feu.showgo.model.TheaterPlay;

public class RoleWordCounter {

	public void updateRoleWords(TheaterPlay play){
		for(Role role : play.getRoles()){
			role.setWords(0);
		}
		
		for(Act act : play.getActs()){
			for(Scene scene : act.getScenes()){
				for(Paragraph paragraph : scene.getParagraphs()){
					if(paragraph instanceof Passage){
						Passage passage = (Passage) paragraph;
						Role passageRole = passage.getRole();
						int textCount = StringUtil.wordCounter(passage.getText());
						if(passageRole.isPseudoRole()){
							for(Role assignedRole : passageRole.getAssigendRoles()){
								assignedRole.setWords(passageRole.getWords() + textCount);
							}
						}else{
							passageRole.setWords(passageRole.getWords() + textCount);
						}
					}
				}
			}
		}
	}
	
}
