package jpl.ch01.ex14;

public class Walkman {
	final private HeadphoneJack headphoneJack = new HeadphoneJack();
	private Song[] tape;
	
	// コンストラクタ
	public Walkman() {
		super();
	}
	public Walkman(Headphone headphone, Song[] tape) {
		this.headphoneJack.setHeadphone(headphone);
		this.tape = tape;
	}
	
	// setter, getter
	public HeadphoneJack getHeadphoneJack() {
		return headphoneJack;
	}
	public Song[] getTape() {
		return tape;
	}
	public void setTape(Song[] tape){
		this.tape = tape;		
	}
	
	// headphoneをheadphoneJackに接続するメソッド
	public void setHeadphone(Headphone headphone){
		getHeadphoneJack().setHeadphone(headphone);
	}
	
	// tapeに収録されている曲を最初からすべて聞く
	public void play() {
		if (!getHeadphoneJack().isConnected()) {
			System.out.println("Connect a headphone..");
			return;			
		}
		if (getTape() == null) {
			System.out.println("Set a tape.");
			return;			
		}
		getHeadphoneJack().stream(collectSoundData());
		return;
	}
	
	public void stop() {
		if (getHeadphoneJack().isConnected()) {
			getHeadphoneJack().getHeadphone().removeSound();			
		}
	}
	
	protected String collectSoundData() {
		String tapeData = new String();
		for (int i = 0; i < getTape().length; i++) {
			tapeData += getTape()[i].getData();
		}
		return tapeData;
	}
}
