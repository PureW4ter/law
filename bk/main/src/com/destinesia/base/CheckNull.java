package com.destinesia.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

public class CheckNull {

	public static void notNull(Object domain, String... field) {
		BeanWrapperImpl bw = new BeanWrapperImpl(domain);
		for (String f : field) {
			Object value = bw.getPropertyValue(f);
			if (value == null)
				throw new StatusMsg(StatusMsg.CODE_FIELD_INVALID, f + " shouldn't be null");
		}
	}

	public static void notBlank(Object domain, String... field) {
		BeanWrapperImpl bw = new BeanWrapperImpl(domain);
		for (String f : field) {
			Object value = bw.getPropertyValue(f);
			String str = null;
			if (value != null)
				str = String.valueOf(value);
			if (StringUtils.isBlank(str))
				throw new StatusMsg(StatusMsg.CODE_FIELD_INVALID, f + " shouldn't be blank");
		}
	}

}
