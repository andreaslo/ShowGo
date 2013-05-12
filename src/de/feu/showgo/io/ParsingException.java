package de.feu.showgo.io;

/**
 * A custom exception that is thrown during parsing of a play input file.
 */
public class ParsingException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParsingException(String message) {
		super(message);
	}

}
