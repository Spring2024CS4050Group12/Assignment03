package assignment.dictionary;

import java.util.*;

public class MyHashTable<K, V> {
    static final boolean debugging = false;
    // The domain size of the HashTable
    static final int domainSize = 500;
    // The actual storage are of the HashTable
    Node<K, V>[] nodeStore = new Node[domainSize];
    private int size = 0;

    public V put(K key, V value) {
        int hash = hash(key);
        if (nodeStore[hash] == null) {
            Node<K, V> node = new Node<K, V>(key, value);
            nodeStore[hash] = node;
            if (debugging) System.out.printf("No collision at hash %d for string %s\n\tThat value from the nodeStore is: %s\n", hash, key, nodeStore[hash].getKey());
        } else {
            // COLLISION
            // Loop insertParent till end of list or key = passed key
            Node<K, V> insertParent = nodeStore[hash];
            while (insertParent.getNextNode() != null) {
                if (insertParent.getKey().equals(key)) break;
                insertParent = insertParent.getNextNode();
            }

            // If key exists, replace value and return old value
            if (insertParent.getKey().equals(key)) {
                if (debugging) System.out.printf("REPLACING: Collision at hash %d for string %s\n", hash, key);
                V holdValue = insertParent.getValue();
                insertParent.setValue(value);
                size ++;
                return holdValue;
            }

            // If key didn't exist append to end of list
            if (debugging) System.out.printf("APPENDING: Collision at hash %d for string %s\n", hash, key);
            Node<K, V> node = new Node<K, V>(key, value);
            insertParent.setNextNode(node);
        }

        size++;
        return null;
    }

    private Node<K, V> findNode(K key) {
        int hash = hash(key);

        Node<K, V> node = nodeStore[hash];

        while (node != null) {
            if (node.getKey().equals(key)) return node;
            node = node.getNextNode();
        }

        return null;
    }

    public V remove(K key) {
        int hash = hash(key);
        Node<K, V> node = nodeStore[hash];

        if (node.getKey().equals(key)) {
            V holdValue = node.getValue();
            nodeStore[hash] = null;
            size--;
            return holdValue;
        } else {
            while (node.getNextNode() != null) {
                if (node.getNextNode().getKey().equals(key)) break;
                node = node.getNextNode();
            }
            // If node's parent wasn't found, return null
            if (!node.getNextNode().getKey().equals(key)) return null;
            // If node doesn't have child remove node and return node value
            if (node.getNextNode().getNextNode() == null) {
                Node<K, V> foundNode = node.getNextNode();
                node.setNextNode(null);
                size--;
                return foundNode.getValue();
            }
            // If node has child, suture and return node value
            Node<K, V> foundNode = node.getNextNode();
            node.setNextNode(foundNode.getNextNode());
            size--;
            return foundNode.getValue();
        }
    }

    public V get(K key) {
        Node<K, V> node = findNode(key);

        return (node == null ? null : node.getValue());
    }

    public boolean containsKey(Object key) {
        Node<K, V> node = findNode((K) key);

        return (node != null);
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
        return (size == 0);
    }

    public int size() {
        return size;
    }

    // Am I stupid? Why would you want to check equality of two hashmaps?
    @Override
    public boolean equals(Object other) {
        return false;
    }

    public void clear() {
        Node<K, V>[] nodeStore = new Node[domainSize];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % domainSize);
    }
}
