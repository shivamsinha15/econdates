package com.econdates.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import com.google.common.base.Objects;

@Entity
@Table(name = EdHoliday.TABLE_NAME)
public class EdHoliday {

	public static final String TABLE_NAME = "ed_holiday";

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "edh_name")
	private String name;

	@Column(name = "edh_date")
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
	private LocalDate date;

	@Column(nullable = false, name = "edh_market_close")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean marketClose;

	@Column(name = "edh_exchange_name")
	private String marketName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "edh_country_id")
	private EdCountry edCountry;

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof EdHoliday) {
			final EdHoliday other = (EdHoliday) obj;
			return Objects.equal(name, other.name)
					&& Objects.equal(date, other.date)
					&& Objects.equal(marketName, other.marketName)
					&& Objects.equal(marketClose, other.marketClose)
					&& Objects.equal(edCountry, other.edCountry);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, date, marketName, marketClose, edCountry);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public EdCountry getEdCountry() {
		return edCountry;
	}

	public void setEdCountry(EdCountry edCountry) {
		this.edCountry = edCountry;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMarketClose() {
		return marketClose;
	}

	public void setMarketClose(boolean marketClose) {
		this.marketClose = marketClose;
	}

	@Override
	public String toString() {
		return "EdHoliday [id=" + id + ", name=" + name + ", date=" + date
				+ ", marketClose=" + marketClose + ", marketName=" + marketName
				+ ", edCountry=" + edCountry + "]";
	}

}
