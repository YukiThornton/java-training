package jpl.ch01.ex14;

public class Talkman extends WalkmanDual {
	
	// コンストラクタ
	public Talkman() {
		super();
	}
	public Talkman(Headphone headphone, Song[] tape) {
		super(headphone, tape);
	}
	public Talkman(Headphone mainHeadphone, Headphone subHeadphone, Song[] tape) {
		super(mainHeadphone,subHeadphone, tape);
	}
	
	public void exchangeCommunication() {
		if (!getHeadphoneJack().isConnected() && !getSubHeadphoneJack().isConnected()) {
			System.out.println("Connect headphones.");
			return;			
		} 
		
		if (getHeadphoneJack().isConnected() && getSubHeadphoneJack().isConnected()) {
			HeadSet mainHeadSet = (HeadSet)getHeadphoneJack().getHeadphone();
			if (mainHeadSet.hasVoice()) {
				getSubHeadphoneJack().stream(mainHeadSet.getVoice());
			}
			
			HeadSet subHeadSet = (HeadSet)getSubHeadphoneJack().getHeadphone();
			if (subHeadSet.hasVoice()) {
				getHeadphoneJack().stream(subHeadSet.getVoice());
			}			
		} else {
			System.out.println("You need a friend.");
		}
		return;
	}
	
	public void clear () {
		if (getHeadphoneJack().isConnected()) {
			HeadSet mainHeadSet = (HeadSet)getHeadphoneJack().getHeadphone();
			if (mainHeadSet.hasVoice()) {
				mainHeadSet.removeVoice();
			}
		} 
		
		if (getSubHeadphoneJack().isConnected()) {
			HeadSet subHeadSet = (HeadSet)getSubHeadphoneJack().getHeadphone();
			if (subHeadSet.hasVoice()) {
				subHeadSet.removeVoice();
			}			
		} 		
	}

}
