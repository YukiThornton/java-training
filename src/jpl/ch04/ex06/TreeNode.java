package jpl.ch04.ex06;

/*
 * (a)
 * 仮定変更：n本木のnodeを表すTreeNode -> 二本木のnodeを表すTreeNode
 * 答え変更：抽象クラス -> 具象クラス
 */
public class TreeNode {
	private final int MAXIMUM_CHILD_NUM = 2;
	private Object data;
	private TreeNode[] children;
	
	public TreeNode(Object data) {
		this.setData(data);
	}
	
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
	
	public int getMaximumChildNum() {
		return MAXIMUM_CHILD_NUM;
	}
}
