//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.08 at 04:20:04 PM EST 
//


package com.econdates.xignite.xsd.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EventCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EventCode">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.xignite.com/services/}AbstractEconodayObject">
 *       &lt;sequence>
 *         &lt;element name="EventCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventCode", propOrder = {
    "eventCode",
    "eventName"
})
public class EventCode
    extends AbstractEconodayObject
{

    @XmlElement(name = "EventCode")
    protected String eventCode;
    @XmlElement(name = "EventName")
    protected String eventName;

    /**
     * Gets the value of the eventCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * Sets the value of the eventCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventCode(String value) {
        this.eventCode = value;
    }

    /**
     * Gets the value of the eventName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the value of the eventName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventName(String value) {
        this.eventName = value;
    }

}