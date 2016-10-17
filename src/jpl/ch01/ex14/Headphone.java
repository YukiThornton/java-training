package jpl.ch01.ex14;

public class Headphone {
	private String sound;
	
	
	public void setSound(String sound) {
		this.sound = sound;
	}


	public String makeSound() {
		return sound;
		
	}
	
	public void removeSound(){
		sound = null;
	}
}
