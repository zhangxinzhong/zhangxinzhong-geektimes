package org.geektimes.cache.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * 类型转换器
 *
 * @author: <a href="mailto:zhangxz_email@163.com">ZhangXinZhong</a>
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-04-11 20:18
 **/
public class TypeConverterSupport {

    private List<Converter> converters = new ArrayList();

    public TypeConverterSupport() {
        converters.add(new StringConverter());
    }

    public Converter getConverter(Class<?> requiredKeyType, Class<?> requiredValueType) {
        return converters.stream().filter(c -> c.isSupport(requiredKeyType, requiredValueType)).findFirst().orElseThrow(() -> new RuntimeException("not found converter"));
    }
}
