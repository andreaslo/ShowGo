package de.feu.showgo.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static int countOccurrences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * This method removes all HTML-tags and trims the string.
	 * @return
	 */
	public static String sanitizeText(String input){
		input = input.replaceAll("<.*?>", "");
		input = input.trim();
		return input;
	}

	public static String sanitizeRoleName(String text) {
		text = text.trim();
		if(text.endsWith(".")){
			return text.substring(0, text.length()-1);
		}
		return text;
	}

	
	public static int wordCounter(String input){
		Pattern p = Pattern.compile("\\p{L}+", Pattern.DOTALL);
		Matcher m = p.matcher(input);
		int count = 0;
		while(m.find()){
			count++;
		}
		return count;
	}
	
}
