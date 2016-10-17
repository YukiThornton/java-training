package jpl.ch01.ex14;

public class WalkmanDual extends Walkman {
	final private HeadphoneJack subHeadphoneJack = new HeadphoneJack();
	
	// コンストラクタ
	public WalkmanDual() {
		super();
	}
	public WalkmanDual(Headphone headphone, Song[] tape) {
		super(headphone, tape);
	}
	public WalkmanDual(Headphone mainHeadphone, Headphone subHeadphone, Song[] tape) {
		super(mainHeadphone, tape);
		this.getSubHeadphoneJack().setHeadphone(subHeadphone);
	}
	
	// setter, getter
	public HeadphoneJack getSubHeadphoneJack() {
		return subHeadphoneJack;
	}
	
	// 2つのheadphoneをheadphoneJackに接続するメソッド
	public void setHeadphones(Headphone mainHeadphone, Headphone subHeadphone){
		setHeadphone(mainHeadphone);
		getSubHeadphoneJack().setHeadphone(subHeadphone);
	}
	
	public void play() {
		if (getTape() == null) {
			System.out.println("Set a tape.");
			return;			
		}
		if (!getHeadphoneJack().isConnected() && !getSubHeadphoneJack().isConnected()) {
			System.out.println("Connect headphones.");
			return;			
		} 
		
		String soundData = collectSoundData();
		if (getHeadphoneJack().isConnected() && getSubHeadphoneJack().isConnected()) {
			getHeadphoneJack().stream(soundData);
			getSubHeadphoneJack().stream(soundData);
			
		} else {
			getHeadphoneJack().stream(soundData);		
		}
		return;
	}
	
	public void stop() {
		super.stop();
		if (getSubHeadphoneJack().isConnected()) {
			getSubHeadphoneJack().getHeadphone().removeSound();
		}
	}
	
}
