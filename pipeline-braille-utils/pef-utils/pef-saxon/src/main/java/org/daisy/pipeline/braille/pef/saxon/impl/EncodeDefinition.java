package org.daisy.pipeline.braille.pef.saxon.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.AtomicSequence;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;

import org.daisy.braille.api.table.Table;
import org.daisy.pipeline.braille.common.Provider;
import static org.daisy.pipeline.braille.common.Provider.util.dispatch;
import static org.daisy.pipeline.braille.common.Provider.util.memoize;
import org.daisy.pipeline.braille.pef.TableProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
	name = "pef:encode",
	service = { ExtensionFunctionDefinition.class }
)
@SuppressWarnings("serial")
public class EncodeDefinition extends ExtensionFunctionDefinition {
	
	private static final StructuredQName funcname = new StructuredQName("pef",
			"http://www.daisy.org/ns/2008/pef", "encode");
	
	@Reference(
		name = "TableProvider",
		unbind = "unbindTableProvider",
		service = TableProvider.class,
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void bindTableProvider(TableProvider provider) {
		tableProviders.add(provider);
	}
	
	protected void unbindTableProvider(TableProvider provider) {
		tableProviders.remove(provider);
		this.tableProvider.invalidateCache();
	}
	
	private List<TableProvider> tableProviders = new ArrayList<TableProvider>();
	private Provider.MemoizingProvider<String,Table> tableProvider
	= memoize(dispatch(tableProviders));
	
	@Override
	public StructuredQName getFunctionQName() {
		return funcname;
	}
	
	@Override
	public int getMinimumNumberOfArguments() {
		return 2;
	}
	
	@Override
	public int getMaximumNumberOfArguments() {
		return 2;
	}
	
	@Override
	public SequenceType[] getArgumentTypes() {
		return new SequenceType[] {
				SequenceType.SINGLE_STRING,
				SequenceType.SINGLE_STRING};
	}
	
	@Override
	public SequenceType getResultType(SequenceType[] suppliedArgumentTypes) {
		return SequenceType.OPTIONAL_STRING;
	}
	
	@Override
	public ExtensionFunctionCall makeCallExpression() {
		return new ExtensionFunctionCall() {
			public Sequence call(XPathContext context, Sequence[] arguments) throws XPathException {
				try {
					String tableQuery = ((AtomicSequence)arguments[0]).getStringValue();
					String braille = ((AtomicSequence)arguments[1]).getStringValue();
					try {
						Table table = tableProvider.get(tableQuery).iterator().next();
						return new StringValue(table.newBrailleConverter().toText(braille)); }
					catch (NoSuchElementException e) {
						throw new RuntimeException("Could not find a table for query: " + tableQuery); }}
				catch (Exception e) {
					logger.error("pef:encode failed", e);
					throw new XPathException("pef:encode failed"); }
			}
		};
	}
	
	private static final Logger logger = LoggerFactory.getLogger(EncodeDefinition.class);
	
}