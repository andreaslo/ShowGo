package de.feu.showgo.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;

public class RecognizePseudoTest {

	@BeforeClass
	public static void setUp(){
		BasicConfigurator.configure();
	}
	
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
		
		play.setRoles(roles);
		PseudoRoleRecognition recognizer = new PseudoRoleRecognition();
		recognizer.recognizePseudoRoles(play);
		
		assertTrue(pseudoRole.isPseudoRole());
		assertTrue(pseudoRole.getAssigendRoles().contains(macbeth));
		assertTrue(pseudoRole.getAssigendRoles().contains(lenox));
		assertFalse(macbeth.isPseudoRole());
		assertFalse(macbeth.isPseudoRole());
		assertEquals(pseudoRole.getAssigendRoles().size(), 2);		
	}

	@Test
	public void testThreeWitches() {
		
		TheaterPlay play = new TheaterPlay();
		Role pseudoRole = new Role();
		pseudoRole.setName("1., 2. UND 3. HEXE");
		Role firstWitch = new Role();
		firstWitch.setName("1. HEXE");
		Role secondWitch = new Role();
		secondWitch.setName("2. HEXE");
		Role thirdWitch = new Role();
		thirdWitch.setName("3. HEXE");
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(firstWitch);
		roles.add(secondWitch);
		roles.add(thirdWitch);
		roles.add(pseudoRole);
		
		play.setRoles(roles);
		PseudoRoleRecognition recognizer = new PseudoRoleRecognition();
		recognizer.recognizePseudoRoles(play);
		
		assertTrue(pseudoRole.isPseudoRole());
		assertTrue(pseudoRole.getAssigendRoles().contains(firstWitch));
		assertTrue(pseudoRole.getAssigendRoles().contains(secondWitch));
		assertTrue(pseudoRole.getAssigendRoles().contains(thirdWitch));
		assertFalse(firstWitch.isPseudoRole());
		assertFalse(secondWitch.isPseudoRole());
		assertFalse(thirdWitch.isPseudoRole());
		assertEquals(pseudoRole.getAssigendRoles().size(), 3);		
	}
	
}
