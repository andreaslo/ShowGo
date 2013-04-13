package de.feu.showgo.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import de.feu.showgo.Main;
import de.feu.showgo.model.ParseResult;

public class PlayParserValidationTest {

	private static final Logger log = Logger.getLogger(PlayParserValidationTest.class);
	private PlayParser parser;
	
	@Before
	public void setup() {
		parser = new PlayParser();
	}
	
	@Test
	public void simpleData1() {
		String testData = "<!--passage--><!--/passage-->";
		ParseResult result = parser.validate(testData);
		assertTrue(result.isValid());
	}
	
	@Test
	public void simpleData2() {
		String testData = 	"<!--passage--><!--rolle-->2. HEXE.<!--/rolle--><br>\n"
							+ "Das thu!<br>\n"
							+"Ich geb' dir einen Wind dazu.<br>\n"
							+"<br>\n"
							+"<!--/passage-->\n";
		ParseResult result = parser.validate(testData);
		assertTrue(result.isValid());
	}

	@Test
	public void validateMacShort(){
		String testData;
		try {
			testData = FileUtil.readFile(new File("testData" + System.getProperty("file.separator") + "Macshort.html"));
			ParseResult result = parser.validate(testData);
			assertTrue(result.isValid());
		} catch (IOException e) {
			log.error("",e);
		}
	}
	
	@Test
	public void validateMacBeth(){
		String testData;
		try {
			testData = FileUtil.readFile(new File("testData" + System.getProperty("file.separator") + "Macbeth.html"));
			ParseResult result = parser.validate(testData);
			assertTrue(result.isValid());
		} catch (IOException e) {
			log.error("",e);
		}
	}
	
	@Test
	public void simpleDataFail1() {
		String testData = 	"<!--passage-->\n<!--/wasanderes-->";
		ParseResult result = parser.validate(testData);
		assertFalse(result.isValid());
		assertEquals(result.getErrorLine(), 2);
		assertEquals("No closing tag for passage", result.getErrorMessage());
	}
	
	@Test
	public void simpleDataFail2() {
		String testData = 	"<!--passage-->\n<!--/wasanderes-->\n<!--/passage-->";
		ParseResult result = parser.validate(testData);
		assertFalse(result.isValid());
		assertEquals(result.getErrorLine(), 2);
		assertEquals("No closing tag for passage", result.getErrorMessage());
	}
	
	@Test
	public void simpleDataFail3() {
		String testData = 	"<!--drumrum-->\n<!--passage-->\n<!--wasanderes-->\n<!--/wasanderes-->\n<!--/passage-->";
		ParseResult result = parser.validate(testData);
		assertFalse(result.isValid());
		assertEquals(result.getErrorLine(), 0);
		assertEquals("No closing tag for drumrum", result.getErrorMessage());
	}
	
	@Test
	public void simpleDataFail4() {
		String testData = 	"<!--/passage-->";
		ParseResult result = parser.validate(testData);
		assertFalse(result.isValid());
		assertEquals(result.getErrorLine(), 1);
		assertEquals("No opening tag for passage", result.getErrorMessage());
	}
	
	@Test
	public void simpleDataFail5() {
		String testData = 	"<!--passage-->\n<!--wasanderes-->\n<!--/wasanderes-->\n<!--/passage-->\n<!--/drumrum-->\n";
		ParseResult result = parser.validate(testData);
		assertFalse(result.isValid());
		assertEquals(result.getErrorLine(), 5);
		assertEquals("No opening tag for drumrum", result.getErrorMessage());
	}
	
	@Test
	public void simpleDataFail6() {
		String testData = 	"<!--wasanderes-->\n<!--passage-->\n<!--/wasanderes-->\n<!--/passage-->";
		ParseResult result = parser.validate(testData);
		assertFalse(result.isValid());
		assertEquals(result.getErrorLine(), 3);
		assertEquals("No closing tag for passage", result.getErrorMessage());
	}
	
	@Test
	public void twoRootElements() {
		String testData = 	"<!--root1-->\n<!--/root1-->\n<!--root2-->\n<!--/root2-->";
		ParseResult result = parser.validate(testData);
		assertFalse(result.isValid());
		assertEquals(result.getErrorLine(), 3);
		assertEquals("Second root element found: root2", result.getErrorMessage());
	}
	

	
}
