package com.econdates.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = EdCity.TABLE_NAME)
public class EdCity {

	public static final String TABLE_NAME = "ed_city";
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "edci_name")
	private String name;

	@Column(name = "edci_latitude")
	private String latitude;

	@Column(name = "edc_longitute")
	private String longitude;

	@Column(name = "edci_time_zone")
	private String timeZone;

	@Column(name = "edci_time_offest")
	private String timeOffest;

	@Column(name = "edci_code")
	private String code;
	
	@Column(name = "edci_daylight_saving")
	@Type(type = "numeric_boolean")
	private boolean dayLightSaving;
	
	

}
