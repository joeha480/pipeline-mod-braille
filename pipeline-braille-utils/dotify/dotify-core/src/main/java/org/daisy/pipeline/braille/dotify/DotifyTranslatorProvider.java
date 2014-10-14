package org.daisy.pipeline.braille.dotify;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.common.base.Optional;

import static org.daisy.braille.css.Query.parseQuery;
import org.daisy.dotify.api.translator.BrailleTranslator;
import org.daisy.dotify.api.translator.BrailleTranslatorFactory;
import org.daisy.dotify.api.translator.BrailleTranslatorFactoryService;
import org.daisy.dotify.api.translator.TranslatorConfigurationException;
import org.daisy.pipeline.braille.common.Provider;
import org.daisy.pipeline.braille.common.TranslatorProvider;
import org.daisy.pipeline.braille.common.util.Locales;
import static org.daisy.pipeline.braille.common.util.Locales.parseLocale;

public class DotifyTranslatorProvider implements TranslatorProvider<DotifyTranslator> {
	
	/**
	 * Try to find a translator based on the given locale.
	 * An automatic fallback mechanism is used: if nothing is found for
	 * language-COUNTRY-variant, then language-COUNTRY is searched, then language.
	 */
	public DotifyTranslator get(Locale query) {
		return cachedProvider.get(query);
	}
	
	public DotifyTranslator get(String query) {
		try {
			Map<String,Optional<String>> q = parseQuery(query);
			if (q.containsKey("locale")) {
				return get(parseLocale(q.get("locale").get())); }}
		catch (Exception e) {}
		return null;
	}
	
	private final List<BrailleTranslatorFactoryService> factoryServices = new ArrayList<BrailleTranslatorFactoryService>();
	
	protected void bindBrailleTranslatorFactoryService(BrailleTranslatorFactoryService service) {
		factoryServices.add(service);
		cachedProvider.invalidateCache();
	}
	
	protected void unbindBrailleTranslatorFactoryService(BrailleTranslatorFactoryService service) {
		factoryServices.remove(service);
		cachedProvider.invalidateCache();
	}
	
	private BrailleTranslator newTranslator(String locale, String grade) throws TranslatorConfigurationException {
		for (BrailleTranslatorFactoryService s : factoryServices)
			if (s.supportsSpecification(locale, grade))
				return s.newFactory().newTranslator(locale, grade);
		throw new RuntimeException("Cannot locate a factory for "
		                           + locale.toLowerCase() + "(" + grade.toUpperCase() + ")");
	}
	
	private final Provider<Locale,DotifyTranslator> provider
		= LocaleBasedProvider.<DotifyTranslator>newInstance(
			new Provider<Locale,DotifyTranslator>() {
				public DotifyTranslator get(Locale locale) {
					try {
						BrailleTranslator translator = newTranslator(
							Locales.toString(locale, '-'), BrailleTranslatorFactory.MODE_UNCONTRACTED);
						translator.setHyphenating(false);
						return new DotifyTranslator(translator); }
					catch (TranslatorConfigurationException e) {
						throw new RuntimeException(e); }
				}
			}
	);
	
	private final CachedProvider<Locale,DotifyTranslator> cachedProvider
		= CachedProvider.<Locale,DotifyTranslator>newInstance(provider);
	
}