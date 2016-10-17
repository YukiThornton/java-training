package jpl.ch01.ex15;

public class Sample {

	public static void main(String[] args) {
		ObjectManager om = new SimpleObjectManager();
		om.add("name1", "value1");
		om.add("name2", "value2");
		om.add("name3", "value3");
		om.remove("name2");
		System.out.println("name1: " + om.find("name1"));
		System.out.println("name2: " + om.find("name2"));
		System.out.println("name3: " + om.find("name3"));
		System.out.println("name4: " + om.find("name4"));


	}

}
