package com.econdates.domain.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


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
	
	@Temporal(TemporalType.DATE)
	@Column(name="edh_release_date")
	private Date releaseDate;
	
	@Column(name = "edh_previous")
	private String previous;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "edh_indicator_id")
	private EdIndicator edIndicator;
	
	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public String getConsensus() {
		return consensus;
	}

	public void setConsensus(String consensus) {
		this.consensus = consensus;
	}

	public String getRevised() {
		return revised;
	}

	public void setRevised(String revised) {
		this.revised = revised;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public EdIndicator getEdIndicator() {
		return edIndicator;
	}

	public void setEdIndicator(EdIndicator edIndicator) {
		this.edIndicator = edIndicator;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, new String[] { "id",
				"analysis","revised" });
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
