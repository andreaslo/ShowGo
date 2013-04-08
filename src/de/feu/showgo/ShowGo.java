package de.feu.showgo;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import de.feu.showgo.io.FileUtil;
import de.feu.showgo.io.ParsingException;
import de.feu.showgo.io.PlayParser;
import de.feu.showgo.model.ParseElement;
import de.feu.showgo.model.TheaterPlay;

public class ShowGo {

	private static final Logger log = Logger.getLogger(ShowGo.class);
	
	public static void main(String[] args){
		String testData;
		try {
			testData = FileUtil.readFile(new File("testData" + System.getProperty("file.separator") + "Macshort.html"));
			
			PlayParser parser = new PlayParser();
			ParseElement root = parser.parse(testData);
			TheaterPlay play = parser.generatePlay(root);
			
			//log.debug(play.toString());
			
			JAXBContext context = JAXBContext.newInstance(TheaterPlay.class);

		    Marshaller m = context.createMarshaller();
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		    File file = File.createTempFile( "play-jaxb-", ".xml" );
		    m.marshal(play, file);
		    TheaterPlay play2 = JAXB.unmarshal( file, TheaterPlay.class );
		    System.out.println(play2);
			
		} catch (IOException e) {
			log.error("",e);
		} catch (ParsingException e) {
			log.error("",e);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
