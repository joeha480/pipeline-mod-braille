<?xml version="1.0" encoding="UTF-8"?>
<p:declare-step type="px:epub3-to-pef.convert" version="1.0"
                xmlns:p="http://www.w3.org/ns/xproc"
                xmlns:px="http://www.daisy.org/ns/pipeline/xproc"
                xmlns:pxi="http://www.daisy.org/ns/pipeline/xproc/internal"
                xmlns:d="http://www.daisy.org/ns/pipeline/data"
                xmlns:c="http://www.w3.org/ns/xproc-step"
                xmlns:pef="http://www.daisy.org/ns/2008/pef"
                xmlns:math="http://www.w3.org/1998/Math/MathML"
                xmlns:html="http://www.w3.org/1999/xhtml"
                xmlns:opf="http://www.idpf.org/2007/opf"
                xmlns:dc="http://purl.org/dc/elements/1.1/"
                xmlns:dcterms="http://purl.org/dc/terms/"
                exclude-inline-prefixes="#all"
                name="main">
    
    <p:input port="fileset.in" primary="true"/>
    <p:input port="in-memory.in" sequence="true"/>
    <p:output port="fileset.out" primary="true">
        <p:pipe port="result" step="fileset.out"/>
    </p:output>
    <p:output port="in-memory.out" sequence="true">
        <p:pipe port="result" step="in-memory.out"/>
    </p:output>
    
    <p:input kind="parameter" port="parameters" sequence="true">
        <p:inline>
            <c:param-set/>
        </p:inline>
    </p:input>
    
    <p:option name="default-stylesheet" select="'http://www.daisy.org/pipeline/modules/braille/epub3-to-pef/css/default.css'"/>
    <p:option name="stylesheet" select="''"/>
    <p:option name="apply-document-specific-stylesheets" select="'false'"/>
    <p:option name="transform" select="'(translator:liblouis)(formatter:dotify)'"/>
    
    <!-- Empty temporary directory dedicated to this conversion -->
    <p:option name="temp-dir" required="true"/>

    <p:import href="http://www.daisy.org/pipeline/modules/common-utils/library.xpl"/>
    <p:import href="http://www.daisy.org/pipeline/modules/braille/common-utils/library.xpl"/>
    <p:import href="http://www.daisy.org/pipeline/modules/braille/xml-to-pef/library.xpl"/>
    <p:import href="http://www.daisy.org/pipeline/modules/braille/pef-utils/library.xpl"/>
    <p:import href="http://www.daisy.org/pipeline/modules/file-utils/library.xpl"/>
    <p:import href="http://www.daisy.org/pipeline/modules/fileset-utils/library.xpl"/>
    
    <!-- Ensure that there's exactly one c:param-set -->
    <p:identity>
        <p:input port="source">
            <p:pipe step="main" port="parameters"/>
        </p:input>
    </p:identity>
    <p:wrap-sequence wrapper="c:param-set"/>
    <p:unwrap match="/c:param-set/c:param-set"/>
    <p:delete match="/c:param-set/c:param[@name = following-sibling::c:param/@name]"/>
    <p:identity name="parameters"/>
    <p:sink/>
    
    <!-- Load OPF and add content files to fileset. -->
    <px:fileset-load media-types="application/oebps-package+xml">
        <p:input port="fileset">
            <p:pipe port="fileset.in" step="main"/>
        </p:input>
        <p:input port="in-memory">
            <p:pipe port="in-memory.in" step="main"/>
        </p:input>
    </px:fileset-load>
    <p:identity name="opf"/>
    <p:xslt>
        <p:input port="parameters">
            <p:empty/>
        </p:input>
        <p:input port="stylesheet">
            <p:document href="../xslt/opf-manifest-to-fileset.xsl"/>
        </p:input>
    </p:xslt>
    <p:identity name="opf-fileset"/>
    <p:sink/>
    
    <!-- Load XHTML documents in spine order. -->
    <px:fileset-load media-types="application/oebps-package+xml application/xhtml+xml">
        <p:input port="fileset">
            <p:pipe port="result" step="opf-fileset"/>
        </p:input>
        <p:input port="in-memory">
            <p:pipe port="in-memory.in" step="main"/>
        </p:input>
    </px:fileset-load>
    <p:for-each>
        <p:add-attribute match="/*" attribute-name="xml:base">
            <p:with-option name="attribute-value" select="base-uri(/*)"/>
        </p:add-attribute>
    </p:for-each>
    <p:wrap-sequence wrapper="wrapper"/>
    <p:xslt>
        <p:input port="parameters">
            <p:empty/>
        </p:input>
        <p:input port="stylesheet">
            <p:document href="../xslt/get-epub3-spine.xsl"/>
        </p:input>
    </p:xslt>
    <p:filter select="/*/*"/>
    
    <!-- In case there exists any CSS in the EPUB already, and $apply-document-specific-stylesheets = 'true',  then inline that CSS. -->
    <px:message message="Processing CSS that is already present in the EPUB"/>
    <p:for-each>
        <p:add-attribute match="/*" attribute-name="xml:base">
            <p:with-option name="attribute-value" select="base-uri(/*)"/>
        </p:add-attribute>
        
        <px:message severity="DEBUG">
            <p:with-option name="message" select="concat('Deleting CSS that is not for embossed media from ',replace(base-uri(/*),'.*/',''),'')"/>
        </px:message>
        <p:delete match="//@style | //html:link[@rel='stylesheet' and not(string(@media)='embossed')] | //html:style[not(string(@media)='embossed')]"/>
        
        <p:choose>
            <p:when test="$apply-document-specific-stylesheets='true'">
                <px:message severity="DEBUG">
                    <p:with-option name="message" select="concat('Inlining document-specific CSS for ',replace(base-uri(/*),'.*/',''),'')"/>
                </px:message>
                <p:group>
                    <!-- <link> not supported in css:inline so we provide the URIs to them explicitly -->
                    <p:variable name="linked-stylesheets" select="string-join(//html:link[@rel='stylesheet' and @media='embossed']/resolve-uri(@href,base-uri(/*)), ' ')"/>
                    <p:delete match="//html:link[@rel='stylesheet' and @media='embossed']"/>
                    <px:apply-stylesheets>
                        <p:with-option name="stylesheets" select="$linked-stylesheets"/>
                        <p:input port="parameters">
                            <p:pipe port="result" step="parameters"/>
                        </p:input>
                    </px:apply-stylesheets>
                </p:group>
            </p:when>
            <p:otherwise>
                <p:delete match="//html:link[@rel='stylesheet' and @media='embossed']"/>
            </p:otherwise>
        </p:choose>
        
        <p:filter select="/*/html:body"/>
    </p:for-each>
    <p:identity name="spine-bodies"/>
    
    <!-- Convert OPF metadata to HTML metadata. -->
    <p:xslt>
        <p:input port="source">
            <p:pipe port="result" step="opf"/>
        </p:input>
        <p:input port="stylesheet">
            <p:document href="../xslt/opf-to-html-head.xsl"/>
        </p:input>
        <p:input port="parameters">
            <p:empty/>
        </p:input>
    </p:xslt>
    <p:identity name="opf-as-head"/>
    
    <!-- Create a new HTML document with <head> based on the OPF and all <body> elements from the input HTML documents -->
    <p:wrap-sequence wrapper="html" wrapper-namespace="http://www.w3.org/1999/xhtml">
        <p:input port="source">
            <p:pipe port="result" step="opf-as-head"/>
            <p:pipe port="result" step="spine-bodies"/>
        </p:input>
    </p:wrap-sequence>
    
    <px:message message="Generating table of contents"/>
    <p:xslt>
        <p:input port="stylesheet">
            <p:document href="http://www.daisy.org/pipeline/modules/braille/xml-to-pef/generate-toc.xsl"/>
        </p:input>
        <p:with-param name="depth" select="/*/*[@name='toc-depth']/@value">
            <p:pipe step="parameters" port="result"/>
        </p:with-param>
    </p:xslt>
    
    <px:message message="Inlining global CSS"/>
    <p:group>
        <p:variable name="first-css-stylesheet"
                    select="tokenize($stylesheet,'\s+')[matches(.,'\.s?css$')][1]"/>
        <p:variable name="first-css-stylesheet-index"
                    select="(index-of(tokenize($stylesheet,'\s+')[not(.='')], $first-css-stylesheet),10000)[1]"/>
        <p:variable name="stylesheets-to-be-inlined"
                    select="string-join((
                              (tokenize($stylesheet,'\s+')[not(.='')])[position()&lt;$first-css-stylesheet-index],
                              $default-stylesheet,
                              resolve-uri('../../css/default.scss'),
                              (tokenize($stylesheet,'\s+')[not(.='')])[position()&gt;=$first-css-stylesheet-index]),' ')">
            <p:inline><_/></p:inline>
        </p:variable>
        <px:message severity="DEBUG">
            <p:with-option name="message" select="concat('stylesheets: ',$stylesheets-to-be-inlined)"/>
        </px:message>
        <px:apply-stylesheets>
            <p:with-option name="stylesheets" select="$stylesheets-to-be-inlined"/>
            <p:input port="parameters">
                <p:pipe port="result" step="parameters"/>
            </p:input>
        </px:apply-stylesheets>
    </p:group>
    
    <p:group>
        <p:variable name="lang" select="(/*/opf:metadata/dc:language[not(@refines)])[1]/text()">
            <p:pipe port="result" step="opf"/>
        </p:variable>
        <p:variable name="transform-query" select="concat('(input:css)(output:pef)',$transform,'(locale:',$lang,')')">
            <p:pipe step="parameters" port="result"/>
        </p:variable>
        
        <px:message message="Transforming MathML"/>
        <p:viewport match="math:math">
            <px:transform>
                <p:with-option name="query" select="concat('(input:mathml)(locale:',$lang,')')"/>
                <p:with-option name="temp-dir" select="$temp-dir"/>
            </px:transform>
        </p:viewport>
        
        <px:message message="Transforming from XML with inline CSS to PEF"/>
        <px:message severity="DEBUG">
            <p:with-option name="message" select="concat('px:transform query=',$transform-query)"/>
        </px:message>
        <px:transform>
            <p:with-option name="query" select="$transform-query"/>
            <p:with-option name="temp-dir" select="$temp-dir"/>
            <p:input port="parameters">
                <p:pipe port="result" step="parameters"/>
            </p:input>
        </px:transform>
    </p:group>
    <p:identity name="pef"/>

    <p:identity>
        <p:input port="source">
            <p:pipe step="pef" port="result"/>
            <p:pipe step="opf" port="result"/>
        </p:input>
    </p:identity>
    <px:message message="Adding metadata to PEF based on EPUB 3 package document metadata"/>
    <p:xslt>
        <p:input port="stylesheet">
            <p:document href="http://www.daisy.org/pipeline/modules/braille/pef-utils/add-opf-metadata-to-pef.xsl"/>
        </p:input>
        <p:input port="parameters">
            <p:empty/>
        </p:input>
    </p:xslt>
    <!-- add xml:lang -->
    <p:group>
        <p:variable name="lang" select="(/*/opf:metadata/dc:language[not(@refines)])[1]/text()">
            <p:pipe port="result" step="opf"/>
        </p:variable>
        <p:choose>
            <p:when test="not($lang='und')">
                <p:add-attribute match="/*" attribute-name="xml:lang">
                    <p:with-option name="attribute-value" select="$lang"/>
                </p:add-attribute>
            </p:when>
            <p:otherwise>
                <p:identity/>
            </p:otherwise>
        </p:choose>
    </p:group>
    <p:add-attribute match="/*" attribute-name="xml:base">
        <p:with-option name="attribute-value" select="replace(base-uri(/*),'[^/]+$',concat(((/*/opf:metadata/dc:identifier[not(@refines)]/text()), 'pef')[1],'.pef'))">
            <p:pipe port="result" step="opf"/>
        </p:with-option>
    </p:add-attribute>
    <p:identity name="in-memory.out"/>
    
    <px:fileset-create>
        <p:with-option name="base" select="replace(base-uri(/*),'[^/]+$','')"/>
    </px:fileset-create>
    <px:fileset-add-entry media-type="application/x-pef+xml">
        <p:with-option name="href" select="base-uri(/*)">
            <p:pipe port="result" step="in-memory.out"/>
        </p:with-option>
    </px:fileset-add-entry>
    <p:identity name="fileset.out"/>
    
</p:declare-step>
