/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月18日 下午6:20:16
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateEditor extends PropertyEditorSupport {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	private boolean emptyAsNull;

	private String dateFormat = DEFAULT_DATE_FORMAT;

	public DateEditor(boolean emptyAsNull) {
		this.emptyAsNull = emptyAsNull;
	}

	public DateEditor(boolean emptyAsNull, String dateFormat) {
		this.emptyAsNull = emptyAsNull;
		this.dateFormat = dateFormat;
	}

	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return value != null ? DateFormatUtils.format(value, dateFormat) : StringUtils.EMPTY;
	}

	@Override
	public void setAsText(String text) {
		if (text != null) {
			String value = text.trim();
			if (emptyAsNull && StringUtils.isEmpty(text)) {
				setValue(null);
			} else {
				try {
					setValue(DateUtils.parseDate(value, DATE_PATTERNS));
				} catch (ParseException e) {
					setValue(null);
				}
			}
		} else {
			setValue(null);
		}
	}

}
