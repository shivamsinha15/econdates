package com.econdates.util;

import java.lang.reflect.Field;

public class ReflectionUtil {

	/**
	 * Sets the value of the field in the given object.
	 * 
	 * @param obj
	 *            the object whose field is to be set.
	 * @param fieldName
	 *            the name of the field.
	 * @param value
	 *            the value of the field.
	 */
	public static void setField(Object obj, String fieldName, Object value) {
		try {
			Field field = null;
			try {
				field = obj.getClass().getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				field = obj.getClass().getSuperclass()
						.getDeclaredField(fieldName);
			}
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Could not set the field value.", e);
		}
	}

	/**
	 * Retrieves the value of a private field on the given object.
	 * 
	 * @param <T>
	 *            the type of object to return.
	 * @param obj
	 *            the object to inspect.
	 * @param fieldName
	 *            the name of the field on the object.
	 * @return the value of the field, of type T.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getField(Object obj, String fieldName) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return (T) field.get(obj);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Could not get the field value.", e);
		}
	}

}