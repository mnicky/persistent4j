package persistent4j;

/** Interface of persistent hash map. */
interface IPersistentMap<K,V> {

    //**************** GETTERS *********************************************//

    /** Returns the number of elements in this map. */
    public long size();

    //**************** PUBLIC METHODS **************************************//

    /** Returns whether this map is empty. */
    public boolean isEmpty();

    /** Returns whether this map contains element with 'key'. */
    public boolean contains(K key);

    //TODO:
    // /** Returns whether this map contains element with 'value'. */
    // public boolean containsValue(V value);

    /** Returns 'value' of element with 'key' from this map or null if not contained. */
    public V get(K key);

    /** Returns this map with element having 'key' and 'value' added (if not already present). */
    public IPersistentMap add(K key, V value);

    /** Returns this map with element having 'key' removed. */
    public IPersistentMap del(K key);

    //TODO:
    // /** Returns this map with all elements having 'value' removed. */
    // public IPersistentMap delAll(V val);

    /** Returns this map with value of element with 'key' replaced by 'newValue'. */
    public IPersistentMap set(K key, V newValue);

}
