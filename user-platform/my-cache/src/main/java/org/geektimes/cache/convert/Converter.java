package org.geektimes.cache.convert;


/**
 * 转换器
 *
 * @author: <a href="mailto:zhangxz_email@163.com">ZhangXinZhong</a>
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-04-11 20:20
 **/
public interface Converter<D, E> {

    boolean isSupport(Class<? extends D> d, Class<? extends E> e);


    /**
     * 反序列化
     *
     * @param d
     * @return
     */
    E deCode(D d);

    /**
     * 序列化
     *
     * @param e
     * @return
     */
    D enCode(E e);


}
