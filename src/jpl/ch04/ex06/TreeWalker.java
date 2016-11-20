package jpl.ch04.ex06;

/*
 * (b)
 * 家庭変更：特定の順番（深さ優先、幅優先など）で木を探索する -> 木を探索する
 * 答え変更：具象クラス -> インターフェース
 */
public interface TreeWalker {
	public int search(Object targetData);

}
