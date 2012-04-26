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
	private Long id;

	@Column(name = "edc_name")
	private String countryName;

	@Column(name = "edc_currency_name")
	private String currencyName;

	@Column(name = "edc_currency_code")
	private String currencyCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	@JoinColumn(name = "id")
	Collection<EdHoliday> edHolidays;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = com.econdates.domain.entities.EdRegion.class)
	@JoinColumn(name = "id")
	Collection<EdRegion> edRegions;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result
				+ ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result
				+ ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result
				+ ((currencyName == null) ? 0 : currencyName.hashCode());
		result = prime * result + ((fips == null) ? 0 : fips.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iso2 == null) ? 0 : iso2.hashCode());
		result = prime * result + ((iso3 == null) ? 0 : iso3.hashCode());
		result = prime * result
				+ ((mapReference == null) ? 0 : mapReference.hashCode());
		result = prime * result
				+ ((mobileExtension == null) ? 0 : mobileExtension.hashCode());
		result = prime
				* result
				+ ((nationalityPlural == null) ? 0 : nationalityPlural
						.hashCode());
		result = prime
				* result
				+ ((nationalitySingular == null) ? 0 : nationalitySingular
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdCountry other = (EdCountry) obj;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (currencyName == null) {
			if (other.currencyName != null)
				return false;
		} else if (!currencyName.equals(other.currencyName))
			return false;
		if (fips == null) {
			if (other.fips != null)
				return false;
		} else if (!fips.equals(other.fips))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (iso2 == null) {
			if (other.iso2 != null)
				return false;
		} else if (!iso2.equals(other.iso2))
			return false;
		if (iso3 == null) {
			if (other.iso3 != null)
				return false;
		} else if (!iso3.equals(other.iso3))
			return false;
		if (mapReference == null) {
			if (other.mapReference != null)
				return false;
		} else if (!mapReference.equals(other.mapReference))
			return false;
		if (mobileExtension == null) {
			if (other.mobileExtension != null)
				return false;
		} else if (!mobileExtension.equals(other.mobileExtension))
			return false;
		if (nationalityPlural == null) {
			if (other.nationalityPlural != null)
				return false;
		} else if (!nationalityPlural.equals(other.nationalityPlural))
			return false;
		if (nationalitySingular == null) {
			if (other.nationalitySingular != null)
				return false;
		} else if (!nationalitySingular.equals(other.nationalitySingular))
			return false;
		return true;
	}
	
	

}