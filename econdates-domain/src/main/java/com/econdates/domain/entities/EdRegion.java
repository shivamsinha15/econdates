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

@Entity
@Table(name = EdRegion.TABLE_NAME)
public class EdRegion {

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "ed_region";

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "edr_country_id")
	private long countryId;

	@Column(name = "edr_name")
	private String name;

	@Column(name = "edr_code")
	private String code;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = com.econdates.domain.entities.EdCity.class)
	@JoinColumn(name = "edci_region_id")
	Collection<EdCity> edCity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Collection<EdCity> getEdCity() {
		return edCity;
	}

	public void setEdCity(Collection<EdCity> edCity) {
		this.edCity = edCity;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return "EdRegion [id=" + id + ", countryId=" + countryId + ", name="
				+ name + ", code=" + code + "]";
	}
	
}
