package de.feu.showgo.ui.listener;

import de.feu.showgo.model.Person;

/**
 * A callback for changes on a person.
 */
public interface PersonEvent {

	/**
	 * The person that was changed.
	 * 
	 * @param person
	 */
	public void personEvent(Person person);

}
