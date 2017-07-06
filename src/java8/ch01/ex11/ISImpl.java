package java8.ch01.ex11;

/*
 * 1. I: 抽象, S: 抽象
 *     実装クラスでオーバーライドする必要がある。
 * 
 * 2. I: 抽象, S: default(具象)
 *     実装クラスでオーバーライドする必要がある。
 *     super.f();でSの実装を呼ぶことができる。
 * 
 * 3. I: default, S: 抽象(具象)
 *     実装クラスでオーバーライドする必要がある。
 *     I.super.f();でIの実装を呼ぶことができる。
 * 
 * 4. I: default, S: default(具象)
 *     オーバーライドする必要はなく、しない場合はSの実装が実行される
 *     オーバーライドする場合はI.super.f();でIの実装を呼ぶことができる。
 * 
 * 5. I: static, S: 抽象(具象)
 *     実装クラスでf()メソッドオーバーライドする必要がある。
 *     その実装として、I.f();のように、Iのデフォルトメソッドを引き継ぐことはできる
 * 
 * 6. I: static, S: default(具象)
 *     オーバーライドする必要はなく、しない場合はSの実装が実行される
 *     その実装として、I.f();のように、Iのデフォルトメソッドを引き継ぐことはできる
 */
public class ISImpl extends S implements I {
    public static void main(String[] args) {
        new ISImpl().f();
    }
    
    public void f() {
        I.f();
    }
}
