package persistent4j;

import persistent4j.IPersistentTree;
import persistent4j.PersistentTree;
import persistent4j.IPersistentMap;
import java.util.Comparator;

/** Implementation of persistent hash map. */
public final class PersistentMap<K,V> implements IPersistentMap<K,V>{

    //**************** INNER CLASSES ***************************************//

    /** Persistent entry of the map. */
    private final static class PersistentMapElement<K,V> {

        /** Key of this map element. */
        public final K key;

        /** Value of this map element. */
        public final V val;

        /** Returns new PersistentMapElement containing 'key' and 'value'. */
        public PersistentMapElement(K key, V val) {
            this.key = key;
            this.val = val;
        }

    	@Override
        public String toString() {
            return "[" + this.key + ", " + this.val + "]";
        }

        @Override
        /** WARNING: the field 'val' is COMPLETELY IGNORED when generating hash! */
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.key == null) ? 0 : this.key.hashCode());
            return result;
        }

        @Override
        /** WARNING: the field 'val' is COMPLETELY IGNORED when comparing! */
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (!(obj instanceof PersistentMapElement))
                return false;

            PersistentMapElement other = (PersistentMapElement) obj;

            if (this.key == null) {
                if (other.key != null)
                    return false;
            }
            else if (!this.key.equals(other.key))
                return false;
            return true;
        }


    }

    //**************** FIELDS **********************************************//

    /** Elements of this map. **/
    private final IPersistentTree<PersistentMapElement<K,V>> elements;

    //**************** GETTERS *********************************************//

    /** Returns the number of elements in this map. **/
    public long size() {
        return elements.size();
    }

    //**************** PRIVATE CONSTRUCTORS & METHODS **********************//

    /** Constructor for all the fields. */
    private PersistentMap(IPersistentTree<PersistentMapElement<K,V>> elements) {
        this.elements = elements;
    }

    //**************** PUBLIC CONSTRUCTORS *********************************//

    /** Returns an empty PersistentMap. */
    public PersistentMap() {
        elements = new PersistentTree();
    }

    //TODO: <? super K>
    /** Returns an empty PersistentMap with specified comparator. */
    public PersistentMap(Comparator<K> comparator) {
        elements = new PersistentTree(comparator);
    }

    //**************** PUBLIC METHODS **************************************//

    /** Returns whether this map is empty. */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /** Returns whether this map contains element with 'key'. */
    public boolean contains(K key) {
        return get(key) != null;
    }

    /** Returns 'value' of element with 'key' from this map or null if not contained. */
    public V get(K key) {
        final PersistentMapElement<K,V> el = elements.get(new PersistentMapElement<K,V>(key, null));
        if (el != null)
          return el.val;
        return null;
    }

    /** Returns this map with element having 'key' and 'value' added (if not already present). */
    public PersistentMap<K,V> add(K key, V value) {
        return new PersistentMap<K,V>(elements.add(new PersistentMapElement<K,V>(key, value)));
    }

    /** Returns this map with element having 'key' removed. */
    public PersistentMap<K,V> del(K key) {
        return new PersistentMap<K,V>(elements.del(new PersistentMapElement<K,V>(key, null)));
    }

    /** Returns this map with value of element with 'key' replaced by 'newValue'. */
    public PersistentMap<K,V> set(K key, V newValue) {
        return new PersistentMap<K,V>(elements.set(new PersistentMapElement<K,V>(key, null), new PersistentMapElement<K,V>(key, newValue)));
    }

    //**************** COMMON PUBLIC METHODS *******************************//

    @Override
    public String toString() {
        return "{" + ((PersistentTree) this.elements).print() + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.elements == null) ? 0 : this.elements.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PersistentMap))
            return false;

        PersistentMap other = (PersistentMap) obj;

        if (this.elements == null) {
            if (other.elements != null)
            return false;
        }
        else if (!this.elements.equals(other.elements))
            return false;
        return true;
    }
}
