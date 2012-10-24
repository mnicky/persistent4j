package persistent4j;

/** Interface of persistent binary search tree. */
interface IPersistentTree<T> {

    //**************** GETTERS *********************************************//

    /** Returns the left child of this node. */
    public IPersistentTree<T> left();

    /** Returns the value stored in this node. */
    public T value();

    /** Returns the right child of this node. */
    public IPersistentTree<T> right();

    /** Returns the number of non-empty nodes in this subtree. */
    public long size();

    //**************** PUBLIC METHODS **************************************//

    /** Returns whether this tree is empty. */
    public boolean isEmpty();

    /** Returns whether this tree contains 'value'. */
    public boolean contains(T value);

    /** Returns 'value' from this tree or null if not contained. */
    public T get(T value);

    /** Returns this tree with 'value' added (if not already present). */
    public IPersistentTree<T> add(T value);

    /** Returns this tree with 'value' removed. */
    public IPersistentTree<T> del(T value);

    /** Returns this tree with 'oldValue' replaced by 'newValue'. */
    public IPersistentTree<T> set(T oldValue, T newValue);

}
