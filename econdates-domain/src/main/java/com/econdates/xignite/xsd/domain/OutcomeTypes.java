//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.08 at 04:20:04 PM EST 
//


package com.econdates.xignite.xsd.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutcomeTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OutcomeTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Success"/>
 *     &lt;enumeration value="SystemError"/>
 *     &lt;enumeration value="RequestError"/>
 *     &lt;enumeration value="RegistrationError"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OutcomeTypes")
@XmlEnum
public enum OutcomeTypes {

    @XmlEnumValue("Success")
    SUCCESS("Success"),
    @XmlEnumValue("SystemError")
    SYSTEM_ERROR("SystemError"),
    @XmlEnumValue("RequestError")
    REQUEST_ERROR("RequestError"),
    @XmlEnumValue("RegistrationError")
    REGISTRATION_ERROR("RegistrationError");
    private final String value;

    OutcomeTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OutcomeTypes fromValue(String v) {
        for (OutcomeTypes c: OutcomeTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
