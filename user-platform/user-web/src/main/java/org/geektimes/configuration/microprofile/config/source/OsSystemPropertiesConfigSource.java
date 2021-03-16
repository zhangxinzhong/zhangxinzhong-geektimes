package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;
import java.util.Set;

/**
 * 系统环境变量
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-03-14 19:42
 **/
public class OsSystemPropertiesConfigSource implements ConfigSource {

    private final Map<String, String> properties;

    public OsSystemPropertiesConfigSource() {
        properties = System.getenv();
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public String getValue(String s) {
        return properties.get(s);
    }

    @Override
    public String getName() {
        return "System OS Properties";
    }
}
