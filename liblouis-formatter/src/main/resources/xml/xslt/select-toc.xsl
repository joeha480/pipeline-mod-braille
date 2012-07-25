<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:louis="http://liblouis.org/liblouis"
    exclude-result-prefixes="xs louis"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    
    <xsl:param name="select" select="1"/>
    
    <xsl:variable name="toc" as="element()" select="/descendant::louis:toc[number($select)]"/>
    
    <xsl:template match="/*">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <louis:no-pagenum>
                <xsl:sequence select="$toc/louis:toc-title"/>
            </louis:no-pagenum>
            <xsl:apply-templates select="child::node()"/>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="louis:toc" mode="move">
        <xsl:copy>
            <xsl:sequence select="louis:toc-title"/>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="*[@xml:id]">
        <xsl:variable name="id" as="xs:string" select="@xml:id"/>
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:choose>
                <xsl:when test="$toc/louis:toc-item[@ref=$id]">
                    <louis:toc-item>
                        <xsl:copy-of select="$toc/louis:toc-item[@ref=$id]/@louis:style"/>
                        <xsl:apply-templates select="node()"/>
                    </louis:toc-item>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:apply-templates select="node()"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
    
</xsl:stylesheet>