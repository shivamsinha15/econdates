package com.econdates.domain.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = EdHoliday.TABLE_NAME)
public class EdHoliday {

	public static final String TABLE_NAME = "ed_holiday";
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "edh_name")
	private String name;

	@Column(name = "edh_date")
	private Date date;

	@Column(nullable = false, name = "edh_market_close")
	@Type(type = "numeric_boolean")
	private boolean marketClose;
	
	@Column(name="edh_market_name")
	private String marketName;

}
