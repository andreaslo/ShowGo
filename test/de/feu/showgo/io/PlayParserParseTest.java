package de.feu.showgo.io;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import de.feu.showgo.model.ParseElement;

public class PlayParserParseTest {

	private static final Logger log = Logger.getLogger(PlayParserParseTest.class);
	private PlayParser parser;
	
	@Before
	public void setup() {
		parser = new PlayParser();
	}
	
	
	@Test
	public void parseMacShort(){
		String testData;
		try {
			testData = FileUtil.readFile(new File("testData" + System.getProperty("file.separator") + "Macshort.html"));
			ParseElement result = parser.parse(testData);
			log.debug(result.toString());
		} catch (IOException e) {
			log.error("",e);
		} catch (ParsingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void simpleParsingTest(){
		String testData = 	"<!--passage-->erster text <!--rolle-->1. HEXE.<!--rolle-->2. HEXE.<!--/rolle--><!--/rolle-->weiterer text <!--rolle-->1. HEXE.<!--/rolle--> letzer text in passage<!--/passage-->";
		ParseElement result;
		try {
			result = parser.parse(testData);
			log.debug(result.toString());
		} catch (ParsingException e) {
			e.printStackTrace();
		}
		
		
		
	
	}

}
