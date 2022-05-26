
/*
 * Represents an expression as a binary expression tree
 * @author Peter DiPalma
 * @version 1.1
 */
import java.util.Stack;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//import java.util.Queue;
import java.lang.Character;

public class ExpTree {
	private TreeNode<String> root;
	private String infix;

	/*
	 * Constructs new new ExpTree with root as nodeIn, assigned children will be
	 * retained. This constructor can only be used safely for one-node subtrees.
	 * 
	 * @param nodeIn a TreeNode<String> containing data
	 */
	public ExpTree(TreeNode<String> nodeIn) {
		this.root = nodeIn;
	}

	/*
	 * Constructs a newly created ExpTree from a parenthesized a infix expression
	 * with a space (' ') given between operators, and parenthesized around the
	 * outside
	 * 
	 * @param a proper infix mathematical statement containing only positive
	 * integers, i.e. ((1 + 2) * (3 * (44 + 55)))
	 */
	public ExpTree(String infix) {
		this.infix = infix;

		// infixToPostfix uses stack to convert infix expression to postfix expression
		String postfix = infixToPostFix(this.getInfixString());

		// System.out.println("raw: " + postfix);

		// LinkedList used to mediate the transaction between raw chars and elements
		LinkedList<TreeNode<String>> nodeList = new LinkedList<>();
		Stack<ExpTree> treeStack = new Stack<>();
		for (int i = 0; i < postfix.length(); ++i) {
			if (postfix.charAt(i) != ' ') {
				String element = "";
				element += postfix.charAt(i);
				// postfix must be space delimited to become separated into individual
				// 'elements'
				while (i < postfix.length() - 1 && postfix.charAt(i + 1) != ' ') {
					element += postfix.charAt(i + 1);
					++i;
				}
				nodeList.addLast(new TreeNode<String>(element));
			}
		}

		while (!nodeList.isEmpty()) {
			// System.out.println(nodeList.pop().getData());
			TreeNode<String> current = nodeList.pop();
			// isNumeric returns a boolean
			// if node data is an operand, we have a leaf, put it on stack and wait for
			// operator
			if (isNumeric(current.getData())) {
				treeStack.push(new ExpTree(current));
			} else {
				// we have a branch, we can pop the last 2 leaves or subtrees from the stack and
				// make them children of the operators
				ExpTree right = new ExpTree(treeStack.pop().getRoot());
				ExpTree left = new ExpTree(treeStack.pop().getRoot());
				ExpTree parent = new ExpTree(current);
				parent.getRoot().setLeft(left.getRoot());
				parent.getRoot().setRight(right.getRoot());
				treeStack.push(parent);
			}
		}
		// tree constructed, set it's root to the single item remaining in the stack
		this.root = treeStack.pop().getRoot();
	}

	/*
	 * returns root node of the tree
	 * 
	 * @return a TreeNode<String> of the tree's root
	 */
	public TreeNode<String> getRoot() {
		return root;
	}

	/*
	 * Returns the postfix expression used to construct the tree
	 * 
	 * @return, a space delimited string of the data contained in each node in
	 * postfix order.
	 */
	public String getPostfixString() {
		List<TreeNode<String>> postfixList = this.getPostorderList();
		String output = "";
		while (!postfixList.isEmpty()) {
			output = output + ' ' + postfixList.remove(0).getData();
		}
		return output;
	}

	public String getInorderString() {
		List<TreeNode<String>> inorderList = this.getInorderList();
		String output = "";
		while (!inorderList.isEmpty()) {
			output = output + ' ' + inorderList.remove(0).getData();
		}
		return output;
	}

	/*
	 * returns a string of the infix expression
	 * 
	 * @return a string of infix expression used to generate the tree
	 */
	public String getInfixString() {
		return infix;
	}

	/*
	 * returns a string of the prefix expression
	 * 
	 * @return a space delimited string of the data contained in each node in prefix
	 * order.
	 */
	public String getPrefixString() {
		List<TreeNode<String>> prefixList = this.getPreorderList();
		String output = "";
		while (!prefixList.isEmpty()) {
			output = output + ' ' + prefixList.remove(0).getData();
		}
		return output;
	}

	/*
	 * overridden equals method from object class
	 * 
	 * @param o, an object to compare to
	 * 
	 * @return a boolean indicating if this expression tree is exactly the same as
	 * another
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ExpTree)) {
			return false;
		}

		ExpTree cast = (ExpTree) o;

		return testEquality(cast.getRoot(), this.getRoot());
	}

	/*
	 * Overridden toString method from object class
	 * 
	 * @return a string representation of the tree, including the infix, prefix, and
	 * postfix representation of the tree
	 */
	@Override
	public String toString() {
		String result = "";
		result = ("Expression Tree\n" + "Infix: " + this.getInfixString() + "\nPrefix:" + this.getPrefixString()
				+ "\nPostfix:" + this.getPostfixString());
		return result;
	}

	/*
	 * Returns a list of all treenodes visited in the postorder traversal, in order
	 * 
	 * @return a TreeNode<String> List class
	 */
	public List<TreeNode<String>> getPostorderList() {
		List<TreeNode<String>> traversalListHelper = new ArrayList<TreeNode<String>>();
		pPostorder(this.getRoot(), traversalListHelper);
		return traversalListHelper;
	}

	/*
	 * Returns a list of all treenodes visited in the preorder traversal, in order
	 * 
	 * @return a TreeNode<String> List class
	 */
	public List<TreeNode<String>> getPreorderList() {
		List<TreeNode<String>> traversalListHelper = new ArrayList<TreeNode<String>>();
		pPreorder(this.getRoot(), traversalListHelper);
		return traversalListHelper;
	}

	/*
	 * Returns a list of all treenodes visited in the inorder traversal, in order
	 * 
	 * @return a TreeNode<String> List class
	 */
	public List<TreeNode<String>> getInorderList() {
		List<TreeNode<String>> traversalListHelper = new ArrayList<TreeNode<String>>();
		pInorder(this.getRoot(), traversalListHelper);
		return traversalListHelper;
	}

	/*
	 * Private helper method used in constructor
	 * 
	 * @param a string that may or may not contain a number
	 * 
	 * @return a boolean indicating if given string is a numeric
	 */
	private boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/*
	 * helper method to convert an infix expression string to a postfix expression
	 * string
	 * 
	 * @param a properly spaced and parenthesized infix expression
	 * 
	 * @return a space delimited string of the postfix version of the infix
	 */
	private String infixToPostFix(String infix) {

		String result = "";
		Stack<Character> charStack = new Stack<>();
		for (int i = 0; i < infix.length(); ++i) {
			char c = infix.charAt(i);
			// if c is an operator
			if (priorityCheck(c) > 0) {
				// while the stack is not empty and the current operator is of the same or
				// greater priority
				// than the one on top of the stack, pop the top of the stack into the output
				while (charStack.isEmpty() == false && priorityCheck(charStack.peek()) >= priorityCheck(c)) {
					result += " ";
					result += charStack.pop();
				}
				// when there is nothing left on the stack, or c is of less priority than the
				// top of stack, put c on stack
				charStack.push(c);
				// if this is a closed parenthesis, we need to put everything on the stack,
				// until the opening into the output
			} else if (c == ')') {
				char x = charStack.pop();
				while (x != '(') {
					result += " ";
					result += x;
					x = charStack.pop();
				}
				// if open parenthesis, push on stack until c is closing parenthesis
			} else if (c == '(') {
				charStack.push(c);
				// c is an operand or a space
			} else {
				result += c;
			}
		}
		// any items left on the stack will be pushed to the output
		if (charStack.size() > 0) {
			for (int i = 0; i <= charStack.size(); i++) {
				result += " ";
				result += charStack.pop();
			}
		}

		// removes extra spaces for cleanliness, not needed as extra spaces will be
		// ignored anyways
		return result.replaceAll("\\s{2,}", " ").trim();
	}

	/*
	 * Checks priority of operand, used in the infix to postfix conversion
	 * 
	 * @return an int indicating higher or lower priorty, (-1 low - 2 high)
	 */
	private int priorityCheck(char c) {
		switch (c) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		default:
			return -1;
		}
	}

	/*
	 * Private recursive method to perform postorder traversal, adds to global
	 * helper list
	 * 
	 * @param the root TreeNode
	 */
	protected void pPostorder(TreeNode<String> node, List<TreeNode<String>> traversalListHelper) {
		if (node == null) {
			return;
		}

		pPostorder(node.getLeft(), traversalListHelper);
		pPostorder(node.getRight(), traversalListHelper);
		traversalListHelper.add(node);
	}

	/*
	 * Private recursive method to perform preorder traversal, adds to global helper
	 * list
	 * 
	 * @param the root TreeNode
	 */
	protected void pPreorder(TreeNode<String> node, List<TreeNode<String>> traversalListHelper) {
		if (node == null) {
			return;
		}

		traversalListHelper.add(node);
		pPreorder(node.getLeft(), traversalListHelper);
		pPreorder(node.getRight(), traversalListHelper);
	}

	/*
	 * Private recursive method to perform inorder traversal, adds to global helper
	 * list
	 * 
	 * @param the root TreeNode
	 */
	protected void pInorder(TreeNode<String> node, List<TreeNode<String>> traversalListHelper) {
		if (node == null) {
			return;
		}

		pInorder(node.getLeft(), traversalListHelper);
		traversalListHelper.add(node);
		pInorder(node.getRight(), traversalListHelper);
	}

	/*
	 * Helper method for .equals, uses a recursive short circuit comparison to
	 * traverse and compare trees
	 * 
	 * @param the two respective roots of the ExpTrees being compared
	 */
	protected boolean testEquality(TreeNode<String> root, TreeNode<String> rroot) {
		if (root == rroot) {
			return true;
		}
		if (root == null || rroot == null) {
			return false;
		}
		return root.getData().equals(rroot.getData()) && testEquality(root.getLeft(), rroot.getLeft())
				&& testEquality(root.getRight(), rroot.getRight());
	}
}
