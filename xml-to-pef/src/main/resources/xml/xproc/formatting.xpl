<?xml version="1.0" encoding="UTF-8"?>
<p:declare-step
    xmlns:p="http://www.w3.org/ns/xproc"
    xmlns:px="http://www.daisy.org/ns/pipeline/xproc"
    xmlns:pxi="http://www.daisy.org/ns/pipeline/xproc/internal"
    xmlns:d="http://www.daisy.org/ns/pipeline/data"
    xmlns:louis="http://liblouis.org/liblouis"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    exclude-inline-prefixes="#all"
    type="pxi:formatting" name="formatting" version="1.0">

    <p:input port="source" primary="true"/>
    <p:input port="metadata"/>
    <p:input port="pages" />
    <p:output port="result" primary="true" px:media-type="application/x-pef+xml"/>
    <p:option name="temp-dir" required="true"/>
    
    <p:import href="http://www.daisy.org/pipeline/modules/file-utils/xproc/file-library.xpl"/>
    <p:import href="http://www.daisy.org/pipeline/modules/braille/liblouis-formatter/xproc/library.xpl"/>
    
    <!-- Create temporary directory -->
    
    <p:xslt name="temp-dir-uri">
        <p:with-param name="href" select="concat($temp-dir,'/')"/>
        <p:input port="source">
            <p:inline>
                <d:file/>
            </p:inline>
        </p:input>
        <p:input port="stylesheet">
            <p:inline>
                <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                    xmlns:pf="http://www.daisy.org/ns/pipeline/functions"
                    version="2.0">
                    <xsl:import href="http://www.daisy.org/pipeline/modules/file-utils/xslt/uri-functions.xsl"/>
                    <xsl:param name="href" required="yes"/>
                    <xsl:template match="/*">
                        <xsl:copy>
                            <xsl:attribute name="href" select="pf:normalize-uri($href)"/>
                        </xsl:copy>
                    </xsl:template>
                </xsl:stylesheet>
            </p:inline>
        </p:input>
    </p:xslt>
    <p:sink/>
    
    <px:mkdir>
        <p:with-option name="href" select="/*/@href">
            <p:pipe port="result" step="temp-dir-uri"/>
        </p:with-option>
    </px:mkdir>
    
    <!-- Format with liblouisutdml -->
    
    <louis:format>
        <p:input port="source">
            <p:pipe step="formatting" port="source"/>
        </p:input>
        <p:input port="pages">
            <p:pipe step="formatting" port="pages"/>
        </p:input>
        <p:with-option name="temp-dir" select="/*/@href">
            <p:pipe port="result" step="temp-dir-uri"/>
        </p:with-option>
        <p:with-option name="title" select="(/*/dc:title/string(),
                                             /*/*[@property=('dc:title','dcterms:title')]/string(),
                                             /*/*[@property=('dc:title','dcterms:title')]/@content/string()
                                            )[1]">
            <p:pipe step="formatting" port="metadata"/>
        </p:with-option>
        <p:with-option name="creator" select="(/*/dc:creator/string(),
                                               /*/*[@property=('dc:creator','dcterms:creator')]/string(),
                                               /*/*[@property=('dc:creator','dcterms:creator')]/@content/string()
                                              )[1]">
            <p:pipe step="formatting" port="metadata"/>
        </p:with-option>
    </louis:format>

</p:declare-step>