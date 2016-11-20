package jpl.ch04.ex05;

/*
 * (a)TreeNodeはn本木のnodeを表す抽象クラス
 * 木構造のnodeで共有する機能を提供しつつ、木構造の種類によってnodeに制限が加えられるように、
 * 共通機能を実装メソッド、制限内容を抽象メソッドで表した抽象クラスが適していると考えた。
 * 例えば、BinaryTreeNodeクラスは二本木に対応するためにchildrenの数を２つに制限した具象クラスである。
 */
public abstract class TreeNode {
	private Object data;
	private TreeNode[] children;
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public TreeNode[] getChildren() {
		return children;
	}
	
	public void setChildren(TreeNode... children) throws Exception {
		if (children.length > getMaximumChildNum()) {
			throw new Exception();
		}
		this.children = children;
	}
	
	public abstract int getMaximumChildNum();
}
