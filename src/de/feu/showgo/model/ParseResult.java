package de.feu.showgo.model;

/**
 * This class stores the results of parsing of play. It is stored whether the
 * input file is valid. If it is not, an error message and a line number is set.
 * 
 */
public class ParseResult {

	private boolean valid;
	private String errorMessage;
	private int errorLine;

	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Sets the valid.
	 *
	 * @param valid the new valid
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the error line.
	 *
	 * @return the error line
	 */
	public int getErrorLine() {
		return errorLine;
	}

	/**
	 * Sets the error line.
	 *
	 * @param errorLine the new error line
	 */
	public void setErrorLine(int errorLine) {
		this.errorLine = errorLine;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParseResult [valid=" + valid + ", errorMessage=" + errorMessage + ", errorLine=" + errorLine + "]";
	}

}
