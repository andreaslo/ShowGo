package de.feu.showgo.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;

public class RecognizePseudoTest {

	@Test
	public void testAndKeyword() {
		
		TheaterPlay play = new TheaterPlay();
		Role pseudoRole = new Role();
		pseudoRole.setName("MACBETH UND LENOX");
		Role macbeth = new Role();
		macbeth.setName("MACBETH");
		Role lenox = new Role();
		lenox.setName("LENOX");
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(macbeth);
		roles.add(lenox);
		roles.add(pseudoRole);
		
		PlayParser parser = new PlayParser();
		parser.recognizePseudoRoles(play);
		
		assertTrue(pseudoRole.isPseudoRole());
		assertTrue(pseudoRole.getAssigendRoles().contains(macbeth));
		assertTrue(pseudoRole.getAssigendRoles().contains(lenox));
		assertFalse(macbeth.isPseudoRole());
		assertFalse(macbeth.isPseudoRole());
		assertEquals(pseudoRole.getAssigendRoles().size(), 2);		
	}

}
