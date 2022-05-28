
package redblacktree;

import java.util.Scanner;

public class RedBlackTree {

	private Node root;
	private Node nil;

	public RedBlackTree() {
		nil = new Node(0);
		nil.setColor(NodeColor.Black);
		root = nil;
	}

	private void inOrderTraversal(Node node) {
		if (node != nil) {
			if (node.getLeft() != nil)
				inOrderTraversal(node.getLeft());
			System.out.println(node + "-" + node.getColor());
			if (node.getRight() != nil)
				inOrderTraversal(node.getRight());
		} else {
			System.out.println("tree is empty");
		}
	}

	private void preOrderTraversal(Node node) {
		if (node != nil) {
			System.out.println(node + "-" + node.getColor());
			if (node.getLeft() != nil) {
				preOrderTraversal(node.getLeft());
			}
			if (node.getRight() != nil) {
				preOrderTraversal(node.getRight());
			}
		} else {
			System.out.println("tree is empty");
		}
	}

	private void postOrderTraversal(Node node) {
		if (node != nil) {

			if (node.getLeft() != nil) {
				postOrderTraversal(node.getLeft());
			}
			if (node.getRight() != nil) {
				postOrderTraversal(node.getRight());
			}
			System.out.println(node + "-" + node.getColor());
		} else {
			System.out.println("tree is empty");
		}
	}

	public Node insert(int data) {

		Node node = new Node(data);
		node.setLeft(nil);
		node.setRight(nil);
		node.setParent(nil);
		root = insertIntoTree(root, node);
		fixViolation(node);
		return node;
	}

	private void fixViolation(Node node) {
		Node parentNode = nil;
		Node grandParentNode = nil;
		while (node != root && node.getColor() != NodeColor.Black && node.getParent().getColor() == NodeColor.RED) {
			parentNode = node.getParent();
			grandParentNode = node.getParent().getParent();
			if (parentNode == grandParentNode.getLeft()) {
				Node uncle = grandParentNode.getRight();
				if (uncle != nil && uncle.getColor() == NodeColor.RED) {
					grandParentNode.setColor(NodeColor.RED);
					parentNode.setColor(NodeColor.Black);
					uncle.setColor(NodeColor.Black);
					node = grandParentNode;
				} else {
					if (node == parentNode.getRight()) {
						leftRotate(parentNode);
						node = parentNode;
						parentNode = node.getParent();

					}
					rightRotate(grandParentNode);
					NodeColor tempColor = parentNode.getColor();
					parentNode.setColor(grandParentNode.getColor());
					grandParentNode.setColor(tempColor);
					node = parentNode;

				}

			} else {
				Node uncle = grandParentNode.getLeft();
				if (uncle != nil && uncle.getColor() == NodeColor.RED) {
					grandParentNode.setColor(NodeColor.RED);
					parentNode.setColor(NodeColor.Black);
					uncle.setColor(NodeColor.Black);
					node = grandParentNode;
				} else {
					if (node == parentNode.getLeft()) {
						rightRotate(parentNode);
						node = parentNode;
						parentNode = node.getParent();

					}
					leftRotate(grandParentNode);
					NodeColor tempColor = parentNode.getColor();
					parentNode.setColor(grandParentNode.getColor());
					grandParentNode.setColor(tempColor);
					node = parentNode;

				}

			}
		}
		if (root.getColor() == NodeColor.RED) {
			root.setColor(NodeColor.Black);
		}
	}

	private void rightRotate(Node node) {
		System.out.println("Rotating to the right" + node);
		Node tempLeftNode = node.getLeft();
		node.setLeft(tempLeftNode.getRight());
		if (node.getLeft() != nil)
			node.getLeft().setParent(node);
		tempLeftNode.setParent(node.getParent());
		if (tempLeftNode.getParent() == nil)
			root = tempLeftNode;
		else if (node == node.getParent().getLeft())
			node.getParent().setLeft(tempLeftNode);
		else
			node.getParent().setRight(tempLeftNode);
		tempLeftNode.setRight((node));
		node.setParent(tempLeftNode);
	}

	private void leftRotate(Node node) {
		System.out.println("Rotating to the left" + node);
		Node tempRightNode = node.getRight();
		node.setRight(tempRightNode.getLeft());
		if (node.getRight() != nil)
			node.getRight().setParent(node);
		tempRightNode.setParent(node.getParent());
		if (tempRightNode.getParent() == nil)
			root = tempRightNode;
		else if (node == node.getParent().getLeft())
			node.getParent().setLeft(tempRightNode);
		else
			node.getParent().setRight(tempRightNode);
		tempRightNode.setLeft((node));
		node.setParent(tempRightNode);
	}

	private Node insertIntoTree(Node root, Node node) {
		if (root == nil)
			return node;
		if (node.getData() < root.getData()) {
			root.setLeft(insertIntoTree(root.getLeft(), node));
			root.getLeft().setParent(root);
		} else if (node.getData() > root.getData()) {
			root.setRight(insertIntoTree(root.getRight(), node));
			root.getRight().setParent(root);
		}
		return root;
	}

	public void transplant(Node u, Node v) {
		if (u.getParent() == nil) {
			root = v;
		}

		else if (u == u.getParent().getLeft()) {
			u.getParent().setLeft(v);
		} else {
			u.getParent().setRight(v);
		}
		if (u.getParent() != nil)
			v.setParent(u.getParent());
	}

	public Node minimum(Node node) {
		while (node.getLeft() != nil) {
			node = node.getLeft();
		}
		return node;
	}

	public void delete(Node node) {
		Node ref = node;
		Node x;
		NodeColor yOrignalColor = ref.getColor();
		if (node.getLeft() == nil) {
			x = node.getRight();
			transplant(node, node.getRight());
		} else if (node.getRight() == nil) {
			x = node.getLeft();
			transplant(node, node.getLeft());
		} else {
			ref = minimum(node.getRight());
			yOrignalColor = ref.getColor();
			x = ref.getRight();
			if (ref.getParent() == node) {
				x.setParent(node);
			} else {
				transplant(ref, ref.getRight());
				ref.setRight(node.getRight());
				ref.getRight().setParent(ref);
			}
			transplant(node, ref);
			ref.setLeft(node.getLeft());
			ref.getLeft().setParent(ref);
			ref.setColor(node.getColor());
		}
		if (yOrignalColor == NodeColor.Black)
			delfix(x);
	}

	public void delfix(Node node) {

		while (node != root && node.getColor() == NodeColor.Black) {
			if (node == node.getParent().getLeft()) {
				Node sibling = node.getParent().getRight();
				if (sibling.getColor() == NodeColor.RED) {
					sibling.setColor(NodeColor.Black);
					node.getParent().setColor(NodeColor.RED);
					leftRotate(node.getParent());
					sibling = node.getParent().getRight();
				}
				if (sibling.getLeft().getColor() == NodeColor.Black
						&& sibling.getRight().getColor() == NodeColor.Black) {
					sibling.setColor(NodeColor.RED);
					node = node.getParent();
					continue;
				} else if (sibling.getRight().getColor() == NodeColor.Black) {
					sibling.getLeft().setColor(NodeColor.Black);
					sibling.setColor(NodeColor.RED);
					rightRotate(sibling);
					sibling = node.getParent().getRight();
				}
				if (sibling.getRight().getColor() == NodeColor.RED) {
					sibling.setColor(node.getParent().getColor());
					node.getParent().setColor(NodeColor.Black);
					sibling.getRight().setColor(NodeColor.Black);
					leftRotate(node.getParent());
					node = root;
				}
			} else {
				Node sibling = node.getParent().getLeft();
				if (sibling.getColor() == NodeColor.RED) {
					sibling.setColor(NodeColor.Black);
					node.getParent().setColor(NodeColor.RED);
					rightRotate(node.getParent());
					sibling = node.getParent().getLeft();
				}
				if (sibling.getRight().getColor() == NodeColor.Black
						&& sibling.getLeft().getColor() == NodeColor.Black) {
					sibling.setColor(NodeColor.RED);
					node = node.getParent();
					continue;
				} else if (sibling.getLeft().getColor() == NodeColor.Black) {
					sibling.getRight().setColor(NodeColor.Black);
					sibling.setColor(NodeColor.RED);
					leftRotate(sibling);
					sibling = node.getParent().getLeft();
				}
				if (sibling.getLeft().getColor() == NodeColor.RED) {
					sibling.setColor(node.getParent().getColor());
					node.getParent().setColor(NodeColor.Black);
					sibling.getLeft().setColor(NodeColor.Black);
					rightRotate(node.getParent());
					node = root;
				}
			}
		}
		node.setColor(NodeColor.Black);
	}

	private Node search(Node target, Node node) {
		if (root == nil) {
			return nil;
		}

		if (target.getData() < node.getData()) {
			if (node.getLeft() != nil) {
				return search(target, node.getLeft());
			}
		} else if (target.getData() > node.getData()) {
			if (node.getRight() != nil) {
				return search(target, node.getRight());
			}
		} else if (target.getData() == node.getData()) {
			return node;
		}
		return nil;
	}

	public static void pressToContinue(boolean exit) {
		System.out.println();
		System.out.println("press any key to go back to the main menu");
		System.out.println("enter -1 to exit the program");
		Scanner sc = new Scanner(System.in);
		String out = sc.nextLine();
		if (out == "-1") {
			System.out.println("thank you");
			exit = true;
		}
	}

	public static void main(String[] args) {

		RedBlackTree RBT = new RedBlackTree();
		Scanner sc = new Scanner(System.in);
		// example 10

		boolean exit = false;
		while (!exit) {
			System.out.println("press 1 to insert new value"); /* showing the functions of the program */
			System.out.println("press 2 to delete a value from the tree");
			System.out.println("press 3 to print the tree");
			System.out.println("enter -1 to exit");
			int i = sc.nextInt(); /* enter the option the user want */
			sc.nextLine();
			switch (i) {
			case 1: {
				System.out.println("enter the new value");
				int value = sc.nextInt();
				Node node = RBT.insert(value);
				System.out.println("the new value is inserted");
				pressToContinue(exit);
				break;

			}
			case 2: {
				System.out.println("enter the value you want to delete");
				int value = sc.nextInt();
				Node target = new Node(value);
				Node node = RBT.search(target, RBT.root);
				RBT.delete(node);
				System.out.println("the value is deleted");
				pressToContinue(exit);
				break;
			}
			case 3: {
				System.out.println("press 1 to print the tree in inorder");
				System.out.println("press 2 to print the tree in preorder");
				System.out.println("press 3 to print the tree in postorder");
				int k = sc.nextInt();
				sc.nextLine();
				switch (k) {
				case 1: {
					RBT.inOrderTraversal(RBT.root);
					break;
				}
				case 2: {
					RBT.preOrderTraversal(RBT.root);
					break;
				}
				case 3: {
					RBT.postOrderTraversal(RBT.root);
					break;
				}
				}
				pressToContinue(exit);
				break;
			}

			case -1: {
				System.out.println("thank you");
				exit = true;
				break;
			}
			default: {
				System.out.println("enter the right number");
				System.out.println();
				break;
			}
			}

		}
	}
}