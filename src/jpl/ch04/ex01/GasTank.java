package jpl.ch04.ex01;

/**
 * 動力源としてのガスタンクを意味するクラス.
 * EnergySourceインターフェース(動力源)の実装クラスで、
 * empty, fillUp, useメソッドを実装している。
 * @author okuno
 *
 */
public class GasTank implements EnergySource {
	
	// ++++++ fields ++++++
	/**
	 * ガスタンクの最大ガス量を保持するフィールド.
	 */
	private final double MAX_GAS_LEVEL;
	
	/**
	 * 現在ガスタンクにあるガス量を保持するフィールド.
	 */
	private double gasLevel;

	
	// ++++++ constructors ++++++
	/**
	 * 最大ガス量とガス量の初期値を設定して、初期化するコンストラクタ.
	 * @param maxGasLevel
	 * @param initialGasLevel
	 * @throws WrongEnegryUsageException maxGasLevelが0以下、initialGasLevelが0より小さい時にthrowされる
	 */
	public GasTank(double maxGasLevel, double initialGasLevel) throws WrongEnegryUsageException {
		if (maxGasLevel <= 0 || initialGasLevel < 0 || maxGasLevel < initialGasLevel) {
			throw new WrongEnegryUsageException("Invalid parameters.");
		}
		MAX_GAS_LEVEL = maxGasLevel;
		this.gasLevel = initialGasLevel;
	}

	/**
	 * 最大ガス量を設定して、初期化するコンストラクタ.
	 * ガス量の初期値は最大ガス量と同様となる。
	 * @param maxGasLevel
	 * @throws WrongEnegryUsageException maxGasLevelが0以下の時にthrowされる
	 */
	public GasTank(double maxGasLevel) throws WrongEnegryUsageException {
		this(maxGasLevel, maxGasLevel);
	}

	// ++++++ Methods only for testing purpose +++++++
	double getMaxGasLevel() {
		return MAX_GAS_LEVEL;
	}
	
	double getGasLevel() {
		return gasLevel;
	}
	
	
	// ++++++ other methods ++++++
	/**
	 * ガスタンクが空かどうか判定し、空の場合はtrueを返す.
	 * @return 空の場合はtrue
	 */
	public boolean empty() {
		return (gasLevel <= 0);
	}
	
	/**
	 * ガスタンクを満タンにする.
	 */
	public void fillUp() {
		gasLevel = MAX_GAS_LEVEL;
	}
	
	/**
	 * ガスを使用するためのメソッド.
	 * 現在のガス量から引数で指定されたガス量を減算する
	 * @param amount 使用するガス量
	 * @throws WrongEnegryUsageException amountが0より小さい時にthrowされる
	 * @throws OutOfEnergyException amountが現在のガス量より大きい時にthrowされる
	 */
	public void use(double amount) throws WrongEnegryUsageException, OutOfEnergyException {
		if (amount < 0) {
			throw new WrongEnegryUsageException("Invalid parameter.");
		}
		double result = gasLevel - amount;
		if (result < 0) {
			throw new OutOfEnergyException("Not enough energy is left.");			
		}
		
		gasLevel = result;
	}

}
