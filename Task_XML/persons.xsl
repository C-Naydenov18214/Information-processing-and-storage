<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  version="1.0">

  <xsl:output method="html"/>
  <xsl:template match="/*[local-name()='xmlConfig/persons']">
    <html>
      <body>
        <xsl:for-each select="person">
          <xsl:if test="mother and father
                    and id(mother)/mother and id(mother)/father
                    and id(father)/mother and id(father)/father
                    and sisters and brothers">
            <table border="1">
              <tr>
                <th>Person Name</th>
                <th>Gender</th>
                <th>Mother</th>
                <th>Father</th>
                <th>Brothers</th>
                <th>Sisters</th>
                <th>Sons</th>
                <th>Daughters</th>
                <th>Grand-Mothers</th>
                <th>Grand-Fathers</th>
                <th>Uncles</th>
                <th>Aunts</th>
              </tr>
              <xsl:apply-templates select="."/>
              <xsl:apply-templates select="id(mother)"/>
              <xsl:apply-templates select="id(father)"/>
              <xsl:for-each select="id(brothers/brother)">
                <xsl:apply-templates select="."/>
              </xsl:for-each>
              <xsl:for-each select="id(sisters/sister)">
                <xsl:apply-templates select="."/>
              </xsl:for-each>
            </table>
            <br/>
            <br/>
            <br/>
          </xsl:if>
        </xsl:for-each>
      </body>
    </html>
  </xsl:template>


  <xsl:template match="person">

    <tr>
      <td>
        <xsl:value-of select="concat(firstname, ' ', surname)"/>
      </td>
      <td>
        <xsl:value-of select="gender"/>
      </td>
      <td>
        <xsl:variable name="mom" select="id(mother)"/>
        <xsl:value-of
          select="concat($mom/firstname, ' ', $mom/surname)"/>
      </td>
      <td>
        <xsl:variable name="fa" select="id(father)"/>
        <xsl:value-of
          select="concat($fa/firstname, ' ', $fa/surname)"/>
      </td>
      <td>
        <xsl:for-each select="brothers/brother">
          <xsl:variable name="bro" select="id(.)"/>
          <xsl:value-of
            select="concat($bro/firstname, ' ', $bro/surname)"/>
          <xsl:if test="position() != last()">
            <xsl:text>,</xsl:text>
          </xsl:if>
        </xsl:for-each>
      </td>
      <td>
        <xsl:for-each select="sisters/sister">
          <xsl:variable name="sis" select="id(.)"/>
          <xsl:value-of
            select="concat($sis/firstname, ' ', $sis/surname)"/>
          <xsl:if test="position() != last()">
            <xsl:text>,</xsl:text>
          </xsl:if>
        </xsl:for-each>
      </td>
      <td>
        <xsl:if test="sons">
          <xsl:for-each select="sons/son">
            <xsl:variable name="son" select="id(.)"/>
            <xsl:value-of
              select="concat($son/firstname, ' ', $son/surname)"/>
            <xsl:if test="position() != last()">
              <xsl:text>,</xsl:text>
            </xsl:if>
          </xsl:for-each>
        </xsl:if>
      </td>
      <td>
        <xsl:if test="daughters">
          <xsl:for-each select="daughters/daughter">
            <xsl:variable name="daughter" select="id(.)"/>
            <xsl:value-of
              select="concat($daughter/firstname, ' ', $daughter/surname)"/>
            <xsl:if test="position() != last()">
              <xsl:text>,</xsl:text>
            </xsl:if>
          </xsl:for-each>
        </xsl:if>
      </td>
      <td>
        <xsl:variable name="grand-momM" select="id(id(mother)/mother)"/>
        <xsl:variable name="grand-momF" select="id(id(father)/mother)"/>
        <xsl:value-of
          select="concat($grand-momM/firstname, ' ', $grand-momM/surname)"/>
        <xsl:text>,</xsl:text>
        <xsl:value-of
          select="concat($grand-momF/firstname, ' ', $grand-momF/surname)"/>
      </td>
      <td>
        <xsl:variable name="grand-faM" select="id(id(mother)/father)"/>
        <xsl:variable name="grand-faF" select="id(id(father)/father)"/>
        <xsl:value-of
          select="concat($grand-faM/firstname, ' ', $grand-faM/surname)"/>
        <xsl:text>,</xsl:text>
        <xsl:value-of
          select="concat($grand-faF/firstname, ' ', $grand-faF/surname)"/>
      </td>
      <td>
        <xsl:if test="id(father)/brothers">
          <xsl:for-each select="id(father)/brothers/brother">
            <xsl:variable name="uncle" select="id(.)"/>
            <xsl:value-of
              select="concat($uncle/firstname, ' ', $uncle/surname)"/>
            <xsl:if test="position() != last()">
              <xsl:text>,</xsl:text>
            </xsl:if>
          </xsl:for-each>
        </xsl:if>
        <xsl:if test="id(mother)/brothers">
          <xsl:if test="id(father)/brothers">
            <xsl:text>,</xsl:text>
          </xsl:if>
          <xsl:for-each select="id(mother)/brothers/brother">
            <xsl:variable name="uncle" select="id(.)"/>
            <xsl:value-of
              select="concat($uncle/firstname, ' ', $uncle/surname)"/>
            <xsl:if test="position() != last()">
              <xsl:text>,</xsl:text>
            </xsl:if>
          </xsl:for-each>
        </xsl:if>
      </td>
      <td>
        <xsl:if test="id(father)/sisters">
          <xsl:for-each select="id(father)/sisters/sister">
            <xsl:variable name="aunt" select="id(.)"/>
            <xsl:value-of
              select="concat($aunt/firstname, ' ', $aunt/surname)"/>
            <xsl:if test="position() != last()">
              <xsl:text>,</xsl:text>
            </xsl:if>
          </xsl:for-each>
        </xsl:if>
        <xsl:if test="id(mother)/sisters">
          <xsl:if test="id(father)/sisters">
            <xsl:text>,</xsl:text>
          </xsl:if>
          <xsl:for-each select="id(mother)/sisters/sister">
            <xsl:variable name="aunt" select="id(.)"/>
            <xsl:value-of
              select="concat($aunt/firstname, ' ', $aunt/surname)"/>
            <xsl:if test="position() != last()">
              <xsl:text>,</xsl:text>
            </xsl:if>
          </xsl:for-each>
        </xsl:if>
      </td>
    </tr>
  </xsl:template>

</xsl:stylesheet>
