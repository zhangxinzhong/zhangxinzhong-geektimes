package org.geektimes.cache.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.codec.RedisCodec;
import org.geektimes.cache.AbstractCacheManager;
import org.geektimes.cache.convert.Converter;
import org.geektimes.cache.convert.TypeConverterSupport;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Properties;

/**
 * 基于 {@code Lettuce} 的 AbstractCacheManager 实现
 *
 * @author: <a href="mailto:zhangxz_email@163.com">ZhangXinZhong</a>
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-04-11 19:34
 **/
public class LettuceCacheManager extends AbstractCacheManager {

    private final RedisClient redisClient;
    private final TypeConverterSupport typeConverterSupport;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        this.redisClient = RedisClient.create(uri.toString());
        typeConverterSupport = new TypeConverterSupport();
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        Converter keyConverter = concludeConverter(configuration.getKeyType(), configuration.getValueType());
        return new LettuceCache(this, cacheName, configuration, redisClient.connect(new KeyRedisCodec(keyConverter)));
    }

    private <K, V> Converter concludeConverter(Class<K> keyType, Class<V> valueType) {

        return typeConverterSupport.getConverter(keyType, valueType);
    }


    @Override
    protected void doClose() {
        redisClient.shutdown();
    }

    static class KeyRedisCodec implements RedisCodec {

        private final Converter converter;

        KeyRedisCodec(Converter converter) {
            this.converter = converter;
        }


        @Override
        public Object decodeKey(ByteBuffer bytes) {
            return converter.deCode(bytes);
        }

        @Override
        public Object decodeValue(ByteBuffer bytes) {
            return converter.deCode(bytes);
        }

        @Override
        public ByteBuffer encodeKey(Object key) {
            return ByteBuffer.wrap(((String) converter.enCode(key)).getBytes());
        }

        @Override
        public ByteBuffer encodeValue(Object value) {
            return null;
        }
    }
}
