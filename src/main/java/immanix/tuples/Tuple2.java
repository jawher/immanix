package immanix.tuples;

public class Tuple2<T1, T2> {
    public final T1 _1;
    public final T2 _2;

    public Tuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple2)) return false;

        Tuple2 tuple = (Tuple2) o;

        if (_1 != null ? !_1.equals(tuple._1) : tuple._1 != null) return false;
        if (_2 != null ? !_2.equals(tuple._2) : tuple._2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = _1 != null ? _1.hashCode() : 0;
        result = 31 * result + (_2 != null ? _2.hashCode() : 0);
        return result;
    }
}
