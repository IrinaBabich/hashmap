package com.babich.map;

import java.util.ArrayList;

public class HashMap implements Map {
    private static final int INITIAL_CAPACITY = 5;
    private int size;
    private ArrayList<Entry>[] bucket;

    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    public HashMap(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("Bucket size should be > 0 , bucket count =" + bucketCount);
        }
        this.bucket = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            bucket[i] = new ArrayList<>();
        }
    }


    @Override
    // если у нас уже был ключ и значение, то когда помещаем новое значение,
    // происходит апдейт и метод нам возвращает новое значение
    public Object put(Object key, Object value) {
        int index = getBucketIndex(key);

        ArrayList<Entry> bucket = this.bucket[index];
        for (Entry entry : bucket) {
            if (key.equals(entry.key)) {
                Object oldValue = entry.value;
                // подменяем значение, если решили внести вместо существующего oldValue
                entry.value = value;
                return oldValue;
            }
        }
        // если не вносим новое значение, делаем возврат того, что было
        bucket.add(new Entry(key, value));
        size++;

        return null;
    }

    private int getBucketIndex(Object key) {
        // находим, по какому индексу (в каком ведре)  находится элемент
        return Math.abs(key.hashCode()) % bucket.length;
    }

    @Override
    public Object get(Object key) {
        int index = getBucketIndex(key);
        for (Entry entry : bucket[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return (key==null ? key==null : key.equals(key));
    }

    // класс-обертка для хранения ключа и значения
    private static class Entry {
        private Object key;
        private Object value;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
