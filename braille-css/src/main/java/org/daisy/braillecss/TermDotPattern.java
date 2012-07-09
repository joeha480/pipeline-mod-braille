package org.daisy.braillecss;

import cz.vutbr.web.css.TermIdent;
import cz.vutbr.web.csskit.TermImpl;

import java.io.UnsupportedEncodingException;

public class TermDotPattern extends TermImpl<Character> {

	private TermDotPattern() {}

	@Override
	public TermDotPattern setValue(Character value) {
		if (value == null) {
			throw new IllegalArgumentException(
					"Invalid value for TermDotPattern(null)");
		}
		//if (value != braille) { throw new IllegalArgumentException("Invalid value for TermDotPattern(" + value + ")"); }
		this.value = value;
		return this;
	}
	
	public static TermDotPattern createDotPattern(TermIdent ident) {
		TermDotPattern pattern = new TermDotPattern();
		String value = ident.getValue();
		try {
			// Mac OS: re-encode String
			value = new String(value.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		if (value.length() != 1) {
			throw new IllegalArgumentException(
					"Invalid value for TermDotPattern(" + value + ")");
		}
		pattern.setValue(value.charAt(0));
		return pattern;
	}
}
