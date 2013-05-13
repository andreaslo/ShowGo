package de.feu.showgo.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import de.feu.showgo.model.ParseElement;
import de.feu.showgo.model.TheaterPlay;

public class PlayParserGenerationTest {

	private static final Logger log = Logger.getLogger(PlayParserGenerationTest.class);
	private PlayParser parser;
	
	@Before
	public void setup() {
		parser = new PlayParser();
	}
	
	@Test
	public void testMacShort(){

		String testData;
		try {
			testData = FileUtil.readFile(new File("testData" + System.getProperty("file.separator") + "Macshort.html"));
			ParseElement root = parser.parse(testData);
			TheaterPlay play = parser.generatePlay(root);
			
			log.debug(play.toString());
			
			assertEquals(play.getName(), "Macshort");
			assertEquals(play.getActs().size(), 4);
			
		} catch (IOException e) {
			log.error("",e);
			fail();
		} catch (ParsingException e) {
			log.error("",e);
			fail();
		}

	}
	
	@Test
	public void testMacBeth(){

		String testData;
		try {
			testData = FileUtil.readFile(new File("testData" + System.getProperty("file.separator") + "Macbeth.html"));
			ParseElement root = parser.parse(testData);
			TheaterPlay play = parser.generatePlay(root);
			
			log.debug("done parsing");
			log.debug(play.toString());
			log.debug("done printing");
			
			assertEquals(play.getName(), "Macbeth");
			assertEquals(play.getActs().size(), 5);
			
		} catch (IOException e) {
			log.error("",e);
			fail();
		} catch (ParsingException e) {
			log.error("",e);
			fail();
		}

	}
	
}
