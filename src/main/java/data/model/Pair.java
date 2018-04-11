package data.model;


import java.io.Serializable;

/**
 * .._  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    4/11/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */
public final class Pair<K,V> implements Serializable {

    /**
     * Left of this <code>Pair</code>.
     */
    private K left;

    /**
     * Right of this this <code>Pair</code>.
     */
    private V right;

    /**
     * Gets the left for this pair.
     * @return left for this pair
     */
    public K getLeft() { return left; }

    /**
     * Gets the right for this pair.
     * @return right for this pair
     */
    public V getRight() { return right; }

    /**
     * Creates a new pair
     * @param left The left for this pair
     * @param right The right to use for this pair
     */
    public Pair(@NamedArg("left") K left, @NamedArg("right") V right) {
        this.left = left;
        this.right = right;
    }

    /**
     * <p><code>String</code> representation of this
     * <code>Pair</code>.</p>
     *
     * <p>The default left/right delimiter '=' is always used.</p>
     *
     *  @return <code>String</code> representation of this <code>Pair</code>
     */
    @Override
    public String toString() {
        return left + "=" + right;
    }

    /**
     * <p>Generate a hash code for this <code>Pair</code>.</p>
     *
     * <p>The hash code is calculated using both the left and
     * the right of the <code>Pair</code>.</p>
     *
     * @return hash code for this <code>Pair</code>
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (left != null ? left.hashCode() : 0);
        hash = 31 * hash + (right != null ? right.hashCode() : 0);
        return hash;
    }

    /**
     * <p>Test this <code>Pair</code> for equality with another
     * <code>Object</code>.</p>
     *
     * <p>If the <code>Object</code> to be tested is not a
     * <code>Pair</code> or is <code>null</code>, then this method
     * returns <code>false</code>.</p>
     *
     * <p>Two <code>Pair</code>s are considered equal if and only if
     * both the lefts and rights are equal.</p>
     *
     * @param o the <code>Object</code> to test for
     * equality with this <code>Pair</code>
     * @return <code>true</code> if the given <code>Object</code> is
     * equal to this <code>Pair</code> else <code>false</code>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Pair) {
            Pair pair = (Pair) o;
            if (left != null ? !left.equals(pair.left) : pair.left != null) return false;
            if (right != null ? !right.equals(pair.right) : pair.right != null) return false;
            return true;
        }
        return false;
    }
}
