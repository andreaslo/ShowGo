package de.feu.showgo.io;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;

public class PseudoRoleMarshallingTest {

	private final static Logger log = Logger.getLogger(PseudoRoleMarshallingTest.class);
	
	@Test
	public void test() {

		Role child1 = new Role();
		child1.setName("role 1");
		Role child2 = new Role();
		child2.setName("role 2");
		Role pseudoRole = new Role();
		pseudoRole.setName("pseudo role");
		pseudoRole.setPseudoRole(true);
		pseudoRole.assignRole(child1);
		pseudoRole.assignRole(child2);

		List<Role> roleList = new ArrayList<Role>();
		roleList.add(child1);
		roleList.add(child2);
		roleList.add(pseudoRole);

		TheaterPlay play = new TheaterPlay();
		play.setName("test play");
		play.setRoles(roleList);

		try {
			StringWriter sw = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(TheaterPlay.class);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(play, sw);

			System.out.println(sw.toString());
			
			 TheaterPlay play2 = JAXB.unmarshal(new StringReader(sw.toString()),
			 TheaterPlay.class);
			
			 assertEquals(play, play2);
			 assertEquals(3, play2.getRoles().size());
			 
			 boolean found = false;
			 for(Role role : play2.getRoles()){
				 if("pseudo role".equals(role.getName())){
					 found = true;
					 assertTrue(role.isPseudoRole());
					 assertEquals(2, role.getAssigendRoles().size());
				 }
			 }
			 assertTrue(found);
		} catch (JAXBException e) {
			log.error("",e);
		}



	}

}
