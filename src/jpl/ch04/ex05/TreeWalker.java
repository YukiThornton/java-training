package jpl.ch04.ex05;

/*
 * (b) 特定の順番（深さ優先、幅優先など）で木を探索するTreeWalkerは具象クラス
 * 特定の順番で探索をすることから、具体的なアルゴリズムが実装された具象クラスが適していると考えた。
 * Walkableのようなインターフェースをimplementすることを想定している。
 */
public class TreeWalker implements Walkable{
	
	TreeNode[] tree;
	
	public TreeWalker(TreeNode[] tree) {
		this.tree = tree;
	}
	
	public int search(Object targetData) {
		// treeの中からtargetDataを一致するものを探索し、そのインデックスを返す
		return 0;
	}
}
