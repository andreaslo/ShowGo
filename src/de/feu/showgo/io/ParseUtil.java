package de.feu.showgo.io;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import de.feu.showgo.model.Person;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.TheaterPlay;

/**
 * A utility class providing methods for parsing plays.
 */
public class ParseUtil {

	/**
	 * This method creates a copy of a TheaterPlay object by marshalling and unamrshalling it using JAXB.
	 * 
	 * @param play
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static TheaterPlay copyPlay(TheaterPlay play) throws JAXBException, IOException {
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(TheaterPlay.class);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(play, sw);

		TheaterPlay play2 = JAXB.unmarshal(new StringReader(sw.toString()), TheaterPlay.class);

		/*
		 * The cast references have to be set manually. The list is empty by
		 * default. The cast is serialized as XMLIdRef to a Person. Persons are
		 * not stored inside the play. So on deserialization the pointers point
		 * into the void and are ignored.
		 */
		for (int i = 0; i < play.getRoles().size(); i++) {
			Role role = play.getRoles().get(i);
			if(role.getCast() != null){
				play2.getRoles().get(i).setCast(new ArrayList<Person>(role.getCast()));
			}
		}

		return play2;
	}

}
