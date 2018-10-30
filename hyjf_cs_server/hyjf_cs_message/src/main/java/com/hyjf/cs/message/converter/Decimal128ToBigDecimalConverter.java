package com.hyjf.cs.message.converter;

import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * @author xiasq
 * @version Decimal128ToBigDecimalConverter, v0.1 2018/10/29 9:21
 */
public class Decimal128ToBigDecimalConverter implements Converter<Decimal128, BigDecimal> {

    @Override
	public BigDecimal convert(Decimal128 decimal128) {
		return decimal128.bigDecimalValue();
	}
}
