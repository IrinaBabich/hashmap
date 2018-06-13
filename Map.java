package com.babich.map;

public interface Map {

    Object put(Object key, Object value);

    void growSize();

    void putAll (HashMap map);
    
    void putAllIfAbsent(HashMap map);

    Object putIfAbsent(Object key, Object value);
    
    int getBucketIndex(Object key);

    Object get(Object key);

    int size();

    boolean isEmpty();

    boolean containsKey(Object key);
}

