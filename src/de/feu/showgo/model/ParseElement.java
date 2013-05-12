package de.feu.showgo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An element in a parsed dom tree. It contains a tag name, a text and a list of
 * children which is empty for leafs.
 * 
 */
public class ParseElement {

	private String tagName;
	private String text;
	private List<ParseElement> children;

	/**
	 * Instantiates a new parses the element.
	 */
	public ParseElement() {
		children = new ArrayList<ParseElement>();
	}

	/**
	 * Gets the tag name.
	 *
	 * @return the tag name
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * Sets the tag name.
	 *
	 * @param tagName the new tag name
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<ParseElement> getChildren() {
		return children;
	}

	/**
	 * Adds a child.
	 *
	 * @param child the child
	 */
	public void addChild(ParseElement child) {
		children.add(child);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[tagName=" + tagName + ", text=" + text + ", children=" + children + "]";
	}

}
