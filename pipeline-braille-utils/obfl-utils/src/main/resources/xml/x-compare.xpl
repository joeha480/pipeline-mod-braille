<?xml version="1.0" encoding="UTF-8"?>
<p:declare-step type="x:obfl-compare" name="main"
                xmlns:p="http://www.w3.org/ns/xproc"
                xmlns:obfl="http://www.daisy.org/ns/2011/obfl"
                xmlns:x="http://www.daisy.org/ns/xprocspec"
                version="1.0">
    
    <p:input port="context" primary="false"/>
    <p:input port="expect" primary="false"/>
    <p:input port="parameters" kind="parameter" primary="true"/>
    
    <p:output port="result" primary="true"/>
    
    <p:import href="compare.xpl"/>
    
    <obfl:compare fail-if-not-equal="false" name="compare">
        <p:input port="source">
            <p:pipe step="main" port="context"/>
        </p:input>
        <p:input port="alternate">
            <p:pipe step="main" port="expect"/>
        </p:input>
    </obfl:compare>
    
    <p:rename match="/*" new-name="x:test-result">
        <p:input port="source">
            <p:pipe port="result" step="compare"/>
        </p:input>
    </p:rename>
    
    <p:add-attribute match="/*" attribute-name="result">
        <p:with-option name="attribute-value" select="if (string(/*)='true') then 'passed' else 'failed'">
            <p:pipe port="result" step="compare"/>
        </p:with-option>
    </p:add-attribute>
    
    <p:delete match="/*/node()" name="result"/>
    
    <p:choose>
        <p:when test="/*/@result='passed'">
            <p:identity/>
        </p:when>
        <p:otherwise>
            <p:wrap-sequence wrapper="x:expected" name="expected">
                <p:input port="source">
                    <p:pipe step="main" port="expect"/>
                </p:input>
            </p:wrap-sequence>
            <p:wrap-sequence wrapper="x:was" name="was">
                <p:input port="source">
                    <p:pipe step="main" port="context"/>
                </p:input>
            </p:wrap-sequence>
            <p:insert match="/*" position="last-child">
                <p:input port="source">
                    <p:pipe step="result" port="result"/>
                </p:input>
                <p:input port="insertion">
                    <p:pipe port="result" step="expected"/>
                    <p:pipe port="result" step="was"/>
                </p:input>
            </p:insert>
            <p:add-attribute match="/*/*" attribute-name="xml:space" attribute-value="preserve"/>
        </p:otherwise>
    </p:choose>
    
</p:declare-step>
