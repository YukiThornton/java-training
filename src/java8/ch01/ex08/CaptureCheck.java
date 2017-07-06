package java8.ch01.ex08;

import java.util.ArrayList;
import java.util.List;


/*
 * runnerオブジェクトはそれぞれ別のnameをキャプチャする。
 * for (int i = 0; i < names.length; i++){} の場合はラムダ式の中でiを参照することができないためコンパイルできない
 */
public class CaptureCheck {
    public static void main(String[] args) {
        check();
    }
    
    public static void check() {
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for (String name: names)
            runners.add(() -> {
                System.out.print(name);
            });
        for (Runnable runner: runners) {
            new Thread(runner).start();
        }
    }

/*
    public static void check2() {
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for (int i = 0; i < names.length; i++)
            runners.add(() -> {
                System.out.print(names[i]);
            });
        for (Runnable runner: runners) {
            new Thread(runner).start();
        }
    }
*/
}
