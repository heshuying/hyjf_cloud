package com.hyjf.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

	public Date convert(String text) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}