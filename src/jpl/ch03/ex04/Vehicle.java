package jpl.ch03.ex04;

public class Vehicle {
	
	// ++++++ fields ++++++
	private static long nextId = 1;
	public static final String TURN_LEFT = "TURN_LEFT";
	public static final String TURN_RIGHT = "TURN_RIGHT";
	
	private final long ID;
	private double speed;
	private double direction;
	private String owner;
	

	// ++++++ constructors ++++++
	public Vehicle() {
		ID = nextId++;		
	}
	
	public Vehicle(String owner) {
		this();
		this.owner = owner;
	}
	
	/*
	 *  各privateフィールドに対する単純なsetter, 
	 *  getterは、メソッド名と仕様が完全に一致することから変更の余地がないことから、
	 *  finalを設定しても有用性の制限にはならないと考えた。
	 *  よってfinalメソッドにした。
	 */
	// ++++++ accessor methods ++++++
	public static final long getNextId() {
		return nextId;
	}
	
	// CAUTION! This method is only for testing purpose.
	protected static void setNextId(long nextId) {
		Vehicle.nextId = nextId;
	}
	
	public final long getId() {
		return ID;
	}
	
	public final double getSpeed() {
		return speed;
	}
	
	public final void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public final double getDirection() {
		return direction;
	}
	
	public final void setDirection(double direction) {
		this.direction = direction;
	}
	
	public final String getOwner() {
		return owner;
	}
	
	public final void setOwner(String owner) {
		this.owner = owner;
	}

	
	// ++++++ other methods ++++++
	/*
	 *  メソッド名から、現在存在する全てのインスタンスのIDの中で最大のものを返しているようなメソッドを想定するが、
	 *  実際はnextIDの一つ前の値を返す簡易な実装のみがされている。
	 *  サブクラスでオーバーライドされる可能性があるため、finalはつけない
	 */
	public static long getMaxId() {
		return nextId - 1;
	}

	/*
	 *  mainメソッドはクラスごとに定義することが多いので
	 *  finalメソッドにはしない
	 */
	public static void main (String[] args) {
		Vehicle vehicleA = new Vehicle(args[0]);
		vehicleA.speed= 60.0;
		vehicleA.direction = 30.0;
				
		System.out.println(vehicleA);
	}
	
	/*
	 *  toStringメソッドはサブクラスでフィールドが追加された場合に
	 *  オーバーライドされる可能性が高いため
	 *  finalメソッドにはしない
	 */
	public String toString() {
		String desc = "Vehicle No." + this.ID + ": ";
		desc += "speed=" + this.speed + "km/h, "; 
		desc += "direction=" + this.direction + ", ";
		desc += "owner=" + this.owner;
		return desc;
	}
	
	/*
	 *  引数で設定された値にspeedを変える or 引数で設定された値をspeedに追加する
	 *  ２つの意味に捉えられることが想定される
	 *  また、例えば自転車のギアを変えるなど、
	 *  乗り物によってはspeedの値を変える以外の動作をする乗り物もあると考えられる
	 *  よって、finalメソッドとした
	 */
	public void changeSpeed (double speed) {
		setSpeed(speed);
	}
	
	/*
	 *  乗り物を止める時=speedが0になるだけとは限らない。
	 *  例えば、PassengerVehicleクラスでは乗車済みの人を降ろす(0人にする)かもしれない
	 *  よって、オーバーライドする余地があるため、finalメソッドにはしない
	 */
	public void stop() {
		setSpeed(0);
	}
	
	/*
	 *  乗り物が曲がる時、directionの値が変わる以外の動作の必要があるかもしれない
	 *  例えば、自動車はウインカーを点滅させるなどの動作が必要になる
	 *  よって、オーバーライドする余地があるため、finalメソッドにはしない
	 */
	public void turn(double rotation) {
		direction += rotation;
	}
	
	/*
	 *  乗り物が曲がる時、directionの値が変わる以外の動作の必要があるかもしれない
	 *  例えば、自動車はウインカーを点滅させるなどの動作が必要になる
	 *  よって、オーバーライドする余地があるため、finalメソッドにはしない
	 */
	public void turn(String rotation) {
		if (rotation.equals(TURN_LEFT)) {
			direction -= 90;
		} else if (rotation.equals(TURN_RIGHT)) {
			direction += 90;
		} else {
			System.out.println("Use TURN_LEFT or TURN_RIGHT.");
		}
	}

}
