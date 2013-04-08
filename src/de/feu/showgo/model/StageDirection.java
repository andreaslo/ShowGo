package de.feu.showgo.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StageDirection extends Paragraph {

	@XmlElement(name="direction")
	private String direction;

	public String getText() {
		return direction;
	}

	public void setText(String text) {
		this.direction = text;
	}

	@Override
	public String toString() {
		return "StageDirection [text=" + direction + "]\n";
	}
	
	
	
}
