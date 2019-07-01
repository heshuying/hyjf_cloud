package com.hyjf.data.convert;

import org.bson.types.Decimal128;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * @author xiasq
 * @version BigDecimalToDecimal128Converter, v0.1 2018/10/29 9:20
 */
public class BigDecimalToDecimal128Converter implements Converter<BigDecimal, Decimal128> {

    private Logger logger = LoggerFactory.getLogger(BigDecimalToDecimal128Converter.class);

    @Override
    public Decimal128 convert(BigDecimal bigDecimal) {
        Decimal128 decimal128 = null;
        try {
            decimal128 = new Decimal128(bigDecimal);
        } catch (NumberFormatException e) {
            logger.error("传入的bigDecimal参数值不正确，使用了非准确的值，请使用new BigDecimal(String)构造函数...", e);
        } catch (Exception e) {
            logger.error("BigDecimal to Decimal128 error", e);
            throw new RuntimeException("BigDecimal to Decimal128 error");
        }
        return decimal128;
    }
}
