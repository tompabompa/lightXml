<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : test-stylesheet.xsl
    Created on : den 9 mars 2011, 15:57
    Author     : thomas
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>

    <xsl:template match="/root">
        <html>
            <head>
                <title><xsl:apply-templates select="tag1"/></title>
            </head>
            <body>
                <xsl:apply-templates select="tag2"/>
                <p><xsl:value-of select="document('test-include-source.xml')/include-root/extra-tag"/></p>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="tag1">
        <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="tag2">
        <h3><xsl:value-of select="."/></h3>
        <h4><xsl:value-of select="@arg1"/></h4>
    </xsl:template>

</xsl:stylesheet>
