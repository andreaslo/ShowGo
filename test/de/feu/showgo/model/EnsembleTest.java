package de.feu.showgo.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class EnsembleTest {

	@Test
	public void testCopyConstructor() {
		
		Person p1 = new Person();
		p1.setName("TestName");
		p1.setGender(Gender.FEMALE);
		p1.setBirthday(new Date());
		p1.setWordsRetention(1234);
		
		Person p2 = new Person();
		p2.setName("\"\"\"");
		p2.setGender(Gender.MALE);
		p2.setBirthday(new Date());
		p2.setWordsRetention(1234);
		
		Person p3 = new Person();
		p3.setName("<id>blibla</id>");
		p3.setGender(Gender.FEMALE);
		p3.setBirthday(new Date());
		p3.setWordsRetention(4433);
		
		Ensemble ensemble = new Ensemble();
		ensemble.assignPerson(p1);
		ensemble.assignPerson(p2);
		ensemble.assignPerson(p3);
		
		Ensemble copiedEnsemble = new Ensemble(ensemble);
		
		assertEquals(ensemble, copiedEnsemble);
		assertEquals(3, copiedEnsemble.getMembers().size());
		
		ensemble.removePerson(p1);
		
		assertNotEquals(ensemble,copiedEnsemble);
		assertEquals(3, copiedEnsemble.getMembers().size());
		assertEquals(2, ensemble.getMembers().size());
		
	}

}
