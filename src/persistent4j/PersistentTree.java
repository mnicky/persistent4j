package persistent4j;

import persistent4j.IPersistentTree;
import java.util.Comparator;

/** Persistent implementation of unbalanced binary search tree. */
public final class PersistentTree implements IPersistentTree {

    //**************** FIELDS **********************************************//

    /** Left child of this node. */
    private final PersistentTree L;

    /** Value stored in this node. */
    private final Object VAL;

    /** Right child of this node. */
    private final PersistentTree R;

    /** Comparator used for this node. */
    private final Comparator COMP;

    /** Number of non-empty nodes in this subtree.
     *  <br><br>
     *
     *  It is computed lazily. The value '-1' means it hasn't been computed yet.
     */
    private long SIZE = -1;

    //**************** GETTERS *********************************************//

    /** Returns the left child of this node. */
    public PersistentTree left() {
        return L;
    }

    /** Returns the value stored in this node.
     *  <br><br>
     *
     *  Although this value may be of a mutable type, it should NEVER be
     *  modified. Should you need to modify it, use its deep copy instead.
     */
    public Object value() {
        return VAL;
    }

    /** Returns the right child of this node. */
    public PersistentTree right() {
        return R;
    }

    /** Returns the number of non-empty nodes in this subtree. */
    public long size() {
        // if size is not computed, compute it
        if (SIZE == -1)
            SIZE = compute_size();
        return SIZE;
    }

    //**************** PRIVATE CONSTRUCTORS & METHODS **********************//

    /** Constructor for all the fields. */
    private PersistentTree(PersistentTree left, Object value, PersistentTree right, Comparator comparator, long size) {
        L = left;
        VAL = value;
        R = right;
        COMP = comparator;
        SIZE = size;
    }

    /** Generic compare for VAL fields of the nodes. */
    private int cmp(Object o1, Object o2) {
        // use Comparator
        if (COMP != null)
            return COMP.compare(o1, o2);

        // use Comparable
        if (o1 instanceof Comparable && o2 instanceof Comparable && o1.getClass() == o2.getClass())
            return ((Comparable) o1).compareTo(o2);

        // generic compare
        else {
            if (o1 == o2 || o1.equals(o2))
                return 0;
            return o1.hashCode() - o2.hashCode();
        }
    }

    /** Returns VAL of successor of this node or null if it doesn't have a successor. */
    private Object find_succ_val() {
        if (R == null)
            return null;

        PersistentTree tmp = R;
        while (tmp.L != null)
            tmp = tmp.L;
        return tmp.VAL;
    }

    /** Returns the number of non-empty nodes in this subtree. */
    private int compute_size() {
        return (L == null ? 0 : L.compute_size()) +
                (VAL == null ? 0 : 1) +
                (R == null ? 0 : R.compute_size());
    }

    //**************** PUBLIC CONSTRUCTORS *********************************//

    /** Returns an empty PersistentTree. */
    public PersistentTree() {
        L = null;
        VAL = null;
        R = null;
        COMP = null;
        SIZE = 0;
    }

    /** Returns empty PersistentTree with specified comparator. */
    public PersistentTree(Comparator comparator) {
        L = null;
        VAL = null;
        R = null;
        COMP = comparator;
        SIZE = 0;
    }

    //**************** PUBLIC METHODS **************************************//

    /** Returns whether this PersistentTree is empty. */
    public boolean isEmpty() {
        return VAL == null;
    }

    /** Returns whether this PersistentTree contains 'value'.
     *
     *  @throws NullPointerException If 'value' is null.
     */
    public boolean contains(Object value) {
        if (value == null) throw new NullPointerException("PersistentTree.contains(): argument 'value' is null.");

        return get(value) != null;
    }

    /** Returns 'value' from this PersistentTree or null if not contained.
     *
     *  @throws NullPointerException If 'value' is null.
     */
    public Object get(Object value) {
        if (value == null) throw new NullPointerException("PersistentTree.contains(): argument 'value' is null.");

        if (VAL == null)
            return null;

        if (cmp(value, VAL) == 0)
            return VAL;

        if (cmp(value, VAL) < 0) {
            if (L == null)
                return null;
            return L.get(value);
        }

        else {
            if (R == null)
                return null;
            return R.get(value);
        }
    }

    /** Returns this PersistentTree with 'value' added (if not already present).
     *  <br><br>
     *
     *  The 'value' itself should be of an immutable type. If it isn't, at least
     *  it should NEVER be modified after adding. Should you need to modify it,
     *  use its deep copy instead.
     *
     *  @throws NullPointerException If 'value' is null.
     */
    public PersistentTree add(Object value) {
        if (value == null) throw new NullPointerException("PersistentTree.add(): argument 'value' is null.");

        if (VAL == null)
            return new PersistentTree(null, value, null, COMP, 1);

        if (cmp(value, VAL) == 0)
            return this;

        if (cmp(value, VAL) < 0) {
            PersistentTree tmp_L = L;
            if (tmp_L == null)
                tmp_L = new PersistentTree(COMP);
            return new PersistentTree(tmp_L.add(value), VAL, R, COMP, -1);
        }

        else {
            PersistentTree tmp_R = R;
            if (tmp_R == null)
                tmp_R = new PersistentTree(COMP);
            return new PersistentTree(L, VAL, tmp_R.add(value), COMP, -1);
        }
    }

    /** Returns this PersistentTree with 'value' removed.
     *
     *  @throws NullPointerException If 'value' is null.
     */
    public PersistentTree del(Object value) {
        if (value == null) throw new NullPointerException("PersistentTree.del(): argument 'value' is null.");
        // call the helper method
        return del(value, true);
    }

    /** Returns this PersistentTree with 'value' removed, differentiating between tree root and other nodes.
     *
     *  @throws NullPointerException If 'value' is null.
     */
    private PersistentTree del(Object value, boolean thisIsTreeRoot) {
        if (value == null) throw new NullPointerException("PersistentTree.del(): argument 'value' is null.");

        if (VAL == null)
            return this;

        if (cmp(value, VAL) == 0) {
            if (L == null && R == null)
                // if at root, return the new empty tree, else "delete" the node
                if (thisIsTreeRoot)
                    return new PersistentTree(COMP);
                else
                    return null;
            if (L != null && R == null)
                return L;
            if (L == null && R != null)
                return R;
            else {
                Object succ = this.find_succ_val();
                return new PersistentTree(L, succ, R.del(succ, false), COMP, -1);
            }
        }

        if (cmp(value, VAL) < 0) {
            if (L == null)
                return this;
            return new PersistentTree(L.del(value, false), VAL, R, COMP, -1);
        }

        else {
            if (R == null)
                return this;
            return new PersistentTree(L, VAL, R.del(value, false), COMP, -1);
        }
    }

    /** Returns this PersistentTree with 'oldValue' replaced by 'newValue'.
     *  <br><br>
     *
     *  The 'newValue' itself should be of an immutable type. If it isn't,
     *  at least it should NEVER be modified after adding. Should you need to
     *  modify it, use its deep copy instead.
     *  <br><br>
     *
     *  WARNING: This operation does not prevent from breaking the
     *  contract of element order in binary search trees - it just literally
     *  replaces the 'oldValue' of node with 'newValue'.
     *
     *  @throws NullPointerException If 'oldValue' or 'newValue' is null.
     */
    public PersistentTree set(Object oldValue, Object newValue) {
        if (oldValue == null) throw new NullPointerException("PersistentTree.set(): argument 'oldValue' is null.");
        if (newValue == null) throw new NullPointerException("PersistentTree.set(): argument 'newValue' is null.");

        if (VAL == null)
            return this;

        if (cmp(oldValue, VAL) == 0)
            return new PersistentTree(L, newValue, R, COMP, SIZE);

        if (cmp(oldValue, VAL) < 0) {
            if (L == null)
                return this;
            return new PersistentTree(L.set(oldValue, newValue), VAL, R, COMP, SIZE);
        }

        else {
            if (R == null)
                return this;
            return new PersistentTree(L, VAL, R.set(oldValue, newValue), COMP, SIZE);
        }
    }

    //**************** COMMON PUBLIC METHODS *******************************//

    @Override
    public String toString() {
        return "(" + (L == null ? "" : L.toString() + " ") +
            (VAL == null ? "" : VAL.toString()) +
            (R == null ? "" : " " + R.toString()) + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((L == null) ? 0 : L.hashCode());
        result = prime * result + ((VAL == null) ? 0 : VAL.hashCode());
        result = prime * result + ((R == null) ? 0 : R.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PersistentTree))
            return false;

        PersistentTree other = (PersistentTree) obj;

        if (this.VAL == null) {
            if (other.VAL != null)
            return false;
        }
        else if (!this.VAL.equals(other.VAL))
            return false;
        if (this.L == null) {
            if (other.L != null)
            return false;
        }
        else if (!this.L.equals(other.L))
            return false;
        if (this.R == null) {
            if (other.R != null)
            return false;
        }
        else if (!this.R.equals(other.R))
            return false;
        return true;
    }

}
