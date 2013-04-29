package de.feu.showgo.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.feu.showgo.model.Gender;
import de.feu.showgo.model.Person;
import de.feu.showgo.model.ShowGo;
import de.feu.showgo.model.TheaterPlay;

public class ShowGoIOTest {

	private static final Logger log = Logger.getLogger(PlayParserValidationTest.class);
	
	@Test
	public void simpleLoadStore() {
		ShowGo saveObj = new ShowGo();
		Person p1 = new Person();
		p1.setName("TestName");
		p1.setGender(Gender.FEMALE);
		p1.setBirthday(new Date());
		p1.setWordsRetention(1234);
		saveObj.addPerson(p1);
		
		try {
			File tempFile = File.createTempFile("showgo", "xml");
			ShowGoIO.saveShowGo(saveObj, tempFile);
			ShowGo loadObj = ShowGoIO.loadShowGo(tempFile);
			assertEquals(1, loadObj.getPersons().size());
			assertEquals(saveObj, loadObj);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void assignedPlaysLoadStore() {
		ShowGo saveObj = new ShowGo();
		Person p1 = new Person();
		p1.setName("TestName");
		p1.setGender(Gender.FEMALE);
		p1.setBirthday(new Date());
		p1.setWordsRetention(1234);
		saveObj.addPerson(p1);
		
		PlayParser parser = new PlayParser();
		File macBethInput = new File("testData" + System.getProperty("file.separator") + "Macbeth.html");
		File macShortInput = new File("testData" + System.getProperty("file.separator") + "Macshort.html");
		TheaterPlay macBeth = null;
		TheaterPlay macShort = null;
		try {
			macBeth = parser.generatePlay(macBethInput);
			macShort = parser.generatePlay(macShortInput);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParsingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(macBeth);
		assertNotNull(macShort);
		saveObj.addPlay(macShort);
		saveObj.addPlay(macBeth);
		
		try {
			File tempFile = File.createTempFile("showgo", "xml");
			ShowGoIO.saveShowGo(saveObj, tempFile);
			ShowGo loadObj = ShowGoIO.loadShowGo(tempFile);
			assertEquals(1, loadObj.getPersons().size());
			assertEquals(saveObj, loadObj);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
