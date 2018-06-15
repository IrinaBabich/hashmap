package com.babich.map;

import java.util.ArrayList;
import java.util.Iterator;

public class HashMap<K, V> implements Map<K, V>, Iterable<HashMap<K, V>> {
    private static final int INITIAL_CAPACITY = 5;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private ArrayList<Entry<K, V>>[] buckets;
    private int size;

    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    public HashMap(int bucketsCount) {
        if (bucketsCount <= 0) {
            throw new IllegalArgumentException("Bucket size should be > 0 , bucket count =" + bucketsCount);
        }
        this.buckets = new ArrayList[bucketsCount];
        for (int i = 0; i < bucketsCount; i++) {
            buckets[i] = new ArrayList<>();
        }
    }

    @Override
// если у нас уже был ключ и значение, то когда помещаем новое значение,
// происходит апдейт и метод нам возвращает новое значение
    public V put(K key, V value) {
        boolean updated = false;
        V oldValue = null;
        int index = getBucketIndex(key);

        int threshold = (int) (buckets.length * DEFAULT_LOAD_FACTOR);
        if (size > threshold) {
            growSize();
        }
        for (Entry<K, V> entry : buckets[index]) {
            if (key.equals(entry.key)) {
                oldValue = entry.value;
                // подменяем значение, если решили внести вместо существующего oldValue
                entry.value = value;
                updated = true;
            }
        }
        // если не вносим новое значение, делаем возврат того, что было
        if (!updated) {
            buckets[index].add(new Entry<>(key, value));
            size++;
        }
        return oldValue;
    }

    public void growSize() {
        int newSize = (buckets.length * 2) + 1;
        ArrayList<Entry<K, V>>[] newBuckets = new ArrayList[newSize];
        for (int i = 0; i < newSize; i++) {
            newBuckets[i] = new ArrayList<>();
        }
    }

    // метод копирует одну хеш-мапу в другую
    public void putAll(HashMap<K, V> hashMap) {
        for (Entry<K, V> entry : hashMap) {
            put(entry.key, entry.value);
        }
    }

    public void putAllIfAbsent(HashMap<K, V> hashMap) {
        for (Entry<K, V> entry : hashMap) {
            putIfAbsent(entry.key, entry.value);
        }
    }


    // если ключ не совпадает со значением, возвращает предыдущее значение связанное с ключем
    public V putIfAbsent(K key, V value) {
        V mapValue = (V) get(key);
        if (mapValue == null) {
            mapValue = put(key, mapValue);
        }
        return mapValue;
    }

    // находим, по какому индексу (в каком ведре)  находится элемент
    public int getBucketIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    @Override
    public Object get(K key) {
        int index = getBucketIndex(key);
        for (Entry<K, V> entry : buckets[index]) {
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
    public boolean containsKey(K key) {
        return (key == null ? key == null : key.equals(key));
    }

    // класс-обертка для хранения ключа и значения
    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<Entry<K, V>> {
        private int index;
        private int bucketIndex = 0;
        private int arrayListIndex = 0;

    // возвращает true, если элементы есть
    public boolean hasNext() {
        if (bucketIndex < size);
        return false;
    }

    // возвращает последующее значение при итерации, идем Next-ом по 2-мерному массиву,
    // в котором хранится HashMap

    public Entry<K, V> next() {
            for (int i = 0; i < buckets.length; i++) {
                for (int j = 0; j < arrayListIndex; j++) {
                    index++;
                    return buckets[bucketIndex].get(arrayListIndex++);
                }
                bucketIndex++;
                arrayListIndex = 0;
            }
            index++;
            return null;
        }
    }
}
