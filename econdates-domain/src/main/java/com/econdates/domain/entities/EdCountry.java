package com.econdates.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity representing countries
 * @author ssinha1
 * 
 */

@Entity
@Table(name = EdCountry.TABLE_NAME)
public class EdCountry {
	
    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "ed_country";
    
    /*
    public enum dayLightSaving {
        On("ON"),
        Off("OFF");

        private String description;

        private dayLightSaving(final String description) {
            this.description = description;
        }

        public String getString() {
            return description;
        }
    }
    */
    
    @Column(name = "id")
    private Long id;
    
    @Column(name="edc_name")
    private String countryName;
    
    @Column(name="edc_currency_name")
    private String currencyName;
    
    @Column(name="edc_currency_code")
    private String currencyCode;
    
    @Column(name="edc_mobile_ext")
    private String mobileExtension;
    
    @Column(name="edc_capital")
    private String capital;
    
    @Column(name="edc_map_reference")
    private String mapReference;
    
    @Column(name="edc_nationality_singular")
    private String nationalitySingular;
    
    @Column(name="edc_nationality_plural")
    private String nationalityPlural;
    
    @Column(name="edc_fips_104")
    private String fips;
    
    @Column(name="edc_iso_2")
    private String iso2;
    
    @Column(name="edc_iso_3")
    private String iso3;
    
    
    
    
    
    
    
    
    
    
    
}