package jpl.ch04.ex05;

/*
 * (a)TreeNodeを継承する2本木用のnodeを表す具象クラス
 */
public class BinaryTreeNode extends TreeNode {
	
	private final int MAXIMUM_CHILD_NUM = 2;

	public BinaryTreeNode(Object data) {
		this.setData(data);
	}
	
	public int getMaximumChildNum() {
		return MAXIMUM_CHILD_NUM;
	}

}
