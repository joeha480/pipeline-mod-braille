package org.daisy.pipeline.braille.liblouis.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.net.URI;
import javax.xml.namespace.QName;

import static com.google.common.base.Objects.toStringHelper;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import static org.daisy.braille.css.Query.parseQuery;
import static org.daisy.braille.css.Query.serializeQuery;
import org.daisy.pipeline.braille.common.Cached;
import static org.daisy.pipeline.braille.common.util.Tuple3;
import static org.daisy.pipeline.braille.common.util.URIs.asURI;
import org.daisy.pipeline.braille.common.CSSBlockTransform;
import static org.daisy.pipeline.braille.common.Transform.Provider.util.logCreate;
import static org.daisy.pipeline.braille.common.Transform.Provider.util.logSelect;
import org.daisy.pipeline.braille.common.XProcTransform;
import org.daisy.pipeline.braille.liblouis.LiblouisTranslator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.ComponentContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LiblouisCSSBlockTransform extends CSSBlockTransform, XProcTransform {
	
	@Component(
		name = "org.daisy.pipeline.braille.liblouis.transform.LiblouisCSSBlockTransform.Provider",
		service = {
			XProcTransform.Provider.class,
			CSSBlockTransform.Provider.class
		}
	)
	public class Provider implements XProcTransform.Provider<LiblouisCSSBlockTransform>, CSSBlockTransform.Provider<LiblouisCSSBlockTransform> {
		
		private URI href;
		
		@Activate
		private void activate(ComponentContext context, final Map<?,?> properties) {
			href = asURI(context.getBundleContext().getBundle().getEntry("xml/transform/liblouis-block-translate.xpl"));
		}
		
		/**
		 * Recognized features:
		 *
		 * - translator: Will only match if the value is `liblouis'.
		 * - locale: If present the value will be used instead of any xml:lang attributes.
		 *
		 * Other features are used for finding sub-transformers of type LiblouisTranslator.
		 */
		public Iterable<LiblouisCSSBlockTransform> get(String query) {
			return logSelect(query, Optional.<LiblouisCSSBlockTransform>fromNullable(transforms.get(query)).asSet(), logger);
		}
		
		private Cached<String,LiblouisCSSBlockTransform> transforms
		= new Cached<String,LiblouisCSSBlockTransform>() {
			public LiblouisCSSBlockTransform delegate(String query) {
				final URI href = Provider.this.href;
				Map<String,Optional<String>> q = new HashMap<String,Optional<String>>(parseQuery(query));
				Optional<String> o;
				if ((o = q.remove("translator")) != null)
					if (!o.get().equals("liblouis"))
						return null;
				String newQuery = serializeQuery(q);
				try {
					final LiblouisTranslator translator = liblouisTranslatorProvider.get(newQuery).iterator().next();
					final Map<String,String> options = ImmutableMap.<String,String>of("query", newQuery);
					return logCreate(
						new LiblouisCSSBlockTransform() {
							public Tuple3<URI,QName,Map<String,String>> asXProc() {
								return new Tuple3<URI,QName,Map<String,String>>(href, null, options);
							}
							@Override
							public String toString() {
								return toStringHelper(LiblouisCSSBlockTransform.class.getSimpleName()).add("translator", translator).toString();
							}
						},
						logger
					);
				} catch (NoSuchElementException e) {}
				return null;
			}
		};
		
		@Reference(
			name = "LiblouisTranslatorProvider",
			unbind = "unbindLiblouisTranslatorProvider",
			service = LiblouisTranslator.Provider.class,
			cardinality = ReferenceCardinality.MULTIPLE,
			policy = ReferencePolicy.DYNAMIC
		)
		protected void bindLiblouisTranslatorProvider(LiblouisTranslator.Provider provider) {
			liblouisTranslatorProviders.add(provider);
		}
	
		protected void unbindLiblouisTranslatorProvider(LiblouisTranslator.Provider provider) {
			liblouisTranslatorProviders.remove(provider);
			liblouisTranslatorProvider.invalidateCache();
		}
	
		private List<LiblouisTranslator.Provider> liblouisTranslatorProviders = new ArrayList<LiblouisTranslator.Provider>();
		private CachedProvider<String,LiblouisTranslator> liblouisTranslatorProvider
		= CachedProvider.<String,LiblouisTranslator>newInstance(
			DispatchingProvider.<String,LiblouisTranslator>newInstance(liblouisTranslatorProviders));
		
		private static final Logger logger = LoggerFactory.getLogger(Provider.class);
		
	}
}
