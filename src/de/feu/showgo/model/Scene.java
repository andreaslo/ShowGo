package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

@XmlAccessorType(XmlAccessType.FIELD)
public class Scene {

	@XmlElement(name="name")
	private String name;

	@XmlElementWrapper( name = "paragraph" )
	@XmlElements( { 
	@XmlElement( name="stageDirection", type = StageDirection.class ),
	@XmlElement( name="passage", type = Passage.class) } )
	private List<Paragraph> paragraphs = new ArrayList<Paragraph>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}
	public void addParagraphs(Paragraph paragraph) {
		paragraphs.add(paragraph);
	}
	@Override
	public String toString() {
		return "Scene [name=" + name + ", paragraphs=" + paragraphs + "]\n";
	}
	
	
	
	
	
}
