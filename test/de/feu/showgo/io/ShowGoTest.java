package de.feu.showgo.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import de.feu.showgo.model.Gender;
import de.feu.showgo.model.Person;
import de.feu.showgo.model.ShowGo;
import de.feu.showgo.model.TheaterPlay;

public class ShowGoTest {

	@Test
	public void equalsTest() {
		Date birthday = new Date();
		
		ShowGo show1 = new ShowGo();
		Person p1 = new Person();
		p1.setName("TestName");
		p1.setGender(Gender.FEMALE);
		p1.setBirthday(birthday);
		p1.setWordsRetention(1234);
		show1.addPerson(p1);
		
		ShowGo show2 = new ShowGo();
		Person p2 = new Person();
		p2.setName("TestName");
		p2.setGender(Gender.FEMALE);
		p2.setBirthday(birthday);
		p2.setWordsRetention(1234);
		show2.addPerson(p2);
		
		assertEquals(show1, show2);
	}
	
	@Test
	public void equalsTestPlays() {
		ShowGo equal1 = new ShowGo();
		ShowGo equal2 = new ShowGo();
		ShowGo different = new ShowGo();
		
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
		
		equal1.addPlay(macBeth);
		equal2.addPlay(macBeth);
		different.addPlay(macShort);
		
		assertEquals(equal1, equal2);
		assertNotEquals(equal1, different);
		
	}

}
