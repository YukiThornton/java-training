package jpl.ch04.ex01;

/**
 * 動力源を意味するインターフェース.
 * @author okuno
 *
 */
public interface EnergySource {
	
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

}
