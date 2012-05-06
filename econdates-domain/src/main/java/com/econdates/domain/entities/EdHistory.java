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

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import com.google.common.base.Objects;

/**
 * @author shivamsinha
 * 
 */
@Entity
@Table(name = EdHistory.TABLE_NAME)
public class EdHistory implements Comparable {

	public static final String TABLE_NAME = "ed_history";

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

	@Column(name = "edh_analysis", columnDefinition = "TEXT")
	private String analysis;

	@Temporal(TemporalType.DATE)
	@Column(name = "edh_release_date")
	private Date releaseDate;

	@Column(name = "edh_previous")
	private String previous;

	@Column(nullable = false, name = "edh_validated")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean validated;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "edh_indicator_id")
	private EdIndicator edIndicator;

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

	public EdIndicator getEdIndicator() {
		return edIndicator;
	}

	public void setEdIndicator(EdIndicator edIndicator) {
		this.edIndicator = edIndicator;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof EdHistory) {
			final EdHistory other = (EdHistory) obj;
			return Objects.equal(releaseDate, other.releaseDate)
					&& Objects.equal(previous, other.previous)
					&& Objects.equal(edIndicator, other.edIndicator);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(actual, consensus, revised, revised,
				edIndicator);
	}

	@Override
	public String toString() {
		return "EdHistory [actual=" + actual + ", consensus=" + consensus
				+ ", revised=" + revised + ", analysis=" + analysis
				+ ", releaseDate=" + releaseDate + "]";
	}

	public int compareTo(Object other) {
		LocalDate releaseDate = LocalDate.fromDateFields(this.releaseDate);
		LocalDate otherReleaseDate = LocalDate
				.fromDateFields(((EdHistory) other).getReleaseDate());
		if (releaseDate.isBefore(otherReleaseDate)) {
			return -1;
		} else if (releaseDate.isAfter(otherReleaseDate)) {
			return 1;
		}
		return 0;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

}
