package jpl.ch14.ex07;

/*
 * 1. args(true 3 A B) -> ABBBAA
 * 2. args(true 3 A B) -> ABABAB
 * 3. args(true 3 A B) -> ABBBAA
 * 4. args(true 3 A B) -> ABBBAA
 * 5. args(true 3 A B) -> ABBBAA
 * 6. args(true 3 A B C) -> CCCBAAABB
 * 7. args(true 3 A B C) -> ACCBACBAB
 * 8. args(true 3 A B C) -> ACCCABABB
 * 9. args(true 3 A B C) -> CBBBAAACC
 * 10. args(true 3 A B C) -> ABBBCAACC
 * 
 */


public class Babble extends Thread {
    static boolean doYield;
    static int howOften;
    private String word;
    
    public Babble(String whatToSay) {
        word = whatToSay;
    }
    
    public void run() {
        for (int i = 0; i < howOften; i++) {
            System.out.println(word);
            if(doYield)
                Thread.yield();
        }
    }

    public static void main(String[] args) {
        doYield = new Boolean(args[0]).booleanValue();
        howOften = Integer.parseInt(args[1]);

        for (int i = 2; i < args.length; i++) {
            new Babble(args[i]).start();
        }
    }

}
