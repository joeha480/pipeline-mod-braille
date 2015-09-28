DAISY Pipeline 2 Braille Modules v1.9.5
=======================================

Changes
-------
- Integration of Dotify's TaskSystem (https://github.com/daisy/pipeline-mod-braille/pull/39)
- Support for row spacing (https://github.com/snaekobbi/pipeline-mod-braille/issues/26,
  https://github.com/snaekobbi/braille-css/issues/5)
- Correct handling of empty blocks (https://github.com/daisy/pipeline-mod-braille/issues/49)

Components
----------
- liblouis ([2.6.3](https://github.com/liblouis/liblouis/releases/tag/v2.6.3)), liblouisutdml
  ([2.5.0](https://github.com/liblouis/liblouisutdml/releases/tag/v2.5.0)), liblouis-java
  ([1.4.0](https://github.com/liblouis/liblouis-java/releases/tag/1.4.0))  
- **dotify** (api [1.2.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.api%2Fv1.2.0), **common**
  [**1.2.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.common%2Fv1.2.0), hyphenator.impl
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.hyphenator.impl%2Fv1.0.0), translator.impl
  [1.1.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.translator.impl%2Fv1.1.0), **formatter.impl**
  [**1.1.3**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.formatter.impl%2Fv1.1.3), **text.impl**
  [**1.0.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.text.impl%2Fv1.0.0), **task-api**
  [**1.0.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.task-api%2Fv1.0.0), **task.impl**
  [**1.0.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.task.impl%2Fv1.0.0))
- brailleutils (api
  [2.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.api%2Fv2.0.0), impl
  [2.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.impl%2Fv2.0.0), pef-tools
  [1.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.pef-tools%2Fv1.0.0))
- **braille-css** ([**1.3.0**](https://github.com/snaekobbi/braille-css/releases/tag/1.3.0))
- jstyleparser ([1.20-p2](https://github.com/snaekobbi/jStyleParser/releases/tag/jStyleParser-1.20-p2))
- libhyphen ([2.6.0](https://github.com/bertfrees/libhyphen-nar/releases/tag/2.6.0)), jhyphen
  ([0.1.5](https://github.com/daisy/jhyphen/releases/tag/v0.1.5))
- texhyphj ([1.2](https://github.com/joeha480/texhyphj/releases/tag/release-1.2))

DAISY Pipeline 2 Braille Modules v1.9.4
=======================================

Changes
-------
- New `stylesheet` option (https://github.com/daisy/pipeline-mod-braille/issues/46) replaces
  `default-stylesheet` option (https://github.com/daisy/pipeline-mod-braille/issues/34)
- Improvements to default style sheets (https://github.com/daisy/pipeline-mod-braille/issues/40)
- Support for more border patterns (https://github.com/daisy/pipeline-mod-braille/issues/45)
- Bug fixes in margins (https://github.com/daisy/pipeline-mod-braille/pull/42) and line breaking
  (https://github.com/daisy/pipeline-mod-braille/pull/43)

Components
----------
- liblouis ([2.6.3](https://github.com/liblouis/liblouis/releases/tag/v2.6.3)), liblouisutdml
  ([2.5.0](https://github.com/liblouis/liblouisutdml/releases/tag/v2.5.0)), liblouis-java
  ([1.4.0](https://github.com/liblouis/liblouis-java/releases/tag/1.4.0))
- **dotify** (**api** [**1.2.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.api%2Fv1.2.0), common
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.common%2Fv1.0.0), hyphenator.impl
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.hyphenator.impl%2Fv1.0.0), **translator.impl**
  [**1.1.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.translator.impl%2Fv1.1.0), formatter.impl
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.formatter.impl%2Fv1.0.0))
- brailleutils (api
  [2.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.api%2Fv2.0.0), impl
  [2.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.impl%2Fv2.0.0), pef-tools
  [1.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.pef-tools%2Fv1.0.0))
- braille-css ([1.2.0](https://github.com/snaekobbi/braille-css/releases/tag/1.2.0))
- jstyleparser ([1.20-p2](https://github.com/snaekobbi/jStyleParser/releases/tag/jStyleParser-1.20-p2))
- libhyphen ([2.6.0](https://github.com/bertfrees/libhyphen-nar/releases/tag/2.6.0)), jhyphen
  ([0.1.5](https://github.com/daisy/jhyphen/releases/tag/v0.1.5))
- texhyphj ([1.2](https://github.com/joeha480/texhyphj/releases/tag/release-1.2))

DAISY Pipeline 2 Braille Modules v1.9.3
=======================================

Changes
-------
- Bug fixes (https://github.com/daisy/pipeline-mod-braille/issues/35,
  https://github.com/daisy/pipeline-mod-braille/issues/33)

Components
----------
- liblouis ([2.6.3](https://github.com/liblouis/liblouis/releases/tag/v2.6.3)), liblouisutdml
  ([2.5.0](https://github.com/liblouis/liblouisutdml/releases/tag/v2.5.0)), liblouis-java
  ([1.4.0](https://github.com/liblouis/liblouis-java/releases/tag/1.4.0))
- dotify (api [1.0.1](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.api%2Fv1.0.1), common
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.common%2Fv1.0.0), hyphenator.impl
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.hyphenator.impl%2Fv1.0.0), translator.impl
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.translator.impl%2Fv1.0.0), formatter.impl
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.formatter.impl%2Fv1.0.0))
- brailleutils (api
  [2.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.api%2Fv2.0.0), impl
  [2.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.impl%2Fv2.0.0), pef-tools
  [1.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.pef-tools%2Fv1.0.0))
- **braille-css** ([**1.2.0**](https://github.com/snaekobbi/braille-css/releases/tag/1.2.0))
- jstyleparser ([1.20-p2](https://github.com/snaekobbi/jStyleParser/releases/tag/jStyleParser-1.20-p2))
- libhyphen ([2.6.0](https://github.com/bertfrees/libhyphen-nar/releases/tag/2.6.0)), jhyphen
  ([0.1.5](https://github.com/daisy/jhyphen/releases/tag/v0.1.5))
- texhyphj ([1.2](https://github.com/joeha480/texhyphj/releases/tag/release-1.2))

DAISY Pipeline 2 Braille Modules v1.9.2
=======================================

Changes
-------
- Correct handling of white space (https://github.com/snaekobbi/pipeline-mod-braille/issues/52)
- Support for vertical positioning (https://github.com/snaekobbi/pipeline-mod-braille/issues/28,
  https://github.com/snaekobbi/braille-css/issues/2)
- Support for namespaces in CSS (https://github.com/snaekobbi/pipeline-mod-braille/issues/11)
- Fixed bug in system startup (https://github.com/snaekobbi/system/issues/2)

Components
----------
- liblouis ([2.6.3](https://github.com/liblouis/liblouis/releases/tag/v2.6.3)), liblouisutdml
  ([2.5.0](https://github.com/liblouis/liblouisutdml/releases/tag/v2.5.0)), liblouis-java
  ([1.4.0](https://github.com/liblouis/liblouis-java/releases/tag/1.4.0))
- dotify (api [1.0.1](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.api%2Fv1.0.1), common
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.common%2Fv1.0.0), hyphenator.impl
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.hyphenator.impl%2Fv1.0.0), translator.impl
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.translator.impl%2Fv1.0.0), formatter.impl
  [1.0.0](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.formatter.impl%2Fv1.0.0))
- brailleutils (api
  [2.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.api%2Fv2.0.0), impl
  [2.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.impl%2Fv2.0.0), pef-tools
  [1.0.0](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.pef-tools%2Fv1.0.0))
- **braille-css** ([**1.1.0**](https://github.com/snaekobbi/braille-css/releases/tag/1.1.0))
- **jstyleparser** ([**1.20-p2**](https://github.com/snaekobbi/jStyleParser/releases/tag/jStyleParser-1.20-p2))
- libhyphen ([2.6.0](https://github.com/bertfrees/libhyphen-nar/releases/tag/2.6.0)), jhyphen
  ([0.1.5](https://github.com/daisy/jhyphen/releases/tag/v0.1.5))
- texhyphj ([1.2](https://github.com/joeha480/texhyphj/releases/tag/release-1.2))

DAISY Pipeline 2 Braille Modules v1.9.1
=======================================

Changes
-------
- HTML to PEF conversion script
- Direct DTBook to PEF conversion script (not through ZedAI)
  (https://github.com/snaekobbi/pipeline-mod-braille/issues/45)
- `transform` option for transformer queries
- Dotify based formatter (https://github.com/daisy/pipeline-mod-braille/pull/11,
  https://github.com/snaekobbi/pipeline-mod-braille/pull/2, https://github.com/snaekobbi/pipeline-mod-braille/issues/32,
  https://github.com/snaekobbi/pipeline-mod-braille/pull/33)
- Support for text-transform property (https://github.com/daisy/pipeline-mod-braille/pull/23)
- Better logging (https://github.com/daisy/pipeline-mod-braille/issues/19)
- Framework redesign (https://github.com/daisy/pipeline-mod-braille/pull/15,
  https://github.com/snaekobbi/pipeline-mod-braille/pull/1)
- Other internal changes (https://github.com/daisy/pipeline-mod-braille/issues/10,
  https://github.com/daisy/pipeline-mod-braille/pull/25, https://github.com/daisy/pipeline-mod-braille/pull/29,
  https://github.com/snaekobbi/pipeline-mod-braille/pull/3, https://github.com/snaekobbi/pipeline-mod-braille/pull/35,
  https://github.com/snaekobbi/pipeline-mod-braille/issues/44)

Components
----------
- **liblouis** ([**2.6.3**](https://github.com/liblouis/liblouis/releases/tag/v2.6.3)), liblouisutdml
  ([2.5.0](https://github.com/liblouis/liblouisutdml/releases/tag/v2.5.0)), **liblouis-java**
  ([**1.4.0**](https://github.com/liblouis/liblouis-java/releases/tag/1.4.0))
- **dotify** (**api** [**1.0.1**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.api%2Fv1.0.1), **common**
  [**1.0.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.common%2Fv1.0.0), **hyphenator.impl**
  [**1.0.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.hyphenator.impl%2Fv1.0.0), **translator.impl**
  [**1.0.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.translator.impl%2Fv1.0.0), **formatter.impl**
  [**1.0.0**](https://github.com/joeha480/dotify/releases/tag/releases%2Fdotify.formatter.impl%2Fv1.0.0))
- **brailleutils** (**api**
  [**2.0.0**](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.api%2Fv2.0.0), **impl**
  [**2.0.0**](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.impl%2Fv2.0.0), **pef-tools**
  [**1.0.0**](https://github.com/joeha480/brailleutils/releases/tag/releases%2Fbraille-utils.pef-tools%2Fv1.0.0))
- **braille-css** ([**1.0.0**](https://github.com/snaekobbi/braille-css/releases/tag/1.0.0))
- **jstyleparser** ([**1.20-p1**](https://github.com/snaekobbi/jStyleParser/releases/tag/jstyleparser-1.20-p1))
- libhyphen ([2.6.0](https://github.com/bertfrees/libhyphen-nar/releases/tag/2.6.0)), jhyphen
  ([0.1.5](https://github.com/daisy/jhyphen/releases/tag/v0.1.5))
- texhyphj ([1.2](https://github.com/joeha480/texhyphj/releases/tag/release-1.2))

DAISY Pipeline 2 Braille Modules v1.9
=====================================

Components
----------
- liblouis ([2.5.4](https://github.com/liblouis/liblouis/releases/tag/liblouis_2_5_4)), liblouisutdml
  ([2.5.0](https://github.com/liblouis/liblouisutdml/releases/tag/v2.5.0)), liblouis-java
  ([1.2.0](https://github.com/liblouis/liblouis-java/releases/tag/1.2.0))
- brailleutils (core [1.2.0](https://github.com/daisy/osgi-libs/releases/tag/brailleutils-core-1.2.0), catalog
  [1.2.0](https://github.com/daisy/osgi-libs/releases/tag/brailleutils-catalog-1.2.0))
- jstyleparser ([1.13](https://github.com/daisy/osgi-libs/releases/tag/jstyleparser-1.13.0-p1))
- libhyphen ([2.6.0](https://github.com/bertfrees/libhyphen-nar/releases/tag/2.6.0)), jhyphen
  ([0.1.5](https://github.com/daisy/jhyphen/releases/tag/v0.1.5))
- texhyphj ([1.2](https://github.com/joeha480/texhyphj/releases/tag/release-1.2))