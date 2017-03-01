package jpl.ch16.ex06;

public class Sample {

    private int privateValue = 0;
    public int publicValue = 0;

    private static class PrivateInner {
    }
    
    public static class PublicInner {
        private PublicInner() {}
    }
    
    private void privateMethod(int arg1, String arg2) {
        System.out.println("privateMethod(arg1=" + arg1 + ", arg2=" + arg2 + ") is invoked.");
        
    }
    public void publicMethod(int arg1, String arg2) {
        System.out.println("publicMethod(arg1=" + arg1 + ", arg2=" + arg2 + ") is invoked.");
        
    }
}
