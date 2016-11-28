package jpl.ch03.ex06;

/**
 * 動力源を意味する抽象クラス.
 * @author okuno
 *
 */
public abstract class EnergySource {
	/**
	 * 動力源が空かどうか判定し、空の場合はtrueを返す.
	 * @return 空の場合はtrue
	 */
	public abstract boolean empty();
	
	/**
	 * 動力を満タンにする.
	 */
	public abstract void fillUp();
	
	/**
	 * 動力を使用するためのメソッド.
	 * 現在の動力から引数で指定された量の動力を減算する
	 * @param cost 使用する動力の量
	 * @throws WrongEnegryUsageException 
	 * @throws OutOfEnergyException 
	 */
	public abstract void use(double cost) throws WrongEnegryUsageException, OutOfEnergyException;
	
	
	/**
	 * EnergySourceクラスとそのサブクラスにおいて使われるネスト例外クラス.
	 * Exceptionクラスを拡張する。
	 * 主にメソッドが誤用された時に、例外メッセージを設定して初期化され、使用される
	 * @author okuno
	 *
	 */
	@SuppressWarnings("serial")
	protected class WrongEnegryUsageException extends Exception {
		public WrongEnegryUsageException(String message) {
			super(message);
		}
	}
}
