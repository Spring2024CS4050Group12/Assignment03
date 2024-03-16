package assignment.dictionary;

import java.util.*;
import java.io.*;
import java.util.Dictionary;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.BiFunction;

public class MyHashTable<K,V> {
    public V put(K key, V value) {
        return null;
    }

    public V remove(K key) {
        return null;
    }

    public V get(K key) {
        return null;
    }

    public boolean containsKey(Object key) {
        return false;
    }

    // TODO: Consider removing this method and just implementing getKeyIterator
    // this method only exists to avoid changing HashedMapAdaptor, but I can't see
    // any reason to create an iterable only to iterate over it once rather than
    // just producing the iterator directly
    public Iterable<K> keySet() {
        return Collections.emptyList();
    }

    // TODO: ditto
    public Iterable<V> values() {
        return Collections.emptyList();
    }

    public boolean isEmpty() {
        return true;
    }

    public int size() {
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    public void clear() {}
}
