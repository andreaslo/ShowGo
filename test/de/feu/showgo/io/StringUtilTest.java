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
}
