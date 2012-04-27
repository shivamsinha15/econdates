package com.econdates.domain.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity representing countries
 * 
 * @author ssinha1
 * 
 */

@Entity
@Table(name = EdCountry.TABLE_NAME)
public class EdCountry {

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "ed_country";

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "edc_name")
	private String countryName;

	@Column(name = "edc_currency_name")
	private String currencyName;

	@Column(name = "edc_currency_code")
	private String currencyCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public String toString() {
		return "EdCountry [id=" + id + ", countryName=" + countryName
				+ ", currencyName=" + currencyName + ", currencyCode="
				+ currencyCode + ", mobileExtension=" + mobileExtension
				+ ", capital=" + capital + ", mapReference=" + mapReference
				+ ", edHoliday=" + edHolidays + ", edRegion=" + edRegions + "]";
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getMobileExtension() {
		return mobileExtension;
	}

	public void setMobileExtension(String mobileExtension) {
		this.mobileExtension = mobileExtension;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getMapReference() {
		return mapReference;
	}

	public void setMapReference(String mapReference) {
		this.mapReference = mapReference;
	}

	public String getNationalitySingular() {
		return nationalitySingular;
	}

	public void setNationalitySingular(String nationalitySingular) {
		this.nationalitySingular = nationalitySingular;
	}

	public String getNationalityPlural() {
		return nationalityPlural;
	}

	public void setNationalityPlural(String nationalityPlural) {
		this.nationalityPlural = nationalityPlural;
	}

	public String getFips() {
		return fips;
	}

	public void setFips(String fips) {
		this.fips = fips;
	}

	public String getIso2() {
		return iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public Collection<EdHoliday> getEdHolidays() {
		return edHolidays;
	}

	public void setEdHoliday(Collection<EdHoliday> edHoliday) {
		this.edHolidays = edHoliday;
	}

	public Collection<EdRegion> getEdRegions() {
		return edRegions;
	}

	public void setEdRegion(Collection<EdRegion> edRegion) {
		this.edRegions = edRegion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}

	@Column(name = "edc_mobile_ext")
	private String mobileExtension;

	@Column(name = "edc_capital")
	private String capital;

	@Column(name = "edc_map_reference")
	private String mapReference;

	@Column(name = "edc_nationality_singular")
	private String nationalitySingular;

	@Column(name = "edc_nationality_plural")
	private String nationalityPlural;

	@Column(name = "edc_fips_104")
	private String fips;

	@Column(name = "edc_iso_2")
	private String iso2;

	@Column(name = "edc_iso_3")
	private String iso3;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = com.econdates.domain.entities.EdHoliday.class)
	@JoinColumn(name = "edh_country_id")
	Collection<EdHoliday> edHolidays;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = com.econdates.domain.entities.EdRegion.class)
	@JoinColumn(name = "edr_country_id")
	Collection<EdRegion> edRegions;


}