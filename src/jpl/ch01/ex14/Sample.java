package jpl.ch01.ex14;

public class Sample {

	public static void main(String[] args) {
		Song[] myTape = new Song[3];
		myTape[0]  = new Song("titleA", "lyricsA");
		myTape[1]  = new Song("titleB", "lyricsB");
		myTape[2]  = new Song("titleC", "lyricsC");
		
		Headphone myHeadphone = new Headphone();
		Headphone herHeadphone = new Headphone();
		HeadSet myHeadSet = new HeadSet();
		HeadSet herHeadSet = new HeadSet();
		

		// 1 Sample for Walkman class.
		// 1.1 One person can listen to music.
		Walkman walkmanSingle = new Walkman(myHeadphone, myTape);
		walkmanSingle.play();
		System.out.println("1.1: I hear " + myHeadphone.makeSound());
		walkmanSingle.stop();

		// 2 Sample for WalkmanDual class.
		// 2.1 One person can listen to music.
		Walkman walkmanDualForOne = new WalkmanDual(myHeadphone, myTape);
		walkmanDualForOne.play();
		System.out.println("2.1: I hear " + myHeadphone.makeSound());
		System.out.println("2.1: My friend hears " + herHeadphone.makeSound());
		walkmanDualForOne.stop();

		// 2.2 Two people can listen to music.
		WalkmanDual walkmanDualForTwo = new WalkmanDual(myHeadphone, herHeadphone, myTape);
		walkmanDualForTwo.play();
		System.out.println("2.2: I hear " + myHeadphone.makeSound());
		System.out.println("2.2: My friend hears " + herHeadphone.makeSound());
		walkmanDualForTwo.stop();

		// 3 Sample for Talkman class.
		// 3.1 One person can listen to music.
		Talkman talkmanForOne = new Talkman(myHeadphone, myTape);
		talkmanForOne.play();
		System.out.println("3.1: I hear " + myHeadphone.makeSound());
		System.out.println("3.1: My friend hears " + herHeadphone.makeSound());
		talkmanForOne.stop();

		// 3.2 Two people can listen to music.
		Talkman talkmanForTwo = new Talkman(myHeadphone, herHeadphone, myTape);
		talkmanForTwo.play();
		System.out.println("3.2: I hear " + myHeadphone.makeSound());
		System.out.println("3.2: My friend hears " + herHeadphone.makeSound());
		talkmanForTwo.stop();

		// 3.3 Two people can listen to music and talk to each other.
		Talkman talkman = new Talkman(myHeadSet, herHeadSet, myTape);
		talkman.play();
		System.out.println("3.3: I hear " + myHeadSet.makeSound());
		System.out.println("3.3: My friend hears " + herHeadSet.makeSound());
		myHeadSet.removeSound();
		herHeadSet.removeSound();
		talkman.stop();

		herHeadSet.setVoice("Hello, my friend.");
		talkman.exchangeCommunication();
		System.out.println("3.3: I also hear " + myHeadSet.makeSound());
		talkman.stop();
		talkman.clear();
		
		myHeadSet.setVoice("Oh, hi.");
		talkman.exchangeCommunication();
		System.out.println("3.3: My friend also hears " + herHeadSet.makeSound());
		talkman.stop();
		talkman.clear();
}

}
