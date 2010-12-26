package immanix.tuples;

public class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {
	public final T3 _3;
	public Tuple3(T1 _1, T2 _2, T3 _3) {
		super( _1,  _2);
		this._3 = _3;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Tuple3)) return false;

		Tuple3 tuple = (Tuple3) o;
		if (_1 != null ? !_1.equals(tuple._1) : tuple._1 != null) return false;
		if (_2 != null ? !_2.equals(tuple._2) : tuple._2 != null) return false;
		if (_3 != null ? !_3.equals(tuple._3) : tuple._3 != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = _1 != null ? _1.hashCode() : 0;
		result = 31 * result + (_2 != null ? _2.hashCode() : 0);
		result = 31 * result + (_3 != null ? _3.hashCode() : 0);
		return result;
	}
}