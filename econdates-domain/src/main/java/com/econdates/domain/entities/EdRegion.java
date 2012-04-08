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
	
	public static final String TABLE_NAME = "ed_region";
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="edr_name")
	private String name;
	
	@Column(name="edr_code")
	private String code;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = EdCity.TABLE_NAME,
			joinColumns = @JoinColumn(name="id"),
			inverseJoinColumns = @JoinColumn(name = "id")
	)
    Collection edCity;
	

}
