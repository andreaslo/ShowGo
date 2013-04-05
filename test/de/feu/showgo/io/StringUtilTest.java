package de.feu.showgo.io;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testChar() {
		int count = StringUtil.countOccurrences("abcacbccbca", 'a');
		assertEquals(count, 3);
	}

	@Test
	public void testWithNewline() {
		int count = StringUtil.countOccurrences("abca\ncbccbca", 'a');
		assertEquals(count, 3);
	}
	
	@Test
	public void testNewline() {
		int count = StringUtil.countOccurrences("ab\nca\ncbc\ncbca", '\n');
		assertEquals(count, 3);
	}
}
