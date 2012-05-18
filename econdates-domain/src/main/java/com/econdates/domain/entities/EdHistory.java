package com.econdates.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import com.google.common.base.Objects;

/**
 * @author shivamsinha
 * 
 */
@Entity
@Table(name = EdHistory.TABLE_NAME)
public class EdHistory extends EdIndicatorValue {

	public static final String TABLE_NAME = "ed_history";

	@Column(nullable = false, name = "edh_validated")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	protected boolean validated;

	@Override
	public int hashCode() {
		return Objects.hashCode(releaseDate, previous, edIndicator, actual,
				consensus);
	}

	@Override
	public int compareTo(EdIndicatorValue other) {
		LocalDate releaseDate = this.releaseDate;
		LocalDate otherReleaseDate = ((EdIndicatorValue) other)
				.getReleaseDate();
		if (releaseDate.isBefore(otherReleaseDate)) {
			return -1;
		} else if (releaseDate.isAfter(otherReleaseDate)) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EdIndicatorValue) {
			final EdHistory other = (EdHistory) obj;
			return Objects.equal(releaseDate, other.releaseDate)
					&& Objects.equal(previous, other.previous)
					&& Objects.equal(edIndicator, other.edIndicator)
					&& Objects.equal(actual, other.actual)
					&& Objects.equal(consensus, other.consensus);
		} else {
			return false;
		}

	}

	@Override
	public String toString() {
		return "EdHistory [actual=" + actual + ", consensus=" + consensus
				+ ", revised=" + revised + ", analysis=" + analysis
				+ ", releaseDate=" + releaseDate + "]";
	}

	public boolean isValidated() {
		return validated;
	}

	public boolean validate(EdHistory previousEdHistory) {
		return ((this.getPrevious().equals(previousEdHistory.getActual()) || this
				.getPrevious().equals(previousEdHistory.getRevised())));
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public EdIndicator getEdIndicator() {
		return edIndicator;
	}

	public void setEdIndicator(EdIndicator edIndicator) {
		this.edIndicator = edIndicator;
	}

}
