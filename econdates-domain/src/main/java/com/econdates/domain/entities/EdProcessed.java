package com.econdates.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = EdProcessed.TABLE_NAME)
public class EdProcessed extends EdIndicatorValue {

	public static final String TABLE_NAME = "ed_processed";

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(EdIndicatorValue other) {
		// TODO Auto-generated method stub
		return 0;
	}

}
