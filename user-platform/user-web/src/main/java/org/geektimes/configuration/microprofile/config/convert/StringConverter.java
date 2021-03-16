package org.geektimes.configuration.microprofile.config.convert;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * {@link String} converter
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-03-14 20:23
 **/
public class StringConverter implements Converter<String> {
    @Override
    public String convert(String s) throws IllegalArgumentException, NullPointerException {
        return s;
    }
}
