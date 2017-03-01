package jpl.ch16.ex06;

public class SampleChild extends Sample {

    private int childPrivateValue = 0;
    public int childPublicValue = 0;

    private void childPrivateMethod(int arg1, String arg2) {
        System.out.println("childPrivateMethod(arg1=" + arg1 + ", arg2=" + arg2 + ") is invoked.");
        
    }
    public void childPublicMethod(int arg1, String arg2) {
        System.out.println("childPublicMethod(arg1=" + arg1 + ", arg2=" + arg2 + ") is invoked.");
        
    }
}
