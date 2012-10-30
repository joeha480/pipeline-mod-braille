package org.daisy.pipeline.braille.liblouis.saxon;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.tree.iter.SingletonIterator;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;

import org.daisy.pipeline.braille.liblouis.LiblouisTableFinder;

@SuppressWarnings("serial")
public class FindTableDefinition extends ExtensionFunctionDefinition {

	private static final StructuredQName funcname = new StructuredQName("louis",
			"http://liblouis.org/liblouis", "find-table");

	private LiblouisTableFinder tableFinder = null;
	
	public void bindTableFinder(LiblouisTableFinder tableFinder) {
		this.tableFinder = tableFinder;
	}

	public void unbindTableFinder(LiblouisTableFinder tableFinder) {
		this.tableFinder = null;
	}
	
	@Override
	public StructuredQName getFunctionQName() {
		return funcname;
	}

	@Override
	public int getMinimumNumberOfArguments() {
		return 1;
	}

	@Override
	public int getMaximumNumberOfArguments() {
		return 1;
	}

	@Override
	public SequenceType[] getArgumentTypes() {
		return new SequenceType[] { SequenceType.SINGLE_STRING };
	}

	@Override
	public SequenceType getResultType(SequenceType[] suppliedArgumentTypes) {
		return SequenceType.OPTIONAL_STRING;
	}

	@Override
	public ExtensionFunctionCall makeCallExpression() {

		return new ExtensionFunctionCall() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public SequenceIterator call(SequenceIterator[] arguments,
					XPathContext context) throws XPathException {

				StringValue locale = (StringValue) arguments[0].next();
				if (locale != null) {
					String table = tableFinder.find(locale.getStringValue()).toExternalForm();
					if (table != null)
						return SingletonIterator.makeIterator(new StringValue(table)); }
				return EmptyIterator.getInstance();
			}
		};
	}
}