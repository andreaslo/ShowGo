package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

public class Scene {

	private String name;
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
