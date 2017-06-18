package java8.ch01.ex11;

/*
 * 1. I: 抽象, J: 抽象
 *     実装クラスでインターフェースのどちらかのメソッドを実装する必要がある。
 * 
 * 2. I: 抽象, J: default
 *     4と同じ結果になると考えられるため、割愛。
 * 
 * 3. I: 抽象, J: static
 *     7と同じ結果になると考えられるため、割愛。
 * 
 * 4. I: default, J: 抽象
 *     実装クラスでf()メソッドオーバーライドする必要がある。
 *     その実装として、I.super.f();のように、Iのデフォルトメソッドを引き継ぐことはできるが
 *     J.super.f();のように、Jのメソッドを引き継ぐことはできない
 * 
 * 5. I: default, J: default
 *     実装クラスでf()メソッドオーバーライドする必要がある。
 *     その実装として、I.super.f();, J.super.f();のように、デフォルトメソッドを引き継ぐことはできる
 * 
 * 6. I: default, J: static
 *     実装クラスでf()メソッドオーバーライドしない場合はIの実装が引き継がれる
 *     オーバーライドする場合は、J.f();のように記述することでJの実装を引き継ぐこともできる
 * 
 * 7. I: static, J: 抽象
 *     実装クラスでJのf()メソッドオーバーライドする必要がある。
 *     その実装として、I.f();のように、Iのデフォルトメソッドを引き継ぐことはできるが
 *     引き継がない場合はIのstaticメソッドの実装は無視される
 * 
 * 8. I: static, J: default
 *     6と同じ結果になると考えられるため、割愛。
 * 
 * 9. I: static, J: static
 *     I, Jともにオーバーライドで実装を引き継ぐことはできない。
 *     実装クラスでは新しくf()メソッドを定義する必要がある。
 * 
 */
public class IJImpl implements I, J{
    public static void main(String[] args) {
        new IJImpl().f();
    }
    public void f() {
        System.out.println("IJImpl");
    }
}
