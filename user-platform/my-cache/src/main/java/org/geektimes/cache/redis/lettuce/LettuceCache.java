package org.geektimes.cache.redis.lettuce;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Lettuce Cache
 *
 * @author: <a href="mailto:zhangxz_email@163.com">ZhangXinZhong</a>
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-04-11 19:37
 **/
public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache<K, V> {

    private final StatefulRedisConnection<K, V> connection;

    private final RedisCommands<K, V> redisCommands;

    public LettuceCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration, StatefulRedisConnection<K, V> connection) {
        super(cacheManager, cacheName, configuration);
        this.connection = connection;
        redisCommands = connection.sync();
    }


    @Override
    protected void doClose() {
        connection.close();
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        return redisCommands.exists(key) > 0L;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        return ExpirableEntry.of(key, redisCommands.get(key));
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        redisCommands.setGet(entry.getKey(), entry.getValue());
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        return ExpirableEntry.of(key, redisCommands.get(key));
    }

    @Override
    protected void clearEntries() throws CacheException {
        redisCommands.flushall();
    }

    @Override
    protected Set<K> keySet() {
        String pattern = "*";
        return new LinkedHashSet<>(redisCommands.keys((K) pattern));
    }
}
