package com.econdates.util;

import java.util.Date;

public class GeneralUtil {

	public static <T> T ifNullReturnNull(T checkIfNull) {
		if (checkIfNull == null) {
			return null;
		} else {
			return checkIfNull;
		}
	}

	public static Date ifDateNullReturnNull(Date date) {
		if (date == null) {
			return null;
		} else {
			return new Date(date.getTime());
		}
	}
}
