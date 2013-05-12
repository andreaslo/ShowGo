package de.feu.showgo.ui.listener;

import de.feu.showgo.model.Role;

/**
 * A callback that triggers if a role is deleted.
 */
public interface RoleDeleteListener {

	/**
	 * A callback that triggers if the provided role was deleted.
	 * 
	 * @param role
	 */
	public void deleteRole(Role role);

}
