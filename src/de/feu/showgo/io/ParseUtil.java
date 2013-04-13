package de.feu.showgo.io;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import de.feu.showgo.model.TheaterPlay;

public class ParseUtil {

	public static TheaterPlay copyPlay(TheaterPlay play) throws JAXBException, IOException {		
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(TheaterPlay.class);
		
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(play, sw);
		
		TheaterPlay play2 = JAXB.unmarshal(new StringReader(sw.toString()), TheaterPlay.class);
		return play2;
	}

}
