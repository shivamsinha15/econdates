package com.econdates.domain.entities;

import hirondelle.date4j.DateTime;

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Entity representing a economic indicator
 * 
 * @author ssinha1
 * 
 */

@Entity
@Table(name = EdIndicator.TABLE_NAME)
public class EdIndicator {

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
	
	@Column(name = "edi_description", columnDefinition="TEXT")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edi_release_time")
	private DateTime releaseTime;
	
	@Column(name = "edi_release_frequency")
	private Integer releaseFrequency;
	
	@Column(name ="edi_release_day_of_week")
	private Integer releaseDayOfWeek;
	
	@Column(name ="edi_release_day_of_month")
	private Integer releaseDayOfMonth;
	
	@Column(name = "release_url")
	private String releaseUrl;
	
	@Column(name = "release_page")
	private String releasePage;





	@Column(name = "source_report")
	private String sourceReport;



	@Column(name = "analysis")
	private String analysis;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	private EdCountry edCountry;

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

	public DateTime getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(DateTime dateTime) {
		this.releaseTime = dateTime;
	}


	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
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

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, new String[] { "id",
				"description" });
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
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

}