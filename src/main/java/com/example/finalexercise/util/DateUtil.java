package com.example.finalexercise.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.finalexercise.exception.BusinessException;

public final class DateUtil {
	
	private DateUtil() {}

	public static LocalDate parse(String date) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return LocalDate.parse(date, formatter);
		} catch (Exception e) {
			throw new BusinessException("Cannot parse date + "+date);
		}
	}
	
	public static String format(LocalDate date) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return formatter.format(date);
		} catch (Exception e) {
			throw new BusinessException("Cannot format date + "+date);
		}
	}
}
