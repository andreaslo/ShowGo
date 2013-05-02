package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class Scene {

	@XmlElement(name="name")
	private String name;

	@XmlElementWrapper( name = "paragraph" )
	@XmlElements( { 
	@XmlElement( name="stageDirection", type = StageDirection.class ),
	@XmlElement( name="passage", type = Passage.class) } )
	private List<Paragraph> paragraphs = new ArrayList<Paragraph>();
	
	@XmlElement(name="allRole")
	private Role allRole;
	
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((paragraphs == null) ? 0 : paragraphs.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scene other = (Scene) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (paragraphs == null) {
			if (other.paragraphs != null)
				return false;
		} else if (!paragraphs.equals(other.paragraphs))
			return false;
		return true;
	}
	public Role getAllRole() {
		return allRole;
	}
	public void setAllRole(Role allRole) {
		this.allRole = allRole;
	}
	public void deleteParagraph(Paragraph paragraph) {
		paragraphs.remove(paragraph);
	}
	
	
	
	
	
}
