<p:library
    xmlns:p="http://www.w3.org/ns/xproc"
    xmlns:px="http://www.daisy.org/ns/pipeline/xproc"
    xmlns:pef="http://xmlcalabash.com/ns/extensions/brailleutils"
    version="1.0">
    
    <p:declare-step type="pef:emboss">
        <p:input port="source" sequence="true"/>
        <p:output port="result" sequence="true"/>
        <p:option name="message" required="true"/>
    </p:declare-step>
    
    <p:declare-step type="pef:pef2text">
        <p:input port="source" sequence="true"/>
        <p:output port="result" sequence="true"/>
        <p:option name="message" required="true"/>
    </p:declare-step>
    
    <p:declare-step type="pef:text2pef">
        <p:input port="source" sequence="false" primary="true"/>
        <p:output port="result" sequence="false" primary="true"/>
        <p:option name="temp-dir" required="true" px:output="temp" px:sequence="false" px:type="anyDirURI"/>
    </p:declare-step>
    
    <p:declare-step type="pef:validate">
        <p:input port="source" sequence="true"/>
        <p:output port="result" sequence="true"/>
        <p:option name="message" required="true"/>
    </p:declare-step>
</p:library>

