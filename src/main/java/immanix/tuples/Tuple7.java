package immanix.tuples;

public class Tuple7<T1, T2, T3, T4, T5, T6, T7> extends Tuple6<T1, T2, T3, T4, T5, T6> {
	public final T7 _7;
	public Tuple7(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
		super( _1,  _2,  _3,  _4,  _5,  _6);
		this._7 = _7;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Tuple7)) return false;

		Tuple7 tuple = (Tuple7) o;
		if (_1 != null ? !_1.equals(tuple._1) : tuple._1 != null) return false;
		if (_2 != null ? !_2.equals(tuple._2) : tuple._2 != null) return false;
		if (_3 != null ? !_3.equals(tuple._3) : tuple._3 != null) return false;
		if (_4 != null ? !_4.equals(tuple._4) : tuple._4 != null) return false;
		if (_5 != null ? !_5.equals(tuple._5) : tuple._5 != null) return false;
		if (_6 != null ? !_6.equals(tuple._6) : tuple._6 != null) return false;
		if (_7 != null ? !_7.equals(tuple._7) : tuple._7 != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = _1 != null ? _1.hashCode() : 0;
		result = 31 * result + (_2 != null ? _2.hashCode() : 0);
		result = 31 * result + (_3 != null ? _3.hashCode() : 0);
		result = 31 * result + (_4 != null ? _4.hashCode() : 0);
		result = 31 * result + (_5 != null ? _5.hashCode() : 0);
		result = 31 * result + (_6 != null ? _6.hashCode() : 0);
		result = 31 * result + (_7 != null ? _7.hashCode() : 0);
		return result;
	}
}