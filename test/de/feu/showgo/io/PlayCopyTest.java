package de.feu.showgo.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import de.feu.showgo.model.ParseElement;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;

public class PlayCopyTest {

	
	private static final Logger log = Logger.getLogger(PlayCopyTest.class);
	private PlayParser parser;
	
	@Before
	public void setup() {
		parser = new PlayParser();
	}
	
	@Test
	public void testMacShortEquals(){
		try {
			File inputFile = new File("testData" + System.getProperty("file.separator") + "Macshort.html");
			ParseElement root = parser.parse(FileUtil.readFile(inputFile));
			TheaterPlay play1 = parser.generatePlay(root);
			
			TheaterPlay play2 = parser.generatePlay(inputFile);
			
			assertEquals(play1, play2);
			
		} catch (IOException e) {
			log.error("",e);
			fail();
		} catch (ParsingException e) {
			log.error("",e);
			fail();
		}
	}

	@Test
	public void testMacShortCopy(){
		try {
			File inputFile = new File("testData" + System.getProperty("file.separator") + "Macshort.html");			
			TheaterPlay play1 = parser.generatePlay(inputFile);
			TheaterPlay play2 = null;
			try {
				play2 = ParseUtil.copyPlay(play1);
			} catch (JAXBException e) {
				log.error("",e);
				fail();
			}
			
			assertEquals(play1, play2);
			
			Role additionalRole = new Role();
			additionalRole.setName("something");
			play1.getRoles().add(additionalRole);
			assertNotEquals(play1, play2);
		} catch (IOException e) {
			log.error("",e);
			fail();
		} catch (ParsingException e) {
			log.error("",e);
			fail();
		}
	}
	
	@Test
	public void testMacBethCopy(){
		try {
			File inputFile = new File("testData" + System.getProperty("file.separator") + "Macbeth.html");			
			TheaterPlay play1 = parser.generatePlay(inputFile);
			TheaterPlay play2 = null;
			try {
				play2 = ParseUtil.copyPlay(play1);
			} catch (JAXBException e) {
				log.error("",e);
				fail();
			}
			
			assertEquals(play1, play2);
			
		} catch (IOException e) {
			log.error("",e);
			fail();
		} catch (ParsingException e) {
			log.error("",e);
			fail();
		}
	}
	
	
}
