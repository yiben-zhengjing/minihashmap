package com.minihashmap.example;


import java.util.ArrayList;
import java.util.List;

public class MyHashMap<K, V> implements MyMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int defaultInitSize;

    private float defaultLoadFactor;

    private int entryUseSize;

    private Entry<K, V>[] table = null;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);

    }

    public MyHashMap(int defaultInitSize, float defaultLoadFactor) {

        if (defaultInitSize < 0) {
            throw new IllegalArgumentException("Illegal initial capacity:" + defaultInitSize);
        }

        if (defaultLoadFactor <= 0 || Float.isNaN(defaultLoadFactor)) {

            throw new IllegalArgumentException("Illegal load factor:" + defaultLoadFactor);
        }


        this.defaultInitSize = defaultInitSize;
        this.defaultLoadFactor = defaultLoadFactor;
        table = new Entry[defaultInitSize];
    }

    @Override
    public V put(K k, V v) {

        V oldValue = null;

        if (entryUseSize >= defaultInitSize * defaultLoadFactor) {
            resize(2 * defaultInitSize);
        }

        int index = hash(k) & (defaultInitSize - 1);

        if (table[index] == null) {
            table[index] = new Entry<K, V>(k, v, null);
            ++entryUseSize;
        } else {
            Entry<K, V> entry = table[index];
            Entry<K, V> e = entry;
            while (e != null) {
                if (k == e.getKey() || k.equals(e.getKey())) {
                    oldValue = e.value;
                    e.value = v;
                    return oldValue;
                }
                e = e.next;
            }
            table[index] = new Entry<>(k, v, entry);
            ++entryUseSize;

        }
        return oldValue;

    }

    private void resize(int i) {

        Entry[] newTable = new Entry[i];

        defaultInitSize = i;
        entryUseSize = 0;
        rehash(newTable);

    }

    private void rehash(Entry[] newTable) {
        List<Entry<K, V>> entryList = new ArrayList<Entry<K, V>>();
        for (Entry entry : table) {
            if (entry != null) {
                do {
                    entryList.add(entry);
                    entry = entry.next;
                } while (entry != null);
            }
        }

        if (newTable.length > 0) {
            table = newTable;
        }

        for (Entry<K, V> entry : entryList) {
            put(entry.getKey(), entry.getValue());

        }
    }


    private int hash(K k) {
        int hashCode = k.hashCode();
        hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
        return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
    }

    @Override
    public V get(K k) {

        int index = hash(k) & (defaultInitSize - 1);

        if (table[index] == null) {
            return null;
        } else {
            Entry<K, V> entry = table[index];
            do {
                if (k == entry.getKey() || k.equals(entry.getKey())) {
                    return entry.value;
                }
                entry = entry.next;
            } while (entry != null);

        }

        return null;


    }


    class Entry<K, V> implements MyMap.Entry<K, V> {

        private K key;

        private V value;

        private Entry<K, V> next;


        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
    }
}
