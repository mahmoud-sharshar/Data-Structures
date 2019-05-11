/**
 * 
 */
package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

/**
 * @author mahmo
 *
 */
public class RBTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private INode<T, V> root;
	private int size;
	private boolean black;
	private boolean red;
	private RBTNode<T, V> NIL;

	public RBTree() {
		this.size = 0;
		this.black = INode.BLACK;
		this.red = INode.RED;
		root = new RBTNode<>();
		NIL = new RBTNode<>();
		NIL.setColor(black);
		NIL.setKey(null);
		this.root = this.NIL;

	}

	@Override
	public INode<T, V> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.size == 0;
	}

	@Override
	public void clear() {
		this.size = 0;
		this.root = this.NIL;
	}

	@Override
	public V search(T key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());

		INode<T, V> temp = this.root;

		while (!temp.equals(this.NIL)) {
			if (temp.getKey().equals(key)) {
				return temp.getValue();
			} else if (temp.getKey().compareTo(key) > 0) {
				temp = temp.getLeftChild();
			} else {
				temp = temp.getRightChild();
			}
		}
		return null;
	}

	@Override
	public boolean contains(T key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());

		INode<T, V> temp = this.root;

		while (!temp.equals(this.NIL)) {
			if (temp.getKey().equals(key)) {
				return true;
			} else if (temp.getKey().compareTo(key) > 0) {
				temp = temp.getLeftChild();
			} else {
				temp = temp.getRightChild();
			}
		}
		return false;
	}

	/**
	 * Insert the given key in the tree while maintaining the red black tree
	 * properties. If the key is already present in the tree, update its value. run
	 * in O(log n)
	 * 
	 * @param key   to be inserted
	 * @param value the associated value with the given key
	 */
	@Override
	public void insert(T key, V value) {
		if (key != null && value != null) {
			INode<T, V> y = this.NIL;
			INode<T, V> x = this.root;
			while (!x.equals(this.NIL)) {
				y = x;
				if (x.getKey().equals(key)) {
					x.setValue(value);// if key exist update its value
					return;
				} else if (x.getKey().compareTo(key) > 0)
					x = x.getLeftChild();
				else
					x = x.getRightChild();
			}

			INode<T, V> newNode = new RBTNode<>();
			newNode.setKey(key);
			newNode.setValue(value);
			newNode.setColor(INode.RED);

			if (y.equals(this.NIL))
				this.root = newNode;
			else if (y.getKey().compareTo(key) > 0)
				y.setLeftChild(newNode);
			else
				y.setRightChild(newNode);

			newNode.setParent(y);
			newNode.setLeftChild(this.NIL);
			newNode.setRightChild(this.NIL);
			this.size++;
			RBInsertFixUp(newNode);
		} else
			throw new RuntimeErrorException(new Error());
	}

	@Override
	public boolean delete(T key) {
		if (key != null) {
			// check if key exist or not
			INode<T, V> z = this.root;
			boolean flag = false;
			while (!z.equals(this.NIL) /* && key != null */) {
				if (z.getKey().equals(key)) {
					flag = true;
					break;
				} else if (z.getKey().compareTo(key) > 0)
					z = z.getLeftChild();
				else
					z = z.getRightChild();
			}
			if (!flag)
				return false;
			// implement deletion
			INode<T, V> x = new RBTNode<>();
			INode<T, V> y = z;
			boolean originalColor = y.getColor();
			if (z.getLeftChild().equals(this.NIL)) {
				x = z.getRightChild();
				transplant(z, z.getRightChild());
			} else if (z.getRightChild().equals(this.NIL)) {
				x = z.getLeftChild();
				transplant(z, z.getLeftChild());
			} else {
				y = RBTMin(z.getRightChild());
				originalColor = y.getColor();
				x = y.getRightChild();
				if (y.getParent().equals(z))
					x.setParent(y);
				else {
					transplant(y, y.getRightChild());
					y.setRightChild(z.getRightChild());
					y.getRightChild().setParent(y);
				}
				transplant(z, y);
				y.setLeftChild(z.getLeftChild());
				y.getLeftChild().setParent(y);
				y.setColor(z.getColor());
			}
			if (originalColor == black)
				RBDeleteFixUp(x);

			this.size--;
			return true;
		} else
			throw new RuntimeErrorException(new Error());

	}

	/**
	 * right rotate of node assuming that left child not equal NIL run in O(1) Only
	 * pointers are changed by a rotation
	 * 
	 * @param x node to be modified
	 */
	private void rightRotate(INode<T, V> x) {
		INode<T, V> y = x.getLeftChild();
		if (!y.equals(this.NIL)) {
			x.setLeftChild(y.getRightChild());
			if (!y.getRightChild().equals(this.NIL))
				y.getRightChild().setParent(x);
			y.setRightChild(x);
			y.setParent(x.getParent());
			if (x.getParent().equals(this.NIL))
				this.root = y;
			else if (x.equals(x.getParent().getLeftChild()))
				x.getParent().setLeftChild(y);
			else
				x.getParent().setRightChild(y);
			x.setParent(y);

		}
	}

	/**
	 * left rotate of node assuming that right child not equal NIL run in O(1) Only
	 * pointers are changed by a rotation
	 * 
	 * @param x
	 */
	private void leftRotate(INode<T, V> x) {
		INode<T, V> y = x.getRightChild();
		if (!y.equals(this.NIL)) {
			x.setRightChild(y.getLeftChild());
			if (!y.getLeftChild().equals(this.NIL))
				y.getLeftChild().setParent(x);
			y.setLeftChild(x);
			y.setParent(x.getParent());
			if (x.getParent().equals(this.NIL))
				this.root = y;
			else if (x.getParent().getLeftChild().equals(x))
				x.getParent().setLeftChild(y);
			else
				x.getParent().setRightChild(y);
			x.setParent(y);
		}
	}

	/**
	 * if parent color of node ,that is inserted ,is red this violate property 4 so
	 * we need to fix it run in O()
	 * 
	 * @param node
	 */
	private void RBInsertFixUp(INode<T, V> node) {

		while (node.getParent().getColor() == this.red) {
			if (node.getParent().equals(node.getParent().getParent().getLeftChild())) {
				INode<T, V> uncle = node.getParent().getParent().getRightChild();
				// Case 1
				if (uncle.getColor() == this.red) {
					uncle.setColor(this.black);
					node.getParent().setColor(this.black);
					node.getParent().getParent().setColor(this.red);
					node = node.getParent().getParent();
				} else {
					// Case 2
					if (node.equals(node.getParent().getRightChild())) {
						node = node.getParent();
						leftRotate(node);
					}
					// Case 3
					node.getParent().setColor(this.black);
					node.getParent().getParent().setColor(this.red);
					rightRotate(node.getParent().getParent());
				}
			} else {
				INode<T, V> uncle = node.getParent().getParent().getLeftChild();
				// Case 1
				if (uncle.getColor() == this.red) {
					uncle.setColor(this.black);
					node.getParent().setColor(this.black);
					node.getParent().getParent().setColor(this.red);
					node = node.getParent().getParent();
				} else {
					// Case 2
					if (node.equals(node.getParent().getLeftChild())) {
						node = node.getParent();
						rightRotate(node);
					}
					// Case 3
					node.getParent().setColor(this.black);
					node.getParent().getParent().setColor(this.red);
					leftRotate(node.getParent().getParent());
				}
			}
		}

		this.root.setColor(this.black);
	}

	private void transplant(INode<T, V> x, INode<T, V> y) {
		if (x.getParent() == this.NIL)
			this.root = y;
		else if (x.equals(x.getParent().getLeftChild()))
			x.getParent().setLeftChild(y);
		else
			x.getParent().setRightChild(y);

		y.setParent(x.getParent());
	}

	private INode<T, V> RBTMin(INode<T, V> root) {
		INode<T, V> temp = root;
		while (!temp.getLeftChild().equals(this.NIL)) {
			temp = temp.getLeftChild();
		}
		return temp;
	}

	private void RBDeleteFixUp(INode<T, V> x) {
		while (!x.equals(this.root) && x.getColor() == black) {
			if (x.equals(x.getParent().getLeftChild())) {
				INode<T, V> w = x.getParent().getRightChild();
				if (w.getColor() == this.red) {
					w.setColor(this.black);
					x.getParent().setColor(this.red);
					leftRotate(x.getParent());
					w = x.getParent().getRightChild();
				}
				if (w.getLeftChild().getColor() == black && w.getRightChild().getColor() == black) {
					w.setColor(red);
					x = x.getParent();
				} else {
					if (w.getRightChild().getColor() == black) {
						w.getLeftChild().setColor(black);
						w.setColor(red);
						rightRotate(w);
						w = x.getParent().getRightChild();

					}
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(black);
					w.getRightChild().setColor(black);
					leftRotate(x.getParent());
					x = this.root;
				}
			} else {
				INode<T, V> w = x.getParent().getLeftChild();
				if (w.getColor() == this.red) {
					w.setColor(this.black);
					x.getParent().setColor(this.red);
					rightRotate(x.getParent());
					w = x.getParent().getLeftChild();
				}
				if (w.getRightChild().getColor() == black && w.getLeftChild().getColor() == black) {
					w.setColor(red);
					x = x.getParent();
				} else {
					if (w.getLeftChild().getColor() == black) {
						w.getRightChild().setColor(black);
						w.setColor(red);
						leftRotate(w);
						w = x.getParent().getLeftChild();

					}
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(black);
					w.getLeftChild().setColor(black);
					rightRotate(x.getParent());
					x = this.root;
				}
			}
		}

		x.setColor(black);
	}
}
