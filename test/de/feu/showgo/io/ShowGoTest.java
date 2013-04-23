package de.feu.showgo.io;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import de.feu.showgo.model.Gender;
import de.feu.showgo.model.Person;
import de.feu.showgo.model.ShowGo;

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

}
