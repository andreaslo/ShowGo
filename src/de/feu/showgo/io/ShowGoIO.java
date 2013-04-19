package de.feu.showgo.io;

import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import de.feu.showgo.model.ShowGo;
import de.feu.showgo.model.TheaterPlay;

/**
 * A utility class for loading and saving ShowGo model objects.
 *
 */
public class ShowGoIO {

	public static void saveShowGo(ShowGo showGoObject, File target) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(ShowGo.class);
		
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(showGoObject, target);
	}
	
	public static ShowGo loadShowGo(File location){
		return JAXB.unmarshal(location, ShowGo.class);
	}


	
}
