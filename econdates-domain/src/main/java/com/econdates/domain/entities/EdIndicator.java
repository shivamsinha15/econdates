package com.econdates.domain.entities;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

	@Temporal(TemporalType.TIME)
	@Column(name = "edi_release_time")
	private Date releaseTime;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edi_last_updated")
	private Date lastUpdated;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "edi_country_id")
	private EdCountry edCountry;

	@OneToMany(mappedBy="edIndicator")
	private Set<EdHistory> edHistories;

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

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
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

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public boolean equals(final Object obj) {
		  if(obj instanceof EdIndicator){
		        final EdIndicator other = (EdIndicator) obj;
		        return Objects.equal(name, other.name)
		            && Objects.equal(edCountry, other.edCountry)
		            && Objects.equal(importance, other.importance);
		    } else{
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

}