package com.econdates.domain.entities;

import javax.persistence.Entity;
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
}