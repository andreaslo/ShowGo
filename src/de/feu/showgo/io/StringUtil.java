package de.feu.showgo.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class containing methods for handling strings.
 * 
 */
public class StringUtil {

	/**
	 * This method counts the number of occurrences of a char int a String.
	 * 
	 * @param haystack
	 * @param needle
	 * @return
	 */
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
	 * 
	 * @return
	 */
	public static String sanitizeText(String input) {
		input = input.replaceAll("<.*?>", "");
		input = input.trim();
		return input;
	}

	/**
	 * This method replaces <br>
	 * tags with newlines, eliminates all other html tags and trims the string.
	 * 
	 * @param input
	 * @return
	 */
	public static String sanitzieParagraphText(String input) {
		input = input.replaceAll("<br>", "\n");
		return sanitizeText(input);
	}

	/**
	 * This method trims the string and removes dots at the end.
	 * 
	 * @param text
	 * @return
	 */
	public static String sanitizeRoleName(String text) {
		text = text.trim();
		if (text.endsWith(".")) {
			return text.substring(0, text.length() - 1);
		}
		return text;
	}

	/**
	 * This methods counts the number of words in a string by counting the number of matches of the regex
	 * \p{L}+
	 * 
	 * @param input
	 * @return
	 */
	public static int wordCounter(String input) {
		Pattern p = Pattern.compile("\\p{L}+", Pattern.DOTALL);
		Matcher m = p.matcher(input);
		int count = 0;
		while (m.find()) {
			count++;
		}
		return count;
	}

}
