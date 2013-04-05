package de.feu.showgo.model;

public class ParseResult {

	private boolean valid;
	private String errorMessage;
	private int errorLine;
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getErrorLine() {
		return errorLine;
	}
	public void setErrorLine(int errorLine) {
		this.errorLine = errorLine;
	}
	
	@Override
	public String toString() {
		return "ParseResult [valid=" + valid + ", errorMessage=" + errorMessage
				+ ", errorLine=" + errorLine + "]";
	}
	
	
}
