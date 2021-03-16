package org.geektimes.configuration.microprofile.config.convert;

import com.sun.istack.Nullable;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * 转换器
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-03-14 20:28
 **/
public class ConverterSupport {

    Map<Class<?>, Converter> converters;
    Map<Class<?>, Converter> defaultConverters;


    /**
     * 设置指定的转换器
     * @param requiredType
     * @param converter
     */
    public void setConverter(Class<?> requiredType, Converter converter) {
        if (converters == null) {
            converters = new HashMap<>();
        }
        converters.put(requiredType, converter);
    }

    /**
     * 通过类型获取转换器
     * @param requiredType
     * @return
     */
    @Nullable
    public Converter getConverter(Class<?> requiredType) {
        if (converters == null) {
            createDefaultConverter();
            return getDefaultConverter(requiredType);
        }
        return converters.get(requiredType);
    }

    /**
     * 创建默认的
     */
    private void createDefaultConverter() {
        if (defaultConverters == null) {
            defaultConverters = new HashMap<>();
        }
        defaultConverters.put(String.class, new StringConverter());
        defaultConverters.put(Integer.class, new IntegerConverter());
    }

    private Converter getDefaultConverter(Class<?> requiredType) {
        return defaultConverters.get(requiredType);
    }


}
