package com.babich.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HashMap implements Map {
    private static final int INITIAL_CAPACITY = 5;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int size;
    private static ArrayList<Entry>[] bucket;

    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    public HashMap(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("Bucket size should be > 0 , bucket count =" + bucketCount);
        }
        bucket = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            bucket[i] = new ArrayList<>();
        }
    }

    @Override
    // если у нас уже был ключ и значение, то когда помещаем новое значение,
    // происходит апдейт и метод нам возвращает новое значение
    public Object put(Object key, Object value) {
        boolean updated = false;
        Object oldValue = null;
        int index = getBucketIndex(key);
        int threshold = (int) (bucket.length * DEFAULT_LOAD_FACTOR);
        if (size > threshold) {
            growSize();
        }
        for (Entry entry : bucket[index]) {
            if (key.equals(entry.key)) {
                oldValue = entry.value;
                // подменяем значение, если решили внести вместо существующего oldValue
                entry.value = value;
                updated = true;
            }
        }

        // если не вносим новое значение, делаем возврат того, что было
        if (!updated) {
            bucket[index].add(new Entry(key, value));
            size++;
        }
        return oldValue;
    }

    private void growSize() {
        int newSize = bucket.length * 2;
        ArrayList<Entry>[] newBuckets = new ArrayList[newSize];
        for (int i = 0; i < newSize; i++) {
            newBuckets[i] = new ArrayList<>();
        }
    }

    // метод копирует одну хеш-мапу в другую
    public void putAll (HashMap map){
        Iterator<ArrayList<Entry>> iterator = map.iterator();
        while (iterator.hasNext()) {
            ArrayList<Entry> listEntries = iterator.next();
            for (Entry entry : listEntries) {
                put(entry.key, entry.value);
            }
        }
    }

    public void putAllIfAbsent(HashMap map) {
        Iterator<ArrayList<Entry>> iterator = map.iterator();
        while (iterator.hasNext()) {
            List<Entry> listEntries = iterator.next();
            for (Entry entry : listEntries) {
                putIfAbsent(entry.key, entry.value);
            }
        }
    }

    // если ключ не совпадает со значением, возвращает предыдущее значение связанное с ключем
    public Object putIfAbsent(Object key, Object value) {
        if (!HashMap.containsKey(key))
            return HashMap(key, value);
        else
            return HashMap.get(key);
    }

    // находим, по какому индексу (в каком ведре)  находится элемент
    private int getBucketIndex(Object key) {
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
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return (key == null ? key == null : key.equals(key));
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

    public Iterator iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator {
        int current;

        public boolean hasNext() {
            return current != bucket.length;
        }

        public ArrayList<Entry> next() {
            int i = current;
            current++;
            return bucket[i];
        }
    }
}
