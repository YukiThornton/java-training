package jpl.ch04.ex01;

/**
 * 動力源としてのバッテリーを意味するクラス.
 * EnergySourceインターフェース(動力源)の実装クラスで、
 * empty, fillUp, useメソッドを実装している。
 * @author okuno
 *
 */
public class Battery implements EnergySource {

	// ++++++ fields ++++++
	/**
	 * バッテリーの最大電力を保持するフィールド.
	 */
	private final double MAX_AMOUNT;
	
	/**
	 * 現在バッテリーに充電されている電力を保持するフィールド.
	 */
	private double currentAmount;

	// ++++++ constructors ++++++
	/**
	 * 最大電力と充電済み電力を設定して、初期化するコンストラクタ.
	 * @param maxAmount
	 * @param initialAmount
	 * @throws WrongEnegryUsageException maxAmountが0以下、initialAmountが0より小さい時にthrowされる
	 */
	public Battery(double maxAmount, double initialAmount) throws WrongEnegryUsageException {
		if (maxAmount <= 0 || initialAmount < 0 || maxAmount < initialAmount) {
			throw new WrongEnegryUsageException("Invalid parameters.");
		}
		MAX_AMOUNT = maxAmount;
		this.currentAmount = initialAmount;
	}

	/**
	 * 最大電力を設定して、初期化するコンストラクタ.
	 * 充電済み電力は最大電力と同様となる。
	 * @param maxAmount
	 * @throws WrongEnegryUsageException maxAmountが0以下の時にthrowされる
	 */
	public Battery(double maxAmount) throws WrongEnegryUsageException {
		this(maxAmount, maxAmount);
	}

	
	// ++++++ Methods only for testing purpose +++++++
	double getMaxAmountl() {
		return MAX_AMOUNT;
	}
	
	double getCurrentAmount() {
		return currentAmount;
	}
	
	
	// ++++++ other methods ++++++
	/**
	 * バッテリーが空かどうか判定し、空の場合はtrueを返す.
	 * @return 空の場合はtrue
	 */
	public boolean empty() {
		return (currentAmount <= 0);
	}

	/**
	 * バッテリーの最大電力まで充電する
	 */
	public void fillUp() {
		currentAmount = MAX_AMOUNT;

	}

	/**
	 * バッテリーを使用するためのメソッド.
	 * 現在の電力から引数で指定された電力を減算する
	 * @param amount 使用する電力
	 * @throws WrongEnegryUsageException amountが0より小さい時にthrowされる
	 * @throws OutOfEnergyException amountが現在のガス量より大きい時にthrowされる
	 */
	public void use(double amount) throws WrongEnegryUsageException, OutOfEnergyException {
		if (amount < 0) {
			throw new WrongEnegryUsageException("Invalid parameter.");
		}
		double result = currentAmount - amount;
		if (result < 0) {
			throw new OutOfEnergyException("Not enough energy is left.");			
		}
		
		currentAmount = result;
	}

}
