/*
 * Generic binary tree node class
 * @author Peter DiPalma
 * @version 1.0
 */
public class TreeNode<T> {
	private T data;
	private TreeNode<T> left;
	private TreeNode<T> right;

	/*
	 * constructs a treenode with given type data
	 * 
	 * @param generic data
	 */
	public TreeNode(T data) {
		this.data = data;
	}

	/*
	 * returns the data stored in the node
	 * 
	 * @return a generic of the data given
	 */
	public T getData() {
		return this.data;
	}

	/*
	 * set or change the data in the node
	 * 
	 * @param generic data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/*
	 * returns the treenode's right child
	 * 
	 * @return a TreeNode<T> of the right child node
	 */
	public TreeNode<T> getRight() {
		return this.right;
	}

	/*
	 * set the TreeNodes right child
	 * 
	 * @param a TreeNode<T> of the node to be assigned as the right child
	 */
	public void setRight(TreeNode<T> right) {
		this.right = right;
	}

	/*
	 * returns the treenode's left child
	 * 
	 * @return a TreeNode<T> of the left child node
	 */
	public TreeNode<T> getLeft() {
		return this.left;
	}

	/*
	 * set the TreeNodes left child
	 * 
	 * @param a TreeNode<T> of the node to be assigned as the left child
	 */
	public void setLeft(TreeNode<T> left) {
		this.left = left;
	}

	/*
	 * overwritten from object class, determines if these nodes are equal based on
	 * the data they contain, does not look at children
	 * 
	 * @param an object to compare this TreeNode to
	 * 
	 * @return a boolean indicating if these nodes contain the same data
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof TreeNode<?>) {
			if (((TreeNode<?>) o).getData().equals(this.getData())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * returns a boolean indicating if this node has a left child
	 * 
	 * @return a boolean indicating if this node has a left child
	 */
	public boolean hasLeftChild() {
		if (this.left == null) {
			return false;
		}
		return true;
	}

	/*
	 * returns a boolean indicating if this node has a right child
	 * 
	 * @return a boolean indicating if this node has a right child
	 */
	public boolean hasRightChild() {
		if (this.right == null) {
			return false;
		}
		return true;
	}

	/*
	 * Overwritten toString method in the format: "TreeNode Data: *data* Children: L-(*left
	 * data*) R-(*right data*)"
	 * 
	 * @return a string in the above format
	 */
	@Override
	public String toString() {
		String output = "\nTreeNode\n" + "Data: " + this.data + "\nChildren: L-";
		if (this.hasLeftChild()) {
			output += "(" + this.left.getData() + ")";
		} else {
			output += "null";
		}
		output += " R-";
		if (this.hasRightChild()) {
			output += "(" + this.right.getData() + ")";
		} else {
			output += "null ";
		}
		return output;

	}
}
