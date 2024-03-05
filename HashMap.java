
/**
 * File: HashMap.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose:  A custom implementation of a hash map using a separate chaining method.
 * It includes common methods and properties for a HashMap.
 */
import java.util.ArrayList;

public class HashMap<K, V> implements MapSet<K, V> {

    /**
     * The representation of a Node
     */
    private static class Node<K, V> extends KeyValuePair<K, V> {
        Node<K, V> next;

        /**
         * The constructor for a Node
         * 
         * @param key   the key
         * @param value the value
         * @param next  the next pointer
         */
        public Node(K key, V value, Node<K, V> next) {
            super(key, value);
            this.next = next;
        }

    }

    protected int size; // the size
    private Node<K, V>[] buckets; // the buckets
    private double maxLoadFactor; // the max load factor
    private boolean customHash; // whether or not using custom hash

    /**
     * The constrcutor for HashMap
     * 
     * @param initialCapacity the Initial Capacity
     * @param maxLoadFactor   the Max Load Factor
     * @param customHash      Custom Hash or Not
     */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity, double maxLoadFactor, boolean customHash) {
        this.buckets = new Node[initialCapacity];
        this.size = 0;
        this.maxLoadFactor = maxLoadFactor;
        this.customHash = customHash;
    }

    /**
     * The constructor for the HashMap
     * 
     * @param initialCapacity The Initial Capacity
     * @param customHash      Custon Hash or Not
     */
    public HashMap(int initialCapacity, boolean customHash) {
        this(initialCapacity, 0.75, customHash);
    }

    /**
     * The constructor for the HashMap
     * 
     * @param customHash boolean if using a custom Hash
     */
    public HashMap(boolean customHash) {
        this(16, customHash);
    }

    /**
     * The constructor for the HashMap
     */
    public HashMap() {
        this(16, false);
    }

    /**
     * Returns capacity of the bucket.
     */
    public int capacity() {
        return buckets.length;
    }

    /**
     * Hashes key to generate an index number.
     */
    private int hash(K key) {
        if (this.customHash == false) {
            return Math.abs(key.hashCode() % capacity());
        } else { // EXTENSION #2
            String strKey = key.toString();
            int hash = 0;
            for (int i = 0; i < strKey.length(); i++) {
                char c = strKey.charAt(i);
                hash = (hash * 31 + c) % capacity(); // Using prime number as a multiplier
            }
            return hash;
        }
    }

    /**
     * Returns the size of the HashMap
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * The clear method for the buckets
     */
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        size = 0;
        maxLoadFactor = 0.75;
        buckets = new HashMap.Node[1];
    }

    @Override
    /**
     * Places a key-value pair into the hashmap.
     */
    public V put(K key, V value) {
        int index = hash(key);

        if (buckets[index] == null) {
            buckets[index] = new Node<K, V>(key, value, null);
        } else {
            for (Node<K, V> curNode = buckets[index]; curNode != null; curNode = curNode.next) {
                if (curNode.getKey().equals(key)) {
                    V oldVal = curNode.getValue();
                    curNode.setValue(value);
                    return oldVal;
                }
            }

            buckets[index] = new Node<>(key, value, buckets[index]);
        }

        size++;
        if (size > capacity() * maxLoadFactor) {
            resize(capacity() * 2);
        }

        return null;
    }

    /**
     * Resizes the number of hashmap buckets based on given newCapacity.
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Node<K, V>[] oldBuckets = buckets;
        buckets = (Node<K, V>[]) new Node[newCapacity];
        size = 0;

        for (Node<K, V> bucket : oldBuckets) {
            for (Node<K, V> curNode = bucket; curNode != null; curNode = curNode.next) {
                put(curNode.getKey(), curNode.getValue());
            }
        }
    }

    @Override
    /**
     * Obtains value of item with given key from the hashmap.
     */
    public V get(K key) {
        int index = hash(key);

        if (buckets[index] == null) {
            return null;
        } else {
            for (Node<K, V> curNode = buckets[index]; curNode != null; curNode = curNode.next) {
                if (curNode.getKey().equals(key)) {
                    V curVal = curNode.getValue();
                    return curVal;
                }
            }
            return null;
        }
    }

    /**
     * Removes key-value pair from the hashmap.
     * 
     * @param key to be removed
     * @return value of the key that has been removed
     *         or null if key doesn't exist
     */
    public V remove(K key) {
        int index = hash(key);

        Node<K, V> curNode = buckets[index];

        if (buckets[index] == null) {
            return null;
        } else {
            if (curNode.getKey().equals(key)) {
                // remove head
                buckets[index] = curNode.next; // changes head to next
                size--;

                if (size < maxLoadFactor * capacity() / 4) {
                    resize(capacity() / 2);
                }

                return curNode.getValue();
            }

            while (curNode.next != null) {
                if (curNode.next.getKey().equals(key)) {
                    // found node to be removed, and its not the head
                    Node<K, V> removedNode = curNode.next;
                    curNode.next = curNode.next.next; // kill the removedNode in between
                    size--;

                    if (size < maxLoadFactor * capacity() / 4) {
                        resize(capacity() / 2);
                    }

                    return removedNode.getValue();
                }
                curNode = curNode.next;
            }

        }
        return null;
    }

    @Override
    /**
     * Checks if map contains the given key or not.
     */
    public boolean containsKey(K key) {
        int index = hash(key);

        if (buckets[index] == null) {
            return false;
        }

        for (Node<K, V> curNode = buckets[index]; curNode != null; curNode = curNode.next) {
            if (curNode.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns ArrayList of all keys in the map.
     */
    public ArrayList<K> keySet() {
        ArrayList<K> keySetArray = new ArrayList<K>();

        for (Node<K, V> bucket : buckets) {
            for (Node<K, V> curNode = bucket; curNode != null; curNode = curNode.next) {
                keySetArray.add(curNode.getKey());
            }
        }

        return keySetArray;
    }

    /**
     * Returns ArrayList of all values in the map.
     */
    public ArrayList<V> values() {
        ArrayList<V> valuesArray = new ArrayList<V>();

        for (K key : keySet()) {
            V value = get(key);
            valuesArray.add(value);
        }

        return valuesArray;
    }

    /**
     * Returns ArrayList of all KVP entries in the Map.
     */
    public ArrayList<KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K, V>> entrySetArray = new ArrayList<KeyValuePair<K, V>>();

        for (K key : keySet()) {
            V value = get(key);
            KeyValuePair<K, V> entry = new KeyValuePair<K, V>(key, value);
            entrySetArray.add(entry);
        }

        return entrySetArray;
    }

    /**
     * The toString Method for HashMap
     */
    public String toString() {
        String outputString = "{";
        int checkComma = 0;
        int size = entrySet().size();

        for (KeyValuePair<K, V> entry : entrySet()) {
            checkComma += 1;
            outputString += entry.getKey() + " = " + entry.getValue();

            if (checkComma < size) {
                outputString += ", ";

            }
        }

        outputString += "}";
        return outputString;

    }

    /**
     * Returns maximum depth (how deep we have to search for an item) of the map.
     */
    public int maxDepth() {

        int maxDepth = 0;

        for (Node<K, V> bucket : buckets) {
            int tempDepth = 0;

            for (Node<K, V> curNode = bucket; curNode != null; curNode = curNode.next) {
                tempDepth++;
            }

            if (tempDepth > maxDepth) {
                maxDepth = tempDepth;
            }
        }

        return maxDepth;
    }

    /**
     * The main method for HashMap class
     * 
     * @param args
     */
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>(true);
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("orange", 3);

        // Test the toString method
        System.out.println(map);

        // Test the size method
        System.out.println(map.size + " == 3");

        // Test the get method
        System.out.println(map.get("apple") + " == 1");
        System.out.println(map.get("banana") + " == 2");
        System.out.println(map.get("orange") + " == 3");

        // Test the containsKey method
        System.out.println(map.containsKey("banana") + " == true");
        System.out.println(map.containsKey("pear") + " == false");

        // Test the remove method
        System.out.println(map.remove("orange") + " == 3");
        System.out.println(map.size() + " == 2");
        System.out.println(map.containsKey("orange") + " == false");

        // Test the keySet method
        ArrayList<String> keys = map.keySet();
        System.out.println(keys.contains("apple") + " == true");
        System.out.println(keys.contains("orange") + " == false");

        // Test the values method
        ArrayList<Integer> values = map.values();
        System.out.println(values.contains(1) + " == true");
        System.out.println(values.contains(3) + " == false");

        // Test the entrySet method
        ArrayList<KeyValuePair<String, Integer>> entries = map.entrySet();
        System.out.println(entries.size() + " == 2");
        System.out.println(map.maxDepth() + " == 1");

        // Test the clear method
        map.clear();
        System.out.println(map.size() + " == 0");
        System.out.println(map.containsKey("banana") + " == false");

        // Test the toString method
        System.out.println(map);

        ////////////

        HashMap<String, Integer> map2 = new HashMap<>(true);
        int numItems = 12; // number of items to add to the HashMap
        int[] bucketCounts; // array to store the count of items in each bucket

        // Add items to the HashMap
        for (int i = 0; i < numItems; i++) {
            String key = "Key" + i;
            int value = i;
            map2.put(key, value);
        }

        // Get the current bucket count after resizing
        int numBuckets = map2.capacity();
        bucketCounts = new int[numBuckets];

        // Update the bucket counts after resizing
        for (String key : map2.keySet()) {
            int bucketIndex = map2.hash(key);
            bucketCounts[bucketIndex]++;
        }

        // Print bucket counts
        for (int i = 0; i < numBuckets; i++) {
            System.out.println("Bucket " + i + ": " + bucketCounts[i] + " items");
        }
    }
}
