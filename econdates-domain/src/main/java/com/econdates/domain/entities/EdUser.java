package com.econdates.domain.entities;

import hirondelle.date4j.DateTime;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = EdUser.TABLE_NAME)
public class EdUser {

	public static final String TABLE_NAME = "ed_user";
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name ="edu_firstname")
	private String firstName;
	
	@Column(name ="edu_lastName")
	private String lastName;
	
	@Column(name = "edu_mobile_number")
	private String mobileNumber;
	
	@Column(name = "edu_mobile_type")
	private String mobileType;
	
	@Column(name ="edu_mobile_id")
	private String mobileId;
	
	@Column(name ="edu_primary_id")
	private String primaryEmail;
	
	@Column(name ="edu_secondary_email")
	private String secondaryEmail;
	
	@Column(name = "edu_password")
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edu_created_date")
	private DateTime createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edi_last_login")
	private DateTime lastLogin;
	
	@Column(name = "edu_ip_address")
	private String ipAddress;

	@Column(name = "edu_role")
	private String role;
	
	@ManyToMany(
			targetEntity=com.econdates.domain.entities.EdIndicator.class,
			fetch = FetchType.EAGER
			)
	@JoinTable(
			name = EdUserIndicator.TABLE_NAME,
			joinColumns=@JoinColumn(name="edu_id"),
			inverseJoinColumns=@JoinColumn(name="edi_id")
			)
	Collection edIndicators;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "edu_city_id")
	private EdCity edCity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "edu_country_id")
	private EdCountry edCountry;
	
}
