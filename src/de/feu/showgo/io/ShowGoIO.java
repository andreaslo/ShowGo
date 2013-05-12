package de.feu.showgo.io;

import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import de.feu.showgo.model.ShowGo;

/**
 * A utility class for loading and saving ShowGo model objects to and from disc.
 * 
 */
public class ShowGoIO {

	/**
	 * This method marshalles a ShowGo Object to XML using JAXB and writes it
	 * into the given file.
	 * 
	 * @param showGoObject
	 * @param target
	 * @throws JAXBException
	 */
	public static void saveShowGo(ShowGo showGoObject, File target) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ShowGo.class);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(showGoObject, target);
	}

	/**
	 * This methods loads the marshalled XML from a file and unmarshalles it
	 * into a XML object using JAXB.
	 * 
	 * @param location
	 * @return
	 */
	public static ShowGo loadShowGo(File location) {
		return JAXB.unmarshal(location, ShowGo.class);
	}

}
