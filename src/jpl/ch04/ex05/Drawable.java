package jpl.ch04.ex05;

/*
 * (c) グラフィックシステムにより描画可能なオブジェクトのためのインターフェース
 * drawメソッドを実行すると、グラフィックシステムに描画する
 * 実装クラス特有の方法で描画方法が実装できるようにdrawメソッドは抽象メソッドであるべき。
 * かつ、Drawableの中で実装すべきメソッドがないため、インターフェースが適している。
 */
public interface Drawable {
	void draw();
}
