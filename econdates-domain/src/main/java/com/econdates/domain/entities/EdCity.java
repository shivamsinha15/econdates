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

	@Column(name = "edci_country_id")
	private Long countryId;

	@Column(name = "edci_region_id")
	private Long regionID;

	@Column(name = "edci_name")
	private String name;

	@Column(name = "edci_latitude")
	private String latitude;

	@Column(name = "edci_longitude")
	private String longitude;
	
	@Column(name = "edci_time_zone")
	private String timeZone;

	@Column(name = "edci_time_offset")
	private String timeOffest;

	@Column(name = "edci_code")
	private String code;

	@Column(name = "edci_daylight_saving")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean dayLightSaving;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getTimeOffest() {
		return timeOffest;
	}

	public void setTimeOffest(String timeOffest) {
		this.timeOffest = timeOffest;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDayLightSaving() {
		return dayLightSaving;
	}

	public void setDayLightSaving(boolean dayLightSaving) {
		this.dayLightSaving = dayLightSaving;
	}

	@Override
	public String toString() {
		return "EdCity [id=" + id + ", countryId=" + countryId + ", regionID="
				+ regionID + ", name=" + name + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", timeZone=" + timeZone
				+ ", timeOffest=" + timeOffest + ", code=" + code
				+ ", dayLightSaving=" + dayLightSaving + "]";
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getRegionID() {
		return regionID;
	}

	public void setRegionID(Long regionID) {
		this.regionID = regionID;
	}

}
