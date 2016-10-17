package jpl.ch01.ex14;

public class HeadphoneJack {
	private Headphone headphone;
	private boolean isConnected;
	
	public Headphone getHeadphone() {
		return headphone;
	}
	public void setHeadphone(Headphone headphone) {
		this.headphone = headphone;
		this.isConnected = true;
	}
	public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	public void stream(String sound) {
		this.headphone.setSound(sound);
	}
	
}
