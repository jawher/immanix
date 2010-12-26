package immanix.tuples;

public class Tuple5<T1, T2, T3, T4, T5> extends Tuple4<T1, T2, T3, T4> {
	public final T5 _5;
	public Tuple5(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
		super( _1,  _2,  _3,  _4);
		this._5 = _5;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Tuple5)) return false;

		Tuple5 tuple = (Tuple5) o;
		if (_1 != null ? !_1.equals(tuple._1) : tuple._1 != null) return false;
		if (_2 != null ? !_2.equals(tuple._2) : tuple._2 != null) return false;
		if (_3 != null ? !_3.equals(tuple._3) : tuple._3 != null) return false;
		if (_4 != null ? !_4.equals(tuple._4) : tuple._4 != null) return false;
		if (_5 != null ? !_5.equals(tuple._5) : tuple._5 != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = _1 != null ? _1.hashCode() : 0;
		result = 31 * result + (_2 != null ? _2.hashCode() : 0);
		result = 31 * result + (_3 != null ? _3.hashCode() : 0);
		result = 31 * result + (_4 != null ? _4.hashCode() : 0);
		result = 31 * result + (_5 != null ? _5.hashCode() : 0);
		return result;
	}
}