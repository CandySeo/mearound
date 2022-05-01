package com.candyseo.mearound.etl.redis;

public interface RedisService<K, V> {
    
    public void save(K key, V object);

    public V get(K key);

}
