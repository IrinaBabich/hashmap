package com.babich.map;

public interface Map<K, V> {

    Object put(K key, V value);

    void growSize();

    void putAll (HashMap<K, V> map);

    void putAllIfAbsent(HashMap<K, V> map);

    Object putIfAbsent(K key, V value);

    int getBucketIndex(K key);

    Object get(K key);

    int size();

    boolean isEmpty();

    boolean containsKey(K key);
}
