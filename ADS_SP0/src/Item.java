
class Item<T extends Comparable<? super T>> implements Comparable {

    T elt;

    Item(T e) {
        elt = e;
    }

    public String toString() {
        return elt.toString();
    }

    @SuppressWarnings("unchecked")
    public int compareTo(Object o) {
        if (o != null) {
            return elt.compareTo(((Item<T>) o).elt);
        } else {
            return 0;
        }
    }
}
