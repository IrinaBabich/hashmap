package com.babich.map;

import java.util.ArrayList;

public class HashMap implements Map {
    private static final int INITIAL_CAPACITY = 5;
    private int size;
    private ArrayList<Entry>[] buckets = new ArrayList[INITIAL_CAPACITY];

    @Override
    // если у нас уже был ключ и значение, то когда помещаем новое значение,
    // происходит апдейт и метод нам возвращает новое значение
    public Object put(Object key, Object value) {
        boolean updated = false;
        Object oldValue = null;
        int index = getIndex(key);

        for (Entry entry : buckets[index]) {
            if (key.equals(entry.key)) {
                oldValue = entry.value;
                // подменяем значение, если решили внести вместо существующего oldValue
                entry.value = value;
                updated = true;
            }
        }
        // если не вносим новое значение, делаем возврат того, что было
        if (!updated) {
            buckets[index].add(new Entry(key, value));
            return null;
        }

        return oldValue;
    }

    private int getIndex(Object key) {
        // находим, по какому индексу (в каком ведре)  находится элемент
        return key.hashCode() % buckets.length;
    }

    @Override
    public Object get(Object key) {
        int index = getIndex(key);
        for (Entry entry : buckets[index]) {
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
        return get(key) != null;
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
