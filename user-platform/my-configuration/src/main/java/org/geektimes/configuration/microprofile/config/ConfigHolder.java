package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;

/**
 * {@link Config} Holder
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-03-21 19:26
 **/
public class ConfigHolder {

    private static final ThreadLocal<Config> securityContext = new ThreadLocal<Config>();


    public static void set(Config context) {

        securityContext.set(context);

    }

    public static Config get() {

        return securityContext.get();

    }

    public static void clear() {

        securityContext.remove();

    }

}
