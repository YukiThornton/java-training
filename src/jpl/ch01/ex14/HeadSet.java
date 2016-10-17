package jpl.ch01.ex14;

public class HeadSet extends Headphone {
	private String voice;

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}
	
	public boolean hasVoice() {
		return voice != null;
	}
	
	public void removeVoice () {
		voice = null;
	}
}
