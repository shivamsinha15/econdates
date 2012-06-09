package com.econdates.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

@Entity
@Table(name = EdJob.TABLE_NAME)
public class EdJob {

	public static final String TABLE_NAME = "ed_job";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "edj_name")
	private String name;

	@Column(name = "edj_scheduler")
	private String scheduler;

	@Column(name = "edj_executed_date")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
	protected LocalDate executedDate;

	@Column(name = "edj_last_updated")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	protected DateTime lastUpdated;

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

	public String getScheduler() {
		return scheduler;
	}

	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

	public LocalDate getReleaseDate() {
		return executedDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.executedDate = releaseDate;
	}

	public DateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(DateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
