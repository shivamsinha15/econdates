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
	private Long id;

	@Column(name = "edr_country_id")
	private long countryId;

	@Column(name = "edr_name")
	private String name;

	@Column(name = "edr_code")
	private String code;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = com.econdates.domain.entities.EdCity.class)
	@JoinColumn(name = "id")
	Collection<EdCity> edCity;

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + (int) (countryId ^ (countryId >>> 32));
		result = prime * result + ((edCity == null) ? 0 : edCity.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		EdRegion other = (EdRegion) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (countryId != other.countryId)
			return false;
		if (edCity == null) {
			if (other.edCity != null)
				return false;
		} else if (!edCity.equals(other.edCity))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
