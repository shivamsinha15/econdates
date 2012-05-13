package com.econdates.domain.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author shivamsinha
 * 
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EdIndicatorValue implements Comparable<EdIndicatorValue> {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	protected Long id;

	@Column(name = "edh_actual")
	protected String actual;

	@Column(name = "edh_consensus")
	protected String consensus;

	@Column(name = "edh_revised")
	protected String revised;

	@Column(name = "edh_analysis", columnDefinition = "TEXT")
	protected String analysis;

	@Temporal(TemporalType.DATE)
	@Column(name = "edh_release_date")
	protected Date releaseDate;

	@Column(name = "edh_previous")
	protected String previous;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "edh_indicator_id")
	protected EdIndicator edIndicator;

	public abstract boolean equals(final Object obj);

	public abstract int hashCode();

	public abstract String toString();

	public abstract int compareTo(EdIndicatorValue other);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public EdIndicator getEdIndicator() {
		return edIndicator;
	}

	public void setEdIndicator(EdIndicator edIndicator) {
		this.edIndicator = edIndicator;
	}

}
