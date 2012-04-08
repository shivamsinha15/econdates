package com.econdates.domain.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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


	@Column(name = "id")
	private Long id;

	@Column(name = "edc_name")
	private String countryName;

	@Column(name = "edc_currency_name")
	private String currencyName;

	@Column(name = "edc_currency_code")
	private String currencyCode;

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

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = EdHoliday.TABLE_NAME, 
	joinColumns = @JoinColumn(name = "id"), 
	inverseJoinColumns = @JoinColumn(name = "id"))
	Collection edHoliday;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = EdRegion.TABLE_NAME, 
	joinColumns = @JoinColumn(name = "id"), 
	inverseJoinColumns = @JoinColumn(name = "id"))
	Collection edRegion;

}