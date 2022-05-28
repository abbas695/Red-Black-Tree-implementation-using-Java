package redblacktree;

public class Node {
	private int data;
	private NodeColor color;
	private Node left;
	private Node right;
	private Node Parent;

	public Node(int data) {
		this.data = data;
		this.color = NodeColor.RED;

	}

	@Override
	public String toString() {
		return "" + this.data;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public NodeColor getColor() {
		return color;
	}

	public void setColor(NodeColor color) {
		this.color = color;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getParent() {
		return Parent;
	}

	public void setParent(Node getParent) {
		this.Parent = getParent;
	}
}
