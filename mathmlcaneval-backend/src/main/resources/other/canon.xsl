<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : canon.xsl
    Created on : December 21, 2013, 3:42 PM
    Author     : Empt
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output omit-xml-declaration="no" indent="yes"/>
    <xsl:strip-space elements="*"/>

    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match=
    "*[not(@*|*|comment()|processing-instruction()) 
     and normalize-space()=''
      ]"/>
</xsl:stylesheet>