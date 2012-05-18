package com.econdates.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

import com.google.common.base.Objects;

@Entity
@Table(name = EdScheduled.TABLE_NAME)
public class EdScheduled extends EdIndicatorValue {

	public static final String TABLE_NAME = "ed_scheduled";

	public EdScheduled() {
	}

	public EdScheduled(long id, String actual, String analysis,
			String consensus, String previous, LocalDate releaseDate,
			String revised, EdIndicator edIndicator) {
		this.id = id;
		this.actual = actual;
		this.analysis = analysis;
		this.consensus = consensus;
		this.previous = previous;
		this.releaseDate = releaseDate;
		this.revised = revised;
		this.edIndicator = new EdIndicator(edIndicator);
	}

	public EdScheduled(EdScheduled edScheduled) {
		this((edScheduled.getId()), edScheduled.getActual(), (edScheduled
				.getAnalysis()), (edScheduled.getConsensus()), (edScheduled
				.getPrevious()), (edScheduled.getReleaseDate()), (edScheduled
				.getRevised()), (edScheduled.getEdIndicator()));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EdIndicatorValue) {
			final EdScheduled other = (EdScheduled) obj;
			return Objects.equal(releaseDate, other.releaseDate)
					&& Objects.equal(previous, other.previous)
					&& Objects.equal(edIndicator, other.edIndicator)
					&& Objects.equal(consensus, other.consensus);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(releaseDate, previous, edIndicator, consensus);
	}

	@Override
	public String toString() {
		return "EdScheduled [Consensus=" + consensus + ", revised=" + revised
				+ ", analysis=" + analysis + ", releaseDate=" + releaseDate
				+ "]";
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

}
