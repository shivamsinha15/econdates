package com.econdates.domain.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import com.google.common.base.Objects;

/**
 * Entity representing a economic indicator
 * 
 * @author ssinha1
 * 
 */

@Entity
@Table(name = EdIndicator.TABLE_NAME)
public class EdIndicator {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "ed_indicator";

	public enum Importance {
		High("high"), Medium("medium"), Low("low");

		private String description;

		private Importance(final String description) {
			this.description = description;
		}

		public String getString() {
			return description;
		}
	}

	public EdIndicator() {
	}

	public EdIndicator(long id, String name, Importance importance,
			String description, LocalTime releaseTime,
			Integer releaseFrequency, String releaseUrl, String sourceReport,
			DateTime lastUpdated, EdCountry edCountry) {
		this.id = id;
		this.name = name;
		this.importance = importance;
		this.description = description;
		this.releaseTime = releaseTime;
		this.releaseFrequency = releaseFrequency;
		this.releaseUrl = releaseUrl;
		this.sourceReport = sourceReport;
		this.lastUpdated = lastUpdated;
		this.edCountry = new EdCountry(edCountry);
	}

	public EdIndicator(EdIndicator edIndicator) {
		this((edIndicator.getId()), (edIndicator.getName()), (edIndicator
				.getImportance()), (edIndicator.getDescription()), (edIndicator
				.getReleaseTime()), (edIndicator.getReleaseFrequency()),
				(edIndicator.getReleaseUrl()), (edIndicator.getSourceReport()),
				(edIndicator.getLastUpdated()), (edIndicator.getEdCountry()));
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "edi_name")
	private String name;

	@Column(name = "edi_importance")
	@Enumerated(EnumType.STRING)
	private Importance importance;

	@Column(name = "edi_description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "edi_release_time")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	private LocalTime releaseTime;

	@Column(name = "edi_release_frequency")
	private Integer releaseFrequency;

	@Column(name = "edi_release_day_of_week")
	private Integer releaseDayOfWeek;

	@Column(name = "edi_release_day_of_month")
	private Integer releaseDayOfMonth;

	@Column(name = "edi_release_url")
	private String releaseUrl;

	@Column(name = "edi_release_page")
	private String releasePage;

	@Column(name = "edi_source_report")
	private String sourceReport;

	@Column(name = "edi_last_updated")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime lastUpdated;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "edi_country_id")
	private EdCountry edCountry;

	@OneToMany(mappedBy = "edIndicator")
	private Set<EdHistory> edHistories;

	@OneToMany(mappedBy = "edIndicator")
	private Set<EdScheduled> edScheduled;

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

	public Importance getImportance() {
		return importance;
	}

	public void setImportance(Importance importance) {
		this.importance = importance;
	}

	public String getReleaseUrl() {
		return releaseUrl;
	}

	public void setReleaseUrl(String releaseUrl) {
		this.releaseUrl = releaseUrl;
	}

	public LocalTime getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(LocalTime releaseTime) {
		this.releaseTime = releaseTime;
	}

	public void setSourceReport(String sourceReport) {
		this.sourceReport = sourceReport;
	}

	public String getSourceReport() {
		return sourceReport;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setEdCountry(EdCountry edCountry) {
		this.edCountry = edCountry;
	}

	public EdCountry getEdCountry() {
		return edCountry;
	}

	public void setEdHistories(Set<EdHistory> edHistories2) {
		this.edHistories = edHistories2;
	}

	public Set<EdHistory> getEdHistories() {
		return edHistories;
	}

	public void setReleaseDayOfWeek(Integer releaseDayOfWeek) {
		this.releaseDayOfWeek = releaseDayOfWeek;
	}

	public Integer getReleaseDayOfWeek() {
		return releaseDayOfWeek;
	}

	public void setReleasePage(String releasePage) {
		this.releasePage = releasePage;
	}

	public String getReleasePage() {
		return releasePage;
	}

	public void setReleaseDayOfMonth(Integer releaseDayOfMonth) {
		this.releaseDayOfMonth = releaseDayOfMonth;
	}

	public Integer getReleaseDayOfMonth() {
		return releaseDayOfMonth;
	}

	public DateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(DateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof EdIndicator) {
			final EdIndicator other = (EdIndicator) obj;
			return Objects.equal(name, other.name)
					&& Objects.equal(edCountry, other.edCountry)
					&& Objects.equal(importance, other.importance);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, edCountry, importance);
	}

	@Override
	public String toString() {
		return "EdIndicator [id=" + id + ", name=" + name + ", importance="
				+ importance + ", description=" + description
				+ ", releaseTime=" + releaseTime + ", releaseFrequency="
				+ releaseFrequency + ", releaseDayOfWeek=" + releaseDayOfWeek
				+ ", releaseDayOfMonth=" + releaseDayOfMonth + ", releaseUrl="
				+ releaseUrl + ", releasePage=" + releasePage
				+ ", sourceReport=" + sourceReport + ", lastUpdated="
				+ lastUpdated + " edi_country_id = " + edCountry.getId() + "]";
	}

	public Integer getReleaseFrequency() {
		return releaseFrequency;
	}

	public void setReleaseFrequency(Integer releaseFrequency) {
		this.releaseFrequency = releaseFrequency;
	}

	public Set<EdScheduled> getEdScheduled() {
		return edScheduled;
	}

	public void setEdScheduled(Set<EdScheduled> edScheduled) {
		this.edScheduled = edScheduled;
	}

}