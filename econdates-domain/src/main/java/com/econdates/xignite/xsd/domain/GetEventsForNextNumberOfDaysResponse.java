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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetEventsForNextNumberOfDaysResult" type="{http://www.xignite.com/services/}EventSummaries" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getEventsForNextNumberOfDaysResult"
})
@XmlRootElement(name = "GetEventsForNextNumberOfDaysResponse")
public class GetEventsForNextNumberOfDaysResponse {

    @XmlElement(name = "GetEventsForNextNumberOfDaysResult")
    protected EventSummaries getEventsForNextNumberOfDaysResult;

    /**
     * Gets the value of the getEventsForNextNumberOfDaysResult property.
     * 
     * @return
     *     possible object is
     *     {@link EventSummaries }
     *     
     */
    public EventSummaries getGetEventsForNextNumberOfDaysResult() {
        return getEventsForNextNumberOfDaysResult;
    }

    /**
     * Sets the value of the getEventsForNextNumberOfDaysResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventSummaries }
     *     
     */
    public void setGetEventsForNextNumberOfDaysResult(EventSummaries value) {
        this.getEventsForNextNumberOfDaysResult = value;
    }

}
