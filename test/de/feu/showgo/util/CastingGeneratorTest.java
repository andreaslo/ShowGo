package de.feu.showgo.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import de.feu.showgo.io.ParsingException;
import de.feu.showgo.io.PlayParser;
import de.feu.showgo.model.Ensemble;
import de.feu.showgo.model.Gender;
import de.feu.showgo.model.Person;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;

public class CastingGeneratorTest {

	@BeforeClass
	public static void setUp(){
		BasicConfigurator.configure();
	}
	
	@Test
	public void test() {
		
		PlayParser parser = new PlayParser();
		File inputFile = new File("testData" + System.getProperty("file.separator") + "Macshort.html");
		TheaterPlay play = null;
		try {
			play = parser.generatePlay(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParsingException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(play);
		
		Ensemble ensemble = new Ensemble();
		
		Person person = new Person();
		person.setGender(Gender.MALE);
		person.setName("Hans Schlegel");
		person.setWordsRetention(3000);
		Calendar c = Calendar.getInstance();
		c.set(1978, 8, 3);
		person.setBirthday(c.getTime());
		ensemble.assignPerson(person);
		
		person = new Person();
		person.setGender(Gender.FEMALE);
		person.setName("Ada Bryon");
		person.setWordsRetention(2000);
		c.set(1979, 12, 10);
		person.setBirthday(c.getTime());
		ensemble.assignPerson(person);

		person = new Person();
		person.setGender(Gender.FEMALE);
		person.setName("Elisa Meitner");
		person.setWordsRetention(500);
		c.set(1978, 11, 07);
		person.setBirthday(c.getTime());
		ensemble.assignPerson(person);
		
		person = new Person();
		person.setGender(Gender.FEMALE);
		person.setName("Kristin Nüsslein");
		person.setWordsRetention(2000);
		c.set(1982, 10, 20);
		person.setBirthday(c.getTime());
		ensemble.assignPerson(person);
		
		person = new Person();
		person.setGender(Gender.MALE);
		person.setName("Bert Koch");
		person.setWordsRetention(800);
		c.set(1973, 12, 11);
		person.setBirthday(c.getTime());
		ensemble.assignPerson(person);
		
		person = new Person();
		person.setGender(Gender.MALE);
		person.setName("Fips Reis");
		person.setWordsRetention(200);
		c.set(1996, 01, 07);
		person.setBirthday(c.getTime());
		ensemble.assignPerson(person);
		
		person = new Person();
		person.setGender(Gender.MALE);
		person.setName("Jon Neumann");
		person.setWordsRetention(5);
		c.set(1953, 12, 28);
		person.setBirthday(c.getTime());
		ensemble.assignPerson(person);
		
		CastingGenerator.generateCasting(ensemble, play);
		
		for(Role role : play.getRoles()){
			if(role.getName().equals("MACBETH")){
				assertNotNull(role.getCast());
				assertEquals(role.getCast().size(), 1);
				assertEquals(role.getCast().get(0), ensemble.getMembers().get(0));
			}
		}
	}

}
