package org.geektimes.configuration.microprofile.config.convert;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * {@link Integer} 转换器
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-03-14 20:26
 **/
public class IntegerConverter implements Converter<Integer> {
    @Override
    public Integer convert(String s) throws IllegalArgumentException, NullPointerException {
        return Integer.valueOf(s);
    }
}
