package org.geektimes.cache.convert;

/**
 * String byte[] Converter
 *
 * @author: <a href="mailto:zhangxz_email@163.com">ZhangXinZhong</a>
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-04-11 20:35
 **/
public class StringConverter implements Converter<byte[], String> {
    @Override
    public boolean isSupport(Class<? extends byte[]> bytes, Class<? extends String> s) {
        return byte[].class.isAssignableFrom(bytes.getClass()) && String.class.isAssignableFrom(s.getClass());
    }

    @Override
    public String deCode(byte[] bytes) {
        return new String(bytes);
    }

    @Override
    public byte[] enCode(String s) {
        return s.getBytes();
    }
}
