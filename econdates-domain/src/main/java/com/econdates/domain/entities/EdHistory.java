package com.econdates.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = EdHistory.TABLE_NAME)
public class EdHistory {
	
	public static final String TABLE_NAME = "ed_history";
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "edh_actual")
	private String actual;
	
	@Column(name = "edh_consensus")
	private String consensus;
	
	@Column(name = "edh_revised")
	private String revised;
	
	@Column(name = "edh_analysis", columnDefinition="TEXT")
	private String analysis;
	

}
