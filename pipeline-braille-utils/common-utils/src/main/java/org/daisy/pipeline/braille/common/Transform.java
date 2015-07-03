package org.daisy.pipeline.braille.common;

import java.util.concurrent.atomic.AtomicLong;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.AbstractIterator;
import static com.google.common.collect.Iterables.transform;

import org.daisy.pipeline.braille.common.WithSideEffect;

import org.slf4j.Logger;

/**
 * Tag interface for anything that can transform a node.
 *
 * Classes that implement this interface must have some kind of "transform"
 * method.
 */
public interface Transform {
	
	public String getIdentifier();
	
	public static abstract class AbstractTransform implements Transform {
		final String id = "transform" + getUniqueId();
		public String getIdentifier() {
			return id;
		}
		private static AtomicLong i = new AtomicLong(0);
		private static long getUniqueId() {
			return i.incrementAndGet();
		}
	}
	
	public interface Provider<T extends Transform> extends org.daisy.pipeline.braille.common.Provider<String,T>,
	                                                       Contextual<Logger,Transform.Provider<T>> {
		
		public static abstract class MemoizingProvider<T extends Transform>
				extends org.daisy.pipeline.braille.common.Provider.MemoizingProvider<String,T>
				implements Transform.Provider<T> {
			protected final Logger context;
			private Map<Logger,Transform.Provider<T>> providerCache;
			protected MemoizingProvider(Logger context) {
				this.context = context;
			}
			private void setProviderCache(Map<Logger,Transform.Provider<T>> cache) {
				providerCache = cache;
			}
			protected abstract Transform.Provider.MemoizingProvider<T> _withContext(Logger context);
			public final Transform.Provider<T> withContext(Logger context) {
				if (providerCache == null) {
					providerCache = new HashMap<Logger,Transform.Provider<T>>();
					providerCache.put(this.context, this); }
				if (providerCache.containsKey(context))
					return providerCache.get(context);
				Transform.Provider.MemoizingProvider<T> provider = _withContext(context);
				providerCache.put(context, provider);
				provider.setProviderCache(providerCache);
				return provider;
			}
			private static class _<T extends Transform> extends Transform.Provider.MemoizingProvider<T> {
				private final Transform.Provider<T> provider;
				private _(Transform.Provider<T> provider, Logger context) {
					super(context);
					this.provider = provider;
				}
				protected Iterable<T> _get(String query) {
					return provider.get(query);
				}
				protected Transform.Provider.MemoizingProvider<T> _withContext(Logger context) {
					return new _<T>(provider.withContext(context), context);
				}
			}
		}
		
		public static abstract class DispatchingProvider<T extends Transform>
				extends org.daisy.pipeline.braille.common.Provider.DispatchingProvider<String,T>
				implements Transform.Provider<T> {
			private final Logger context;
			public DispatchingProvider(Logger context) {
				this.context = context;
			}
			protected abstract Iterable<Transform.Provider<T>> _dispatch();
			public final Iterable<org.daisy.pipeline.braille.common.Provider<String,T>> dispatch() {
				return transform(
					_dispatch(),
					new Function<Transform.Provider<T>,org.daisy.pipeline.braille.common.Provider<String,T>>() {
						public org.daisy.pipeline.braille.common.Provider<String,T> apply(Transform.Provider<T> provider) {
							return provider.withContext(context); }});
			}
			private static class _<T extends Transform> extends Transform.Provider.DispatchingProvider<T> {
				private final Iterable<Transform.Provider<T>> dispatch;
				private _(Iterable<Transform.Provider<T>> dispatch, Logger context) {
					super(context);
					this.dispatch = dispatch;
				}
				protected Iterable<Transform.Provider<T>> _dispatch() {
					return dispatch;
				}
				public Transform.Provider<T> withContext(Logger context) {
					return new _<T>(dispatch, context);
				}
			}
			
		}
		
		public static abstract class AbstractProvider<T extends Transform> extends Transform.Provider.MemoizingProvider<T> {
			private final static Pattern ID_FEATURE_RE = Pattern.compile(
				"\\s*\\(\\s*id\\s*\\:\\s*(?<id>[_a-zA-Z][_a-zA-Z0-9-]*)\\s*\\)\\s*"
			);
			private final Function<WithSideEffect<T,Logger>,T> applyContext;
			protected AbstractProvider(Logger context) {
				super(context);
				applyContext = new Function<WithSideEffect<T,Logger>,T>() {
					public T apply(WithSideEffect<T,Logger> value) {
						return value.apply(AbstractProvider.this.context);
					}
				};
			}
			protected abstract Iterable<WithSideEffect<T,Logger>> __get(String query);
			protected final Iterable<T> _get(String query) {
				Matcher m = ID_FEATURE_RE.matcher(query);
				if (m.matches()) {
					String id = m.group("id");
					return Optional.fromNullable(fromId(id)).asSet(); }
				else
					return rememberId(
						filterOutThrowsWithSideEffectException(
							transform(
								__get(query),
								applyContext)));
			}
			private final Map<String,T> fromId = new HashMap<String,T>();
			protected T fromId(String id) {
				return fromId.get(id);
			}
			private Iterable<T> rememberId(final Iterable<T> iterable) {
				return new Iterable<T>() {
					public Iterator<T> iterator() {
						return new Iterator<T>() {
							Iterator<T> i = null;
							public boolean hasNext() {
								if (i == null) i = iterable.iterator();
								return i.hasNext();
							}
							public T next() {
								T t;
								if (i == null) i = iterable.iterator();
								t = i.next();
								fromId.put(t.getIdentifier(), t);
								return t;
							}
							public void remove() {
								if (i == null) i = iterable.iterator();
								i.remove();
							}
						};
					}
				};
			}
			private static <T> Iterable<T> filterOutThrowsWithSideEffectException(final Iterable<T> iterable) {
				return new Iterable<T>() {
					public Iterator<T>iterator() {
						return new AbstractIterator<T>() {
							private Iterator<T> i;
							protected T computeNext() {
								if (i == null)
									i = iterable.iterator();
								while (true) {
									try {
										return i.next(); }
									catch (WithSideEffect.Exception e) {
										continue; }
									catch (NoSuchElementException e) {
										return endOfData(); }}
							}
						};
					}
				};
			}
		}
		
		public static abstract class util {
			
			public static <T extends Transform> Transform.Provider.MemoizingProvider<T> memoize(Transform.Provider<T> provider) {
				return new Transform.Provider.MemoizingProvider._<T>(provider, null);
			}
			
			@SuppressWarnings(
				"unchecked" // safe cast to Iterable<Provider<Q,X>>
			)
			public static <T extends Transform> Transform.Provider<T> dispatch(Iterable<? extends Transform.Provider<T>> dispatch) {
				return new Transform.Provider.DispatchingProvider._<T>((Iterable<Transform.Provider<T>>)dispatch, null);
			}
			
			public static <T extends Transform> WithSideEffect<T,Logger> logCreate(final T t) {
				return new WithSideEffect<T,Logger>() {
					public T _apply() {
						applyWithSideEffect(debug("Created " + t));
						return t; }};
			}
			
			public static <T extends Transform> Iterable<WithSideEffect<T,Logger>> logSelect(final String query,
			                                                                                 final Iterable<T> iterable) {
				return new Iterable<WithSideEffect<T,Logger>>() {
					public Iterator<WithSideEffect<T,Logger>> iterator() {
						return new Iterator<WithSideEffect<T,Logger>>() {
							Iterator<T> i = null;
							public boolean hasNext() {
								if (i == null)
									return true;
								return i.hasNext();
							}
							public WithSideEffect<T,Logger> next() {
								final T t;
								if (i == null) {
									i = iterable.iterator();
									try { t = i.next(); }
									catch (final NoSuchElementException e) {
										return new WithSideEffect<T,Logger>() {
											public T _apply() {
												applyWithSideEffect(debug("No match for query " + query));
												throw e;
											}
										};
									}
								} else
									t = i.next();
								return new WithSideEffect<T,Logger>() {
									public T _apply() {
										applyWithSideEffect(info("Selected " + t + " for query " + query));
										return t;
									}
								};
							}
							public void remove() {
								throw new UnsupportedOperationException();
							}
						};
					}
				};
			}
			
			public static Function<Logger,Void> debug(final String message) {
				return new Function<Logger,Void>() {
					public Void apply(Logger logger) {
						if (logger != null)
							logger.debug(message);
						return null;
					}
				};
			}
			
			public static Function<Logger,Void> info(final String message) {
				return new Function<Logger,Void>() {
					public Void apply(Logger logger) {
						if (logger != null)
							logger.info(message);
						return null;
					}
				};
			}
		}
	}
}
