/**
 * 
 */
package eg.edu.alexu.csd.filestructure.redblacktree;

/**
 * @author mahmo
 *
 */
public class RBTNode<T extends Comparable<T>, V> implements INode<T, V> {

	private boolean color;
	private T key;
	private V value;
	private INode<T, V> left;
	private INode<T, V> right;
	private INode<T, V> parent;

	@Override
	public void setParent(INode<T, V> parent) {
		this.parent = parent;

	}

	@Override
	public INode<T, V> getParent() {
		// TODO Auto-generated method stub
		return this.parent;
	}

	@Override
	public void setLeftChild(INode<T, V> leftChild) {
		this.left = leftChild;

	}

	@Override
	public INode<T, V> getLeftChild() {
		// TODO Auto-generated method stub
		return this.left;
	}

	@Override
	public void setRightChild(INode<T, V> rightChild) {
		this.right = rightChild;

	}

	@Override
	public INode<T, V> getRightChild() {
		// TODO Auto-generated method stub
		return this.right;
	}

	@Override
	public T getKey() {
		// TODO Auto-generated method stub
		return this.key;
	}

	@Override
	public void setKey(T key) {
		this.key = key;

	}

	@Override
	public V getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public void setValue(V value) {
		this.value = value;

	}

	@Override
	public boolean getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}

	@Override
	public void setColor(boolean color) {
		// TODO Auto-generated method stub
		this.color = color;
	}

	@Override
	public boolean isNull() {
		if (this.key == null)
			return true;

		return false;
	}

}
