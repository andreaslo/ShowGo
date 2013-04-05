package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;



public class ParseElement {

	private String tagName;
	private String text;
	private List<ParseElement> children;
	
	public ParseElement() {
		children = new ArrayList<ParseElement>();
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<ParseElement> getChildren() {
		return children;
	}
	public void addChild(ParseElement child) {
		children.add(child);
	}

	@Override
	public String toString() {
		return "[tagName=" + tagName + ", text=" + text
				+ ", children=" + children + "]";
	}
	
	
	
}
