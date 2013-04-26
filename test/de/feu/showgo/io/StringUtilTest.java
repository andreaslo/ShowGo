package de.feu.showgo.io;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testCountOccurrencesChar() {
		int count = StringUtil.countOccurrences("abcacbccbca", 'a');
		assertEquals(count, 3);
	}

	@Test
	public void testCountOccurrencesWithNewline() {
		int count = StringUtil.countOccurrences("abca\ncbccbca", 'a');
		assertEquals(count, 3);
	}
	
	@Test
	public void testCountOccurrencesNewline() {
		int count = StringUtil.countOccurrences("ab\nca\ncbc\ncbca", '\n');
		assertEquals(count, 3);
	}
	
	@Test
	public void testSanitizeRoleName() {
		assertEquals("MACBETH", StringUtil.sanitizeRoleName("MACBETH."));
		assertEquals("MACBETH", StringUtil.sanitizeRoleName("MACBETH"));
	}
	
	@Test
	public void testCount() {
		assertEquals(4, StringUtil.wordCounter("Dies sind vier Wörter."));
	}
	
	@Test
	public void testCount2() {
		assertEquals(100, StringUtil.wordCounter("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."));
	}
}
